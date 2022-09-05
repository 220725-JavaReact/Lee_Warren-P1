package com.revature.rest.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.rest.dao.DAO;
import com.revature.rest.models.Store;

/**
 * A service to perform custom logic on stores.
 * @author Warren Lee
 */
public class StoreService {
	private static Logger logger = LogManager.getLogger(StoreService.class);
	private DAO<Store> storeDAO;
	/**
	 * Constructs a StoreService using the input DAO.
	 * @param storeDAO the DAO instance
	 */
	public StoreService(DAO<Store> storeDAO) {
		this.storeDAO = storeDAO;
	}
	/**
	 * Adds a new store to the database and returns it with its id.
	 * @param newStore the new store
	 * @return the store with its id
	 */
	public Store addStore(Store newStore) {
		logger.info("Adding new store...");
		return storeDAO.addInstance(newStore);
	}
	/**
	 * Returns a list of all stores.
	 * @return a list of all stores
	 */
	public List<Store> getAllStores() {
		logger.info("Getting all stores...");
		return storeDAO.getAllInstances();
	}
	/**
	 * Returns a store with matching id.
	 * @param id the store's id
	 * @return a store with matching id
	 */
	public Store getStoreById(int id) {
		logger.info("Getting store with id = " + id + "...");
		List<Store> stores = getAllStores();
		Optional<Store> foundStore = stores.stream().filter(store -> store.getId() == id).findFirst();
		if (foundStore.isPresent()) return foundStore.get();
		else {
			logger.warn("Failed to get store with id = " + id + ".");
			Store notFoundStore = new Store();
			notFoundStore.setName("Store not found");
			return notFoundStore;
		}
	}
	/**
	 * Updates a store and returns a boolean that indicates whether the operation was successful.
	 * @param updateStore the store to update
	 * @return a boolean that indicates whether the operation was successful
	 */
	public boolean updateStore(Store updateStore) {
		logger.info("Updating store...");
		return storeDAO.updateInstance(updateStore);
	}
}
