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
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.dao.impl.ShippingStatusDao;
import rozaryonov.delivery.entities.Invoice;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.exceptions.DaoException;

public class CreateInvoices implements ActionCommand {
	private Logger logger = LogManager.getLogger(DeliveryCost.class.getName());

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/view/prg.jsp";

		// login logic here
		//ServletContext ctx = request.getServletContext();
		String[] shippingIds = request.getParameterValues("shippingId");
		Connection cn = DeliveryConnectionPool.getConnection();
		ShippingDao shDao = new ShippingDao(cn);
		//ShippingDao shDao = (ShippingDao) ctx.getAttribute("shippingDao");
		Set<Shipping> setShippings = new HashSet<>();
		for (String str : shippingIds) {
			Shipping s = null;
			try {
				s = shDao.findById(Long.parseLong(str))
						.orElseThrow(() -> new DaoException("No Shipping while CreateInvoices cmd."));
			} catch (DaoException e) {
				logger.warn(e.getMessage());
			}
			setShippings.add(s);
		}
		// group shippings by Persons
		Map<Person, List<Shipping>> personIdShippingsMap = setShippings.stream()
				.collect(Collectors.groupingBy((Shipping se) -> se.getPerson()));
		// create invoices
		InvoiceStatusDao inStDao = new InvoiceStatusDao(cn);
		InvoiceDao invoiceDao = new InvoiceDao(cn);
		ShippingStatusDao shStDao = new ShippingStatusDao(cn);
		for (Map.Entry<Person, List<Shipping>> me : personIdShippingsMap.entrySet()) {
			Invoice inv = new Invoice();
			inv.setPerson(me.getKey());
			inv.setCreationDateTime(Timestamp.valueOf(LocalDateTime.now()));
			Set<Shipping> shippingSet = me.getValue().stream().collect(Collectors.toSet());
			BigDecimal sum = shippingSet.stream().map(x -> x.getFare()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
			inv.setSum(sum);
			inv.setInvoiceStatus(inStDao.findByName("waiting for pay")
					.orElseThrow(() -> new DaoException("No InvoiceStatus while CreateInvoices cmd"))); 
			for (Shipping sh : shippingSet) {
				sh.setShippingStatus(shStDao.findById(2L)
						.orElseThrow(() -> new DaoException ("No ShippingStatus while CreateInvoices cmd"))); 
			}
			inv.setShippings(shippingSet);
			// save each invoice
			try {
				//cn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				//cn.setAutoCommit(false);
				
				// update statuses of shippings
				for (Shipping shp : shippingSet) {
					shDao.save(shp);
				}
				// save invoice and it's shippings 
				invoiceDao.save(inv);
				
				//cn.commit();
				//cn.setAutoCommit(true);
				
			} catch (Exception e) {
				logger.warn(e.getMessage());
				
				try {
					cn.rollback();
				} catch (SQLException e1) {
					logger.warn(e1.getMessage());
				}
				
			}
		}
		try {
			cn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		HttpSession session = request.getSession(true);
		session.setAttribute("goTo", "/delivery/manager/create_invoices.jsp");
		session.setAttribute("message", "prg.invoiceOk");

		
		
		return redirection;
	}

}
