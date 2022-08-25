package com.revature.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Test;

public class TestRunner {
	@Test
	public void startTest() {
		runTest(DAOTest.class.getDeclaredMethods(), new DAOTest());
		runTest(UtilsTest.class.getDeclaredMethods(), new UtilsTest());
	}
	public <T> void runTest(Method[] methods, T object) {
		for (Method currentMethod : methods) {
			try {
				currentMethod.invoke(object);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
