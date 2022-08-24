package com.revature.rest.models;

import com.revature.rest.utils.AccessLevel;

/**
 * The application's users.
 * @author Warren Lee
 *
 */
public class User {
	private int id;
	private String name;
	private String address;
	private String email;
	private String phone;
	private String username;
	private String password;
	private AccessLevel level;
	/**
	 * Constructs a user from the specified data.
	 * @param name the user's name
	 * @param address the user's address
	 * @param email the user's email address, can be null
	 * @param phone the user's phone number, can be null
	 * @param username the user's username
	 * @param password the encoded password string
	 * @param level the user's access level
	 */
	public User(String name, String address, String email, String phone, String username, String password, AccessLevel level) {
		id = 0;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.level = level;
	}
	/**
	 * Constructs a user from the specified data.
	 * @param id the user's id
	 * @param name the user's name
	 * @param address the user's address
	 * @param email the user's email address, can be null
	 * @param phone the user's phone number, can be null
	 * @param username the user's username
	 * @param password the encoded password string
	 * @param level the user's access level
	 */
	public User(int id, String name, String address, String email, String phone, String username, String password, AccessLevel level) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.username = username;
		this.password = password;
		this.level = level;
	}
	/**
	 * Returns the user's id.
	 * @return the user's id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the user's name.
	 * @return the user's name
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the user's name.
	 * @param name the user's new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returns the user's address.
	 * @return the user's address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * Sets the user's address.
	 * @param address the user's new address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * Returns the user's email address.
	 * @return the user's email address
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Sets the user's email address.
	 * @param email the user's new email address
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * Returns the user's phone number.
	 * @return the user's phone number
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * Sets the user's phone number.
	 * @param phone the user's new phone number
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * Returns the user's username.
	 * @return the user's username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * Returns the user's encoded password string.
	 * @return the user's encoded password string
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * Sets the user's encoded password string.
	 * @param password the user's new encoded password string
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * Returns the user's access level.
	 * @return the user's access level
	 */
	public AccessLevel getLevel() {
		return level;
	}
	/**
	 * Returns a boolean to indicate whether the user is an admin.
	 * @return a boolean to indicate whether the user is an admin
	 */
	public boolean isAdmin() {
		return level == AccessLevel.ADMIN;
	}
	/**
	 * Sets the user's access level.
	 * @param level the user's new access level
	 */
	public void setLevel(AccessLevel level) {
		this.level = level;
	}
	/**
	 * Outputs this user as a string including the id, name, address, email address, phone number, username, and access level.
	 * @return a string representation of this user
	 */
	@Override
	public String toString() {
		return (id == 0 ? "" : ("ID: " + id + "\t")) + "Name: " + name + " (" + username + ")\tAccess: " + level.name + "\nAddress: " + address + "\nEmail: " + email + "\tPhone: " + phone;
	}
}
