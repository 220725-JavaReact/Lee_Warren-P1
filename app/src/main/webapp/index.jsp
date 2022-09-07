<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Wyn's Wild Wares</title>
	</head>
	<body>
		<%
		if (session.getAttribute("user_id") != null) response.sendRedirect("main.jsp");
		%>
		<h2>Welcome to Wyn's Wild Wares</h2>
		<h3>Your friendly neighborhood dealer of fine magical merchandise and adventuring gear.</h3>
		
		<form action="/app/login" method="post">
			Username: <input type="text" pattern="^(?!.*\.\.)(?!.*\.$)[^\W][\w.]{0,29}$" required autofocus name="username"/>
			<br>
			Password: <input type="password" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$" required name="password"/>
			<br>
			<input type="submit" value="Login"/>
		</form>
		
		<form action="/app/register.jsp" method="get">
			<input type="submit" value="Register"/>
		</form>
	</body>
</html>