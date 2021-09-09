package rozaryonov.delivery.commands;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.DateTimeException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.ConnectionFactory;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.dao.impl.ShippingStatusDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.entities.ShippingStatus;
import rozaryonov.delivery.exceptions.DaoException;

public class ResumeOrder implements ActionCommand {
	private Logger logger = LogManager.getLogger(DeliveryCost.class.getName());
	
	@Override
	public String execute (HttpServletRequest request) {
		
		String redirection=null;
		redirection=request.getParameter("goTo");
		if (redirection==null) redirection="auth_user/view/cabinet.jsp";
		
		HttpSession session = request.getSession(true);
		// 1(ShippingStatus) session.getAttribute("shippingStatus")
		//long personId = ((Person) session.getAttribute("person")).getId();
		int year = Integer.parseInt(request.getParameter("year"));
		int month = Integer.parseInt(request.getParameter("month"));
		int day = Integer.parseInt(request.getParameter("day"));
		int hour = Integer.parseInt(request.getParameter("hour"));
		int minute = Integer.parseInt(request.getParameter("minute"));
		//2-12
		//long loadLocalitiId = ((Locality) session.getAttribute("loadLocality")).getId();
		String shipper = request.getParameter("shipper");
		String downloadAddress = request.getParameter("downloadAddress");
		//long unloadLocalitiId = ((Locality) session.getAttribute("unloadLocality")).getId();
		String consignee = request.getParameter("consignee");
		String unloadAddress = request.getParameter("unloadAddress");
		double distance = (Double) session.getAttribute("distanceD");
		double weight = (Double) session.getAttribute("weightD");
		double volume = (Double) session.getAttribute("volumeD");
		BigDecimal fare = BigDecimal.valueOf((Double) session.getAttribute("totalD"));
		long shippingStatusId = 1;
		LocalDateTime dateTime =null;
		try {
		dateTime = LocalDateTime.of(year, month, day, hour, minute);
		} catch (DateTimeException e) {
			logger.warn("DateTimeException while ResumeOrder command.");
			redirection = "auth_user/cabinet_resume_order.jsp";
			request.setAttribute("errorDescription", "Date or time is wrong. Please Check.");
			session.setAttribute("shipper", shipper);
			session.setAttribute("downloadAddress", downloadAddress);
			session.setAttribute("consignee", consignee);
			session.setAttribute("unloadAddress", unloadAddress);
			
			return redirection;
		}
		// 13
		Timestamp creationTs = Timestamp.valueOf(LocalDateTime.now());
		Timestamp downloadTs = Timestamp.valueOf(dateTime);

		Connection cn = ConnectionFactory.getConnection();
		ShippingStatusDao ssDao = new ShippingStatusDao(cn);
		Shipping shipping = new Shipping.Builder()
				//.withPersonId(personId)
				.withPerson((Person) session.getAttribute("person"))
				.withCreationTimestamp(creationTs)
				//.withLoadLocalityId(loadLocalitiId)
				.withLoadLocality((Locality) session.getAttribute("loadLocality"))
				.withShipper(shipper)
				.withDownLoadDateTime(downloadTs)
				.withdownloadAddress(downloadAddress)
				//.withUnloadLocalityId(unloadLocalitiId)
				.withUnloadLocality((Locality) session.getAttribute("unloadLocality"))
				.withConsignee(consignee)
				.withUnloadAddress(unloadAddress)
				.withDistance(distance)
				.withWeight(weight)
				.withVolume(volume)
				.withFare(fare)
				//.withShippingStatusId(shippingStatusId)
				.withShippingStatus((ShippingStatus) ssDao.findById(shippingStatusId)
						.orElseThrow(()-> new DaoException("No sippingStatus while ResumeOrder command.")))
				.build();
		
		ShippingDao shippingDao = new ShippingDao(cn);
		shippingDao.save(shipping);
		
		return redirection;
		}
}
