package rozaryonov.delivery.commands;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.services.MessageManager;

public class SetLocale implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "index.jsp";

		// login logic here
		String lang = request.getParameter("lang");
		HttpSession session = request.getSession(true);
		//session.removeAttribute("locale");
		if (lang!=null) {
			switch (lang) {
			case "ru" : session.setAttribute("locale", "ru_RU"); break;
			case "en" : session.setAttribute("locale", "en_US"); break;
			default : session.setAttribute("locale", "en_US"); break;
			}
		}
		
		return redirection;
	}

}
