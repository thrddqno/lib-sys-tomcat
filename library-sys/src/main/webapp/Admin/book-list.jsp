<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Books | Library System Admin</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="../css/admin-books.css">
</head>
<body >
<header>
    <nav class="navbar navbar-expand-md navbar-dark" style="background-color: tomato">
        <div>
            <a href="https://www.javaguides.net" class="navbar-brand">Library System</a>
        </div>
        <ul class="navbar-nav">
            <li><a href="<%=request.getContextPath()%>/admin/books" class="nav-link">Books</a></li>
            <li><a href="<%=request.getContextPath()%>/logout" class="nav-link">Logout</a></li>
        </ul>
    </nav>
</header>
<br>
<div class="container">
    <h3 class="text-center">List of Books</h3>
    <hr>
    <div class="container text-left">
        <a href="<%=request.getContextPath()%>/admin/newbook" class="btn btn-success">Add New Book</a>
    </div>
    <br>
    <c:choose>
        <c:when test="${empty listBook}">
            <p>No books available.</p>
        </c:when>
        <c:otherwise>
            <div class="row">
                <c:forEach var="book" items="${listBook}">
                    <div class="col">
                        <div class="card book-card">
                            <img src="../images/defaultcover.png" class="card-img-top book-image" alt="Book Image">
                            <div class="card-body">
                                <h5 class="card-title">${book.title}</h5>
                                <p class="card-text author">${book.author}</p>
                                <p class="card-text">${book.description}</p>
                                <p class="card-text category">${book.categories}</p>
                            </div>
                            <div class="d-flex justify-content-around mb-5">
                                <p class="card-text">${book.isAvailable ? 'Available' : 'Not Available'}</p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- Pagination -->
            <nav aria-label="Books Pagination">
                <ul class="pagination justify-content-center">
                    <c:forEach var="i" begin="1" end="${totalPages}">
                        <li class="page-item ${currentPage eq i ? 'active' : ''}">
                            <a class="page-link" href="<%=request.getContextPath()%>/admin/books?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>
