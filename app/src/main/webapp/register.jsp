<%@page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx" crossorigin="anonymous">
		<meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Wyn's Wild Wares - Register</title>
    </head>
    <body>
        <%
		if (session.getAttribute("user_id") != null) response.sendRedirect("main.jsp");
		%>
        <div class="container">
            <h2>Welcome to Wyn's Wild Wares</h2>
		    <h3>Your friendly neighborhood dealer of fine magical merchandise and adventuring gear.</h3>
            <h4>Register a New User</h4>
            <form action="/app/register" method="post">
                <small class="form-text text-muted">
                    <p>Fields marked with a <span class="text-danger">*</span> are required.</p>
                </small>
                <div class="form-group">
                    <label for="name">Name<span class="text-danger">*</span></label>
                    <input type="text" name="name" id="name" class="form-control" minlength="1" required autofocus/>
                </div>
                <div class="form-group">
                    <label for="address">Address<span class="text-danger">*</span></label>
                    <input type="text" name="address" id="address" class="form-control" minlength="1" required/>
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" name="email" id="email" class="form-control" pattern="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?"/>
                </div>
                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input type="tel" name="phone" id="phone" class="form-control" pattern="(?:\d{1}\s)?\(?(\d{3})\)?-?\s?(\d{3})-?\s?(\d{4})"/>
                </div>
                <div class="form-group">
                    <label for="username">Username<span class="text-danger">*</span></label>
                    <input type="text" name="username" id="username" class="form-control" pattern="^(?!.*\.\.)(?!.*\.$)[^\W][\w.]{0,29}$" required/>
                    <small class="form-text text-muted">
                        <ul>
                            <li>Max length 30</li>
                            <li>Alphanumeric characters, underscores, and periods allowed</li>
                            <li>Username cannot start nor end with a period and cannot have more than one consecutive period</li>
                        </ul>
                    </small>
                </div>
                <div class="form-group">
                    <label for="password">Password<span class="text-danger">*</span></label>
                    <input type="password" name="password" id="password" class="form-control" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$" required/>
                    <small class="form-text text-muted">
                        <ul>
                            <li>Must be at least 8 characters</li>
                            <li>Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number</li>
                            <li>Special characters are allowed</li>
                        </ul>
                    </small>
                </div>
                <input type="submit" class="btn btn-primary" value="Submit"/>
            </form>
        </div>
    </body>
</html>