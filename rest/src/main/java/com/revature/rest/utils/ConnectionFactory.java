package com.revature.rest.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Establish a connection to the database.
 * @author Warren Lee
 */
public class ConnectionFactory {
	private static ConnectionFactory connectionFactory = new ConnectionFactory();
	private static Logger logger = LogManager.getLogger(ConnectionFactory.class);
	static {
		logger.info("Checking for dependencies...");
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			logger.warn("Could not locate dependency.", e);
		}
	}
	/**
	 * Returns the ConnectionFactory instance.
	 * @return the ConnectionFactory instance
	 */
	public static ConnectionFactory getInstance() {
		logger.info("Getting ConnectionFactory instance...");
		return connectionFactory;
	}
	/**
	 * Gets a connection to the database and returns it.
	 * @return a connection to the database
	 */
	public static Connection getConnection() {
		logger.info("Getting connection to database...");
		Connection dbConnection = null;
		Properties properties = PropertiesFactory.getInstance().loadProperties();
		if (properties.isEmpty()) return null;
		try {
			dbConnection = DriverManager.getConnection(properties.getProperty("db_url"), properties.getProperty("db_username"), properties.getProperty("db_password"));
		} catch (SQLException e) {
			logger.warn("Failed to get connection to database.", e);
			return null;
		}
		return dbConnection;
	}
}
