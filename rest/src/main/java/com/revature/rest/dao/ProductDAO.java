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

import com.revature.rest.models.Product;
import com.revature.rest.utils.ConnectionFactory;
import com.revature.rest.utils.ProductCategory;

/**
 * Communicates with the database to perform CRUD operations on products.
 * @author Warren Lee
 */
public class ProductDAO implements DAO<Product> {
	private static Logger logger = LogManager.getLogger(ProductDAO.class);
	@Override
	public Product addInstance(Product instance) {
		logger.info("Adding new product...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "insert into products (name, category, description, price) values (?, ?, ?, ?) returning id";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setString(1, instance.getName());
			statement.setString(2, instance.getCategory().toString());
			statement.setString(3, instance.getDescription());
			statement.setDouble(4, instance.getPrice());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) instance.setId(resultSet.getInt("id"));
		} catch (SQLException e) {
			logger.warn("Failed to add new product.", e);
		}
		return instance;
	}
	@Override
	public List<Product> getAllInstances() {
		logger.info("Getting all products...");
		List<Product> products = new ArrayList<>();
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "select * from products";
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				products.add(new Product(
					resultSet.getInt("id"),
					resultSet.getString("name"),
					ProductCategory.valueOf(resultSet.getString("category")),
					resultSet.getString("description"),
					resultSet.getDouble("price")
				));
			}
		} catch (SQLException e) {
			logger.warn("Failed to get all products.", e);
		}
		return products;
	}
	@Override
	public boolean updateInstance(Product instance) {
		logger.info("Updating product with id = " + instance.getId() + "...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "update products set name = ?, category = ?, description = ?, price = ? where id = ?";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setString(1, instance.getName());
			statement.setString(2, instance.getCategory().toString());
			statement.setString(3, instance.getDescription());
			statement.setDouble(4, instance.getPrice());
			statement.setInt(5, instance.getId());
			statement.execute();
			if (statement.getUpdateCount() > 0) return true;
			return false;
		} catch (SQLException e) {
			logger.warn("Failed to update product with id = " + instance.getId() + ".", e);
			return false;
		}
	}
	@Override
	public boolean deleteInstance(Product instance) {
		logger.info("Deleting product with id = " + instance.getId() + "...");
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "delete from products where id = ?";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setInt(1, instance.getId());
			statement.execute();
			if (statement.getUpdateCount() > 0) return true;
			return false;
		} catch (SQLException e) {
			logger.warn("Failed to delete product with id = " + instance.getId() + ".", e);
			return false;
		}
	}
}
