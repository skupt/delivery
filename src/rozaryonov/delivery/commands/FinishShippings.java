package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.controller.Controller;
import rozaryonov.delivery.dao.ConnectionFactory;
import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.dao.impl.ShippingStatusDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.entities.ShippingStatus;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.PageableFactory;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.services.Page;
import rozaryonov.delivery.services.PasswordEncoder;

public class FinishShippings implements ActionCommand {
	private static Logger logger = LogManager.getLogger(FinishShippings.class.getName());

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/view/prg.jsp";

		// login logic here
		// set session attributes
		HttpSession session = request.getSession(true);
		// ServletContext ctx = session.getServletContext();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy K:mm a");
		LocalDateTime unloadDate = LocalDateTime.parse(request.getParameter("unloadDate"), formatter);
		System.out.println("ParsedDate: " + unloadDate);
		Connection connection = ConnectionFactory.getConnection();
		ShippingDao shippingDao = new ShippingDao(connection);
		ShippingStatusDao shippingStatusDao = new ShippingStatusDao(connection);
		ShippingStatus statusDelivered = shippingStatusDao.findByName("delivered")
				.orElseThrow(() -> new DaoException("No ShippingsStstus while FinishShippings.execute()"));
		String[] shipIdsStr = request.getParameterValues("shippingId");

		try {
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			for (int i = 0; i < shipIdsStr.length; i++) {
				Long shId = Long.parseLong(shipIdsStr[i]);
				if (shId != null) {
					connection.setAutoCommit(false);
					Shipping shipping = shippingDao.findById(shId)
							.orElseThrow(() -> new DaoException("No Shippings for id while FinishShippings.execute()"));
					shipping.setUnloadingDatetime(Timestamp.valueOf(unloadDate));
					shipping.setShippingStatus(statusDelivered);
					shippingDao.save(shipping);
					connection.commit();
					connection.setAutoCommit(true);

				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
		}

		session.setAttribute("goTo", "/delivery/manager/finish_shippings.jsp");
		session.setAttribute("message", "prg.shippingsFinishedOk");

		return redirection;
	}

}
