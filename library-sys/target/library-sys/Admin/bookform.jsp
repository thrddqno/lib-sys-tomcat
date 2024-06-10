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
    <link rel="stylesheet" href="../css/admin.css" />
    <link rel="stylesheet" type="text/css" href="../css/general-sans.css">
    <title>Edit Book - Library System Admin</title>
</head>

<body class="secondary20-bg">
    <div class="d-flex" id="wrapper">
        <!-- Sidebar -->
        <div class="bg-white" id="sidebar-wrapper">
            <div class="sidebar-heading text-center py-4 primary-text fs-4 fw-bold text-uppercase border-bottom">Library System</div>
            <div class="list-group list-group-flush my-3">
                <a href="<%=request.getContextPath()%>/admin/home.jsp" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"><i class="fas fa-chart-line me-2"></i>Dashboard</a>
                <a href="<%=request.getContextPath()%>/admin/books?page=1" class="list-group-item list-group-item-action bg-transparent second-text active"><i class="fas fa-book-open me-2"></i>Books</a>
                <a href="#" class="list-group-item list-group-item-action bg-transparent second-text fw-bold"><i class="fas fa-folder me-2"></i>Issuances</a>
            </div>
        </div>
        <!-- /#sidebar-wrapper -->

        <!-- Page Content -->
        <div id="page-content-wrapper">
            <nav class="navbar navbar-expand-lg navbar-light bg-transparent py-4 px-4">
                <div class="d-flex align-items-center">
                    <h2 class="fs-2 m-0 primary-text">Edit Book</h2>
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
            <div class="container mt-4" style="width: 50rem">
                <div class="card">
                    <div class="card-body">
                        <form action="<%=request.getContextPath()%>/admin/editbook" method="post">
                            <input type="hidden" name="id" value="${book.bookId}">
                            <div class="mb-3">
                                <label for="title" class="form-label">Title:</label>
                                <input type="text" class="form-control" id="title" name="title" value="${book.title}">
                            </div>
                            <div class="mb-3">
                                <label for="author" class="form-label">Author:</label>
                                <input type="text" class="form-control" id="author" name="author" value="${book.author}">
                            </div>
                            <div class="mb-3">
                                <label for="description" class="form-label">Description:</label>
                                <textarea class="form-control" id="description" name="description" rows=5>${book.description}</textarea>
                            </div>
                            <div class="mb-3">
                                <label for="category" class="form-label">Category:</label>
                                <input type="text" class="form-control" id="category" name="category" value="${book.categories}">
                            </div>
                            <div class="mb-3">
                                <label for="availability" class="form-label">Availability:</label>
                                <select class="form-select" id="availability" name="availability">
                                    <option value="true" ${book.isAvailable ? 'selected' : ''}>Available</option>
                                    <option value="false" ${!book.isAvailable ? 'selected' : ''}>Not Available</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-primary my-2">Save Changes</button>
                        </form>
                        <form action="<%=request.getContextPath()%>/admin/deletebook?id=${book.bookId}" method="post">
                            <input type="hidden" id="id" name="id" value="${book.bookId}">
                            <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this book?')">Delete</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</body>

</html>

