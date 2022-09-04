package com.revature.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.rest.models.User;
import com.revature.rest.utils.AccessLevel;
import com.revature.rest.utils.ConnectionFactory;

/**
 * Communicates with the database to perform CRUD operations on users.
 * @author Warren Lee
 */
public class UserDAO implements DAO<User> {
	private static Logger logger = LogManager.getLogger(UserDAO.class);
	@Override
	public User addInstance(User instance) {
		logger.info("Adding new user...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "insert into users (name, address, email, phone, username, password, level) values (?, ?, ?, ?, ?, ?, ?) returning id";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setString(1, instance.getName());
			statement.setString(2, instance.getAddress());
			statement.setString(3, instance.getEmail());
			statement.setString(4, instance.getPhone());
			statement.setString(5, instance.getUsername());
			statement.setString(6, instance.getPassword());
			statement.setString(7, instance.getLevel().toString());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) instance.setId(resultSet.getInt("id"));
		} catch (SQLException e) {
			logger.warn("Failed to add new user.", e);
		}
		return instance;
	}
	@Override
	public List<User> getAllInstances() {
		logger.info("Getting all users...");
		List<User> users = new ArrayList<>();
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "select * from users";
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				users.add(new User(
					resultSet.getInt("id"),
					resultSet.getString("name"),
					resultSet.getString("address"),
					resultSet.getString("email"),
					resultSet.getString("phone"),
					resultSet.getString("username"),
					resultSet.getString("password"),
					AccessLevel.valueOf(resultSet.getString("level"))
				));
			}
		} catch (SQLException e) {
			logger.warn("Failed to get all users.", e);
		}
		return users;
	}
	@Override
	public boolean updateInstance(User instance) {
		logger.info("Updating user with id = " + instance.getId() + "...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "update users set name = ?, address = ?, email = ?, phone = ?, username = ?, password = ?, level = ? where id = ?";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setString(1, instance.getName());
			statement.setString(2, instance.getAddress());
			statement.setString(3, instance.getEmail());
			statement.setString(4, instance.getPhone());
			statement.setString(5, instance.getUsername());
			statement.setString(6, instance.getPassword());
			statement.setString(7, instance.getLevel().toString());
			statement.setInt(8, instance.getId());
			statement.execute();
			if (statement.getUpdateCount() > 0) return true;
			return false;
		} catch (SQLException e) {
			logger.warn("Failed to update user with id = " + instance.getId() + ".", e);
			return false;
		}
	}
	@Override
	public boolean deleteInstance(User instance) {
		logger.info("Deleting user with id = " + instance.getId() + "...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "delete from users where id = ?";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setInt(1, instance.getId());
			statement.execute();
			if (statement.getUpdateCount() > 0) return true;
			return false;
		} catch (SQLException e) {
			logger.warn("Failed to delete user with id = " + instance.getId() + ".", e);
			return false;
		}
	}
}
