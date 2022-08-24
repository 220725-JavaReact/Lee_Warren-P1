package com.revature.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.revature.rest.dao.DAO;
import com.revature.rest.dao.UserDAO;
import com.revature.rest.models.User;
import com.revature.rest.utils.AccessLevel;
import com.revature.rest.utils.PasswordFactory;

public class DAOTest {
	DAO<User> userDAO = new UserDAO();
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
