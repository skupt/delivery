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

import rozaryonov.delivery.entities.Locality;
import rozaryonov.delivery.exceptions.DaoException;

public class LocalityDao extends AbstractDao<Locality, Long> {
	private static final String UPDATE = "update locality set name=? where id = ?";
	private static final String INSERT = "insert into locality (name) values (?)";
	private static final String FIND_BY_ID = "select id, name from locality where id=?";
	private static final String FIND_ALL = "select id, name from locality";
	private static final String DELETE = "delete from table locality where id=?";
	private static final String EXIST = "select id from locality where id=?";
	private static Logger logger = LogManager.getLogger(LocalityDao.class.getName());
	
	public LocalityDao(Connection connection) {
		super(connection);
	}
	
	@Override
	public Locality save(Locality locality) {
		if (locality.getId() == 0) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
				ps.setString(1, locality.getName());
				if (ps.executeUpdate() > 0) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						locality.setId(rs.getLong(1));
					}
				}
			} catch (SQLException e) {
				logger.error("SQLException while Locality save. ", e.getMessage());
				throw new DaoException("SQLException while Locality save.", e.getCause());
			}
			
		} else {
			try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
				ps.setString(1, locality.getName());
				ps.setLong(2, locality.getId());
			} catch (SQLException e) {
				logger.error("SQLException while Locality save. ", e.getMessage());
				throw new DaoException("SQLException while Locality save.", e);
			}
			
		}
		return locality;
	}

	@Override
	public Optional<Locality> findById(Long id) {
		Locality locality = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					long id2 = rs.getLong(1);
					String name = rs.getString(2);
					if (id != 0) {
						locality = new Locality();
						locality.setId(id2);
						locality.setName(name);
					}
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while Locality save. ", e.getMessage());
			throw new DaoException("SQLException while Locality find by id.", e);
		}

		return Optional.ofNullable(locality);
	}

	@Override
	public boolean existsById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(EXIST);) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return true;
		} catch (SQLException e) {
			logger.error("SQLException while Locality save. ", e.getMessage());
			throw new DaoException("SQLException while Locality find by id.", e);
		}
		return false;
	}

	@Override
	public Iterable<Locality> findAll() {
		ArrayList<Locality> localities = new ArrayList<>();;
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Locality locality = new Locality();
				locality.setId(rs.getLong(1));
				locality.setName(rs.getString(2));
				localities.add(locality);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Locality findAll. ", e.getMessage());
			throw new DaoException("SQLException while Locality deleteById.", e);
		}
		return localities;
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while Locality deleteById. ", e.getMessage());
			throw new DaoException("SQLException while Locality deleteById.", e);
		}
	}
	@Override
	public void close() {
		super.close();
	}
}
