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

import com.revature.rest.models.LineItem;
import com.revature.rest.models.Order;
import com.revature.rest.services.ProductService;
import com.revature.rest.utils.ConnectionFactory;

/**
 * Communicates with the database to perform CRUD operations on orders.
 * @author Warren Lee
 */
public class OrderDAO implements DAO<Order> {
	private static Logger logger = LogManager.getLogger(OrderDAO.class);
	private static ProductService productService = new ProductService(new ProductDAO());
	@Override
	public Order addInstance(Order instance) {
		logger.info("Adding new order for user with id = " + instance.getUserId() + " and store with id = " + instance.getStoreId() + "...");
		Connection dbConnection = ConnectionFactory.getInstance().getConnection();
		try {
			dbConnection.setAutoCommit(false);
			String query = "insert into orders (user_id, store_id, total, date) values (?, ?, ?, ?) returning id";
			PreparedStatement statement = dbConnection.prepareStatement(query);
			statement.setInt(1, instance.getUserId());
			statement.setInt(2, instance.getStoreId());
			statement.setDouble(3, instance.getTotal());
			statement.setTimestamp(4, instance.getDate());
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				instance.setId(resultSet.getInt("id"));
				logger.info("Adding line items for order with id = " + instance.getId() + "...");
				for (LineItem currentLineItem : instance.getLineItems()) {
					String query2 = "insert into order_items (order_id, product_id, quantity) values (?, ?, ?)";
					PreparedStatement statement2 = dbConnection.prepareStatement(query2);
					statement2.setInt(1, instance.getId());
					statement2.setInt(2, currentLineItem.getProduct().getId());
					statement2.setInt(3, currentLineItem.getQuantity());
					statement2.execute();
					if (statement2.getUpdateCount() > 0) {
						logger.info("Updating inventory of store with id = " + instance.getStoreId() + " for product with id = " + currentLineItem.getProduct().getId() + "...");
						String query3 = "update store_products set quantity = quantity - ? where store_id = ? and product_id = ?";
						PreparedStatement statement3 = dbConnection.prepareStatement(query3);
						statement3.setInt(1, currentLineItem.getQuantity());
						statement3.setInt(2, instance.getStoreId());
						statement3.setInt(3, currentLineItem.getProduct().getId());
						statement3.execute();
						if (statement3.getUpdateCount() > 0) continue;
					}
					logger.warn("Transaction to add new order for user with id = " + instance.getUserId() + " and store with id = " + instance.getStoreId() + " failed. Rolling back changes.");
					dbConnection.rollback();
					dbConnection.setAutoCommit(true);
					instance.setId(0);
					break;
				}
				logger.info("Updating balance of store with id = " + instance.getStoreId() + "...");
				String query4 = "update stores set balance = balance + ? where id = ?";
				PreparedStatement statement4 = dbConnection.prepareStatement(query4);
				statement4.setDouble(1, instance.getTotal());
				statement4.setInt(2, instance.getStoreId());
				statement4.execute();
				if (statement4.getUpdateCount() > 0) {
					logger.info("Transaction to add new order with id = " + instance.getId() + " for user with id = " + instance.getUserId() + " and store with id = " + instance.getStoreId() + " succeeded. Committing changes...");
					dbConnection.commit();
					dbConnection.setAutoCommit(true);
				} else {
					logger.warn("Transaction to add new order for user with id = " + instance.getUserId() + " and store with id = " + instance.getStoreId() + " failed. Rolling back changes.");
					dbConnection.rollback();
					dbConnection.setAutoCommit(true);
					instance.setId(0);
				}
			} else {
				logger.warn("Transaction to add new order for user with id = " + instance.getUserId() + " and store with id = " + instance.getStoreId() + " failed. Rolling back changes.");
				dbConnection.rollback();
				dbConnection.setAutoCommit(true);
				instance.setId(0);
			}
		} catch (SQLException e) {
			logger.warn("Transaction to add new order for user with id = " + instance.getUserId() + " and store with id = " + instance.getStoreId() + " failed. Rolling back changes.", e);
			try {
				dbConnection.rollback();
				dbConnection.setAutoCommit(true);
			} catch (SQLException e2) {
				logger.warn("Failed to rollback changes.", e2);
			}
			instance.setId(0);
		}
		return instance;
	}
	@Override
	public List<Order> getAllInstances() {
		logger.info("Getting all orders...");
		List<Order> orders = new ArrayList<>();
		try (Connection dbConnection = ConnectionFactory.getInstance().getConnection()) {
			String query = "select * from orders join order_items on orders.id = order_items.order_id";
			Statement statement = dbConnection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			int orderId = 0;
			Order currentOrder = null;
			ArrayList<LineItem> currentLineItems = new ArrayList<>();
			while (resultSet.next()) {
				if (orderId != resultSet.getInt("id")) {
					if (!currentLineItems.isEmpty()) {
						currentOrder.setLineItems(currentLineItems);
						orders.add(currentOrder);
						currentLineItems = new ArrayList<>();
					}
					orderId = resultSet.getInt("id");
					currentOrder = new Order();
					currentOrder.setId(orderId);
					currentOrder.setUserId(resultSet.getInt("user_id"));
					currentOrder.setStoreId(resultSet.getInt("store_id"));
					currentOrder.setTotal(resultSet.getDouble("total"));
					currentOrder.setDate(resultSet.getTimestamp("date"));
				}
				currentLineItems.add(new LineItem(orderId, productService.getProductById(resultSet.getInt("product_id")), resultSet.getInt("quantity")));
			}
			if (!currentLineItems.isEmpty()) {
				currentOrder.setLineItems(currentLineItems);
				orders.add(currentOrder);
			}
		} catch (SQLException e) {
			logger.warn("Failed to get all orders.", e);
		}
		return orders;
	}
	@Override
	public boolean updateInstance(Order instance) {
		// not implementing
		return false;
	}
	@Override
	public boolean deleteInstance(Order instance) {
		// not implementing
		return false;
	}
}
