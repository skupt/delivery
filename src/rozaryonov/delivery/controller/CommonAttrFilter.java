package rozaryonov.delivery.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Comparator;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.LocalityDao;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.services.Pagination;

@WebFilter(
		filterName = "LocalitiesAttrFilter", 
		description = "set attr locality where it is necessary", 
		urlPatterns = { "/login.jsp", "/costs.jsp" }
		)
public class CommonAttrFilter implements Filter {
	private static Logger logger = LogManager.getLogger();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		logger.info("Requested Resource: "+uri);
		
		HttpSession session = req.getSession(true);

		Iterable<Locality> localities = null;
		localities = (Iterable<Locality>) session.getAttribute("localities");
		if (localities == null) {
			LocalityDao localityDao = new LocalityDao(DeliveryConnectionPool.getConnection());
			localities = (Iterable<Locality>) localityDao.findAll();
			session.setAttribute("localities", localities);
		}
		
		
		chain.doFilter(request, response);
	}


}
