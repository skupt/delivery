package rozaryonov.delivery.dao;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public interface Paginationable <T> {
	//List<T> findAll(int limit, int offset,  Comparator<T> c, Predicate<T> p);
	List<T> findAllInPeriod(Timestamp after, Timestamp before, int limit, int offset,  Comparator<T> c, Predicate<T> p);
	long countRows(); 
}
