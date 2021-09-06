package rozaryonov.delivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.Property;
import rozaryonov.delivery.entities.Role;
import rozaryonov.delivery.exceptions.DaoException;

public class PropertyDao extends AbstractDao<Property, String> {
	private static final String REPLACE = "replace into properties (id, value) values (?, ?)";
	private static final String FIND_BY_ID = "select id, value from properties where id=?";
	private static final String DELETE = "delete from table properties where id=?";
	private static Logger logger = LogManager.getLogger(PropertyDao.class.getName());

	public PropertyDao(Connection connection) {
		super(connection);
	}

	@Override
	public Property save(Property prop) {
		if (prop.getKey() != null) {
			try (PreparedStatement ps = connection.prepareStatement(REPLACE);) {
				ps.setString(1, prop.getKey());
				ps.setString(2, prop.getValue());
			} catch (SQLException e) {
				logger.error("SQLException while Property save. ", e.getMessage());
				throw new DaoException("SQLException while Property save.", e.getCause());
			}
		}
		return prop;
	}

	@Override
	public Optional<Property> findById(String key) {
		Property prop = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setString(1, key);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					String key2 = rs.getString(1);
					String value = rs.getString(2);
					if (key != null) {
						prop = new Property();
						prop.setKey(key2);
						prop.setValue(value);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while Property findById. ", e.getMessage());
			throw new DaoException("SQLException while Property findById.", e);
		}

		return Optional.ofNullable(prop);
	}

	@Override
	public boolean existsById(String id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Iterable<Property> findAll() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteById(String key) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setString(1, key);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while Property deleteById. ", e.getMessage());
			throw new DaoException("SQLException while Property deleteById.", e);
		}
	}
	@Override
	public void close() {
		super.close();
	}
}
