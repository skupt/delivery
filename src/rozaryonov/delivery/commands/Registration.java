package rozaryonov.delivery.commands;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.dao.impl.RoleDao;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Role;
import rozaryonov.delivery.entities.validators.PersonValidator;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.services.PasswordEncoder;

public class Registration implements ActionCommand {
	private Logger logger = LogManager.getLogger(DeliveryCost.class.getName());
	@Override
	public String execute(HttpServletRequest request) {
		
		String redirection=null;
		redirection=request.getParameter("goTo");
		if (redirection==null) redirection="auth_user/view/cabinet.jsp";
		
		HttpSession session = request.getSession(true);
		String login = request.getParameter("login");
		String password = request.getParameter("pass");
		String name = request.getParameter("name");
		Person person = new Person();
		person.setLogin(login);
		person.setPassword(password);
		person.setName(name);
		PersonValidator pv = new PersonValidator();
		Connection connection = DeliveryConnectionPool.getConnection();
		RoleDao roleDao = new RoleDao(connection);
		Role userRole = roleDao.findByName("user").orElseThrow(()->new DaoException("Role user is not found in Roles table."));
		person.setRole(userRole);
		
		PersonDao personDao = new PersonDao(connection);
		if (pv.validate(person)) {
			String passHash = PasswordEncoder.getHash(password);
			person.setPassword(passHash);
			try {
				personDao.save(person);
			}catch (DaoException e) {
				//user already exist maybe
				redirection="view/error.jsp";
				session.setAttribute("errorDescription", "An error ocured while saving your login. Try to use another one.");
			}
		}
		personDao.close();
		roleDao.close();
		session.setAttribute("person", person);
		
		return redirection;
	}
}
