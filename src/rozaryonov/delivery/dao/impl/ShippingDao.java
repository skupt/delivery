package rozaryonov.delivery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
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
import rozaryonov.delivery.entities.Role;
import rozaryonov.delivery.entities.Shipping;
import rozaryonov.delivery.exceptions.DaoException;

public class ShippingDao extends AbstractDao<Shipping, Long> implements Paginationable<Shipping> {

//	private static final String UPDATE = "update `shipping` set `person_id`=? `download_datetime`=? "
//										+ "`load_locality_id`=? `shipper`=? `download_address`=? `consignee`=? "
//										+ "`unload_locality_id`=? `unload_address`=? `unloading_datetime`=? `distance`=? "
//										+ "`weight`=? `volume`=? `fare`=? `shipping_status_id`=? "
//										+ "where `id`=?";
	private static final String UPDATE = "update shipping set person_id=?, download_datetime=?, "
			+ "load_locality_id=?, shipper=?, download_address=?, consignee=?, "
			+ "unload_locality_id=?, unload_address=?, unloading_datetime=?, distance=?, "
			+ "weight=?, volume=?, fare=?, shipping_status_id=? "
			+ "where id=?";
	private static final String INSERT = "insert into shipping ("
										+ "creation_timestamp, person_id, download_datetime, load_locality_id, "
										+ "shipper, download_address, consignee, unload_locality_id, "
										+ "unload_address, distance, weight, volume, "
										+ "fare, shipping_status_id) "
										+ "values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String FIND_BY_ID = "select "
										+ "id, creation_timestamp, person_id, download_datetime, load_locality_id, "
										+ "shipper, download_address, consignee, unload_locality_id, "
										+ "unload_address, unloading_datetime, distance, weight, volume, "
										+ "fare, shipping_status_id "
										+ "from shipping where id=?";
	private static final String FIND_ALL = "select "
										+ "id, creation_timestamp, person_id, download_datetime, load_locality_id, "
										+ "shipper, download_address, consignee, unload_locality_id, "
										+ "unload_address, unloading_datetime, distance, weight, volume, "
										+ "fare, shipping_status_id "
										+ "from shipping";
	private static final String DELETE = "delete from shipping where id=?";
	private static final String EXIST = "select id from shipping where id=?";
	private static final String FILTER_USER_DATE = "select "
			+ "id, creation_timestamp, person_id, download_datetime, load_locality_id, "
			+ "shipper, download_address, consignee, unload_locality_id, "
			+ "unload_address, unloading_datetime, distance, weight, volume, "
			+ "fare, shipping_status_id "
			+ "from shipping "
			+ "where creation_timestamp>=? and creation_timestamp<? "
			+ "and person_id=?";
	private static final String FILTER_DATE = "select "
			+ "id, creation_timestamp, person_id, download_datetime, load_locality_id, "
			+ "shipper, download_address, consignee, unload_locality_id, "
			+ "unload_address, unloading_datetime, distance, weight, volume, "
			+ "fare, shipping_status_id "
			+ "from shipping "
			+ "where creation_timestamp>=? and creation_timestamp<? ";

	private static final String PERIOD_PAGE = "select "
			+ "id, creation_timestamp, person_id, download_datetime, load_locality_id, "
			+ "shipper, download_address, consignee, unload_locality_id, "
			+ "unload_address, unloading_datetime, distance, weight, volume, "
			+ "fare, shipping_status_id "
			+ "from shipping "
			+ "where creation_timestamp>=? and creation_timestamp<? "
			+ "order by creation_timestamp "
			+ "limit ? offset ?";

	private static final String ROW_AMOUNT = "select count(*) from shipping";

	
	private static Logger logger = LogManager.getLogger(ShippingDao.class.getName());


// id creation_timestamp person_id download_datetime load_locality_id shipper download_address 
// consignee unload_locality_id unload_address unloading_datetime distance weight volume fare shipping_status_id
	
	public ShippingDao(Connection connection) {
		super(connection);
	}

