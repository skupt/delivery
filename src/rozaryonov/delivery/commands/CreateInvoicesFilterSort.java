package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.util.Comparator;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.services.Pagination;

public class CreateInvoicesFilterSort implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/create_invoices.jsp";

		// login logic here
		HttpSession session = request.getSession(true);
		String filterStatus = request.getParameter("filterStatus");
		String sortDate = request.getParameter("sortDate");
		String sortPerson = request.getParameter("sortPerson");
		
		Predicate<Shipping> p = null;
		if (filterStatus!=null) {
			switch (filterStatus) {
			case "1" : p = e -> e.getShippingStatus().getId() == 1; break;
			case "2" : p = e -> e.getShippingStatus().getId() == 2; break;
			case "4" : p = e -> e.getShippingStatus().getId() == 4; break;
			case "5" : p = e -> e.getShippingStatus().getId() == 5; break;
			default : p = e -> true; break;
			}
		}
		
		Comparator<Shipping> cD = null;
		if (sortDate!=null) {
			switch (sortDate) {
			case "1" : cD = Comparator.comparing((Shipping s)-> s.getCreationTimestamp().toLocalDateTime()); break;
			case "2" : cD = Comparator.comparing((Shipping s)-> s.getCreationTimestamp().toLocalDateTime()).reversed(); break;
			default : cD = Comparator.comparing((Shipping s)-> s.getCreationTimestamp()); break;
			}
		}
		
		Comparator<Shipping> cP = null;
		if (sortDate!=null) {
			switch (sortDate) {
			case "1" : cP = Comparator.comparing((Shipping s)-> s.getPerson().getLogin()); break;
			case "2" : cP = Comparator.comparing((Shipping s)-> s.getPerson().getLogin()).reversed(); break;
			default : cP = Comparator.comparing((Shipping s)-> s.getPerson().getLogin()); break;
			}
		}
		
		Pagination<Shipping, ShippingDao> paginationShipping = (Pagination<Shipping, ShippingDao>) session.getAttribute("paginationShipping");
		if (p != null) paginationShipping.setPredicat(p);
		if (cD != null) paginationShipping.setComparator(cD);
		if (cP != null) paginationShipping.setComparator(cP);
		//Connection cn = DeliveryConnectionPool.getConnection();
		//ShippingDao shDao = new ShippingDao(cn);
		//paginationShipping.setDao(shDao);
		paginationShipping.init();
		session.removeAttribute("shippingList");
		session.setAttribute("shippingList", paginationShipping.nextPage());
		session.removeAttribute("paginationShipping");
		session.setAttribute("paginationShipping", paginationShipping);
		
		return redirection;
	}

}
