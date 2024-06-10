<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign up - Library System</title>
    <link rel="stylesheet" type="text/css" href="css/general-sans.css">
    <link rel="stylesheet" href="./css/styles.css">
</head>
<body>
    <header>
        <a class="logo" href="home.jsp">Library System</a>
    </header>
    <div class="login-form">
        <div class="login-container">
            <div class="main">
                <div class="content">
                    <h2>Log in</h2>
                    <form action="<%= request.getContextPath() %>/login" method="post">
                        <input type="text" name="username" placeholder="Username" required>
                        <input type="password" name="password" placeholder="Password" required>
                        <button class="btn" type="submit">Login</button>
                    </form>
					<p class="error-message">
					    <% if (request.getAttribute("error") != null) { %>
					        <%= request.getAttribute("error") %>
					    <% } %>
					</p>
                    <p class="account">Haven't registered? <a href="signup.jsp">Sign up</a></p>
                </div>
                <div class="form-img">
                </div>
            </div>
        </div>
    </div>
</body>
</html>
