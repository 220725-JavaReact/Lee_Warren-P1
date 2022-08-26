package com.revature.rest.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.rest.dao.DAO;
import com.revature.rest.models.Product;

/**
 * A service to perform custom logic on products.
 * @author Warren Lee
 */
public class ProductService {
	private static Logger logger = LogManager.getLogger(ProductService.class);
	private DAO<Product> productDAO;
	/**
	 * Constructs a ProductService using the input DAO.
	 * @param productDAO the DAO instance
	 */
	public ProductService(DAO<Product> productDAO) {
		this.productDAO = productDAO;
	}
	/**
	 * Returns a list of all products.
	 * @return a list of all products
	 */
	public List<Product> getAllProducts() {
		logger.info("Getting all products...");
		return productDAO.getAllInstances();
	}
	/**
	 * Returns a product with matching id.
	 * @param id the product's id
	 * @return a product with matching id
	 */
	public Product getProductById(int id) {
		logger.info("Getting product with id = " + id + "...");
		List<Product> products = getAllProducts();
		Optional<Product> foundProduct = products.stream().filter(product -> product.getId() == id).findFirst();
		if (foundProduct.isPresent()) return foundProduct.get();
		else {
			logger.warn("Failed to get product with id = " + id + "...");
			Product product = new Product();
			product.setName("Product not found");
			return product;
		}
	}
}
