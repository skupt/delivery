package rozaryonov.delivery.services;

import java.sql.Connection;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import rozaryonov.delivery.dao.ConnectionWrapper;
import rozaryonov.delivery.dao.Paginationable;
import rozaryonov.delivery.dao.impl.ShippingDao;
import rozaryonov.delivery.entities.Shipping;
/**
 * An Exampleof use:
 * 		Connection cn = ConnectionWrapper.getConnection();
 *		ShippingDao sd = new ShippingDao(cn);
 *		Pagination<Shipping, ShippingDao> pag = new Pagination<>();
 *		
 *		pag.setDao(sd);
 *		Comparator<Shipping> c = Comparator.comparing((Shipping s)-> s.getPerson().getName());
 *		Predicate<Shipping> p = (e)-> true ;
 *		pag.setComparator(c);
 *		pag.setPredicat(p);
 *		pag.setCapacity(3);
 *		pag.init();
 *		
 *		pag.nextPage().forEach(e->System.out.println(ShipStr(e)));
 *		System.out.println("--------------------------");
 *
 *		pag.prevPage().forEach(e->System.out.println(ShipStr(e)));
 *		System.out.println("**************************");
 * 
 * @author Vitaly Rozaryonov
 *	
 * @param <T> type of elements returned in List<T>
 * @param <D> type of Dao that implement interface Paginationable
 */



public class Pagination <T, D extends Paginationable<T>> {
	private Timestamp after = Timestamp.valueOf(LocalDateTime.of(1970, 1, 1, 0, 0));
	private Timestamp before = Timestamp.valueOf(LocalDateTime.of(3000, 1, 1, 0, 0));
	private int capacity = 5;
	private int pageNum = 0;
	private long rowMax = 1;
	private Paginationable<T> dao;
	private Comparator<T> comparator;
	private Predicate<T> predicat = (e)-> true;
	
	public Pagination () {}
	
	public Pagination (int cap, int pageNum, Paginationable<T> dao) {
		this.capacity=cap;
		this.pageNum=pageNum;
	}
	
	public void init() {
		rowMax = dao.countRows();
	}
	
	public List<T> nextPage() {
		if (((pageNum + 1)*capacity-(capacity-1)) <= rowMax) pageNum += 1; 
		int offset = (pageNum-1)*capacity;
		return dao.findAllInPeriod(after, before, capacity, offset, comparator, predicat);
	}
	public List<T> prevPage() {
		if (pageNum > 1) pageNum -= 1;
		int offset = (pageNum-1)*capacity;
		return dao.findAllInPeriod(after, before, capacity, offset, comparator, predicat);
	}
	
	public void setAfter(Timestamp after) {
		this.after = after;
	}
	public void setBefore(Timestamp before) {
		this.before = before;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public void setDao(Paginationable<T> dao) {
		this.dao = dao;
	}
	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	public void setPredicat(Predicate<T> predicat) {
		this.predicat = predicat;
	}
	
	


}
