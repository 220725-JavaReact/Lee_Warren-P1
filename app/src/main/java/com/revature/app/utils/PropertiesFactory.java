package com.revature.app.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Interact with the application's properties file.
 * @author Warren Lee
 */
public class PropertiesFactory {
	private static PropertiesFactory propertiesFactory = new PropertiesFactory();
	private static Logger logger = LogManager.getLogger(PropertiesFactory.class);
	/**
	 * Returns the PropertiesFactory instance.
	 * @return the PropertiesFactory instance
	 */
	public static PropertiesFactory getInstance() {
		logger.info("Getting PropertiesFactory instance...");
		return propertiesFactory;
	}
	/**
	 * Loads the information from the properties file and returns the information as a Properties object.
	 * @return the information from the properties file as a Properties object
	 */
	public Properties loadProperties() {
		logger.info("Loading properties from file...");
		Properties properties = new Properties();
		try {
			properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
		}catch (IOException e) {
			logger.warn("Could not load from properties file.", e);
		}
		return properties;
	}
}
