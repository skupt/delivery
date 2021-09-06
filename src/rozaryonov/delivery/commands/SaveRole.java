package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.RoleDao;
import rozaryonov.delivery.entities.Role;

public class SaveRole implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {
		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "NextPage.jsp";

		Role role = new Role();
		role.setId(Long.parseLong(request.getParameter("roleId")));
		role.setName(request.getParameter("roleName"));
		Connection cn = DeliveryConnectionPool.getConnection();
		RoleDao roleDao = new RoleDao(cn);
		roleDao.save(role);
		// ArrayList<Role> roles = (ArrayList<Role>) roleDao.findAll();
		// request.getSession().setAttribute("roleDao", roleDao);
		// roleDao.close();
		return redirection;
	}

}
