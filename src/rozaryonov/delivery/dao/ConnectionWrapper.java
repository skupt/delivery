package rozaryonov.delivery.dao;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionWrapper {
	private static final String CONNECTION_URL = "jdbc:mysql://localhost:3306/delivery?serverTimezone=Europe/Kiev";
	private static final String USER = "root";
	private static final String PASSWORD = "w123";
	private static Logger logger = LogManager.getLogger();

	private static Connection connection;

	private ConnectionWrapper() {
	}

	public static synchronized Connection getConnection() {
		try {
			if (connection == null || connection.isClosed()) {
				connection = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
			}
		} catch (SQLException e) {
			logger.error("SQL Exception while getConnection in ConnectionWrapper", e);
			Thread.currentThread().interrupt();
		}
		return connection;

	}

	public static void createDB() {
		byte[] ba = null;
		try {
			ba = Files.readAllBytes(Paths.get("sql/db-create.sql"));
		} catch (IOException e1) {
			// e1.printStackTrace();
			logger.error("IO Exception while createDB. " + e1.getMessage());
			Thread.currentThread().interrupt();
		}
		String is = new String(ba, StandardCharsets.UTF_8);
		String[] sqls = is.trim().split(";");
		try {
			Connection con = getConnection();
			for (String q : sqls) {
				try (PreparedStatement ps = con.prepareStatement(q);) {
					ps.execute();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// logger.warning("Exception while db creation: " + e.getMessage());
			Thread.currentThread().interrupt();
		}
	}
}
