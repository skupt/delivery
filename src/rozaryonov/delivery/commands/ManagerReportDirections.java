package rozaryonov.delivery.commands;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.entities.Tariff;
import rozaryonov.delivery.repository.PageableFactory;
import rozaryonov.delivery.repository.impl.DayReportRepo;
import rozaryonov.delivery.repository.impl.DirectionReportRepo;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.repository.impl.TariffRepo;
import rozaryonov.delivery.repository.reportable.DayReport;
import rozaryonov.delivery.repository.reportable.DirectionReport;
import rozaryonov.delivery.services.Page;
import rozaryonov.delivery.services.PasswordEncoder;

public class ManagerReportDirections implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/report_direction.jsp";


		// login logic here
		// set session attributes
		HttpSession session = request.getSession(true);
		ServletContext ctx = session.getServletContext();

		PageableFactory pageableFactory = (PageableFactory) ctx.getAttribute("pageableFactory");

		Page<DirectionReport, DirectionReportRepo> pageReportDirection = pageableFactory.getPageableForManagerDirectionReport(1000);
		session.setAttribute("pageReportDirection", pageReportDirection);
		List<DirectionReport> reportDirectionList = pageReportDirection.nextPage(); 
		session.setAttribute("reportDirectionList", reportDirectionList);
		

		return redirection;
	}

}
