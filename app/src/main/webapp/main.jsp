<%@page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wyn's Wild Wares - Dashboard</title>
	</head>
	<body>
		<%
		if (session.getAttribute("user_id") == null) response.sendRedirect("login.jsp");
		String accessLevel = (String) session.getAttribute("access_level");
		%>
		<div class="container">
			<h2>Welcome to Wyn's Wild Wares</h2>
			<h3>Your friendly neighborhood dealer of fine magical merchandise and adventuring gear.</h3>
			<div class="btn-group btn-group-justified">
				<a href="/app/browse" class="btn btn-primary">Browse</a>
				<a href="/app/orders" class="btn btn-primary">Orders</a>
				<a href="/app/account" class="btn btn-primary">Account</a>
				<%
				if (accessLevel.equals("ADMIN")) {
				%>
				<a href="/app/admin" class="btn btn-primary">Admin</a>
				<%
				}
				%>
				<a href="/app/logout" class="btn btn-primary">Logout</a>
			</div>
		</div>
	</body>
</html>