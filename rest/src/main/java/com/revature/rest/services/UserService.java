package com.revature.rest.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.rest.dao.DAO;
import com.revature.rest.models.User;
import com.revature.rest.utils.PasswordFactory;

/**
 * A service to perform custom logic on users.
 * @author Warren Lee
 */
public class UserService {
	private static Logger logger = LogManager.getLogger(UserService.class);
	private DAO<User> userDAO;
	/**
	 * Constructs a UserService using the input DAO.
	 * @param userDAO the DAO instance
	 */
	public UserService(DAO<User> userDAO) {
		this.userDAO = userDAO;
	}
	/**
	 * Adds a new user to the database and returns it with its id.
	 * @param newUser the new user
	 * @return the user with its id
	 */
	public User addUser(User newUser) {
		logger.info("Adding new user...");
		return userDAO.addInstance(newUser);
	}
	/**
	 * Returns a list of all users.
	 * @return a list of all users
	 */
	public List<User> getAllUsers() {
		logger.info("Getting all users...");
		return userDAO.getAllInstances();
	}
	/**
	 * Returns a user with matching id.
	 * @param id the user's id
	 * @return a user with matching id
	 */
	public User getUserById(int id) {
		logger.info("Getting user with id = " + id + "...");
		List<User> users = getAllUsers();
		Optional<User> foundUser = users.stream().filter(user -> user.getId() == id).findFirst();
		if (foundUser.isPresent()) return foundUser.get();
		else {
			logger.warn("Failed to get user with id = " + id + ".");
			User notFoundUser = new User();
			notFoundUser.setName("User not found");
			return notFoundUser;
		}
	}
	/**
	 * Returns a user with matching username.
	 * @param username the user's username
	 * @return a user with matching username
	 */
	public User getUserByUsername(String username) {
		logger.info("Getting user with username = " + username + "...");
		List<User> users = getAllUsers();
		Optional<User> foundUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst();
		if (foundUser.isPresent()) return foundUser.get();
		else {
			logger.warn("Failed to get user with username = " + username + ".");
			User notFoundUser = new User();
			notFoundUser.setName("User not found");
			return notFoundUser;
		}
	}
	/**
	 * Returns a user with matching username and password.
	 * @param username the user's username
	 * @param password the user's raw password
	 * @return a user with matching username and password
	 */
	public User getUserByMatchedCredentials(String username, String password) {
		logger.info("Getting user with username = " + username + ":" + password.replaceAll(".", "*") + "...");
		User user = getUserByUsername(username);
		if (PasswordFactory.getInstance().matchPassword(password, user.getPassword())) return user;
		else {
			logger.warn("Failed to get user with username = " + username + " with entered credentials.");
			User notFoundUser = new User();
			notFoundUser.setName("User not found; Invalid username/password");
			return notFoundUser;
		}
	}
}
