package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.entities.Settlements;
import rozaryonov.delivery.repository.impl.SettlementsRepo;
import rozaryonov.delivery.services.Page;

public class PaymentsNext implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/payments.jsp";

		Connection cn = DeliveryConnectionPool.getConnection();

		// login logic here
		// set session attributes
		HttpSession session = request.getSession(true);
		ServletContext ctx = session.getServletContext();

		Page<Settlements, SettlementsRepo> pageSettlementsAddPayment = (Page<Settlements, SettlementsRepo>) 
				session.getAttribute("pageSettlementsAddPayment");
		
		List<Settlements> settlementsList = pageSettlementsAddPayment.nextPage(); 
		session.setAttribute("settlementsList", settlementsList);
		

		return redirection;
	}

}
