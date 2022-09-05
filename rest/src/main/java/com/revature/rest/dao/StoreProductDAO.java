package com.revature.rest.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.rest.models.LineItem;
import com.revature.rest.services.ProductService;
import com.revature.rest.utils.ConnectionFactory;

/**
 * Communicates with the database to perform CRUD operations on store inventories.
 * @author Warren Lee
 */
public class StoreProductDAO implements DAO<LineItem> {
	private static Logger logger = LogManager.getLogger(StoreProductDAO.class);
	private static ProductService productService = new ProductService(new ProductDAO());
	@Override
	public LineItem addInstance(LineItem instance) {
		logger.info("Adding new product with id = " + instance.getProduct().getId() + " to store with id = " + instance.getId() + "...");
		Connection dbConnection = ConnectionFactory.getInstance().getConnection();
		try {
			dbConnection.setAutoCommit(false);
			String query = "insert into store_products (store_id, product_id, quantity) values (?, ?, ?)";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setInt(1, instance.getId());
			statement.setInt(2, instance.getProduct().getId());
			statement.setInt(3, instance.getQuantity());
			statement.execute();
			if (statement.getUpdateCount() > 0) {
				logger.info("Updating balance for store with id = " + instance.getId() + "...");
				String query2 = "update stores set balance = balance - ? where id = ?";
				PreparedStatement statement2 = dbConnection.prepareStatement(query2);
				statement2.setDouble(1, instance.getProduct().getPrice() * instance.getQuantity() * 0.80);
				statement2.setInt(2, instance.getId());
				statement2.execute();
				if (statement2.getUpdateCount() > 0) {
					logger.info("Transaction to add new product with id = " + instance.getProduct().getId() + " to store with id = " + instance.getId() + " succeeded. Committing changes...");
					dbConnection.commit();
					dbConnection.setAutoCommit(true);
				} else {
					logger.warn("Transaction to add new product with id = " + instance.getProduct().getId() + " to store with id = " + instance.getId() + " failed. Rolling back changes.");
					dbConnection.rollback();
					dbConnection.setAutoCommit(true);
					instance.setId(0);
				}
			} else {
				logger.warn("Transaction to add new product with id = " + instance.getProduct().getId() + " to store with id = " + instance.getId() + " failed. Rolling back changes.");
				dbConnection.rollback();
				dbConnection.setAutoCommit(true);
				instance.setId(0);
			}
		} catch (SQLException e) {
			logger.warn("Transaction to add new product with id = " + instance.getProduct().getId() + " to store with id = " + instance.getId() + " failed. Rolling back changes.", e);
			try {
				dbConnection.rollback();
				dbConnection.setAutoCommit(true);
				instance.setId(0);
			} catch (SQLException e2) {
				logger.warn("Failed to rollback changes.", e2);
				instance.setId(0);
			}
		}
		return instance;
	}
	@Override
	public List<LineItem> getAllInstances() {
		logger.info("Getting all store inventories...");
		List<LineItem> storeProducts = new ArrayList<>();
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "select * from store_products";
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				storeProducts.add(new LineItem(
					resultSet.getInt("store_id"),
					productService.getProductById(resultSet.getInt("product_id")),
					resultSet.getInt("quantity")
				));
			}
		} catch (SQLException e) {
			logger.warn("Failed to get all store inventories.", e);
		}
		return storeProducts;
	}
	@Override
	public boolean updateInstance(LineItem instance) {
		logger.info("Updating product with id = " + instance.getProduct().getId() + " for store with id = " + instance.getId() + "...");
		Connection dbConnection = ConnectionFactory.getInstance().getConnection();
		try {
			dbConnection.setAutoCommit(false);
			String query = "update store_products set quantity = quantity + ? where store_id = ? and product_id = ?";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setInt(1, instance.getQuantity());
			statement.setInt(2, instance.getId());
			statement.setInt(3, instance.getProduct().getId());
			statement.execute();
			if (statement.getUpdateCount() > 0) {
				logger.info("Updating balance for store with id = " + instance.getId() + "...");
				String query2 = "update stores set balance = balance - ? where id = ?";
				PreparedStatement statement2 = dbConnection.prepareStatement(query2);
				statement2.setDouble(1, instance.getProduct().getPrice() * instance.getQuantity() * 0.80);
				statement2.setInt(2, instance.getId());
				statement2.execute();
				if (statement2.getUpdateCount() > 0) {
					logger.info("Transaction to update product with id = " + instance.getProduct().getId() + " for store with id = " + instance.getId() + " succeeded. Committing changes...");
					dbConnection.commit();
					dbConnection.setAutoCommit(true);
					return true;
				} else {
					logger.warn("Transaction to update product with id = " + instance.getProduct().getId() + " for store with id = " + instance.getId() + " failed. Rolling back changes.");
					dbConnection.rollback();
					dbConnection.setAutoCommit(true);
					return false;
				}
			} else {
				logger.warn("Transaction to update product with id = " + instance.getProduct().getId() + " for store with id = " + instance.getId() + " failed. Rolling back changes.");
				dbConnection.rollback();
				dbConnection.setAutoCommit(true);
				return false;
			}
		} catch (SQLException e) {
			logger.warn("Transaction to update product with id = " + instance.getProduct().getId() + " for store with id = " + instance.getId() + " failed. Rolling back changes.", e);
			try {
				dbConnection.rollback();
				dbConnection.setAutoCommit(true);
				return false;
			} catch (SQLException e2) {
				logger.warn("Failed to rollback changes.", e2);
				return false;
			}
		}
	}
	@Override
	public boolean deleteInstance(LineItem instance) {
		logger.info("Deleting product with id = " + instance.getProduct().getId() + " from store with id = " + instance.getId() + "...");
		List<LineItem> storeProducts = getAllInstances();
		Optional<LineItem> foundStoreProduct = storeProducts.stream().filter(storeProduct -> storeProduct.getId() == instance.getId() && storeProduct.getProduct().getId() == instance.getProduct().getId()).findFirst();
		if (foundStoreProduct.isPresent()) {
			LineItem storeProduct = foundStoreProduct.get();
			int quantity = storeProduct.getQuantity();
			if (quantity > 0) {
				storeProduct.setQuantity(quantity * -1);
				updateInstance(storeProduct);
			}
			try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
				String query = "delete from store_products where store_id = ? and product_id = ?";
				PreparedStatement statement = dbConnection.prepareStatement(query);
				statement.setInt(1, instance.getId());
				statement.setInt(2, instance.getProduct().getId());
				statement.execute();
				if (statement.getUpdateCount() > 0) return true;
				return false;
			} catch (SQLException e) {
				logger.warn("Failed to update product with id = " + instance.getProduct().getId() + " for store with id = " + instance.getId() + ".", e);
				return false;
			}
		}
		logger.warn("Failed to delete product with id = " + instance.getProduct().getId() + " from store with id = " + instance.getId() + ". Store does not have inventory of this product.");
		return false;
	}
}
