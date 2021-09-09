package rozaryonov.delivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.Invoice;
import rozaryonov.delivery.exceptions.DaoException;

public class InvoiceDao extends AbstractDao<Invoice, Long> {
	private static final String UPDATE = "update invoice set person_id=? creation_datetime=? sum=? invoice_status_id where id=?";
	private static final String INSERT = "insert into invoice (person_id, creation_datetime, sum, invoice_status_id) values (?,?,?,?)";
	private static final String FIND_BY_ID = "select id, person_id, creation_datetime, sum, invoice_status_id from invoice where id=?";
	private static final String FIND_ALL = "select id, person_id, creation_datetime, sum, invoice_status_id from invoice";
	private static final String DELETE = "delete from invoice where id=?";
	private static final String EXIST = "select id from invoice where id=?";
	private static Logger logger = LogManager.getLogger(InvoiceDao.class.getName());

	public InvoiceDao(Connection connection) {
		super(connection);
	}

	@Override
	public Invoice save(Invoice invoice) {
		if (invoice.getId() == 0) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
				ps.setLong(1, invoice.getPerson().getId());
				ps.setTimestamp(2, invoice.getCreationDateTime());
				ps.setBigDecimal(3, invoice.getSum());
				ps.setLong(4, invoice.getInvoiceStatus().getId());
				if (ps.executeUpdate() > 0) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						invoice.setId(rs.getLong(1));
					}
				}
				InvoiceHasShippingDao ihsDao = new InvoiceHasShippingDao(connection);
				System.out.println("Invoice.save(): invoiceId=" + invoice.getId()); //
				ihsDao.saveInvoiceShipppings(invoice.getId(), invoice.getShippings());
			} catch (SQLException e) {
				logger.error("SQLException while Invoice save. ", e.getMessage());
				throw new DaoException("SQLException while Invoice save.", e.getCause());
			}

		} else {
			try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
				ps.setLong(1, invoice.getPerson().getId());
				ps.setTimestamp(2, invoice.getCreationDateTime());
				ps.setBigDecimal(3, invoice.getSum());
				ps.setLong(4, invoice.getInvoiceStatus().getId());
				ps.setLong(5, invoice.getId());
			} catch (SQLException e) {
				logger.error("SQLException while Invoice save. ", e.getMessage());
				throw new DaoException("SQLException while Invoice save.", e);
			}

		}
		return invoice;
	}

	//	private static final String FIND_BY_ID = "select id, person_id, creation_datetime, sum, invoice_status_id from invoice where id=?";

	@Override
	public Optional<Invoice> findById(Long id) {
		Invoice invoice = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setLong(1, id);
			PersonDao pDao = new PersonDao(connection);
			InvoiceHasShippingDao sDao = new InvoiceHasShippingDao(connection);
			InvoiceStatusDao isDao = new InvoiceStatusDao(connection);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					invoice = new Invoice();
					invoice.setId(rs.getLong(1));
					invoice.setPerson(pDao.findById(rs.getLong(1)).orElseThrow(()->new DaoException("No Person while InvoiceDao findById")));
					invoice.setCreationDateTime((rs.getTimestamp(3)));
					invoice.setSum(rs.getBigDecimal(4));
					invoice.setInvoiceStatus(isDao.findById(rs.getLong(5)).orElseThrow(()->new DaoException("No InvoiceStatus while findById")));
					invoice.setShippings(sDao.findInvoiceShippings(id));
					}
				}
		} catch (SQLException e) {
			logger.error("SQLException while Invoice findById. ", e.getMessage());
			throw new DaoException("SQLException while Invoice find by id.", e);
		}

		return Optional.ofNullable(invoice);
	}

	@Override
	public boolean existsById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(EXIST);) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			logger.error("SQLException while Invoice save. ", e.getMessage());
			throw new DaoException("SQLException while Invoice find by id.", e);
		}
		return false;
	}

	//FIND_ALL = "select id, person_id, creation_datetime, sum, invoice_status_id from invoice";
	
	@Override
	public Iterable<Invoice> findAll() {
		ArrayList<Invoice> invoices = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			PersonDao pDao = new PersonDao(connection);
			InvoiceHasShippingDao sDao = new InvoiceHasShippingDao(connection);
			InvoiceStatusDao isDao = new InvoiceStatusDao(connection);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Invoice invoice = new Invoice();
				invoice = new Invoice();
				long id = rs.getLong(1);
				invoice.setId(id);
				invoice.setPerson(pDao.findById(rs.getLong(1)).orElseThrow(()->new DaoException("No Person while InvoiceDao findById")));
				invoice.setCreationDateTime((rs.getTimestamp(3)));
				invoice.setSum(rs.getBigDecimal(4));
				invoice.setInvoiceStatus(isDao.findById(rs.getLong(5)).orElseThrow(()->new DaoException("No InvoiceStatus while findById")));
				invoice.setShippings(sDao.findInvoiceShippings(id));
				invoices.add(invoice);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Invoice findAll. ", e.getMessage());
			throw new DaoException("SQLException while Invoice deleteById.", e);
		}
		return invoices;
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while Invoice deleteById. ", e.getMessage());
			throw new DaoException("SQLException while Invoice deleteById.", e);
		}
	}

	@Override
	public void close() {
		super.close();
	}

}
