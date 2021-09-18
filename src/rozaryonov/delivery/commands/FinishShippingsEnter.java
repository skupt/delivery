package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.repository.PageableFactory;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.services.Page;
import rozaryonov.delivery.services.PasswordEncoder;

public class FinishShippingsEnter implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/finish_shippings.jsp";


		// login logic here
		// set session attributes
		HttpSession session = request.getSession(true);
		ServletContext ctx = session.getServletContext();

		PageableFactory pageableFactory = (PageableFactory) ctx.getAttribute("pageableFactory");

		Page<Shipping, ShippingRepo> pageShippingFinish = pageableFactory.getPageableForShippingFinishPage(6);
		session.setAttribute("pageShippingFinish", pageShippingFinish);
		List<Shipping> shippingListFinish = pageShippingFinish.nextPage(); 
		session.setAttribute("shippingListFinish", shippingListFinish);
		

		return redirection;
	}

}
