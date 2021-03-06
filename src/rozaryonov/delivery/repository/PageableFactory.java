package rozaryonov.delivery.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.Invoice;
import rozaryonov.delivery.entities.Person;
import rozaryonov.delivery.entities.Settlements;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.entities.Tariff;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.impl.DayReportRepo;
import rozaryonov.delivery.repository.impl.DirectionReportRepo;
import rozaryonov.delivery.repository.impl.InvoiceRepo;
import rozaryonov.delivery.repository.impl.SettlementsRepo;
import rozaryonov.delivery.repository.impl.ShippingRepo;
import rozaryonov.delivery.repository.impl.TariffRepo;
import rozaryonov.delivery.repository.reportable.DayReport;
import rozaryonov.delivery.repository.reportable.DirectionReport;
import rozaryonov.delivery.services.Page;

public class PageableFactory {
	private static Logger logger = LogManager.getLogger(PageableFactory.class.getName());
	private Connection connection;
	
	public PageableFactory () {};
	
	public PageableFactory (Connection connection) {
		this.connection = connection;
	}
	
	public Page<Shipping, ShippingRepo> getPageableForInvoiceCreationPage(int rowsOnPage) {
		 ShippingRepo repo = new ShippingRepo(connection);
		 Page<Shipping, ShippingRepo> page = new Page<>(repo, Comparator.comparing((Shipping s) -> s.getCreationTimestamp()));
		 page.setPredicat(e->e.getShippingStatus().getName().equals("just created"));
		 page.init();

		 return page;
	}
	
	public Page<Shipping, ShippingRepo> getPageableForShippingFinishPage(int rowsOnPage) {
		 ShippingRepo repo = new ShippingRepo(connection);
		 Page<Shipping, ShippingRepo> page = new Page<>(repo, Comparator.comparing((Shipping s) -> s.getCreationTimestamp()));
		 page.setPredicat(e->e.getShippingStatus().getName().equals("delivering"));
		 page.init();

		 return page;
	}
	public Page<Settlements, SettlementsRepo> getPageableForManagerPaymentsPage (int rowsOnPage) {
		 SettlementsRepo repo = new SettlementsRepo(connection);
		 Page<Settlements, SettlementsRepo> page = new Page<>(repo, Comparator.comparing((Settlements s) -> s.getCreationDatetime()));
		 page.setPredicat(e->e.getSettlementType().getName().equals("payment"));
		 page.init();

		 return page;
	}

	public Page<Invoice, InvoiceRepo> getPageableForUserSpendingPage (int rowsOnPage, Person person) {
		 InvoiceRepo repo = new InvoiceRepo(connection);
		 Page<Invoice, InvoiceRepo> page = new Page(repo, Comparator.comparing((Invoice s) -> s.getCreationDateTime()));
		 page.setPredicat((Invoice e)-> (e.getInvoiceStatus().getId()==1)&&(e.getPerson().getId()==person.getId()));
		 page.init();

		 return page;
	}
	/**
	 * 
	 * @param rowsOnPage - number of rows per each page
	 * @param c = Comparator for Tariff
	 * @param p = Predicate for Tariff
	 * @return Page <T, R extends Pageable<T>>
	 */
	public Page<Tariff, TariffRepo> getPageableForTariffArchive (int rowsOnPage, Comparator<Tariff> c, Predicate<Tariff> p) {
		 TariffRepo repo = new TariffRepo(connection);
		 Page<Tariff, TariffRepo> page = null;
		 if (c == null) {
			 page = new Page<>(repo, Comparator.comparing((Tariff s) -> s.getCreationTimestamp()));
		 } else {
			 page = new Page<>(repo, c);
		 }
		 if (p == null) {
			 page.setPredicat((Tariff e)-> true);
		 } else {
			 page.setPredicat(p);
		 }
		 page.init();

		 return page;
	}
	
	public Page<DayReport, DayReportRepo> getPageableForManagerDayReport (int rowsOnPage) {
		 DayReportRepo repo = new DayReportRepo(connection);
		 Page<DayReport, DayReportRepo> page = new Page<>(repo, Comparator.comparing((DayReport r) -> r.getIndex()));
		 //page.setPredicat((Invoice e)-> (e.getInvoiceStatus().getId()==1)&&(e.getPerson().getId()==person.getId()));
		 page.init();

		 return page;
	}

	public Page<DirectionReport, DirectionReportRepo> getPageableForManagerDirectionReport (int rowsOnPage) {
		 DirectionReportRepo repo = new DirectionReportRepo(connection);
		 Page<DirectionReport, DirectionReportRepo> page = new Page<>(repo, Comparator.comparing((DirectionReport r) -> r.getIndex()));
		 //page.setPredicat((Invoice e)-> (e.getInvoiceStatus().getId()==1)&&(e.getPerson().getId()==person.getId()));
		 page.init();

		 return page;
	}


	
	
	public void close() {
		try {
			if (connection != null)
			connection.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			throw new DaoException ("SQL Exception in PageableFactory while close()" + e.getMessage());
		}
	}
	
	


}
