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

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.SettlementType;
import rozaryonov.delivery.exceptions.DaoException;

public class SettlementTypeDao extends AbstractDao<SettlementType, Long> {
	private static final String UPDATE = "update settlement_type set name=? vector=? where id = ?";
	private static final String INSERT = "insert into settlement_type (name, vector) values (?,?)";
	private static final String FIND_BY_ID = "select id, name, vector from settlement_type where id=?";
	private static final String FIND_BY_NAME = "select id, name,vector from settlement_type where name=?";
	private static final String FIND_ALL = "select id, name, vector from settlement_type";
	private static final String DELETE = "delete from settlement_type where id=?";
	private static final String EXIST = "select id from settlement_type where id=?";
	private static Logger logger = LogManager.getLogger(SettlementTypeDao.class.getName());

	public SettlementTypeDao(Connection connection) {
		super(connection);
	}

	@Override
	public SettlementType save(SettlementType settlementType) {
		if (settlementType.getId() == 0) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
				ps.setString(1, settlementType.getName());
				ps.setInt(2, settlementType.getVector());
				if (ps.executeUpdate() > 0) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						settlementType.setId(rs.getLong(1));
					}
				}
			} catch (SQLException e) {
				logger.error("SQLException while SettlementType save. ", e.getMessage());
				throw new DaoException("SQLException while SettlementType save.", e.getCause());
			}

		} else {
			try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
				ps.setString(1, settlementType.getName());
				ps.setInt(2, settlementType.getVector());
				ps.setLong(3, settlementType.getId());
			} catch (SQLException e) {
				logger.error("SQLException while SettlementType save. ", e.getMessage());
				throw new DaoException("SQLException while SettlementType save.", e);
			}

		}
		return settlementType;
	}

	@Override
	public Optional<SettlementType> findById(Long id) {
		SettlementType settlementType = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					long id2 = rs.getLong(1);
					String name = rs.getString(2);
					if (id != 0) {
						settlementType = new SettlementType();
						settlementType.setId(id2);
						settlementType.setName(name);
						settlementType.setVector(rs.getInt(3));
					}
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while SettlementType findById. ", e.getMessage());
			throw new DaoException("SQLException while SettlementType find by id.", e);
		}

		return Optional.ofNullable(settlementType);
	}

	@Override
	public boolean existsById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(EXIST);) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			logger.error("SQLException while SettlementType save. ", e.getMessage());
			throw new DaoException("SQLException while SettlementType find by id.", e);
		}
		return false;
	}

	@Override
	public Iterable<SettlementType> findAll() {
		ArrayList<SettlementType> settlementTypes = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				SettlementType settlementType = new SettlementType();
				settlementType.setId(rs.getLong(1));
				settlementType.setName(rs.getString(2));
				settlementType.setVector(rs.getInt(3));
				settlementTypes.add(settlementType);
			}
		} catch (SQLException e) {
			logger.error("SQLException while SettlementType findAll. ", e.getMessage());
			throw new DaoException("SQLException while SettlementType deleteById.", e);
		}
		return settlementTypes;
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while SettlementType deleteById. ", e.getMessage());
			throw new DaoException("SQLException while SettlementType deleteById.", e);
		}
	}

	@Override
	public void close() {
		super.close();
	}

	public Optional<SettlementType> findByName(String settlementTypeName) {
		SettlementType settlementType = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_NAME);) {
			ps.setString(1, settlementTypeName);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					long id2 = rs.getLong(1);
					String name = rs.getString(2);
					int vector = rs.getInt(3);
					settlementType = new SettlementType();
					settlementType.setId(id2);
					settlementType.setName(name);
					settlementType.setVector(vector);
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while SettlementType findByName. ", e.getMessage());
			throw new DaoException("SQLException while SettlementType find by id.", e);
		}

		return Optional.ofNullable(settlementType);
	}

}
