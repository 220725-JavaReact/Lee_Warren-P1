<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Wyn's Wild Wares - Register</title>
    </head>
    <body>
        <h2>Welcome to Wyn's Wild Wares</h2>
		<h3>Your friendly neighborhood dealer of fine magical merchandise and adventuring gear.</h3>

        <h4>Register a New User</h4>
        <form action="/app/register" method="post">
            Name: <input type="text" minlength="1" required autofocus name="name"/>
            <br>
            Address: <input type="text" minlength="1" size="100" required name="address"/>
            <br>
            Email: <input type="text" pattern="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?" name="email"/>
            Phone: <input type="tel" pattern="(?:\d{1}\s)?\(?(\d{3})\)?-?\s?(\d{3})-?\s?(\d{4})" name="phone"/>
            <br>
            Username: <input type="text" pattern="^(?!.*\.\.)(?!.*\.$)[^\W][\w.]{0,29}$" required name="username"/>
            <br>
            <small>
                <div id="username_constraints">
                    <ul>Max length 30</ul>
                    <ul>Alphanumeric characters, underscores, and periods allowed</ul>
                    <ul>Username cannot start nor end with a period and cannot have more than one consecutive period</ul>
                </div>
            </small>
            <br>
            Password: <input type="password" pattern="^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$" required name="password"/>
            <br>
            <small>
                <div id="password_constraints">
                    <ul>Must be at least 8 characters</ul>
                    <ul>Must contain at least 1 uppercase letter, 1 lowercase letter, and 1 number</ul>
                    <ul>Special characters are allowed</ul>
                </div>
            </small>
            <br>
            <input type="submit" value="Submit"/>
        </form>
    </body>
</html>