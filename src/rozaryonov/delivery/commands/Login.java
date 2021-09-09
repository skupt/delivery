package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.services.PasswordEncoder;

public class Login implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "index.jsp";

		Connection cn = DeliveryConnectionPool.getConnection();

		// login logic here
		String login = request.getParameter("login");
		String pass = request.getParameter("pass");
		PersonDao pd = new PersonDao(DeliveryConnectionPool.getConnection());
		Person person = pd.findByLogin(login).orElse(null);
		if (person == null) {
			request.setAttribute("errorDescription", "pageLogin.loginAbsent");
			redirection = "login.jsp";
			return redirection;
		}
		String hashPassSaved = person.getPassword();
		String hashPassEntered = PasswordEncoder.getHash(pass);
		if (!hashPassSaved.equals(hashPassEntered)) {
			request.setAttribute("errorDescription", "pageLogin.passwodWrong");
			redirection = "login.jsp";
			return redirection;
		}
		// set session attribute Person and define auth_user or manager cabinet page
		HttpSession session = request.getSession(true);
		session.setAttribute("person", person);
		switch (person.getRole().getName()) {
		case "user":
			redirection = "auth_user/view/cabinet.jsp";
			break;
		case "manager":
			redirection = "manager/view/cabinet.jsp";
			break;
		default:
			redirection = "index.jsp";
			break;
		}

		return redirection;
	}

}
