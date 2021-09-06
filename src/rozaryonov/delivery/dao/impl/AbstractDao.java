package rozaryonov.delivery.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import rozaryonov.delivery.dao.CrudRepository;

public abstract class AbstractDao <T, ID> implements CrudRepository<T, ID> {
	protected Connection connection;
	protected Logger logger = LogManager.getLogger();
	
	protected AbstractDao(Connection connection) {
		this.connection = connection;
	}
	
	protected void close() {
		if (connection !=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warn(e.getMessage());
			}
			connection=null;
		}
	}

}
