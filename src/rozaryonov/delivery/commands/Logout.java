package rozaryonov.delivery.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class Logout implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "index.jsp";

		// login logic here
		HttpSession session = request.getSession(true);
		session.invalidate();
		
		return redirection;
	}

}
