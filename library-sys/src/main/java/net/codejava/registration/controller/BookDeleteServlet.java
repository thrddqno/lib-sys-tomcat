package net.codejava.registration.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codejava.registration.dao.BooksDao;

@WebServlet(urlPatterns = {"/admin/deletebook"})
public class BookDeleteServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private BooksDao booksDao;

    public BookDeleteServlet() {
    	super();
        booksDao = new BooksDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  // Use POST for deletion
        int id;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            if (id <= 0) {  // Validate the ID
                throw new IllegalArgumentException("Invalid book ID");
            }
            booksDao.deleteBook(id);
        } catch (SQLException | IllegalArgumentException e) {
            // Log the error for debugging
            e.printStackTrace();
            // Send an error message to the user
            request.setAttribute("error", "Failed to delete book. Please try again.");
            request.getRequestDispatcher("/admin/books").forward(request, response);
            return;
        }
        // If deletion is successful, redirect to books list
        response.sendRedirect(request.getContextPath() + "/admin/books");
    }
}