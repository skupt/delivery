package rozaryonov.delivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.exceptions.DaoException;

public class InvoiceHasShippingDao {

	private static final String INSERT = "insert into invoice_has_shipping (invoice_id, shipping_id, sum) values (?,?,?)";
	private static final String FIND_SHIPS_OF_INV = "select invoice_id, shipping_id, sum from invoice_has_shipping where invoice_id=?";
	private static final String DELETE_SHIPS_OF_INV = "delete from invoice_has_shipping where invoice_id=?";
	// ?
	private static final String EXIST_BY_IDS = "select invoice_id, shipping_id from invoice_has_shipping where invoice_id=? and shipping_id=?";
	private static final String DELETE_BY_IDS = "delete from invoice_has_shipping where invoice_id=? and shipping_id=?";

	private static Logger logger = LogManager.getLogger(InvoiceHasShippingDao.class.getName());

	private Connection connection;
	
	public InvoiceHasShippingDao(Connection connection) {
		this.connection=connection;
	}

	public void saveInvoiceShipppings(long invoiceId, Set<Shipping> shippings) {
		// delete existing if they are
		deleteInvoiceShippings(invoiceId);
		// insert rows into invoice_has_shippings
		for (Shipping sh : shippings) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT);) {
				ps.setLong(1, invoiceId);
				ps.setLong(2, sh.getId());
				ps.setBigDecimal(3, sh.getFare());
				ps.executeUpdate();
			} catch (SQLException e) {
				logger.error("SQLException while InvoiceHasShipping save. ", e.getMessage());
				throw new DaoException("SQLException while InvoiceHasShipping save.", e.getCause());
			}
		}
		//update shipping status
		ShippingDao shippingDao = new ShippingDao(connection);
		for (Shipping sh1 : shippings) {
			shippingDao.save(sh1);
		}

	}
	
//	private void deleteInvoiceShippings(long invoiceId) {
//		try (PreparedStatement ps = connection.prepareStatement(DELETE_SHIPS_OF_INV);) {
//			ps.setLong(1, invoiceId);
//			ps.executeUpdate();
//		} catch (SQLException e) {
//			logger.error("SQLException while InvoiceHasShipping deleteInvoiceShippings() ", e.getMessage());
//			throw new DaoException("SQLException while InvoiceHasShipping deleteInvoiceShippings()" + e.getMessage());
//		}
//	}
	
	public void deleteInvoiceShippings(long invoiceId) {
		Set<Shipping> setSh = findInvoiceShippings(invoiceId); // works
		for (Shipping s : setSh) {
			if (existsById(invoiceId, s.getId())) {
				deleteById(invoiceId, s.getId());
			}
		}
	}
	
	public void deleteById(long invoiceId, long shippingId) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE_BY_IDS);) {
			ps.setLong(1, invoiceId);
			ps.setLong(2, shippingId);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while Role deleteById. ", e.getMessage());
			throw new DaoException("SQLException while Role deleteById.", e);
		}
	}

	
	
	public Set<Shipping> findInvoiceShippings(long invoiceId) {
		Set<Shipping> invoiceShippings = new HashSet<>();
		try (PreparedStatement ps = connection.prepareStatement(FIND_SHIPS_OF_INV);) {
			ps.setLong(1, invoiceId);
			ShippingDao shippingDao = new ShippingDao(connection);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				try {
				Shipping shipping = shippingDao.findById(rs.getLong(2)).orElseThrow(()-> new DaoException("No shipping for id found"));
				invoiceShippings.add(shipping);
				} catch (DaoException e) {
					logger.warn(e.getMessage());
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while findInvoiceShippings. ", e.getMessage());
			throw new DaoException("SQLException while InvoiceHasShipping findInvoiceShippings", e);
		}
		return invoiceShippings;
	}


	
	public boolean existsById(long invoiceId, long shippingId) {
		try (PreparedStatement ps = connection.prepareStatement(EXIST_BY_IDS);) {
			ps.setLong(1, invoiceId);
			ps.setLong(2, shippingId);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			logger.error("SQLException while InvoiceHasShipping save. ", e.getMessage());
			throw new DaoException("SQLException while InvoiceHasShipping exist by id." + e.getMessage());
		}
		return false;
	}


}
