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

import rozaryonov.delivery.entities.InvoiceStatus;
import rozaryonov.delivery.exceptions.DaoException;

public class InvoiceStatusDao extends AbstractDao<InvoiceStatus, Long> {
	private static final String UPDATE = "update invoice_status set name=? where id = ?";
	private static final String INSERT = "insert into invoice_status (name) values (?)";
	private static final String FIND_BY_ID = "select id, name from invoice_status where id=?";
	private static final String FIND_BY_NAME = "select id, name from invoice_status where name=?";
	private static final String FIND_ALL = "select id, name from invoice_status";
	private static final String DELETE = "delete from invoice_status where id=?";
	private static final String EXIST = "select id from invoice_status where id=?";
	private static Logger logger = LogManager.getLogger(InvoiceStatusDao.class.getName());

	public InvoiceStatusDao(Connection connection) {
		super(connection);
	}

	@Override
	public InvoiceStatus save(InvoiceStatus invoiceStatus) {
		if (invoiceStatus.getId() == 0) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
				ps.setString(1, invoiceStatus.getName());
				if (ps.executeUpdate() > 0) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						invoiceStatus.setId(rs.getLong(1));
					}
				}
			} catch (SQLException e) {
				logger.error("SQLException while InvoiceStatus save. ", e.getMessage());
				throw new DaoException("SQLException while InvoiceStatus save.", e.getCause());
			}

		} else {
			try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
				ps.setString(1, invoiceStatus.getName());
				ps.setLong(2, invoiceStatus.getId());
			} catch (SQLException e) {
				logger.error("SQLException while InvoiceStatus save. ", e.getMessage());
				throw new DaoException("SQLException while InvoiceStatus save.", e);
			}

		}
		return invoiceStatus;
	}

	@Override
	public Optional<InvoiceStatus> findById(Long id) {
		InvoiceStatus invoiceStatus = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					long id2 = rs.getLong(1);
					String name = rs.getString(2);
					if (id != 0) {
						invoiceStatus = new InvoiceStatus();
						invoiceStatus.setId(id2);
						invoiceStatus.setName(name);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while InvoiceStatus findById. ", e.getMessage());
			throw new DaoException("SQLException while InvoiceStatus find by id.", e);
		}

		return Optional.ofNullable(invoiceStatus);
	}

	@Override
	public boolean existsById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(EXIST);) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			logger.error("SQLException while InvoiceStatus save. ", e.getMessage());
			throw new DaoException("SQLException while InvoiceStatus find by id.", e);
		}
		return false;
	}

	@Override
	public Iterable<InvoiceStatus> findAll() {
		ArrayList<InvoiceStatus> invoiceStatuss = new ArrayList<>();
		;
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				InvoiceStatus invoiceStatus = new InvoiceStatus();
				invoiceStatus.setId(rs.getLong(1));
				invoiceStatus.setName(rs.getString(2));
				invoiceStatuss.add(invoiceStatus);
			}
		} catch (SQLException e) {
			logger.error("SQLException while InvoiceStatus findAll. ", e.getMessage());
			throw new DaoException("SQLException while InvoiceStatus deleteById.", e);
		}
		return invoiceStatuss;
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while InvoiceStatus deleteById. ", e.getMessage());
			throw new DaoException("SQLException while InvoiceStatus deleteById.", e);
		}
	}

	@Override
	public void close() {
		super.close();
	}

	public Optional<InvoiceStatus> findByName(String invoiceStatusName) {
		InvoiceStatus invoiceStatus = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_NAME);) {
			ps.setString(1, invoiceStatusName);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					long id2 = rs.getLong(1);
					String name = rs.getString(2);
					invoiceStatus = new InvoiceStatus();
					invoiceStatus.setId(id2);
					invoiceStatus.setName(name);
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while InvoiceStatus findByName. ", e.getMessage());
			throw new DaoException("SQLException while InvoiceStatus find by id.", e);
		}

		return Optional.ofNullable(invoiceStatus);
	}

}
