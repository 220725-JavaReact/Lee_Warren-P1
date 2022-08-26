package com.revature.rest.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rest.dao.UserDAO;
import com.revature.rest.models.User;
import com.revature.rest.services.UserService;

public class UserController extends HttpServlet {
	private static Logger logger = LogManager.getLogger(UserController.class);
	private static UserService userService = new UserService(new UserDAO());
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/rest/", "");
		resp.setContentType("application/json");
		String jsonResponse;
		switch (requestURI) {
		case "user":
			User user = null;
			int id = 0;
			try {
				id = Integer.parseInt(req.getParameter("id"));
			} catch (NumberFormatException e) {
				logger.warn("Invalid id parameter.", e);
			}
			String username = req.getParameter("username");
			if (id > 0) user = userService.getUserById(id);
			else if (!username.equals(null)) user = userService.getUserByUsername(username);
			else {
				user = new User();
				user.setName("User not found");
			}
			user.setPassword("HIDDEN");
			jsonResponse = objectMapper.writeValueAsString(user);
			resp.getWriter().println(jsonResponse);
			break;
		case "users":
			List<User> users = userService.getAllUsers();
			for (User currentUser : users) currentUser.setPassword("HIDDEN");
			jsonResponse = objectMapper.writeValueAsString(users);
			resp.getWriter().println(jsonResponse);
			break;
		default:
			super.doGet(req, resp);
			break;
		}
	}
}
