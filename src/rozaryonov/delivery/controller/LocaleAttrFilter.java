package rozaryonov.delivery.controller;

import java.io.IOException;

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
import rozaryonov.delivery.entities.Locality;

@WebFilter(
		filterName = "LocaleAttrFilter", 
		description = "set attr locale where it is necessary", 
		urlPatterns = { "/costs.jsp", "/register.jsp", "/login.jsp" }
		)
public class LocaleAttrFilter implements Filter {
	private static Logger logger = LogManager.getLogger();

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();
		logger.info("Requested Resource: "+uri);
		
		HttpSession session = req.getSession(true);
		String locale = (String) session.getAttribute("locale");
		if (locale == null) {
			session.setAttribute("locale", "ru_RU");
		}
			chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
