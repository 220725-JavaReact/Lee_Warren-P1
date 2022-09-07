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
 * Handles servlet requests for sessions.
 * @author Warren Lee
 *
 */
public class SessionServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(SessionServlet.class);
	private static ObjectMapper objectMapper = new ObjectMapper();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String requestURI = req.getRequestURI().replace("/app/", "");
		switch (requestURI) {
		case "login":
			logger.info("Logging in...");
			ObjectNode requestJson = objectMapper.createObjectNode();
			requestJson.put("username", req.getParameter("username"));
			requestJson.put("password", req.getParameter("password"));
			String response = RESTFactory.getInstance().sendPost("/user", objectMapper.writeValueAsString(requestJson));
			JsonNode responseJson = objectMapper.readTree(response);
			if (responseJson.get("id").asInt() == 0) {
				RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/index.jsp");
				resp.getWriter().println("<font color=red>Incorrect username/password.</font>");
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
			break;
		case "logout":
			logger.info("Logging out...");
			HttpSession session = req.getSession(false);
			if (session != null) session.invalidate();
			resp.sendRedirect("index.jsp");
			break;
		default:
			super.doPost(req, resp);
			break;
		}
	}
}
