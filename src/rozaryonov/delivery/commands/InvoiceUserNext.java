package rozaryonov.delivery.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.entities.Invoice;
import rozaryonov.delivery.repository.impl.InvoiceRepo;
import rozaryonov.delivery.services.Page;

public class InvoiceUserNext implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "auth_user/invoices_of_user.jsp";

		// login logic here
		// set session attributes
		HttpSession session = request.getSession(true);

		@SuppressWarnings("unchecked")
		Page<Invoice, InvoiceRepo> pageInvoicesOfUserToPay = (Page<Invoice, InvoiceRepo>) 
				session.getAttribute("pageInvoicesOfUserToPay");
		
		List<Invoice> invoiceList = pageInvoicesOfUserToPay.nextPage(); 
		session.setAttribute("invoiceList", invoiceList);
		

		return redirection;
	}

}
