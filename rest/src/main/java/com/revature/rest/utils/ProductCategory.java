package com.revature.rest.utils;

/**
 * An enum for product categories.
 * @author Warren Lee
 */
public enum ProductCategory {
	ACCESSORY("Accessory"),
	AMMUNITION("Ammunition"),
	ARMOR("Armor"),
	POTION("Potion"),
	SCROLL("Scroll"),
	WEAPON("Weapon"),
	WONDROUS_ITEM("Wondrous Item");
	public final String name;
	private ProductCategory(String name) {
		this.name = name;
	}
}
