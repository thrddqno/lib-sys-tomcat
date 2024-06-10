<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" />
    <link rel="stylesheet" href="../css/user.css" />
    <link rel="stylesheet" type="text/css" href="../css/general-sans.css">
    <title>View Book - Library System</title>
</head>

<body class="secondary20-bg">
    <div class="d-flex" id="wrapper">
        <!-- Sidebar -->
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">
            <nav class="navbar navbar-expand-lg navbar-light bg-transparent py-4 px-4">
                <div class="d-flex align-items-center">
                    <h2 class="fs-2 m-0 primary-text">Library System</h2>
                </div>

                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle second-text fw-bold" href="#" id="navbarDropdown"
                                role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                <i class="fas fa-user me-2"></i>
                            </a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="<%=request.getContextPath()%>/logout">Logout</a></li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </nav>

            <!-- Book Details -->
            <div class="container mt-4">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <h6 class="card-subtitle card-author mb-2 text-muted">Author: ${book.author}</h6>
                        <p class="card-text description">${book.description}</p>
                        <p class="card-text"><strong>Category:</strong> ${book.categories}</p>
                        <p class="card-text"><strong>Availability:</strong> ${book.isAvailable ? 'Available' : 'Not Available'}</p>
                        <a href="<%=request.getContextPath()%>/user/books" class="btn btn-primary">Back to Books</a>
                        <c:if test="${book.isAvailable}">
                			<a href="<%=request.getContextPath()%>/user/rent?id=${book.bookId}" class="btn btn-primary">Rent</a>
           				</c:if>
                    </div>
                </div>
            </div>
            <!-- End Book Details -->

        </div>
        <!-- /#page-content-wrapper -->

    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
