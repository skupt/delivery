package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.entities.Tariff;
import rozaryonov.delivery.repository.PageableFactory;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.repository.impl.TariffRepo;
import rozaryonov.delivery.services.Page;
import rozaryonov.delivery.services.PasswordEncoder;

public class TariffArchiveApply implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "tariffs.jsp";


		// login logic here
		// set session attributes
		HttpSession session = request.getSession(true);
		ServletContext ctx = session.getServletContext();
		
		String sort = request.getParameter("sorting");
		int filter = Integer.parseInt(request.getParameter("logConf"));
		// comparator creation
		Comparator<Tariff> c = null;
		switch (sort) {
		case "incr" : c = Comparator.comparing((Tariff t) -> t.getCreationTimestamp()); break;
		case "decr" : c = Comparator.comparing((Tariff t) -> t.getCreationTimestamp()).reversed(); break;
		default : c = Comparator.comparing((Tariff t) -> t.getCreationTimestamp()); break;
		}
		//Predicetecreation
		Predicate<Tariff> p = (Tariff t)-> t.getLogisticConfigId()==filter;

		PageableFactory pageableFactory = (PageableFactory) ctx.getAttribute("pageableFactory");

		Page<Tariff, TariffRepo> pageTariffArchive = pageableFactory.getPageableForTariffArchive(6, c, p);
		session.setAttribute("pageTariffArchive", pageTariffArchive);
		List<Tariff> tariffArchiveList = pageTariffArchive.nextPage(); 
		session.setAttribute("tariffArchiveList", tariffArchiveList);
		

		return redirection;
	}

}
