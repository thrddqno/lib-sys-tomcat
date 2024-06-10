<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error - Library System</title>
</head>
<body>
    <h2>Error</h2>
    <p>An error occurred:</p>
    <p><%= request.getAttribute("errorMessage") %></p>
    <a href="home.jsp">Back to Home</a>

</body>
</html>
