package rozaryonov.delivery.controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.commands.ActionCommand;
import rozaryonov.delivery.dao.ConnectionFactory;
import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.LocalityDao;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.dao.impl.PropertyDao;
import rozaryonov.delivery.dao.impl.SettlementTypeDao;
import rozaryonov.delivery.dao.impl.SettlementsDao;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.dao.impl.TariffDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.PageableFactory;
import rozaryonov.delivery.services.PathFinder;

@WebServlet( 
		value="/Controller",
		loadOnStartup = 1
		)
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = LogManager.getLogger(Controller.class.getName());
	
	public Controller() {
		super();
		System.out.println("Controller constructor");
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("Controller init() begin");
		ServletContext context = config.getServletContext();
		Connection connection = ConnectionFactory.getConnection();

		LocalityDao localityDao = new LocalityDao(DeliveryConnectionPool.getConnection());
		Iterable<Locality> localities = (Iterable<Locality>) localityDao.findAll();
		PageableFactory pageableFactory = new PageableFactory (connection);
		PropertyDao propertyDao = new PropertyDao(connection);
		long logisticConfigId = Long.parseLong(propertyDao.findById("currentLogisticConfigId")
				.orElseThrow(()->new DaoException("Can't find currentLogisticConfigId")).getValue());
		PathFinder pathFinder = new PathFinder(connection, logisticConfigId);
		long currentTariffId = Long.parseLong(propertyDao.findById("currentTariffId")
				.orElseThrow(()->new DaoException("Can't find current tariff id")).getValue());
		TariffDao tariffDao = new TariffDao(connection);
		PersonDao personDao = new PersonDao(connection);
		Iterable<Person> persons = personDao.findAll();
		SettlementsDao settlementsDao = new SettlementsDao (connection);
		SettlementTypeDao settlementTypeDao = new SettlementTypeDao (connection);
		ShippingDao shippingDao = new ShippingDao (connection);
		
		
		context.setAttribute("localities", localities);
		context.setAttribute("pageableFactory", pageableFactory);
		context.setAttribute("propertyDao", propertyDao);
		context.setAttribute("pathFinder", pathFinder);
		context.setAttribute("currentTariffId", currentTariffId);
		context.setAttribute("tariffDao", tariffDao);
		context.setAttribute("personDao", personDao);
		context.setAttribute("persons", persons);
		context.setAttribute("settlementsDao", settlementsDao);
		context.setAttribute("settlementTypeDao", settlementTypeDao);
		context.setAttribute("shippingDao", shippingDao);
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ActionFactory f = new ActionFactory();
		ActionCommand cmd = f.defineCommand(request);
		String page = cmd.execute(request);
		if (page != null) {
			if (page.toLowerCase().contains("view")) {
				response.sendRedirect(page);
			} else {
				request.getRequestDispatcher(page).forward(request, response);
			}
		} else {
			request.setAttribute("errorDescription", "Target page is null after command executing.");
			request.getRequestDispatcher("/delivery/view/error.jsp").forward(request, response);
		}
	}

}
