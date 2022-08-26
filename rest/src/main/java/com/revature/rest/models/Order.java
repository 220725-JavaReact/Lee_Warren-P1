package com.revature.rest.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The users' orders.
 * @author Warren Lee
 */
public class Order {
	private int id;
	private int userId;
	private int storeId;
	private Double total;
	private Timestamp date;
	private ArrayList<LineItem> lineItems;
	/**
	 * Constructs a blank order.
	 */
	public Order() {
		
	}
	/**
	 * Constructs an order from the specified data.
	 * @param userId the user's id
	 * @param storeId the store's id
	 */
	public Order(int userId, int storeId) {
		id = 0;
		this.userId = userId;
		this.storeId = storeId;
		total = 0.0;
		date = Timestamp.valueOf(LocalDateTime.MIN);
		lineItems = new ArrayList<>();
	}
	/**
	 * Constructs an order from the specified data.
	 * @param id the order's id
	 * @param userId the user's id
	 * @param storeId the store's id
	 * @param total the order's total
	 * @param date the timestamp when the order was placed
	 * @param lineItems the arraylist containing the order's line items
	 */
	public Order(int id, int userId, int storeId, Double total, Timestamp date, ArrayList<LineItem> lineItems) {
		this.id = id;
		this.userId = userId;
		this.storeId = storeId;
		this.total = total;
		this.date = date;
		this.lineItems = lineItems;
	}
	/**
	 * Returns the order's id
	 * @return the order's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Sets the order's id.
	 * @param newId the order's new id
	 */
	public void setId(int newId) {
		id = newId;
	}
	/**
	 * Returns the user's id.
	 * @return the user's id
	 */
	public int getUserId() {
		return userId;
	}
	/**
	 * Sets the user's id.
	 * @param newUserId The new user's id
	 */
	public void setUserId(int newUserId) {
		userId = newUserId;
	}
	/**
	 * Returns the store's id.
	 * @return the store's id
	 */
	public int getStoreId() {
		return storeId;
	}
	/**
	 * Sets the store's id.
	 * @param newStoreId the new store's id
	 */
	public void setStoreId(int newStoreId) {
		storeId = newStoreId;
	}
	/**
	 * Returns the order's total.
	 * @return the order's total
	 */
	public Double getTotal() {
		return total;
	}
	/**
	 * Sets the order's total.
	 * @param newTotal the order's new total
	 */
	public void setTotal(Double newTotal) {
		total = newTotal;
	}
	/**
	 * Returns the order's timestamp.
	 * @return the order's timestamp
	 */
	public Timestamp getDate() {
		return date;
	}
	/**
	 * Sets the order's timestamp.
	 * @param newDate the order's new timestamp
	 */
	public void setDate(Timestamp newDate) {
		date = newDate;
	}
	/**
	 * Returns an arraylist of the order's line items.
	 * @return an arraylist of the order's line items
	 */
	public ArrayList<LineItem> getLineItems() {
		return lineItems;
	}
	/**
	 * Sets the order's line items to an input arraylist.
	 * @param newLineItems the order's new line items
	 */
	public void setLineItems(ArrayList<LineItem> newLineItems) {
		lineItems = newLineItems;
	}
	/**
	 * Adds a line item to the order and updates the order total.
	 * @param newLineItem the new line item
	 */
	public void addItem(LineItem newLineItem) {
		Optional<LineItem> foundLineItem = lineItems.stream().filter(lineItem -> lineItem.getProduct().equals(newLineItem.getProduct())).findFirst();
		if (foundLineItem.isPresent()) {
			LineItem lineItem = foundLineItem.get();
			lineItem.setQuantity(lineItem.getQuantity() + newLineItem.getQuantity());
		} else {
			lineItems.add(newLineItem);
		}
		total += newLineItem.getProduct().getPrice() * newLineItem.getQuantity();
	}
	/**
	 * Removes a line item from the order and updates the order total.
	 * @param removeLineItem the line item to remove
	 */
	public void removeItem(LineItem removeLineItem) {
		Optional<LineItem> foundLineItem = lineItems.stream().filter(lineItem -> lineItem.getProduct().equals(removeLineItem.getProduct())).findFirst();
		if (foundLineItem.isPresent()) {
			LineItem lineItem = foundLineItem.get();
			if (lineItem.getQuantity() < removeLineItem.getQuantity()) return;
			else if (lineItem.getQuantity() > removeLineItem.getQuantity()) {
				lineItem.setQuantity(lineItem.getQuantity() - removeLineItem.getQuantity());
			} else if (lineItem.getQuantity() == removeLineItem.getQuantity()) {
				lineItems.remove(lineItem);
			}
			total -= removeLineItem.getProduct().getPrice() * removeLineItem.getQuantity();
		}
	}
	/**
	 * Outputs this order as a string including the order's id, the user's id, the store's id, the order timestamp, each line item, and the order's total.
	 * @returns a string representation of this order
	 */
	@Override
	public String toString() {
		String returnString = "Order ID: " + id + "\tUser ID: " + userId + "\tStore ID: " + storeId + "\tOrder Date: " + date.toString() + "\n";
		for (LineItem currentLineItem : lineItems) {
			returnString += currentLineItem.toString() + "\n";
		}
		returnString += "Total: $" + String.format("%.2f", total);
		return returnString;
	}
}
