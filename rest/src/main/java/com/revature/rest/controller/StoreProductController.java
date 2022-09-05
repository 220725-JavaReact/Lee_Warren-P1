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
import com.revature.rest.dao.StoreProductDAO;
import com.revature.rest.models.LineItem;
import com.revature.rest.models.Product;
import com.revature.rest.services.StoreProductService;

/**
 * Handles servlet requests for store inventories.
 * @author Warren Lee
 */
public class StoreProductController extends HttpServlet {
	private static Logger logger = LogManager.getLogger(StoreProductController.class);
	private static StoreProductService storeProductService = new StoreProductService(new StoreProductDAO());
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/rest/", "");
		Map<String, String[]> parameterMap = req.getParameterMap();
		resp.setContentType("application/json");
		String jsonResponse = null;
		switch (requestURI) {
		case "store/products":
			List<LineItem> storeProducts = null;
			if (parameterMap.containsKey("id")) {
				int id = 0;
				try {
					id = Integer.parseInt(parameterMap.get("id")[0]);
					if (id > 0) storeProducts = storeProductService.getAllStoreProductsByStoreId(id);
				} catch (NumberFormatException e) {
					logger.warn("Invalid id parameter.", e);
				}
			}
			jsonResponse = objectMapper.writeValueAsString(storeProducts);
			resp.getWriter().println(jsonResponse);
			break;
		case "product/stores":
			List<LineItem> productStores = null;
			if (parameterMap.containsKey("id")) {
				int id = 0;
				try {
					id = Integer.parseInt(parameterMap.get("id")[0]);
					if (id > 0) productStores = storeProductService.getAllStoreProductsByProductId(id);
				} catch (NumberFormatException e) {
					logger.warn("Invalid id parameter.", e);
				}
			}
			jsonResponse = objectMapper.writeValueAsString(productStores);
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
		LineItem storeProduct = objectMapper.readValue(jsonRequest, LineItem.class);
		switch (requestURI) {
		case "store/products/add":
			storeProduct = storeProductService.addStoreProduct(storeProduct);
			if (storeProduct.getId() < 1) {
				storeProduct = new LineItem();
				Product notAddedProduct = new Product();
				notAddedProduct.setName("Failed to add new inventory");
				storeProduct.setProduct(notAddedProduct);
			}
			jsonResponse = objectMapper.writeValueAsString(storeProduct);
			resp.getWriter().println(jsonResponse);
			break;
		case "store/products/update":
			boolean updateSuccess = storeProductService.updateStoreProduct(storeProduct);
			if (!updateSuccess) {
				storeProduct = new LineItem();
				Product notUpdatedProduct = new Product();
				notUpdatedProduct.setName("Failed to update inventory");
				storeProduct.setProduct(notUpdatedProduct);
			}
			jsonResponse = objectMapper.writeValueAsString(storeProduct);
			resp.getWriter().println(jsonResponse);
			break;
		case "store/products/delete":
			boolean deleteSuccess = storeProductService.deleteStoreProduct(storeProduct);
			if (!deleteSuccess) {
				storeProduct = new LineItem();
				Product notDeletedProduct = new Product();
				notDeletedProduct.setName("Failed to delete inventory");
				storeProduct.setProduct(notDeletedProduct);
			}
			jsonResponse = objectMapper.writeValueAsString(storeProduct);
			resp.getWriter().println(jsonResponse);
			break;
		default:
			super.doPost(req, resp);
			break;
		}
	}
}
