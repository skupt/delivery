package rozaryonov.delivery.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.services.Page;

public class FinishShippingsNext implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/finish_shippings.jsp";

		// login logic here
		HttpSession session = request.getSession();
		Page<Shipping, ShippingRepo> pageShippingFinish  = (Page<Shipping, ShippingRepo>) session.getAttribute("pageShippingFinish");
		
		List<Shipping> shippingListFinish = pageShippingFinish.nextPage();
		session.setAttribute("shippingListFinish", shippingListFinish);
		
		
		return redirection;
	}

}
