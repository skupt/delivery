package rozaryonov.delivery.commands;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.InvoiceDao;
import rozaryonov.delivery.dao.impl.InvoiceStatusDao;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.dao.impl.SettlementTypeDao;
import rozaryonov.delivery.dao.impl.SettlementsDao;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.dao.impl.ShippingStatusDao;
import rozaryonov.delivery.entities.Invoice;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Settlements;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.entities.ShippingStatus;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.PageableFactory;
import rozaryonov.delivery.repository.impl.InvoiceRepo;
import rozaryonov.delivery.services.Page;

public class InvoiceUserPay implements ActionCommand {
	private Logger logger = LogManager.getLogger(InvoiceUserPay .class.getName());

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "auth_user/view/prg.jsp";

		// login logic here
		HttpSession session = request.getSession();
		Long invoiceId = Long.parseLong(request.getParameter("invoiceId"));
		Connection cn = DeliveryConnectionPool.getConnection();
		InvoiceDao invDao = new InvoiceDao(cn);
		PersonDao pDao = new PersonDao(cn);
		SettlementTypeDao stDao = new SettlementTypeDao(cn);
		SettlementsDao sDao = new SettlementsDao(cn);
		InvoiceStatusDao isDao = new InvoiceStatusDao(cn);
		ShippingStatusDao ssDao = new ShippingStatusDao(cn);
		Invoice i = invDao.findById(invoiceId).orElseThrow(() -> new DaoException("No Invoice while PayInvoice cmd."));
		// dangerous operation
		Person curPerson = (Person) request.getSession().getAttribute("person");
		//Person curPerson = i.getPerson();
		//System.out.println("PayInv. person: " + curPerson);
		BigDecimal balance = pDao.calcAndReplaceBalance(curPerson.getId());
		BigDecimal sum = i.getSum();
		// prepare money spending (settlement)
		Settlements paym = new Settlements();
		paym.setPerson(curPerson);
		paym.setCreationDatetime(LocalDateTime.now());
		paym.setAmount(sum);
		paym.setSettlementType(
				stDao.findById(2L).orElseThrow(() -> new DaoException("No SettlementType found for id=2")));
		// check balance
		if (balance.compareTo(sum) >= 0) {
			System.out.println("Pay invoice: balance >");
			// start transaction
			try {
			cn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
			cn.setAutoCommit(false);
			sDao.save(paym);
			//System.out.println("Pay invoice: payment saved");
			pDao.calcAndReplaceBalance(curPerson.getId());
			//System.out.println("Pay invoice: balance updated");
			i.setInvoiceStatus(
					isDao.findById(2L).orElseThrow(() -> new DaoException("No InvpoceStatus found for id=2")));
			ShippingStatus delivering  = ssDao.findById(4L).orElseThrow(()-> new DaoException("No ShippingStatus found"));
			for (Shipping shp : i.getShippings()) {
				shp.setShippingStatus(delivering);
			}
			//System.out.println("pay invoice: invoice before save: " + i);
			invDao.save(i);
			//System.out.println("Pay invoice: invoice statuse chenged to paid");
			cn.commit();
			System.out.println("Pay invoice: transaction committed");
			cn.setAutoCommit(true);
			// end transaction
			session.setAttribute("balance", pDao.calcAndReplaceBalance(curPerson.getId()));

			} catch (SQLException e) {
				logger.warn(e.getMessage());
				try {
					cn.rollback();
				} catch (SQLException e1) {
					logger.warn(e1.getMessage());
				}
			}

			ServletContext ctx = session.getServletContext();					
			PageableFactory pageableFactory = (PageableFactory) ctx.getAttribute("pageableFactory");
			Person person = (Person) session.getAttribute("person");
			Page<Invoice, InvoiceRepo> pageInvoicesOfUserToPay = pageableFactory.getPageableForUserSpendingPage(6, person);
			session.setAttribute("pageInvoicesOfUserToPay", pageInvoicesOfUserToPay);
			List<Invoice> invoiceList = pageInvoicesOfUserToPay.nextPage();
			session.setAttribute("invoiceList", invoiceList);

		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		session.setAttribute("goTo", "/delivery/auth_user/invoices_of_user.jsp");
		session.setAttribute("message", "prg.spendingOk");
		

		return redirection;
	}

}
