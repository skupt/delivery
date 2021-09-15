package rozaryonov.delivery.commands;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import rozaryonov.delivery.dao.DeliveryConnectionPool;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.dao.impl.SettlementTypeDao;
import rozaryonov.delivery.dao.impl.SettlementsDao;
import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.SettlementType;
import rozaryonov.delivery.entities.Settlements;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.PageableFactory;
import rozaryonov.delivery.repository.impl.SettlementsRepo;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.services.Page;
import rozaryonov.delivery.services.PasswordEncoder;

public class PaymentsInsert implements ActionCommand {

	@Override
	public String execute(HttpServletRequest request) {

		String redirection = null;
		redirection = request.getParameter("goTo");
		if (redirection == null)
			redirection = "manager/view/prg.jsp";


		HttpSession session = request.getSession(true);
		ServletContext ctx = session.getServletContext();
		PersonDao personDao = (PersonDao) ctx.getAttribute("personDao");
		SettlementsDao settlementsDao = (SettlementsDao) ctx.getAttribute("settlementsDao");
		SettlementTypeDao settlementTypeDao = (SettlementTypeDao) ctx.getAttribute("settlementTypeDao");
		// logic here
		// Params
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy K:mm a");
		LocalDateTime paymentDate = LocalDateTime.parse(request.getParameter("paymentDate"), 
				formatter);
		Person person = personDao.findById(Long.parseLong(request.getParameter("person"))).orElseThrow(()-> 
							new DaoException("No Person with id while PaymentInsert"));
		BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(request.getParameter("amount")));
		SettlementType settlementType = settlementTypeDao.findById(1L).orElseThrow(()->
							new DaoException("No SettlementType with while PaymentInsert"));;
		Settlements settlement = new Settlements();
		settlement.setCreationDatetime(paymentDate);
		settlement.setPerson(person);
		settlement.setSettlementType(settlementType);
		settlement.setAmount(amount);
		settlementsDao.save(settlement);
		session.setAttribute("goTo", "/delivery/manager/payments.jsp");
		session.setAttribute("message", "prg.paymentOk");

		return redirection;
	}
	
}
