package com.revature.rest.utils;

/**
 * An enum for user access levels.
 * @author Warren Lee
 */
public enum AccessLevel {
	ADMIN("Administrator"),
	CUSTOMER("Customer");
	public final String name;
	private AccessLevel(String name) {
		this.name = name;
	}
}
