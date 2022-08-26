package com.revature.rest.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	private static final String propertiesPath = "..\\Lee-Warren-P1\\rest\\src\\main\\resources\\application.properties";
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
			properties.load(new FileReader(propertiesPath));
		} catch (FileNotFoundException e) {
			logger.warn("Could not locate properties file.", e);
		} catch (IOException e) {
			logger.warn("Could not load from properties file.", e);
		}
		return properties;
	}
	/**
	 * Stores the information from the input into the properties file.
	 * @param updatedProperties the updated properties
	 */
	public void storeProperties(Properties updatedProperties) {
		logger.info("Storing properties to file...");
		try {
			updatedProperties.store(new FileWriter(propertiesPath), null);
		} catch (IOException e) {
			logger.warn("Could not store to properties file.", e);
		}
	}
}
