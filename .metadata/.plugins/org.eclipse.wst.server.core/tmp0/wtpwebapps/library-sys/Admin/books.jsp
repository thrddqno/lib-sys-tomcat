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
    <title>Books - Library System Admin</title>
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
                    <h2 class="fs-2 m-0 primary-text">Books</h2>
                </div>
                <div class="container text-right">
		        	<a class="btn btn-success" data-bs-toggle="modal" data-bs-target="#bookFormModal">Add New Book</a>
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
            <c:choose>
        		<c:when test="${empty listBook}">
            		<p class="booksAvailable">No books available.</p>
        		</c:when>
        		<c:otherwise>
		            <div class="container-card py-1 d-flex flex-wrap">
		            	<c:forEach var="book" items="${listBook}">
			            <div class="col-md-3 py-2">
			            	<div class="card" style="max-width: 23rem;">
			            		<img src="../images/defaultcover.png" class="card-img-top" alt="cover">
			            		<div class="card-body overflow-hidden">
			            			<a class="card-title primary-text" href="<%=request.getContextPath()%>/admin/editbook?id=${book.bookId}">${book.title}</a>
			            			<p class="card-text author second-text">${book.author}</p>
			            			<p class="card-text card-desc second-text">${book.description}</p>
			            		</div>
			            		<div class="d-flex justify-content-around">
			            			<p class="isAvailable second-text" style="background-color: ${book.isAvailable ? 'green' : 'red'};">
								        ${book.isAvailable ? 'Available' : 'Not Available'}
								    </p>
			            		</div>
			            	</div>
			            </div>
			            </c:forEach> 
		            </div>
            </c:otherwise>
    		</c:choose>    
        </div>
        
        <div class="pagination-wrapper">
	        <nav aria-label="pagination justify-content-center">
	        	<ul class="pagination pagination-sm justify-content-center">
	        		<c:forEach var="i" begin="1" end="${totalPages}">
		        		<li class="page-item ${currentPage eq i ? 'active' : ''}">
		        			<a class="page-link" href="<%=request.getContextPath()%>/admin/books?page=${i}">${i}</a>
		        		</li>
		        	</c:forEach>
	        	</ul>
	        </nav>
        </div>
    </div>
    <!-- /#page-content-wrapper -->

	<!-- Modal -->
	<div class="modal fade" id="bookFormModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	    <div class="modal-dialog modal-dialog-centered">
	        <div class="modal-content">
	            <div class="modal-body">
	                <button type="button" class="btn-close btn-close-black" data-bs-dismiss="modal" aria-label="Close"></button>
	                <div class="myform">
	                    <h2 class="text-center">${book != null ? 'Edit Book' : 'Add Book'}</h2>
	                    <form action="${book != null ? 'editbook' : 'newbook'}" method="post">
	                        <div class="mb-3 mt-4">
	                            <label for="title" class="form-label">Title</label>
	                            <input type="text" class="form-control" id="title" name="title" value="${book != null ? book.title : ''}" required>
	                        </div>
	                        <div class="mb-3">
	                            <label for="author" class="form-label">Author</label>
	                            <input type="text" class="form-control" id="author" name="author" value="${book != null ? book.author : ''}">
	                        </div>
	                        <div class="mb-3">
	                            <label for="description" class="form-label">Description</label>
	                            <textarea class="form-control" id="description" name="description" rows="3">${book != null ? book.description : ''}</textarea>
	                        </div>
	                        <div class="mb-3">
	                            <label for="category" class="form-label">Category(s)</label>
	                            <input type="text" class="form-control" id="category" name="category" value="${book != null ? book.categories : ''}">
	                        </div>
	                        <div class="mb-3">
	                            <label for="availability" class="form-label">Availability</label>
	                            <select class="form-control" id="availability" name="availability">
	                                <option value="true" ${book != null && book.isAvailable ? 'selected' : ''}>Available</option>
	                                <option value="false" ${book != null && !book.isAvailable ? 'selected' : ''}>Not Available</option>
	                            </select>
	                        </div>
	                        <button type="submit" class="btn btn-save mt-3">${book != null ? 'Save Changes' : 'Add Book'}</button>
	                    </form>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>