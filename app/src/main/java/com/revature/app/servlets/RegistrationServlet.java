package com.revature.app.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.revature.app.utils.RESTFactory;

/**
 * Handles servlet requests for registration.
 * @author Warren Lee
 */
public class RegistrationServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(RegistrationServlet.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/app/", "");
		if (requestURI.equals("register")) {
			logger.info("Registering a user...");
			ObjectNode requestJson = objectMapper.createObjectNode();
			requestJson.put("name", req.getParameter("name"));
			requestJson.put("address", req.getParameter("address"));
			String email = req.getParameter("email");
			requestJson.put("email", email.equals("") ? null : email);
			String phone = req.getParameter("phone");
			requestJson.put("phone", phone.equals("") ? null : phone);
			requestJson.put("username", req.getParameter("username"));
			requestJson.put("password", req.getParameter("password"));
			requestJson.put("level", "CUSTOMER");
			String response = RESTFactory.getInstance().sendPost("/user/add", objectMapper.writeValueAsString(requestJson));
			JsonNode responseJson = objectMapper.readTree(response);
			if (responseJson.get("id").asInt() == 0) {
				RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/register.jsp");
				resp.getWriter().println("<font color=red>Failed to register user. Try again.</font>");
				requestDispatcher.include(req, resp);
			} else {
				HttpSession session = req.getSession();
				session.setAttribute("user_id", responseJson.get("id").asText());
				session.setAttribute("access_level", responseJson.get("level").asText());
				session.setMaxInactiveInterval(1800); // Set max timeout of session to 30 minutes
				Cookie sessionCookie = new Cookie("user_id", responseJson.get("id").asText());
				sessionCookie.setMaxAge(1800); // Set max age of cookie to 30 minutes
				resp.addCookie(sessionCookie);
				resp.sendRedirect("main.jsp");
			}
		} else {
			super.doPost(req, resp);
		}
	}
}
