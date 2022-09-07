<%@page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Wyn's Wild Wares</title>
	</head>
	<body>
		<%
		if (session.getAttribute("user_id") != null) response.sendRedirect("main.jsp");
		%>
		<div class="container">
			<h2>Welcome to Wyn's Wild Wares</h2>
			<h3>Your friendly neighborhood dealer of fine magical merchandise and adventuring gear.</h3>
			<h4>User Login</h4>
			<form action="/app/login" method="post">
				<div class="form-group">
					<label for="username">Username</label>
					<input type="text" name="username" id="username" class="form-control" pattern="^(?!.*\.\.)(?!.*\.$)[^\W][\w.]{0,29}$" required autofocus placeholder="Enter username"/>
				</div>
				<div class="form-group">
					<label for="password">Password</label>
					<input type="password" name="password" id="password" class="form-control" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$" required placeholder="Password"/>
				</div>
				<input type="submit" class="btn btn-primary" value="Login"/>
			</form>
			<h4>Don't have an account yet?</h4>
			<form action="/app/register.jsp" method="get">
				<input type="submit" class="btn btn-primary" value="Register"/>
			</form>
		</div>
	</body>
</html>