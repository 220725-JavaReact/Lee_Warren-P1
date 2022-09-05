package com.revature.rest.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.rest.dao.DAO;
import com.revature.rest.models.Order;

/**
 * A service to perform custom logic on orders.
 * @author Warren Lee
 */
public class OrderService {
	private static Logger logger = LogManager.getLogger(OrderService.class);
	private DAO<Order> orderDAO;
	/**
	 * Constructs an OrderService using the input DAO.
	 * @param orderDAO the DAO instance
	 */
	public OrderService(DAO<Order> orderDAO) {
		this.orderDAO = orderDAO;
	}
	/**
	 * Adds a new order to the database and returns it with its id.
	 * @param newOrder the new order
	 * @return the order with its id
	 */
	public Order addOrder(Order newOrder) {
		logger.info("Adding new order...");
		return orderDAO.addInstance(newOrder);
	}
	/**
	 * Returns a list of all orders.
	 * @return a list of all orders
	 */
	public List<Order> getAllOrders() {
		logger.info("Getting all orders...");
		return orderDAO.getAllInstances();
	}
	/**
	 * Returns a list of all orders with matching user id.
	 * @param id the user's id
	 * @return a list of all orders with matching user id
	 */
	public List<Order> getAllOrdersByUserId(int id) {
		logger.info("Getting all orders for user with id = " + id + "...");
		return getAllOrders().stream().filter(order -> order.getUserId() == id).collect(Collectors.toList());
	}
	/**
	 * Returns a list of all orders with matching store id.
	 * @param id the store's id
	 * @return a list of all orders with matching store id
	 */
	public List<Order> getAllOrdersByStoreId(int id) {
		logger.info("Getting all orders for store with id = " + id + "...");
		return getAllOrders().stream().filter(order -> order.getStoreId() == id).collect(Collectors.toList());
	}
	/**
	 * Returns an order with matching id.
	 * @param id the order's id
	 * @return an order with matching id
	 */
	public Order getOrderById(int id) {
		logger.info("Getting order with id = " + id + "...");
		List<Order> orders = getAllOrders();
		Optional<Order> foundOrder = orders.stream().filter(order -> order.getId() == id).findFirst();
		if (foundOrder.isPresent()) return foundOrder.get();
		else {
			logger.warn("Failed to get order with id = " + id + ".");
			Order notFoundOrder = new Order();
			return notFoundOrder;
		}
	}
}
