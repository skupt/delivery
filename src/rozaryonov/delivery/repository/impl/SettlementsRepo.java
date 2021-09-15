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

import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.dao.impl.SettlementTypeDao;
import rozaryonov.delivery.entities.Settlements;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.Pageable;

public class SettlementsRepo implements Pageable<Settlements> {
	private static final String FITER_BY_PERIOD = "select "
			+ "id, creation_datetime, person_id, settlment_type_id, amount " + "from settlements "
			+ "where creation_datetime>=? and creation_datetime<? ";

	private Connection connection;
	private static Logger logger = LogManager.getLogger(SettlementsRepo.class.getName());

	public SettlementsRepo(Connection connection) {
		this.connection = connection;
	}

	@Override
	public List<Settlements> findFilterSort(Timestamp after, Timestamp before, Predicate<Settlements> p,
			Comparator<Settlements> c) {
		List<Settlements> settlements = findAllInPeriod(after, before);

		return settlements.stream().filter(p).sorted(c).collect(Collectors.toList());
	}

	private List<Settlements> findAllInPeriod(Timestamp after, Timestamp before) {
		ArrayList<Settlements> settlements = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(FITER_BY_PERIOD);) {
			ps.setTimestamp(1, after);
			ps.setTimestamp(2, before);
			PersonDao pDao = new PersonDao(connection);
			SettlementTypeDao stDao = new SettlementTypeDao(connection);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Settlements s = new Settlements();
				s.setId(rs.getLong(1));
				s.setCreationDatetime(rs.getTimestamp(2).toLocalDateTime());
				s.setPerson(pDao.findById(rs.getLong(3))
						.orElseThrow(() -> new DaoException("No Person while SettlementsDao.findAll")));
				s.setSettlementType(stDao.findById(rs.getLong(4))
						.orElseThrow(() -> new DaoException("No load Locality while SettlementsDao.findAllinPeriod")));
				s.setAmount(rs.getBigDecimal(5));

				settlements.add(s);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Settlements findAllInPeriod. ", e.getMessage());
			throw new DaoException("SQLException while Settlements findAllInPeriod .", e);
		}

		return settlements;

	}

}
