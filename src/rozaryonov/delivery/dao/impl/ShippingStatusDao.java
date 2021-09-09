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

import rozaryonov.delivery.entities.ShippingStatus;
import rozaryonov.delivery.exceptions.DaoException;

public class ShippingStatusDao extends AbstractDao<ShippingStatus, Long> {
	private static final String UPDATE = "update shipping_status set name=? where id = ?";
	private static final String INSERT = "insert into shipping_status (name) values (?)";
	private static final String FIND_BY_ID = "select id, name from shipping_status where id=?";
	private static final String FIND_BY_NAME = "select id, name from shipping_status where name=?";
	private static final String FIND_ALL = "select id, name from shipping_status";
	private static final String DELETE = "delete from shipping_status where id=?";
	private static final String EXIST = "select id from shipping_status where id=?";
	private static Logger logger = LogManager.getLogger(ShippingStatusDao.class.getName());

	public ShippingStatusDao(Connection connection) {
		super(connection);
	}

	@Override
	public ShippingStatus save(ShippingStatus shipping_status) {
		if (shipping_status.getId() == 0) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
				ps.setString(1, shipping_status.getName());
				if (ps.executeUpdate() > 0) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						shipping_status.setId(rs.getLong(1));
					}
				}
			} catch (SQLException e) {
				logger.error("SQLException while ShippingStatus save. ", e.getMessage());
				throw new DaoException("SQLException while ShippingStatus save.", e.getCause());
			}

		} else {
			try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
				ps.setString(1, shipping_status.getName());
				ps.setLong(2, shipping_status.getId());
			} catch (SQLException e) {
				logger.error("SQLException while ShippingStatus save. ", e.getMessage());
				throw new DaoException("SQLException while ShippingStatus save.", e);
			}

		}
		return shipping_status;
	}

	@Override
	public Optional<ShippingStatus> findById(Long id) {
		ShippingStatus shipping_status = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					long id2 = rs.getLong(1);
					String name = rs.getString(2);
					if (id != 0) {
						shipping_status = new ShippingStatus();
						shipping_status.setId(id2);
						shipping_status.setName(name);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while ShippingStatus findById. ", e.getMessage());
			throw new DaoException("SQLException while ShippingStatus find by id.", e);
		}

		return Optional.ofNullable(shipping_status);
	}

	@Override
	public boolean existsById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(EXIST);) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			logger.error("SQLException while ShippingStatus save. ", e.getMessage());
			throw new DaoException("SQLException while ShippingStatus find by id.", e);
		}
		return false;
	}

	@Override
	public Iterable<ShippingStatus> findAll() {
		ArrayList<ShippingStatus> shipping_statuss = new ArrayList<>();
		;
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ShippingStatus shipping_status = new ShippingStatus();
				shipping_status.setId(rs.getLong(1));
				shipping_status.setName(rs.getString(2));
				shipping_statuss.add(shipping_status);
			}
		} catch (SQLException e) {
			logger.error("SQLException while ShippingStatus findAll. ", e.getMessage());
			throw new DaoException("SQLException while ShippingStatus deleteById.", e);
		}
		return shipping_statuss;
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while ShippingStatus deleteById. ", e.getMessage());
			throw new DaoException("SQLException while ShippingStatus deleteById.", e);
		}
	}

	@Override
	public void close() {
		super.close();
	}

	public Optional<ShippingStatus> findByName(String shipping_statusName) {
		ShippingStatus shipping_status = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_NAME);) {
			ps.setString(1, shipping_statusName);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					long id2 = rs.getLong(1);
					String name = rs.getString(2);
					shipping_status = new ShippingStatus();
					shipping_status.setId(id2);
					shipping_status.setName(name);
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while ShippingStatus findByName. ", e.getMessage());
			throw new DaoException("SQLException while ShippingStatus find by id.", e);
		}

		return Optional.ofNullable(shipping_status);
	}

}
