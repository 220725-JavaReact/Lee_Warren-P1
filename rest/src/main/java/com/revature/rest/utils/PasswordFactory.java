package com.revature.rest.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Provides an encoder for password hashing and matching.
 * @author Warren Lee
 */
public class PasswordFactory {
	private static PasswordFactory passwordFactory = new PasswordFactory();
	private static Logger logger = LogManager.getLogger(PasswordFactory.class);
	static {
		logger.info("Checking for dependencies...");
		try {
			Class.forName("org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder");
		} catch (ClassNotFoundException e) {
			logger.warn("Could not locate dependency.", e);
		}
	}
	/**
	 * Returns the PasswordFactory instance.
	 * @return the PasswordFactory instance
	 */
	public static PasswordFactory getInstance() {
		logger.info("Getting PasswordFactory instance...");
		return passwordFactory;
	}
	/**
	 * Accepts a raw password and returns the hashed version as a string.
	 * @param rawPassword the raw password to be encoded
	 * @return the hashed version of the raw password
	 */
	public String encodePassword(String rawPassword) {
		return new BCryptPasswordEncoder().encode(rawPassword);
	}
	/**
	 * Accepts a raw password and encoded string. Returns a boolean that indicates if the raw password matches the encoded version.
	 * @param rawPassword the raw password to be compared
	 * @param encodedPassword the encoded password to be compared
	 * @return a boolean that indicates if the raw password matches the encoded version
	 */
	public boolean matchPassword(String rawPassword, String encodedPassword) {
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);
	}
}
