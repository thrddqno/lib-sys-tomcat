package net.codejava.registration.controller;

import java.io.IOException;
import java.sql.SQLException;

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

@WebServlet("/user/viewbook")
public class ViewBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LogManager.getLogger(ViewBookServlet.class);
    private BooksDao bookDao;

    public ViewBookServlet() {
        super();
        bookDao = new BooksDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String bookIdStr = request.getParameter("id");
        if (bookIdStr == null || bookIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/user/books");
            return;
        }
        int bookId = Integer.parseInt(bookIdStr);

        Books book;

		book = BooksDao.selectBook(bookId);

		if (book == null) {
		    response.sendRedirect(request.getContextPath() + "/user/books");
		    return;
		}

		request.setAttribute("book", book);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/user/viewbook.jsp");
		dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
