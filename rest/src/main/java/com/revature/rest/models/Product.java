package com.revature.rest.models;

import com.revature.rest.utils.ProductCategory;

/**
 * The products for sale.
 * @author Warren Lee
 */
public class Product {
	private int id;
	private String name;
	private ProductCategory category;
	private String description;
	private Double price;
	/**
	 * Constructs a product from the specified data.
	 * @param name the product's name
	 * @param category the product's category
	 * @param description the product's description
	 * @param price the product's price
	 */
	public Product(String name, ProductCategory category, String description, Double price) {
		id = 0;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
	}
	/**
	 * Constructs a product from the specified data.
	 * @param id the product's id
	 * @param name the product's name
	 * @param category the product's category
	 * @param description the product's description
	 * @param price the product's price
	 */
	public Product(int id, String name, ProductCategory category, String description, Double price) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.description = description;
		this.price = price;
	}
	/**
	 * Returns the product's id.
	 * @return the product's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the product's name.
	 * @return the product's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the product's name.
	 * @param newName the product's new name
	 */
	public void setName(String newName) {
		name = newName;
	}
	/**
	 * Returns the product's category.
	 * @return the product's category
	 */
	public ProductCategory getCategory() {
		return category;
	}
	/**
	 * Returns the product's description.
	 * @return the product's description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Sets the product's description.
	 * @param newDescription the product's new description
	 */
	public void setDescription(String newDescription) {
		description = newDescription;
	}
	/**
	 * Returns the product's price.
	 * @return the product's price
	 */
	public Double getPrice() {
		return price;
	}
	/**
	 * Sets the product's price.
	 * @param newPrice the product's new price
	 */
	public void setPrice(Double newPrice) {
		price = newPrice;
	}
	/**
	 * Outputs this product as a string including the name, category, price, and description.
	 * @return a string representation of this product
	 */
	@Override
	public String toString() {
		return name + (id == 0 ? "" : (" (" + id + ")")) + "\t" + category.name + "\t$" + String.format("%.2f", price) + "\n" + description;
	}
}
