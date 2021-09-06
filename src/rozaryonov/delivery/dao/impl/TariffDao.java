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

import rozaryonov.delivery.entities.Role;
import rozaryonov.delivery.entities.Tariff;
import rozaryonov.delivery.exceptions.DaoException;

public class TariffDao extends AbstractDao<Tariff, Long> {
	// creation_timestamp logistic_config_id truck_velocity density paperwork
	// targeted_receipt targeted_delivery shipping_rate insurance_worth
	// insurance_rate
	private static final String UPDATE = "update tariff set logistic_config_id=? truck_velocity=? density=? paperwork=? targeted_receipt=? targeted_delivery=? shipping_rate=? insurance_worth=? insurance_rate where id = ?";
	private static final String INSERT = "insert into tariff (logistic_config_id, truck_velocity, density, paperwork, targeted_receipt, targeted_delivery, shipping_rate, insurance_worth, insurance_rate) values (?,?,?,?,?,?,?,?,?)";
	private static final String FIND_BY_ID = "select id, creation_timestamp, logistic_config_id, truck_velocity, density, paperwork, targeted_receipt, targeted_delivery, shipping_rate, insurance_worth, insurance_rate from tariff where id=?";
	private static final String FIND_ALL = "select id, creation_timestamp, logistic_config_id, truck_velocity, density, paperwork, targeted_receipt, targeted_delivery, shipping_rate, insurance_worth, insurance_rate from tariff";
	private static final String DELETE = "delete from table tariff where id=?";
	private static final String EXIST = "select id from tariff where id=?";
	private static Logger logger = LogManager.getLogger(TariffDao.class.getName());

	public TariffDao(Connection connection) {
		super(connection);
	}

	@Override
	public Tariff save(Tariff tariff) {
		if (tariff.getId() == 0) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
// logistic_config_id truck_velocity density paperwork targeted_receipt targeted_delivery shipping_rate insurance_worth insurance_rate 
				ps.setLong(1, tariff.getLogisticConfigId());
				ps.setInt(2, tariff.getTruckVelocity());
				ps.setDouble(3, tariff.getDensity());
				ps.setDouble(4, tariff.getPaperwork());
				ps.setInt(5, tariff.getTargetedReceipt());
				ps.setInt(6, tariff.getTargetedDelivery());
				ps.setDouble(7, tariff.getShippingRate());
				ps.setDouble(8, tariff.getInsuranceWorth());
				ps.setDouble(9, tariff.getInsuranceRate());
				if (ps.executeUpdate() > 0) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						tariff.setId(rs.getLong(1));
					}
				}
			} catch (SQLException e) {
				logger.error("SQLException while Tariff save. ", e.getMessage());
				throw new DaoException("SQLException while Role save.", e.getCause());
			}

		} else {
			try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
				ps.setLong(1, tariff.getLogisticConfigId());
				ps.setInt(2, tariff.getTruckVelocity());
				ps.setDouble(3, tariff.getDensity());
				ps.setDouble(4, tariff.getPaperwork());
				ps.setInt(5, tariff.getTargetedReceipt());
				ps.setInt(6, tariff.getTargetedDelivery());
				ps.setDouble(7, tariff.getShippingRate());
				ps.setDouble(8, tariff.getInsuranceWorth());
				ps.setDouble(9, tariff.getInsuranceRate());
				ps.setLong(10, tariff.getId());
			} catch (SQLException e) {
				logger.error("SQLException while Role save. ", e.getMessage());
				throw new DaoException("SQLException while Tariff save.", e);
			}

		}
		return tariff;
	}

	@Override
	public Optional<Tariff> findById(Long id) {
		Tariff tariff = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setLong(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
// id, creation_timestamp, logistic_config_id, truck_velocity, density, paperwork, targeted_receipt, targeted_delivery, shipping_rate, insurance_worth, insurance_rate					
					tariff = new Tariff();
					tariff.setId(rs.getLong(1));
					tariff.setCreationTimestamp(rs.getTimestamp(2));
					tariff.setLogisticConfigId(rs.getLong(3));
					tariff.setTruckVelocity(rs.getInt(4));
					tariff.setDensity(rs.getDouble(5));
					tariff.setPaperwork(rs.getDouble(6));
					tariff.setTargetedReceipt(rs.getInt(7));
					tariff.setTargetedDelivery(rs.getInt(8));
					tariff.setShippingRate(rs.getDouble(9));
					tariff.setInsuranceWorth(rs.getDouble(10));
					tariff.setInsuranceRate(rs.getDouble(11));
				}
			}
		} catch (SQLException e) {
			logger.error("SQLException while Tariff save. ", e.getMessage());
			throw new DaoException("SQLException while Tariff find by id.", e);
		}
		return Optional.ofNullable(tariff);
	}

	@Override
	public boolean existsById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(EXIST);) {
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) return true;
		} catch (SQLException e) {
			logger.error("SQLException while Tariff save. ", e.getMessage());
			throw new DaoException("SQLException while Tariff find by id.", e);
		}
		return false;
	}

	@Override
	public Iterable<Tariff> findAll() {
		ArrayList<Tariff> tariffs = new ArrayList<>();;
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Tariff tariff = new Tariff();
				tariff.setId(rs.getLong(1));
				tariff.setCreationTimestamp(rs.getTimestamp(2));
				tariff.setLogisticConfigId(rs.getLong(3));
				tariff.setTruckVelocity(rs.getInt(4));
				tariff.setDensity(rs.getDouble(5));
				tariff.setPaperwork(rs.getDouble(6));
				tariff.setTargetedReceipt(rs.getInt(7));
				tariff.setTargetedDelivery(rs.getInt(8));
				tariff.setShippingRate(rs.getDouble(9));
				tariff.setInsuranceWorth(rs.getDouble(10));
				tariff.setInsuranceRate(rs.getDouble(11));
				
				tariffs.add(tariff);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Tariff findAll. ", e.getMessage());
			throw new DaoException("SQLException while Tariff deleteById.", e);
		}
		return tariffs;
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while Tariff deleteById. ", e.getMessage());
			throw new DaoException("SQLException while Tariff deleteById.", e);
		}
	}
	@Override
	public void close() {
		super.close();
	}

}
