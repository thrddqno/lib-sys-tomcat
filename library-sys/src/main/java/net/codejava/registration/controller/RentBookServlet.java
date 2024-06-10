package net.codejava.registration.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.codejava.registration.dao.BooksDao;

@WebServlet("/user/rent")
public class RentBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(RentBookServlet.class);

    public RentBookServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookIdStr = request.getParameter("id");
        if (bookIdStr == null || bookIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/books");
            return;
        }
        int bookId = Integer.parseInt(bookIdStr);

        try {
            // Update book availability to false (rented)
            boolean success = BooksDao.updateBookAvailability(bookId, false);
            if (!success) {
                logger.error("Failed to update book availability for bookId: {}", bookId);
                // Redirect back to the book list page or display an error message
                response.sendRedirect(request.getContextPath() + "/user/books");
                return;
            }

            // Redirect back to the book list page or any other appropriate page
            response.sendRedirect(request.getContextPath() + "/user/books");
        } catch (SQLException e) {
            logger.error("Error occurred while renting book: " + e.getMessage(), e);
            throw new ServletException("Error occurred while renting book", e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
