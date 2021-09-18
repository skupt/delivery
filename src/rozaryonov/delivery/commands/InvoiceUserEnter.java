package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.InvoiceStatusDao;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.entities.Invoice;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.repository.PageableFactory;
import rozaryonov.delivery.repository.impl.InvoiceRepo;
import rozaryonov.delivery.services.Page;

public class InvoiceUserEnter implements ActionCommand {
	private Logger logger = LogManager.getLogger(InvoiceUserEnter .class.getName());

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "auth_user/invoices_of_user.jsp";

		// login logic here
		HttpSession session = request.getSession();
		ServletContext ctx = session.getServletContext();					
		PageableFactory pageableFactory = (PageableFactory) ctx.getAttribute("pageableFactory");
		Person person = (Person) session.getAttribute("person");

		Page<Invoice, InvoiceRepo> pageInvoicesOfUserToPay = pageableFactory.getPageableForUserSpendingPage(6, person);
		session.setAttribute("pageInvoicesOfUserToPay", pageInvoicesOfUserToPay);

		List<Invoice> invoiceList = pageInvoicesOfUserToPay.nextPage();
		session.setAttribute("invoiceList", invoiceList);

		Connection cn = DeliveryConnectionPool.getConnection();

		PersonDao pDao = new PersonDao(cn);
		InvoiceStatusDao isDao = new InvoiceStatusDao(cn);
		Person curPerson = (Person) request.getSession().getAttribute("person");

		session.setAttribute("balance", pDao.calcAndReplaceBalance(curPerson.getId()));



		return redirection;
	}

}
