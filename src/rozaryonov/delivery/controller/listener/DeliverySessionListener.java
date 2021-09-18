package rozaryonov.delivery.controller.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class DeliverySessionListener implements HttpSessionListener {
	public void sessionCreated(HttpSessionEvent e) {
		HttpSession session = e.getSession();
		session.setAttribute("locale", "ru_RU");
		System.out.println("DeliverySessionListener: session.setAttribute(locale, ru_RU)");
	}
	
	public void sessionDestroyed(HttpSessionEvent e) {
		
	}
}
