package com.revature.rest.dao;

import java.util.List;

public interface DAO<T> {
	/**
	 * Adds an object to the database and returns it with any fields populated by the database.
	 * @param instance the object to be added
	 * @return the object after it has been added to the database
	 */
	public T addInstance(T instance);
	/**
	 * Returns a list of all objects from the database.
	 * @return a list of all objects from the database
	 */
	public List<T> getAllInstances();
	/**
	 * Updates an object in the database and returns a boolean that indicates whether the operation was successful.
	 * @param instance the object to be updated
	 * @return a boolean that indicates whether the operation was successful
	 */
	public boolean updateInstance(T instance);
	/**
	 * Deletes an object from the database and returns a boolean that indicates whether the operation was successful.
	 * @param instance the object to be deleted
	 * @return a boolean that indicates whether the operation was successful
	 */
	public boolean deleteInstance(T instance);
}
