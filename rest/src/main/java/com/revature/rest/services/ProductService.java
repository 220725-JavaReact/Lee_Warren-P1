package com.revature.rest.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.rest.dao.DAO;
import com.revature.rest.models.Product;
import com.revature.rest.utils.ProductCategory;

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
	 * Adds a new product to the database and returns it with its id.
	 * @param newProduct the new product
	 * @return the product with its id
	 */
	public Product addProduct(Product newProduct) {
		logger.info("Adding new product...");
		return productDAO.addInstance(newProduct);
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
	 * Returns a list of all products with matching category
	 * @param category the ProductCategory
	 * @return a list of all products with matching category
	 */
	public List<Product> getProductsByCategory(ProductCategory category) {
		logger.info("Gettin all products with category = " + category.toString() + "...");
		return getAllProducts().stream().filter(product -> product.getCategory().equals(category)).collect(Collectors.toList());
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
			logger.warn("Failed to get product with id = " + id + ".");
			Product notFoundProduct = new Product();
			notFoundProduct.setName("Product not found");
			return notFoundProduct;
		}
	}
	/**
	 * Updates a product in the database and returns a boolean that indicates whether the operation was successful.
	 * @param updatedProduct the updated product
	 * @return a boolean that indicates whether the operation was successful
	 */
	public boolean updateProduct(Product updatedProduct) {
		logger.info("Updating product...");
		return productDAO.updateInstance(updatedProduct);
	}
	/**
	 * Deletes a product from the database and returns a boolean that indicates whether the operation was successful.
	 * @param discontinuedProduct the discontinued product
	 * @return a boolean that indicates whether the operation was successful
	 */
	public boolean deleteProduct(Product discontinuedProduct) {
		logger.info("Deleting product...");
		return productDAO.deleteInstance(discontinuedProduct);
	}
}
