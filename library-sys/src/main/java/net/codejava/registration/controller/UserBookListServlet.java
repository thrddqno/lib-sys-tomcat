package net.codejava.registration.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.codejava.registration.dao.BooksDao;
import net.codejava.registration.model.Books;

@WebServlet(urlPatterns = {"/user/books"})
public class UserBookListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(UserBookListServlet.class);

    public UserBookListServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageIdStr = request.getParameter("page");
        int pageId = 1;
        if (pageIdStr != null && !pageIdStr.isEmpty()) {
            pageId = Integer.parseInt(pageIdStr);
        }
        
        int pageSize = 12;

        try {
            int totalBooks = BooksDao.getTotalBooksCount();
            List<Books> list = BooksDao.listAvailableBooks(pageId, pageSize); // this needs to be modified as per your requirement for user view
            int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

            request.setAttribute("listBook", list);
            request.setAttribute("currentPage", pageId);
            request.setAttribute("totalPages", totalPages);
        } catch (SQLException e) {
            logger.error("Error occurred while fetching books: " + e.getMessage(), e);
            throw new ServletException("Error occurred while fetching books", e);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/user/userlandingpage.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
