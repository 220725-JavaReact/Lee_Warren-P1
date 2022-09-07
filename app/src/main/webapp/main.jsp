<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Wyn's Wild Wares - Login Success</title>
	</head>
	<body>
		<%
		if (session.getAttribute("user_id") == null) response.sendRedirect("login.jsp");
		String cookieUserId = null;
		String sessionId = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie currentCookie : cookies) {
				if (currentCookie.getName().equals("user_id")) cookieUserId = currentCookie.getValue();
				if (currentCookie.getName().equals("JSESSIONID")) sessionId = currentCookie.getValue();
			}
		}
		%>
		
		<h2>Welcome to Wyn's Wild Wares</h2>
		<h3>Your friendly neighborhood dealer of fine magical merchandise and adventuring gear.</h3>
		
		<p>Hello User#<%=cookieUserId%>. Login Successful.</p>

		<form action="/app/logout" method="post">
			<input type="submit" value="Logout"/>
		</form>
	</body>
</html>