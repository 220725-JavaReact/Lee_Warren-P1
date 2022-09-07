package com.revature.rest.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.rest.dao.UserDAO;
import com.revature.rest.models.User;
import com.revature.rest.services.UserService;
import com.revature.rest.utils.PasswordFactory;

/**
 * Handles servlet requests for users.
 * @author Warren Lee
 */
public class UserController extends HttpServlet {
	private static Logger logger = LogManager.getLogger(UserController.class);
	private static UserService userService = new UserService(new UserDAO());
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/rest/", "");
		Map<String, String[]> parameterMap = req.getParameterMap();
		resp.setContentType("application/json");
		String jsonResponse = null;
		switch (requestURI) {
		case "user":
			User user = new User();
			user.setName("User not found");
			if (parameterMap.containsKey("id")) {
				int id = 0;
				try {
					id = Integer.parseInt(parameterMap.get("id")[0]);
					if (id > 0) user = userService.getUserById(id);
				} catch (NumberFormatException e) {
					logger.warn("Invalid id parameter.", e);
				}
			} else if (parameterMap.containsKey("username")) {
				user = userService.getUserByUsername(parameterMap.get("username")[0]);
			}
			jsonResponse = objectMapper.writeValueAsString(user);
			resp.getWriter().println(jsonResponse);
			break;
		case "users":
			List<User> users = null;
			if (parameterMap.isEmpty()) users = userService.getAllUsers();
			jsonResponse = objectMapper.writeValueAsString(users);
			resp.getWriter().println(jsonResponse);
			break;
		default:
			super.doGet(req, resp);
			break;
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/rest/", "");
		resp.setContentType("application/json");
		String jsonRequest = req.getReader().lines().collect(Collectors.joining());
		String jsonResponse = null;
		switch (requestURI) {
		case "user/add":
			User userToAdd = objectMapper.readValue(jsonRequest, User.class);
			userToAdd.setPassword(PasswordFactory.getInstance().encodePassword(userToAdd.getPassword()));
			userToAdd = userService.addUser(userToAdd);
			if (userToAdd.getId() < 1) {
				userToAdd = new User();
				userToAdd.setName("Failed to add new user");
			}
			jsonResponse = objectMapper.writeValueAsString(userToAdd);
			resp.getWriter().println(jsonResponse);
			break;
		case "user/update":
			User userToUpdate = objectMapper.readValue(jsonRequest, User.class);
			boolean updateSuccess = userService.updateUser(userToUpdate);
			if (!updateSuccess) {
				userToUpdate = new User();
				userToUpdate.setName("Failed to update user");
			}
			jsonResponse = objectMapper.writeValueAsString(userToUpdate);
			resp.getWriter().println(jsonResponse);
			break;
		case "user":
			JsonNode requestJson = objectMapper.readTree(jsonRequest);
			User user = new User();
			user.setName("User not found");
			String username = requestJson.get("username").asText();
			String password = requestJson.get("password").asText();
			user = userService.getUserByMatchedCredentials(username, password);
			jsonResponse = objectMapper.writeValueAsString(user);
			resp.getWriter().println(jsonResponse);
			break;
		default:
			super.doPost(req, resp);
			break;
		}
	}
}
