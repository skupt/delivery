package rozaryonov.delivery.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.Person;

@WebFilter(filterName = "AuthManagerFilter", description = "Provide access to user's resources only for authorized users", urlPatterns = {
		"/manager/*" }, initParams = {
				@WebInitParam(name = "role", value = "manager", description = "role name for manager filtering") })
public class ManagerFilter implements Filter {
	private String roleName;
	private static Logger logger = LogManager.getLogger();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		logger.info("Requested Resource::" + uri);

		HttpSession session = req.getSession(true);
		Person person = (Person) session.getAttribute("person");
		String requestRoleName =  null;
		if (person!=null) requestRoleName = person.getRole().getName();

		if (session == null || !roleName.equalsIgnoreCase(requestRoleName)) {
			req.setAttribute("errorDescription", "Unauthorized access request to authorized user's resource area");
			req.getRequestDispatcher("/view/error.jsp").forward(req, res);
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		 roleName = fConfig.getInitParameter("role");
	}

}
