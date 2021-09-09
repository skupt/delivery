package rozaryonov.delivery.controller;

import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.Comparator;

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

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.dao.impl.SettlementsDao;
import rozaryonov.delivery.dao.impl.ShippingDao;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Settlements;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.services.Pagination;

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
			// set manager attributes
			// Attr "paginationShipping" = pagShipping (manager pagination for create invoices (predicate: shipping_statis== "just_created"
			Pagination<Shipping, ShippingDao> pagShipping = (Pagination <Shipping, ShippingDao>) session.getAttribute("paginationShipping");
			if (pagShipping == null) {
				Connection cn = DeliveryConnectionPool.getConnection();
				ShippingDao shDao = new ShippingDao(cn);
				pagShipping = new Pagination<>();
				pagShipping.setComparator(Comparator.comparing((Shipping s)-> s.getCreationTimestamp()));
				pagShipping.setPredicat((e)-> e.getShippingStatus().getId()==1L);
				pagShipping.setDao(shDao);
				pagShipping.init();
				session.setAttribute("paginationShipping", pagShipping);
			}
			
			// Attr "paginationSettlements" = pagSettlements (manager pagination for input payments from clients
			Pagination<Settlements, SettlementsDao> pagSettlements = (Pagination <Settlements, SettlementsDao>) session.getAttribute("paginationSettlements");
			if (pagSettlements == null) {
				Connection cn = DeliveryConnectionPool.getConnection();
				SettlementsDao stDao = new SettlementsDao(cn);
				pagSettlements = new Pagination<>();
				pagSettlements.setComparator(Comparator.comparing((Settlements st)-> st.getCreationDatetime()));
				pagSettlements.setPredicat((e1)-> e1.getSettlementType().getId()==1L);
				pagSettlements.setDao(stDao);
				pagSettlements.init();
				session.setAttribute("paginationSettlements", pagSettlements);
			}

			// Attr "persons" = persons (manager pagination for input payments from clients
			Iterable<Person> persons = (Iterable<Person>) session.getAttribute("persons");
			if (persons == null) {
				Connection cn = DeliveryConnectionPool.getConnection();
				PersonDao pnDao = new PersonDao(cn);
				persons = pnDao.findAll();
				session.setAttribute("persons", persons);
			}
			// CurrentDate
			session.setAttribute("date", LocalDate.now());

			
			
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		 roleName = fConfig.getInitParameter("role");
	}

}
