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

import com.revature.rest.models.Store;
import com.revature.rest.utils.ConnectionFactory;

/**
 * Communicates with the database to perform CRUD operations on stores.
 * @author Warren Lee
 */
public class StoreDAO implements DAO<Store> {
	private static Logger logger = LogManager.getLogger(StoreDAO.class);
	@Override
	public Store addInstance(Store instance) {
		logger.info("Adding new store...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "insert into stores (name, address, balance) values (?, ?, ?) returning id";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setString(1, instance.getName());
			statement.setString(2, instance.getAddress());
			statement.setDouble(3, instance.getBalance());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) instance.setId(resultSet.getInt("id"));
		} catch (SQLException e) {
			logger.warn("Failed to add new store.", e);
		}
		return instance;
	}
	@Override
	public List<Store> getAllInstances() {
		logger.info("Getting all stores...");
		List<Store> stores = new ArrayList<>();
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "select * from stores";
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				stores.add(new Store(
					resultSet.getInt("id"),
					resultSet.getString("name"),
					resultSet.getString("address"),
					resultSet.getDouble("balance")
				));
			}
		} catch (SQLException e) {
			logger.warn("Failed to get all stores.", e);
		}
		return stores;
	}
	@Override
	public boolean updateInstance(Store instance) {
		logger.info("Updating store with id = " + instance.getId() + "...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "update stores set name = ?, address = ?, balance = ? where id = ?";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setString(1, instance.getName());
			statement.setString(2, instance.getAddress());
			statement.setDouble(3, instance.getBalance());
			statement.setInt(4, instance.getId());
			statement.execute();
			if (statement.getUpdateCount() > 0) return true;
			return false;
		} catch (SQLException e) {
			logger.warn("Failed to update store with id = " + instance.getId() + ".", e);
			return false;
		}
	}
	@Override
	public boolean deleteInstance(Store instance) {
		logger.info("Deleting store with id = " + instance.getId() + "...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "delete from stores where id = ?";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setInt(1, instance.getId());
			statement.execute();
			if (statement.getUpdateCount() > 0) return true;
			return false;
		} catch (SQLException e) {
			logger.warn("Failed to delete store with id = " + instance.getId() + ".", e);
			return false;
		}
	}
}
