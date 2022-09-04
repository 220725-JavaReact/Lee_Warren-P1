package com.revature.rest.utils;

/**
 * An enum for product categories.
 * @author Warren Lee
 */
public enum ProductCategory {
	ADVENTURING_GEAR("Adventuring Gear"),
	AMMUNITION("Ammunition"),
	ARMOR("Armor"),
	POTION("Potion"),
	SCROLL("Scroll"),
	TOOL("Tool"),
	WEAPON("Weapon"),
	WONDROUS_ITEM("Wondrous Item");
	public final String name;
	private ProductCategory(String name) {
		this.name = name;
	}
}
