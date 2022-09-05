package com.revature.rest.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rest.dao.OrderDAO;
import com.revature.rest.models.Order;
import com.revature.rest.services.OrderService;

/**
 * Handles servlet requests for orders.
 * @author Warren Lee
 */
public class OrderController extends HttpServlet {
	private static Logger logger = LogManager.getLogger(OrderController.class);
	private static OrderService orderService = new OrderService(new OrderDAO());
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/rest/", "");
		Map<String, String[]> parameterMap = req.getParameterMap();
		String jsonResponse = null;
		switch (requestURI) {
		case "order":
			Order order = new Order();
			if (parameterMap.containsKey("id")) {
				int id = 0;
				try {
					id = Integer.parseInt(parameterMap.get("id")[0]);
					if (id > 0) order = orderService.getOrderById(id);
				} catch (NumberFormatException e) {
					logger.warn("Invalid id parameter.", e);
				}
			}
			jsonResponse = objectMapper.writeValueAsString(order);
			resp.getWriter().println(jsonResponse);
			break;
		case "orders":
			List<Order> orders = null;
			if (parameterMap.isEmpty()) orders = orderService.getAllOrders();
			else if (parameterMap.containsKey("store_id")) {
				int storeId = 0;
				try {
					storeId = Integer.parseInt(parameterMap.get("store_id")[0]);
					if (storeId > 0) orders = orderService.getAllOrdersByStoreId(storeId);
				} catch (NumberFormatException e) {
					logger.warn("Invalid store_id parameter.", e);
				}
			} else if (parameterMap.containsKey("user_id")) {
				int userId = 0;
				try {
					userId = Integer.parseInt(parameterMap.get("user_id")[0]);
					if (userId > 0) orders = orderService.getAllOrdersByUserId(userId);
				} catch (NumberFormatException e) {
					logger.warn("Invalid user_id parameter.", e);
				}
			}
			jsonResponse = objectMapper.writeValueAsString(orders);
			resp.getWriter().println(jsonResponse);
			break;
		default:
			super.doGet(req, resp);
			break;
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/rest/", "");
		resp.setContentType("application/json");
		String jsonRequest = req.getReader().lines().collect(Collectors.joining());
		String jsonResponse = null;
		Order order = objectMapper.readValue(jsonRequest, Order.class);
		switch (requestURI) {
		case "order/add":
			order = orderService.addOrder(order);
			if (order.getId() < 1) order = new Order();
			jsonResponse = objectMapper.writeValueAsString(order);
			resp.getWriter().println(jsonResponse);
			break;
		default:
			super.doPost(req, resp);
			break;
		}
	}
}
