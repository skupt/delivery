package rozaryonov.delivery.commands;

import javax.servlet.http.HttpServletRequest;

public class EmptyCmd implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		//Here  is empty logic
		request.setAttribute("errorDescription", "Command is not recognized");
		return "view/error.jsp";
	}

}
