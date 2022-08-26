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
		logger.info("Adding new order...");
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
				logger.info("Adding new order's line items...");
				for (LineItem currentLineItem : instance.getLineItems()) {
					String query2 = "insert into order_items (order_id, product_id, quantity) values (?, ?, ?)";
					PreparedStatement statement2 = dbConnection.prepareStatement(query2);
					statement2.setInt(1, instance.getId());
					statement2.setInt(2, currentLineItem.getProduct().getId());
					statement2.setInt(3, currentLineItem.getQuantity());
					statement2.execute();
					if (statement2.getUpdateCount() > 0) {
						logger.info("Updating store's inventory of order's line item...");
						String query3 = "update store_products set quantity = quantity - ? where store_id = ? and product_id = ?";
						PreparedStatement statement3 = dbConnection.prepareStatement(query3);
						statement3.setInt(1, currentLineItem.getQuantity());
						statement3.setInt(2, instance.getStoreId());
						statement3.setInt(3, currentLineItem.getProduct().getId());
						if (statement3.getUpdateCount() > 0) continue;
					}
					logger.warn("Transaction failed. Rolling back changes.");
					dbConnection.rollback();
					dbConnection.setAutoCommit(true);
					instance.setId(0);
					break;
				}
				logger.info("Transaction succeeded. Commiting changes...");
				dbConnection.commit();
				dbConnection.setAutoCommit(true);
			} else {
				logger.warn("Transaction failed. Rolling back changes.");
				dbConnection.rollback();
				dbConnection.setAutoCommit(true);
				instance.setId(0);
			}
		} catch (SQLException e) {
			logger.warn("Failed to add new order. Rolling back changes.", e);
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
				currentLineItems.add(new LineItem(productService.getProductById(resultSet.getInt("product_id")), resultSet.getInt("quantity")));
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
