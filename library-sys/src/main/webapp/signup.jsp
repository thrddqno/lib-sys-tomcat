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
    <div class="signup-form">
        <div class="container">
            <div class="main">
                <div class="content">
                    <h2>Sign Up</h2>
                    <form action="<%= request.getContextPath() %>/register" method="post">
                        <input type="text" name="username" placeholder="Username" required>
                        <input type="email" name="email" placeholder="Email" required>
                        <input type="password" name="password" placeholder="Password" required>
                        <input type="tel" pattern="[0-9]{11}" name="contact" placeholder="Contact No." required>
                        <select name="userType">
					        <option value="true">Admin</option>
					        <option value="false" selected>User</option>
					    </select>
                        <button class="btn" type="submit">Sign up</button>
                    </form>
                    <p class="error-message">
					    <% if (request.getAttribute("error") != null) { %>
					        <%= request.getAttribute("error") %>
					    <% } %>
					</p>
                    <p class="account">Already have an account? <a href="login.jsp">Log in</a></p>
                </div>
                <div class="form-img">
                </div>
            </div>
        </div>
    </div>
</body>
</html>
