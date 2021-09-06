package rozaryonov.delivery.controller;

import javax.servlet.http.HttpServletRequest;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.commands.ActionCommand;
import rozaryonov.delivery.commands.EmptyCmd;

public class ActionFactory {
	//static Logger logger = LogManager.getLogger();

	@SuppressWarnings("unchecked")
	public <T extends ActionCommand> T defineCommand(HttpServletRequest request) {
		ActionCommand cmd = null;
		String cmdName = request.getParameter("cmd");
		Class<T> cl = null;
		if (cmdName != null) {
			try {
				cl = (Class<T>) Class.forName("rozaryonov.delivery.commands." + cmdName);
				if (cl != null) cmd = cl.newInstance();
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				//logger.warn("Exception while defineCommand. ", e);
			}
		}
		if (cmd == null)
			cmd = new EmptyCmd();
		return (T) cmd;
	}
}