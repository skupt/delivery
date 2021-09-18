package rozaryonov.delivery.commands;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OrderUserResumeEnter implements ActionCommand {
	private Logger logger = LogManager.getLogger(DeliveryCost.class.getName());
	
	@Override
	public String execute (HttpServletRequest request) {
		
		String redirection=null;
		redirection=request.getParameter("goTo");
		if (redirection==null) redirection="auth_user/cabinet_resume_order.jsp";
		
		return redirection;
		}
}
