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
import rozaryonov.delivery.services.Page;

public class TariffRepo implements Pageable<Tariff> {

	private static final String FITER_BY_PERIOD  = "select"
			+ " id, creation_timestamp, logistic_config_id, truck_velocity, density, paperwork,"
			+ " targeted_receipt, targeted_delivery, shipping_rate, insurance_worth, insurance_rate"
			+ " from tariff"
			+ " where creation_timestamp>=? and creation_timestamp<? ";

	
	private Connection connection;
	private static Logger logger = LogManager.getLogger(TariffRepo.class.getName());

	public TariffRepo(Connection connection) {
		this.connection = connection;
	}
	
	
	@Override
	public List<Tariff> findFilterSort(Timestamp after, Timestamp before, Predicate<Tariff> p,
			Comparator<Tariff> c) {
		
		List <Tariff> tariffs = findAllInPeriod(after, before);
		
		return tariffs.stream().filter(p).sorted(c).collect(Collectors.toList());
	}
	
	private List<Tariff> findAllInPeriod(Timestamp after, Timestamp before) {
		ArrayList<Tariff> tariffs = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(FITER_BY_PERIOD);) {
			ps.setTimestamp(1, after);
			ps.setTimestamp(2, before);
			
			// " id, creation_timestamp, logistic_config_id, truck_velocity, density, paperwork,"
			// " targeted_receipt, targeted_delivery, shipping_rate, insurance_worth, insurance_rate"

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tariff t = new Tariff();
				t.setId(rs.getLong(1));
				t.setCreationTimestamp(rs.getTimestamp(2));
				t.setLogisticConfigId(rs.getLong(3));
				t.setTruckVelocity(rs.getInt(4));
				t.setDensity(rs.getDouble(5));
				t.setPaperwork(rs.getDouble(6));
				t.setTargetedReceipt(rs.getInt(7));
				t.setTargetedDelivery(rs.getInt(8));
				t.setShippingRate(rs.getDouble(9));
				t.setInsuranceWorth(rs.getDouble(10));
				t.setInsuranceRate(rs.getDouble(11));
				tariffs.add(t);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Shipping findAllInPeriod. ", e.getMessage());
			throw new DaoException("SQLException while findAllInPeriod deleteById.", e);
		}
		return tariffs;
	}

}
