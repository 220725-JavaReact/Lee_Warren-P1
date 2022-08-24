package com.revature.test;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.revature.rest.utils.ConnectionFactory;
import com.revature.rest.utils.PasswordFactory;
import com.revature.rest.utils.PropertiesFactory;

public class UtilsTest {
	@Test
	public void testConnection() {
		Assert.assertNotNull(ConnectionFactory.getInstance().getConnection());
	}
	@Test
	public void testPassword() {
		String testPassword = "password123";
		String encodedTestPassword = PasswordFactory.getInstance().encodePassword(testPassword);
		Assert.assertTrue(PasswordFactory.getInstance().matchPassword(testPassword, encodedTestPassword));
	}
	@Test
	public void testProperties() {
		Properties properties = PropertiesFactory.getInstance().loadProperties();
		Assert.assertFalse(properties.isEmpty());
		Assert.assertEquals(properties.getProperty("db_url"), "jdbc:postgresql://demodb.ci6atgv104kr.us-east-1.rds.amazonaws.com:5432/wyns_wild_wares");
	}
}
