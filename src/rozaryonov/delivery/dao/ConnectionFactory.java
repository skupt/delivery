package rozaryonov.delivery.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionFactory {
	
	private ConnectionFactory () {}
	
	public static Connection getConnection() {
		Connection connection = null;
		DataSource ds=null;
		try {
		Context initContext = new InitialContext();
		Context envContext  = (Context)initContext.lookup("java:comp/env");
		ds = (DataSource) envContext.lookup("jdbc/delivery");
		} catch (NamingException e) {
			//do nothing
		}
		if (ds == null) connection = ConnectionWrapper.getConnection(); else connection = DeliveryConnectionPool.getConnection();
		return connection;
	}
}
