package com.revature.rest.controller;

import java.io.IOException;
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
import com.revature.rest.dao.StoreDAO;
import com.revature.rest.models.Store;
import com.revature.rest.services.StoreService;

/**
 * Handles servlet requests for stores.
 * @author Warren Lee
 */
public class StoreController extends HttpServlet {
	private static Logger logger = LogManager.getLogger(StoreController.class);
	private static StoreService storeService = new StoreService(new StoreDAO());
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/rest/", "");
		Map<String, String[]> parameterMap = req.getParameterMap();
		resp.setContentType("application/json");
		String jsonResponse = null;
		switch (requestURI) {
		case "store":
			Store store = new Store();
			store.setName("Store not found");
			if (parameterMap.containsKey("id")) {
				int id = 0;
				try {
					id = Integer.parseInt(parameterMap.get("id")[0]);
					if (id > 0) {
						store = storeService.getStoreById(id);
					}
				} catch (NumberFormatException e) {
					logger.warn("Invalid id parameter.", e);
				}
			}
			jsonResponse = objectMapper.writeValueAsString(store);
			resp.getWriter().println(jsonResponse);
			break;
		case "stores":
			List<Store> stores = null;
			if (parameterMap.isEmpty()) stores = storeService.getAllStores();
			jsonResponse = objectMapper.writeValueAsString(stores);
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
		switch (requestURI) {
		case "store/add":
			Store store = objectMapper.readValue(jsonRequest, Store.class);
			store = storeService.addStore(store);
			if (store.getId() < 1) {
				store = new Store();
				store.setName("Failed to add new store");
			}
			jsonResponse = objectMapper.writeValueAsString(store);
			resp.getWriter().println(jsonResponse);
			break;
		default:
			super.doPost(req, resp);
			break;
		}
	}
}
