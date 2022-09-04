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
import com.revature.rest.dao.ProductDAO;
import com.revature.rest.models.Product;
import com.revature.rest.services.ProductService;
import com.revature.rest.utils.ProductCategory;

/**
 * Handles servlet requests for products.
 * @author Warren Lee
 */
public class ProductController extends HttpServlet {
	private static Logger logger = LogManager.getLogger(ProductController.class);
	private static ProductService productService = new ProductService(new ProductDAO());
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/rest/", "");
		Map<String, String[]> parameterMap = req.getParameterMap();
		resp.setContentType("application/json");
		String jsonResponse = null;
		switch (requestURI) {
		case "product":
			Product product = new Product();
			product.setName("Prouct not found");
			if (parameterMap.containsKey("id")) {
				int id = 0;
				try {
					id = Integer.parseInt(parameterMap.get("id")[0]);
					if (id > 0) {
						product = productService.getProductById(id);
					}
				} catch (NumberFormatException e) {
					logger.warn("Invalid id parameter.", e);
				}
			}
			jsonResponse = objectMapper.writeValueAsString(product);
			resp.getWriter().println(jsonResponse);
			break;
		case "products":
			List<Product> products = null;
			if (parameterMap.isEmpty()) {
				products = productService.getAllProducts();
			} else if (parameterMap.containsKey("category")) {
				try {
					products = productService.getProductsByCategory(ProductCategory.valueOf(parameterMap.get("category")[0].toUpperCase()));
				} catch (IllegalArgumentException e) {
					logger.warn("Invalid id parameter.", e);
				}
			}
			jsonResponse = objectMapper.writeValueAsString(products);
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
		String jsonResponse = null;
		switch (requestURI) {
		case "product/add":
			String jsonRequest = req.getReader().lines().collect(Collectors.joining());
			Product product = objectMapper.readValue(jsonRequest, Product.class);
			product = productService.addProduct(product);
			if (product.getId() < 1) {
				product = new Product();
				product.setName("Failed to add new product");
			}
			jsonResponse = objectMapper.writeValueAsString(product);
			resp.getWriter().println(jsonResponse);
			break;
		default:
			super.doPost(req, resp);
			break;
		}
	}
}
