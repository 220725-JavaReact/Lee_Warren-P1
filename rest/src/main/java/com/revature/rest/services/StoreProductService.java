package com.revature.rest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.rest.dao.DAO;
import com.revature.rest.models.LineItem;

/**
 * A service to perform custom logic on store inventories.
 * @author Warren Lee
 */
public class StoreProductService {
	private static Logger logger = LogManager.getLogger(StoreProductService.class);
	private DAO<LineItem> storeProductDAO;
	/**
	 * Constructs a StoreProductService using the input DAO.
	 * @param storeProductDAO the DAO instance
	 */
	public StoreProductService(DAO<LineItem> storeProductDAO) {
		this.storeProductDAO = storeProductDAO;
	}
	/**
	 * Adds a new store product to the database and returns it.
	 * @param newLineItem the new store product
	 * @return the store product
	 */
	public LineItem addStoreProduct(LineItem newLineItem) {
		logger.info("Adding new store product...");
		return storeProductDAO.addInstance(newLineItem);
	}
	/**
	 * Returns a list of all store inventories.
	 * @return a list of all store inventories
	 */
	public List<LineItem> getAllStoreProducts() {
		logger.info("Getting all store inventories...");
		return storeProductDAO.getAllInstances();
	}
	/**
	 * Returns a list of all store products with matching store id.
	 * @param id the store's id
	 * @return a list of all store products with matching store id
	 */
	public List<LineItem> getAllStoreProductsByStoreId(int id) {
		logger.info("Getting inventory for store with id = " + id + "...");
		return getAllStoreProducts().stream().filter(storeProduct -> storeProduct.getId() == id).collect(Collectors.toList());
	}
	/**
	 * Returns a list of all store products with matching product id.
	 * @param id the product's id
	 * @return a list of all store products with matching product id
	 */
	public List<LineItem> getAllStoreProductsByProductId(int id) {
		logger.info("Getting all store inventories for products with id = " + id + "...");
		return getAllStoreProducts().stream().filter(storeProduct -> storeProduct.getProduct().getId() == id).collect(Collectors.toList());
	}
	/**
	 * Updates a store product and returns a boolean that indicates whether the operation was successful.
	 * @param updateLineItem the store product to update
	 * @return a boolean that indicates whether the operation was successful
	 */
	public boolean updateStoreProduct(LineItem updateLineItem) {
		logger.info("Updating product...");
		return storeProductDAO.updateInstance(updateLineItem);
	}
	/**
	 * Deletes a store product and returns a boolean that indicates whether the operation was successful.
	 * @param deleteLineItem the store product to delete
	 * @return a boolean that indicates whether the operation was successful
	 */
	public boolean deleteStoreProduct(LineItem deleteLineItem) {
		logger.info("Deleting product...");
		return storeProductDAO.deleteInstance(deleteLineItem);
	}
}
