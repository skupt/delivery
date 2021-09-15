package rozaryonov.delivery.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.services.Page;

public class CreateInvoicesShippingNext implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/create_invoices.jsp";

		// login logic here
		HttpSession session = request.getSession();
		Page<Shipping, ShippingRepo> pagShipping  = (Page<Shipping, ShippingRepo>) session.getAttribute("pageShippingCreateInvoices");
		List<Shipping> shippingsList = pagShipping.nextPage();
		session.setAttribute("shippingList", shippingsList);
		
		
		return redirection;
	}

}
