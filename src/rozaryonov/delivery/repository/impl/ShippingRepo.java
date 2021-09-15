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
import rozaryonov.delivery.exceptions.DaoException;
import rozaryonov.delivery.repository.Pageable;
import rozaryonov.delivery.services.Page;

public class ShippingRepo implements Pageable<Shipping> {

	private static final String FITER_BY_PERIOD = "select "
			+ "id, creation_timestamp, person_id, download_datetime, load_locality_id, "
			+ "shipper, download_address, consignee, unload_locality_id, "
			+ "unload_address, unloading_datetime, distance, weight, volume, "
			+ "fare, shipping_status_id "
			+ "from shipping "
			+ "where creation_timestamp>=? and creation_timestamp<? ";
	
	private Connection connection;
	private static Logger logger = LogManager.getLogger(ShippingRepo.class.getName());

	public ShippingRepo(Connection connection) {
		this.connection = connection;
	}
	
	
	@Override
	public List<Shipping> findFilterSort(Timestamp after, Timestamp before, Predicate<Shipping> p,
			Comparator<Shipping> c) {
		
		List <Shipping> shippings = findAllInPeriod(after, before);
		
		return shippings.stream().filter(p).sorted(c).collect(Collectors.toList());
	}
	
	private List<Shipping> findAllInPeriod(Timestamp after, Timestamp before) {
		ArrayList<Shipping> shippings = new ArrayList<>();
		try (PreparedStatement ps = connection.prepareStatement(FITER_BY_PERIOD);) {
			ps.setTimestamp(1, after);
			ps.setTimestamp(2, before);
			
			PersonDao pDao = new PersonDao(connection);
			LocalityDao lDao = new LocalityDao(connection);
			ShippingStatusDao ssDao = new ShippingStatusDao(connection);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Shipping s = new Shipping();
				s.setId(rs.getLong(1));
				s.setCreationTimestamp(rs.getTimestamp(2));
				s.setPerson(pDao.findById(rs.getLong(3)).orElseThrow(()-> new DaoException("No Person while ShippingDao.findAll")));
				s.setDownloadDatetime(rs.getTimestamp(4));
				s.setLoadLocality(lDao.findById(rs.getLong(5)).orElseThrow(()-> new DaoException("No load Locality while ShippingDao.findAll")));
				s.setShipper(rs.getString(6));
				s.setDownloadAddress(rs.getString(7));
				s.setConsignee(rs.getString(8));
				s.setUnloadLocality(lDao.findById(rs.getLong(9)).orElseThrow(()-> new DaoException("No unload Locality while ShippingDao.findAll")));
				s.setUnloadAddress(rs.getString(10));
				s.setUnloadingDatetime(rs.getTimestamp(11));
				s.setDistance(rs.getDouble(12));
				s.setWeight(rs.getDouble(13));
				s.setVolume(rs.getDouble(14));
				s.setFare(rs.getBigDecimal(15));
				s.setShippingStatus(ssDao.findById(rs.getLong(16)).orElseThrow(()->new DaoException("No ShippingStatus while ShippingDao.findAll")));
				
				shippings.add(s);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Shipping findAllInPeriod. ", e.getMessage());
			throw new DaoException("SQLException while findAllInPeriod deleteById.", e);
		}
		//for (Shipping s : shippings) System.out.println(s);
		return shippings;
	}

}
