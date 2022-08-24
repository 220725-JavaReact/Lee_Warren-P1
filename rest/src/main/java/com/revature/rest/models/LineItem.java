package com.revature.rest.models;

/**
 * An object containing a product and a quantity.
 * @author Warren Lee
 */
public class LineItem {
	private Product item;
	private int quantity;
	/**
	 * Constructs a line item from the specified data.
	 * @param item the line item's product
	 * @param quantity the line item's quantity
	 */
	public LineItem(Product item, int quantity) {
		this.item = item;
		this.quantity = quantity;
	}
	/**
	 * Returns the line item's product.
	 * @return the line item's product
	 */
	public Product getItem() {
		return item;
	}
	/**
	 * Returns the line item's quantity.
	 * @return the line item's quantity
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * Sets the line item's quantity.
	 * @param quantity the line item's new quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	/**
	 * Outputs this line item as a string including the quantity and product name.
	 * @return a string representation of this line item
	 */
	public String toString() {
		return quantity + "x\t" + item.toString();
	}
}
