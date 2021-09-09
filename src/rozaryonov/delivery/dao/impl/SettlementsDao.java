package rozaryonov.delivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.Paginationable;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.Settlements;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.exceptions.DaoException;

public class SettlementsDao extends AbstractDao<Settlements, Long> implements Paginationable<Settlements> {
	private static final String UPDATE = "update settlements set creation_datetime=?, person_id=?, settlment_type_id=?, amount=? where id = ?";
	private static final String INSERT = "insert into settlements (datetime, person_id, settlment_type_id, amount) values (?,?,?,?)";
	private static final String FIND_BY_ID = "select id, datetime, person_id, settlment_type_id, amount from settlements where id=?";
	private static final String FIND_ALL = "select id, datetime, person_id, settlment_type_id, amount from settlements";
	private static final String DELETE = "delete from settlements where id=?";
	private static final String EXIST = "select id from settlements where id=?";
	private static final String PERIOD_PAGE = "select "
			+ "id, creation_datetime, person_id, settlment_type_id, amount "
			+ "from settlements "
			+ "where creation_datetime>=? and creation_datetime<? "
			+ "order by creation_datetime "
			+ "limit ? offset ?";

	private static final String ROW_AMOUNT = "select count(*) from settlements";

	
	
	private static Logger logger = LogManager.getLogger(SettlementsDao.class.getName());

	public SettlementsDao(Connection connection) {
		super(connection);
	}

	@Override
	public Settlements save(Settlements settlements) {
		if (settlements.getId() == 0) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
				//datetime, person_id, settlment_type_id, amount
				ps.setTimestamp(1, Timestamp.valueOf(settlements.getCreationDatetime()));
				ps.setLong(2, settlements.getPerson().getId());
				ps.setLong(3, settlements.getSettlementType().getId());
				ps.setBigDecimal(4, settlements.getAmount());
				if (ps.executeUpdate() > 0) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						settlements.setId(rs.getLong(1));
					}
				}
			} catch (SQLException e) {
				logger.error("SQLException while Settlements save. ", e.getMessage());
				throw new DaoException("SQLException while Settlements save.", e.getCause());
			}

		} else {
			try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
				//set creation_datetime=? person_id=? settlment_type_id=? amount=? where id = ?
				ps.setTimestamp(1, Timestamp.valueOf(settlements.getCreationDatetime()));
				ps.setLong(2, settlements.getPerson().getId());
				ps.setLong(3, settlements.getSettlementType().getId());
				ps.setBigDecimal(4, settlements.getAmount());
				ps.setLong(5, settlements.getId());
			} catch (SQLException e) {
				logger.error("SQLException while Settlements save. ", e.getMessage());
				throw new DaoException("SQLException while Settlements save.", e);
			}

		}
		return settlements;
	}

	@Override
	public Optional<Settlements> findById(Long id) {
		Settlements settlements = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setLong(1, id);
			PersonDao pDao = new PersonDao(super.connection);
			SettlementTypeDao stDao = new SettlementTypeDao(super.connection);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					//select id, datetime, person_id, settlment_type_id, amount from settlements where id=?
					settlements = new Settlements();
					settlements.setId(rs.getLong(1));
					settlements.setCreationDatetime(((Timestamp) rs.getTimestamp(2)).toLocalDateTime());
					settlements.setPerson(pDao.findById(rs.getLong(3)).orElseThrow(()-> new DaoException("No Person while SettlementsDao.findById")));
					settlements.setSettlementType(stDao.findById(rs.getLong(4)).orElseThrow(()-> new DaoException("No SetlementType while SettlementsDao.findById")));
					settlements.setAmount(rs.getBigDecimal(5));
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while Settlements findById. ", e.getMessage());
			throw new DaoException("SQLException while Settlements find by id.", e);
		}

		return Optional.ofNullable(settlements);
	}

	@Override
	public boolean existsById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(EXIST);) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				return true;
		} catch (SQLException e) {
			logger.error("SQLException while Settlements save. ", e.getMessage());
			throw new DaoException("SQLException while Settlements find by id.", e);
		}
		return false;
	}

	@Override
	public Iterable<Settlements> findAll() {
		ArrayList<Settlements> settlementss = new ArrayList<>();
		;
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			ResultSet rs = ps.executeQuery();
			PersonDao pDao = new PersonDao(super.connection);
			SettlementTypeDao stDao = new SettlementTypeDao(super.connection);
			while (rs.next()) {
				Settlements settlements = new Settlements();
				settlements.setId(rs.getLong(1));
				settlements.setCreationDatetime(((Timestamp) rs.getTimestamp(2)).toLocalDateTime());
				settlements.setPerson(pDao.findById(rs.getLong(3)).orElseThrow(()-> new DaoException("No Person while SettlementsDao.findById")));
				settlements.setSettlementType(stDao.findById(rs.getLong(4)).orElseThrow(()-> new DaoException("No SetlementType while SettlementsDao.findById")));
				settlements.setAmount(rs.getBigDecimal(5));
				settlementss.add(settlements);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Settlements findAll. ", e.getMessage());
			throw new DaoException("SQLException while Settlements deleteById.", e);
		}
		return settlementss;
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while Settlements deleteById. ", e.getMessage());
			throw new DaoException("SQLException while Settlements deleteById.", e);
		}
	}

	@Override
	public void close() {
		super.close();
	}

//	PERIOD_PAGE = "select "
//			+ "id, creation_datetime, person_id, settlment_type_id, amount "
//			+ "from settlements "
//			+ "where creation_datetime>=? and creation_datetime<? "
//			+ "order by creation_datetime "
//			+ "limit ? offset ?";
	
	@Override
	public List<Settlements> findAllInPeriod(Timestamp after, Timestamp before, int limit, int offset, 
			Comparator<Settlements> c, Predicate<Settlements> p) {
		ArrayList<Settlements> settlements = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(PERIOD_PAGE);) {
			ps.setTimestamp(1, after);
			ps.setTimestamp(2, before);
			ps.setInt(3, limit);
			ps.setInt(4, offset);
			
			PersonDao pDao = new PersonDao(connection);
			SettlementTypeDao stDao = new SettlementTypeDao(connection);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Settlements s = new Settlements();
				s.setId(rs.getLong(1));
				s.setCreationDatetime(rs.getTimestamp(2).toLocalDateTime());
				s.setPerson(pDao.findById(rs.getLong(3)).orElseThrow(()-> new DaoException("No Person while SettlementsDao.findAll")));
				s.setSettlementType(stDao.findById(rs.getLong(4)).orElseThrow(()-> new DaoException("No load Locality while SettlementsDao.findAllinPeriod")));
				s.setAmount(rs.getBigDecimal(5));
				
				settlements.add(s);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Settlements findAllInPeriod. ", e.getMessage());
			throw new DaoException("SQLException while Settlements findAllInPeriod .", e);
		}
		
		return settlements.stream().filter(p).sorted(c).collect(Collectors.toList());
	}

	@Override
	public long countRows() {
		long res=0;
		try (PreparedStatement ps = connection.prepareStatement(ROW_AMOUNT);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				res = rs.getLong(1);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Settlements countRow(). ", e.getMessage());
			throw new DaoException("SQLException while Settlements countRow().", e);
		}
		
		return res;
	}

}
