package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.services.MessageManager;
import rozaryonov.delivery.services.Pagination;

public class CreateInvoicesShippingNext implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/create_invoices.jsp";

		// login logic here
		HttpSession session = request.getSession();
		Pagination<Shipping, ShippingDao> pagShipping  = (Pagination<Shipping, ShippingDao>) session.getAttribute("paginationShipping");
		List<Shipping> shippingsList = pagShipping.nextPage();
		//session.removeAttribute("hippingList");
		session.setAttribute("shippingList", shippingsList);
		
		
		return redirection;
	}

}
