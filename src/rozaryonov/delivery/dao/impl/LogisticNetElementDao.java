package rozaryonov.delivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.entities.LogisticNetElement;
import rozaryonov.delivery.exceptions.DaoException;

public class LogisticNetElementDao {
	private static final String FIND_ALL = "select city_id, neighbor_id, distance, logistic_config_id from logistic_net where logistic_config_id=? order by city_id, neighbor_id;";
	private static Logger logger = LogManager.getLogger(LogisticNetElementDao.class.getName());
	private Connection connection;
	
	
	public LogisticNetElementDao (Connection connection) {
		this.connection = connection;
	}
	
	public Iterable<LogisticNetElement> findByNetConfig(long netConfigId) {
		ArrayList<LogisticNetElement> localities = new ArrayList<>();;
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			ps.setLong(1, netConfigId);
			ResultSet rs = ps.executeQuery();
			LocalityDao locDao = new LocalityDao(connection);
			while (rs.next()) {
				LogisticNetElement netElement = new LogisticNetElement();
				netElement.setCity(locDao.findById(rs.getLong(1)).orElseThrow(()-> 
					new DaoException("SQLException while LogisticNetElement findAll")));
				netElement.setNeighbor(locDao.findById(rs.getLong(2)).orElseThrow(()-> 
					new DaoException("SQLException while LogisticNetElement findAll")));
				netElement.setDistance(rs.getDouble(3));
				localities.add(netElement);
			}
		} catch (SQLException e) {
			logger.error("SQLException while LogisticNetElement findAll. ", e.getMessage());
			throw new DaoException("SQLException while LogisticNetElement deleteById.", e);
		}
		return localities;
	}
	
//	public void close() {
//		if (connection !=null) {
//			try {
//				connection.close();
//			} catch (SQLException e) {
//				logger.warn(e.getMessage());
//			}
//			connection=null;
//		}
//	}
}
