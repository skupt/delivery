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

import rozaryonov.delivery.dao.ConnectionFactory;
import rozaryonov.delivery.dao.impl.LocalityDao;
import rozaryonov.delivery.dao.impl.PersonDao;
import rozaryonov.delivery.dao.impl.ShippingStatusDao;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.entities.Tariff;
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.Pageable;
import rozaryonov.delivery.repository.reportable.DirectionReport;
import rozaryonov.delivery.services.Page;

public class DirectionReportRepo implements Pageable<DirectionReport> {

	private static final String FITER_BY_PERIOD  = 
			"select concat_ws(\" -> \", dep.name, arr.name) as Direction, sum(a.fare) as Turnover\n" + 
			"from shipping a,\n" + 
			"	locality dep,\n" + 
			"    locality arr\n" + 
			"where a.load_locality_id = dep.id\n" + 
			"and a.unload_locality_id = arr.id\n" + 
			"group by Direction;";

	
	private Connection connection;
	private static Logger logger = LogManager.getLogger(DirectionReportRepo.class.getName());

	public DirectionReportRepo(Connection connection) {
		this.connection = connection;
	}
	
	
	@Override
	public List<DirectionReport> findFilterSort(Timestamp after, Timestamp before, Predicate<DirectionReport> p,
			Comparator<DirectionReport> c) {
		
		List <DirectionReport> tariffs = findAllInPeriod(after, before);
		
		return tariffs.stream().filter(p).sorted(c).collect(Collectors.toList());
	}
	
	private List<DirectionReport> findAllInPeriod(Timestamp after, Timestamp before) {
		ArrayList<DirectionReport> reportRows = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(FITER_BY_PERIOD);) {
			//ps.setTimestamp(1, after);
			//ps.setTimestamp(2, before);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				DirectionReport r = new DirectionReport();
				r.setIndex(rs.getString(1));
				r.setValue(Double.parseDouble(rs.getString(2)));
				reportRows.add(r);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Shipping findAllInPeriod. ", e.getMessage());
			throw new DaoException("SQLException while findAllInPeriod deleteById.", e);
		}
		return reportRows;
	}

}
