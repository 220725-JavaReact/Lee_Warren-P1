package com.revature.rest.models;

/**
 * The locations selling products.
 * @author Warren Lee
 */
public class Store {
	private int id;
	private String name;
	private String address;
	private Double balance;
	/**
	 * Constructs a store with the specified data.
	 * @param name the store's name
	 * @param address the store's address
	 * @param balance the store's balance
	 */
	public Store(String name, String address, Double balance) {
		id = 0;
		this.name = name;
		this.address = address;
		this.balance = balance;
	}
	/**
	 * Constructs a store with the specified data.
	 * @param id the store's id
	 * @param name the store's name
	 * @param address the store's address
	 * @param balance the store's balance
	 */
	public Store(int id, String name, String address, Double balance) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.balance = balance;
	}
	/**
	 * Returns the store's id.
	 * @return the store's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the store's name.
	 * @return the store's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the store's name.
	 * @param name the store's new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the store's address.
	 * @return the store's address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Sets the store's address.
	 * @param address the store's new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * Returns the store's balance.
	 * @return the store's balance
	 */
	public Double getBalance() {
		return balance;
	}
	/**
	 * Sets the store's balance.
	 * @param balance the store's new balance
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	/**
	 * Outputs this store as a string including the id, name, address, and store balance.
	 * @return a string representation of this store
	 */
	public String toString() {
		return (id == 0 ? "" : ("ID: " + id + "\t")) + "Name: " + name + "\nAddress: " + address + "\nBalance: $" + String.format("%.2f", balance);
	}
}
