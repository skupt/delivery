package rozaryonov.delivery.commands;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.entities.Tariff;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.repository.impl.TariffRepo;
import rozaryonov.delivery.services.Page;

public class TariffArchivePrev implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "tariffs.jsp";

		// login logic here
		HttpSession session = request.getSession();
		Page<Tariff, TariffRepo> pageTariffArchive  = (Page<Tariff, TariffRepo>) session.getAttribute("pageTariffArchive");
		List<Tariff> tariffArchiveList = pageTariffArchive.prevPage();
		session.setAttribute("tariffArchiveList", tariffArchiveList);
		
	
		
		return redirection;
	}

}
