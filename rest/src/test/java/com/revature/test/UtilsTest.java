package com.revature.test;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.revature.rest.utils.ConnectionFactory;
import com.revature.rest.utils.PropertiesFactory;

public class UtilsTest {
	ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
	PropertiesFactory propertiesFactory = PropertiesFactory.getInstance();
	@Test
	public void testProperties() {
		Properties properties = propertiesFactory.loadProperties();
		Assert.assertFalse(properties.isEmpty());
		Assert.assertEquals(properties.getProperty("db_url"), "jdbc:postgresql://demodb.ci6atgv104kr.us-east-1.rds.amazonaws.com:5432/wyns_wild_wares");
	}
	@Test
	public void testConnection() {
		Assert.assertNotNull(connectionFactory.getConnection());
	}
}
