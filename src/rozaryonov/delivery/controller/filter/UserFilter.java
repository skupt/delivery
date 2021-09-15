package rozaryonov.delivery.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.Person;

@WebFilter(filterName = "AuthUserFilter", description = "Provide access to user's resources only for authorized users", urlPatterns = {
		"/auth_user/*" }, initParams = {
				@WebInitParam(name = "role", value = "user", description = "role name for filtering") })
public class UserFilter implements Filter {
	private String roleName;
	// private ServletContext context;
	private static Logger logger = LogManager.getLogger();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String uri = req.getRequestURI();
		logger.info("Requested Resource::" + uri);

		HttpSession session = req.getSession(true);
		Person person = (Person) session.getAttribute("person");
		String requestRoleName = null;
		if (person != null)
			requestRoleName = person.getRole().getName();

		if (session == null || !roleName.equalsIgnoreCase(requestRoleName)) {
			logger.info("Requested Resource::" + uri);
			req.setAttribute("errorDescription", "Unauthorized access request to authorized user's resource area");
			req.getRequestDispatcher("/view/error.jsp").forward(req, res);
		} else {
			// Attr "paginationInvoice" = pagInvoice (manager pagination for input payments
			// from clients
			/*
			Pagination<Invoice, InvoiceDao> pagInvoice = (Pagination<Invoice, InvoiceDao>) session
					.getAttribute("paginationInvoiceUser");
			Connection cn = DeliveryConnectionPool.getConnection();
			InvoiceDao stDao = new InvoiceDao(cn);
			pagInvoice = new Pagination<>();
			pagInvoice.setComparator(Comparator.comparing((Invoice st) -> st.getCreationDateTime()));
//			pagInvoice.setPredicat(
//					(e1) -> (e1.getInvoiceStatus().getId() == 1L) & (e1.getPerson().getId() == person.getId()));
			pagInvoice.setDao(stDao);
			pagInvoice.init();
			session.setAttribute("paginationInvoiceUser", pagInvoice);
			// CurrentDate
			session.setAttribute("date", LocalDateTime.now());
			// Balance
			cn = DeliveryConnectionPool.getConnection();
			PersonDao pDao = new PersonDao(cn);
			session.setAttribute("balance", pDao.calcAndReplaceBalance(person.getId()));
			pDao.close();
			*/
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		roleName = fConfig.getInitParameter("role");
	}

}
