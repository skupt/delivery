package rozaryonov.delivery.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DeliveryConnectionPool {
	private static DataSource ds;
	private static Logger logger = LogManager.getLogger(DeliveryConnectionPool.class.getName());

	
	private DeliveryConnectionPool() {}
	
	private static synchronized DataSource getDataSource() {
		if (ds==null) initDataSource();
		return ds;
	}
	
	private static void initDataSource() {
		try {
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:comp/env");
		ds = (DataSource) envContext.lookup("jdbc/delivery");
		} catch (NamingException e) {
			logger.fatal("Exception while initDataSource", e.getMessage());
			e.printStackTrace();
			Thread.currentThread().interrupt();
		}
		
	}
	
	public static Connection getConnection() {
		Connection cn=null;
		try {
			cn = getDataSource().getConnection();
		} catch (SQLException e) {
			logger.fatal("Can't get sql connection from DataSource", e.getMessage());
			Thread.currentThread().interrupt();
		}
		return cn;
	}
}