	@Override
	public Shipping save(Shipping s) {
		if (s.getId() == 0) {
			try (PreparedStatement ps = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);) {
				
				ps.setTimestamp(1, s.getCreationTimestamp());
				//ps.setLong(2, s.getPersonId());
				ps.setLong(2, s.getPerson().getId());
				ps.setTimestamp(3, s.getDownloadDatetime());
				//ps.setLong(4, s.getLoadLocalityId());
				ps.setLong(4, s.getLoadLocality().getId());
				ps.setString(5, s.getShipper());
				ps.setString(6, s.getDownloadAddress());
				ps.setString(7, s.getConsignee());
				//ps.setLong(8, s.getUnloadLocalityId());
				ps.setLong(8, s.getUnloadLocality().getId());
				ps.setString(9, s.getUnloadAddress());
				ps.setDouble(10, s.getDistance());
				ps.setDouble(11, s.getWeight());
				ps.setDouble(12, s.getVolume());
				ps.setBigDecimal(13, s.getFare());
				//ps.setLong(14, s.getShippingStatusId());
				ps.setLong(14, s.getShippingStatus().getId());
				
				if (ps.executeUpdate() > 0) {
					ResultSet rs = ps.getGeneratedKeys();
					if (rs.next()) {
						s.setId(rs.getLong(1));
					}
				}
			} catch (SQLException e) {
				logger.error("SQLException while Shipping save. ", e.getMessage());
				e.printStackTrace();
				throw new DaoException("SQLException while Shipping save.", e.getCause());
			}
		} else {
			try (PreparedStatement ps = connection.prepareStatement(UPDATE);) {
				
				//ps.setLong(1, s.getPersonId());
				ps.setLong(1, s.getPerson().getId());
				ps.setTimestamp(2, s.getDownloadDatetime());
				//ps.setLong(3, s.getLoadLocalityId());
				ps.setLong(3, s.getLoadLocality().getId());
				ps.setString(4, s.getShipper());
				ps.setString(5, s.getDownloadAddress());
				ps.setString(6, s.getConsignee());
				//ps.setLong(7, s.getUnloadLocalityId());
				ps.setLong(7, s.getUnloadLocality().getId());
				ps.setString(8, s.getUnloadAddress());
				ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
				ps.setDouble(10, s.getDistance());
				ps.setDouble(11, s.getWeight());
				ps.setDouble(12, s.getVolume());
				ps.setBigDecimal(13, s.getFare());
				//ps.setLong(14, s.getShippingStatusId());
				ps.setLong(14, s.getShippingStatus().getId());
				ps.setLong(15, s.getId());
				ps.executeUpdate();

			} catch (SQLException e) {
				logger.error("SQLException while Shipping save. " + e.getMessage());
				throw new DaoException("SQLException while Shipping save." + e.getMessage());
			}
		}
		return s;
	}
	
	@Override
	public Optional<Shipping> findById(Long id) {
		//ArrayList<Shipping> shippings = new ArrayList<Shipping>();
		Shipping s = null;
		try (PreparedStatement ps = connection.prepareStatement(FIND_BY_ID);) {
			ps.setLong(1, id);
			PersonDao pDao = new PersonDao(connection);
			LocalityDao lDao = new LocalityDao(connection);
			ShippingStatusDao ssDao = new ShippingStatusDao(connection);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				s = new Shipping();
				s.setId(rs.getLong(1));
				s.setCreationTimestamp(rs.getTimestamp(2));
				//s.setPersonId(rs.getLong(3));
				s.setPerson(pDao.findById(rs.getLong(3)).orElseThrow(()-> new DaoException("No Person while ShippingDao.findAll")));
				s.setDownloadDatetime(rs.getTimestamp(4));
				//s.setLoadLocalityId(rs.getLong(5));
				s.setLoadLocality(lDao.findById(rs.getLong(5)).orElseThrow(()-> new DaoException("No load Locality while ShippingDao.findAll")));
				s.setShipper(rs.getString(6));
				s.setDownloadAddress(rs.getString(7));
				s.setConsignee(rs.getString(8));
				//s.setUnloadLocalityId(rs.getLong(9));
				s.setUnloadLocality(lDao.findById(rs.getLong(9)).orElseThrow(()-> new DaoException("No unload Locality while ShippingDao.findAll")));
				s.setUnloadAddress(rs.getString(10));
				s.setUnloadingDatetime(rs.getTimestamp(11));
				s.setDistance(rs.getDouble(12));
				s.setWeight(rs.getDouble(13));
				s.setVolume(rs.getDouble(14));
				s.setFare(rs.getBigDecimal(15));
				//s.setShippingStatusId(rs.getLong(16));
				s.setShippingStatus(ssDao.findById(rs.getLong(16)).orElseThrow(()->new DaoException("No ShippingStatus while ShippingDao.findAll")));
			}
		} catch (SQLException e) {
			logger.error("SQLException while Shipping findAll. ", e.getMessage());
			throw new DaoException("SQLException while Shipping deleteById.", e);
		}
		return Optional.ofNullable(s);	
	}
	
	//1	id
	//2	creation_timestamp
	//3	person_id 
	//4	download_datetime 
	//5	load_locality_id 
	//6	shipper 
	//7	download_address 
	//8	consignee 
	//9	unload_locality_id 
	//10	unload_address 
	//11	unloading_datetime
	//12	distance 
	//13	weight 
	//14	volume 
	//15	fare 
	//16	shipping_status_id

	@Override
	public Iterable<Shipping> findAll() {
		ArrayList<Shipping> shippings = new ArrayList<Shipping>();
		try (PreparedStatement ps = connection.prepareStatement(FIND_ALL);) {
			PersonDao pDao = new PersonDao(connection);
			LocalityDao lDao = new LocalityDao(connection);
			ShippingStatusDao ssDao = new ShippingStatusDao(connection);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Shipping s = new Shipping();
				s.setId(rs.getLong(1));
				s.setCreationTimestamp(rs.getTimestamp(2));
				//s.setPersonId(rs.getLong(3));
				s.setPerson(pDao.findById(rs.getLong(3)).orElseThrow(()-> new DaoException("No Person while ShippingDao.findAll")));
				s.setDownloadDatetime(rs.getTimestamp(4));
				//s.setLoadLocalityId(rs.getLong(5));
				s.setLoadLocality(lDao.findById(rs.getLong(5)).orElseThrow(()-> new DaoException("No load Locality while ShippingDao.findAll")));
				s.setShipper(rs.getString(6));
				s.setDownloadAddress(rs.getString(7));
				s.setConsignee(rs.getString(8));
				//s.setUnloadLocalityId(rs.getLong(9));
				s.setUnloadLocality(lDao.findById(rs.getLong(9)).orElseThrow(()-> new DaoException("No unload Locality while ShippingDao.findAll")));
				s.setUnloadAddress(rs.getString(10));
				s.setUnloadingDatetime(rs.getTimestamp(11));
				s.setDistance(rs.getDouble(12));
				s.setWeight(rs.getDouble(13));
				s.setVolume(rs.getDouble(14));
				s.setFare(rs.getBigDecimal(15));
				//s.setShippingStatusId(rs.getLong(16));
				s.setShippingStatus(ssDao.findById(rs.getLong(16)).orElseThrow(()->new DaoException("No ShippingStatus while ShippingDao.findAll")));
				shippings.add(s);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Shipping findAll. ", e.getMessage());
			throw new DaoException("SQLException while Shipping deleteById.", e);
		}
		return shippings;
	}

	@Override
	public boolean existsById(Long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteById(Long id) {
		try (PreparedStatement ps = connection.prepareStatement(DELETE);) {
			ps.setLong(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("SQLException while Shipping deleteById. ", e.getMessage());
			throw new DaoException("SQLException while Shipping deleteById.", e);
		}
	}

	@Override
	public void close() {
		super.close();
	}
	
	public Iterable<Shipping> findByPeriodAndPerson(Timestamp after, Timestamp before, Long personId) {
		ArrayList<Shipping> shippings = new ArrayList<Shipping>();
		try (PreparedStatement ps = connection.prepareStatement(FILTER_USER_DATE);) {
			ps.setTimestamp(1, after);
			ps.setTimestamp(2, before);
			ps.setLong(3, personId);
			PersonDao pDao = new PersonDao(connection);
			LocalityDao lDao = new LocalityDao(connection);
			ShippingStatusDao ssDao = new ShippingStatusDao(connection);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Shipping s = new Shipping();
				s.setId(rs.getLong(1));
				s.setCreationTimestamp(rs.getTimestamp(2));
				//s.setPersonId(rs.getLong(3));
				s.setPerson(pDao.findById(rs.getLong(3)).orElseThrow(()-> new DaoException("No Person while ShippingDao.findAll")));
				s.setDownloadDatetime(rs.getTimestamp(4));
				//s.setLoadLocalityId(rs.getLong(5));
				s.setLoadLocality(lDao.findById(rs.getLong(5)).orElseThrow(()-> new DaoException("No load Locality while ShippingDao.findAll")));
				s.setShipper(rs.getString(6));
				s.setDownloadAddress(rs.getString(7));
				s.setConsignee(rs.getString(8));
				//s.setUnloadLocalityId(rs.getLong(9));
				s.setUnloadLocality(lDao.findById(rs.getLong(9)).orElseThrow(()-> new DaoException("No unload Locality while ShippingDao.findAll")));
				s.setUnloadAddress(rs.getString(10));
				s.setUnloadingDatetime(rs.getTimestamp(11));
				s.setDistance(rs.getDouble(12));
				s.setWeight(rs.getDouble(13));
				s.setVolume(rs.getDouble(14));
				s.setFare(rs.getBigDecimal(15));
				//s.setShippingStatusId(rs.getLong(16));
				s.setShippingStatus(ssDao.findById(rs.getLong(16)).orElseThrow(()->new DaoException("No ShippingStatus while ShippingDao.findAll")));
				shippings.add(s);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Shipping findAll. ", e.getMessage());
			throw new DaoException("SQLException while Shipping deleteById.", e);
		}
		return shippings;
	}
	public Iterable<Shipping> findByPeriod(Timestamp after, Timestamp before) {
		ArrayList<Shipping> shippings = new ArrayList<Shipping>();
		try (PreparedStatement ps = connection.prepareStatement(FILTER_USER_DATE);) {
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
				//s.setPersonId(rs.getLong(3));
				s.setPerson(pDao.findById(rs.getLong(3)).orElseThrow(()-> new DaoException("No Person while ShippingDao.findAll")));
				s.setDownloadDatetime(rs.getTimestamp(4));
				//s.setLoadLocalityId(rs.getLong(5));
				s.setLoadLocality(lDao.findById(rs.getLong(5)).orElseThrow(()-> new DaoException("No load Locality while ShippingDao.findAll")));
				s.setShipper(rs.getString(6));
				s.setDownloadAddress(rs.getString(7));
				s.setConsignee(rs.getString(8));
				//s.setUnloadLocalityId(rs.getLong(9));
				s.setUnloadLocality(lDao.findById(rs.getLong(9)).orElseThrow(()-> new DaoException("No unload Locality while ShippingDao.findAll")));
				s.setUnloadAddress(rs.getString(10));
				s.setUnloadingDatetime(rs.getTimestamp(11));
				s.setDistance(rs.getDouble(12));
				s.setWeight(rs.getDouble(13));
				s.setVolume(rs.getDouble(14));
				s.setFare(rs.getBigDecimal(15));
				//s.setShippingStatusId(rs.getLong(16));
				s.setShippingStatus(ssDao.findById(rs.getLong(16)).orElseThrow(()->new DaoException("No ShippingStatus while ShippingDao.findAll")));
				
				shippings.add(s);
			}
		} catch (SQLException e) {
			logger.error("SQLException while Shipping findAll. ", e.getMessage());
			throw new DaoException("SQLException while Shipping deleteById.", e);
		}
		return shippings;
	}
	
	@Override
	public List<Shipping> findAllInPeriod(Timestamp after, Timestamp before, int limit, int offset, 
			Comparator<Shipping> c, Predicate<Shipping> p) {
		ArrayList<Shipping> shippings = new ArrayList<Shipping>();
		try (PreparedStatement ps = connection.prepareStatement(PERIOD_PAGE);) {
			ps.setTimestamp(1, after);
			ps.setTimestamp(2, before);
			ps.setInt(3, limit);
			ps.setInt(4, offset);
			
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
		
		return shippings.stream().filter(p).sorted(c).collect(Collectors.toList());
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
			logger.error("SQLException while Shipping deleteById. ", e.getMessage());
			throw new DaoException("SQLException while Shipping deleteById.", e);
		}
		
		return res;
	}



}
