package com.revature.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.revature.rest.dao.DAO;
import com.revature.rest.dao.ProductDAO;
import com.revature.rest.dao.StoreDAO;
import com.revature.rest.dao.UserDAO;
import com.revature.rest.models.Product;
import com.revature.rest.models.Store;
import com.revature.rest.models.User;
import com.revature.rest.utils.AccessLevel;
import com.revature.rest.utils.PasswordFactory;
import com.revature.rest.utils.ProductCategory;

public class DAOTest {
	private DAO<Product> productDAO = new ProductDAO();
	private DAO<Store> storeDAO = new StoreDAO();
	private DAO<User> userDAO = new UserDAO();
	@Test
	public void testProductDAO() {
		Product product = new Product(
			"Bead of Nourishment",
			ProductCategory.WONDROUS_ITEM,
			"This spongy, flavorless, gelatinous bead dissolves on your tongue and provides as much nourishment as 1 day of rations.",
			50.0
		);
		product = productDAO.addInstance(product);
		Assert.assertNotEquals(product.getId(), 0);
		ArrayList<Product> products = (ArrayList<Product>) productDAO.getAllInstances();
		Assert.assertFalse(products.isEmpty());
		String newName = "Cube of Nourishment";
		product.setName(newName);
		Assert.assertTrue(productDAO.updateInstance(product));
		Assert.assertTrue(productDAO.deleteInstance(product));
	}
	@Test
	public void testStoreDAO() {
		Store store = new Store(
			"Gravity Falls Mall",
			"618 Gopher Rd, Gravity Falls, OR 97478",
			16551.0
		);
		store = storeDAO.addInstance(store);
		Assert.assertNotEquals(store.getId(), 0);
		ArrayList<Store> stores= (ArrayList<Store>) storeDAO.getAllInstances();
		Assert.assertFalse(stores.isEmpty());
		String newName = "Gravity Falls Mega Mall";
		store.setName(newName);
		Assert.assertTrue(storeDAO.updateInstance(store));
		Assert.assertTrue(storeDAO.deleteInstance(store));
	}
	@Test
	public void testUserDAO() {
		String encodedPassword = PasswordFactory.getInstance().encodePassword("password123");
		User user = new User(
			"John Doe",
			"407 Lake Floyd Circle, Washington, MD 20011",
			"john.doe@hotmail.com",
			"3019440340",
			"heresjohnny123",
			encodedPassword,
			AccessLevel.CUSTOMER
		);
		user = userDAO.addInstance(user);
		Assert.assertNotEquals(user.getId(), 0);
		ArrayList<User> users = (ArrayList<User>) userDAO.getAllInstances();
		Assert.assertFalse(users.isEmpty());
		String newName = "Jane Doe";
		user.setName(newName);
		Assert.assertTrue(userDAO.updateInstance(user));
		Assert.assertTrue(userDAO.deleteInstance(user));
	}
}
