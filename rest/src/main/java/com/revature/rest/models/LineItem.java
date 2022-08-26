package com.revature.rest.models;

/**
 * An object containing a product and a quantity.
 * @author Warren Lee
 */
public class LineItem {
	private Product product;
	private int quantity;
	/**
	 * Constructs a blank line item.
	 */
	public LineItem() {
		
	}
	/**
	 * Constructs a line item from the specified data.
	 * @param item the line item's product
	 * @param quantity the line item's quantity
	 */
	public LineItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	/**
	 * Returns the line item's product.
	 * @return the line item's product
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * Sets the line item's product.
	 * @param newProduct the line item's new product
	 */
	public void setProduct(Product newProduct) {
		product = newProduct;
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
	 * Outputs this line item as a string including the quantity and product information.
	 * @return a string representation of this line item
	 */
	public String toString() {
		return quantity + "x\t" + product.toString();
	}
}
