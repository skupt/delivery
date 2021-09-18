package rozaryonov.delivery.repository.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.impl.InvoiceHasShippingDao;
import rozaryonov.delivery.dao.impl.InvoiceStatusDao;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.entities.Invoice;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.Pageable;

public class InvoiceRepo implements Pageable<Invoice> {
	private static final String FITER_BY_PERIOD = "select "
			+ "id, person_id, creation_datetime, sum, invoice_status_id "
			+ "from invoice "
			+ "where creation_datetime>=? and creation_datetime<? ";

	private Connection connection;
	private static Logger logger = LogManager.getLogger(InvoiceRepo.class.getName());

	public InvoiceRepo(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Invoice> findFilterSort(
			Timestamp after, 
			Timestamp before, 
			Predicate<Invoice> p,
			Comparator<Invoice> c) {
		List<Invoice> invoices = findAllInPeriod(after, before);

		return invoices.stream().filter(p).sorted(c).collect(Collectors.toList());
	}

	private List<Invoice> findAllInPeriod(Timestamp after, Timestamp before) {
		ArrayList<Invoice> invoices = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(FITER_BY_PERIOD);) {
			ps.setTimestamp(1, after);
			ps.setTimestamp(2, before);
			
			PersonDao pDao = new PersonDao(connection);
			InvoiceStatusDao isDao = new InvoiceStatusDao(connection);
			InvoiceHasShippingDao ihsDao = new InvoiceHasShippingDao(connection);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Invoice i = new Invoice();
				i.setId(rs.getLong(1));
				i.setPerson(pDao.findById(rs.getLong(2))
						.orElseThrow(() -> new DaoException("No Person while InvoiceRepo.findAllinPeriod")));
				i.setCreationDateTime(rs.getTimestamp(3));
				i.setSum(rs.getBigDecimal(4));
				i.setInvoiceStatus(isDao.findById(rs.getLong(5))
						.orElseThrow(()-> new DaoException("No Person while InvoiceRepo.findAllinPeriod")));
				i.setShippings(ihsDao.findInvoiceShippings(i.getId()));
				invoices.add(i);
			}
		} catch (SQLException e) {
			logger.error("SQLException while InvoiceRepo.findAllinPeriod. ", e.getMessage());
			throw new DaoException("SQLException while InvoiceRepo.findAllinPeriod .", e);
		}

		return invoices;

	}

}
