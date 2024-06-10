package net.codejava.registration.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.codejava.registration.dao.BooksDao;
import net.codejava.registration.model.Books;

/**
 * Servlet implementation class BookFormServlet
 */
@WebServlet(urlPatterns = {"/admin/newbook", "/admin/editbook"})
public class BookFormServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private BooksDao booksDao;
	
	
    public BookFormServlet() {
        super();
        booksDao = new BooksDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        if (action.equals("/admin/editbook")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Books existingBook = booksDao.selectBook(id);
            request.setAttribute("book", existingBook);
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/bookform.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        boolean isAvailable = Boolean.parseBoolean(request.getParameter("availability"));

        if (request.getServletPath().equals("/admin/newbook")) {
            Books newBook = new Books(title, author, description, category, isAvailable);
            try {
				booksDao.createBook(newBook);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
        	int id = Integer.parseInt(request.getParameter("id"));
            Books book = new Books(id, title, author, description, category, isAvailable);
            try {
				booksDao.updateBookInfo(book);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        response.sendRedirect(request.getContextPath() + "/admin/books");
    }
}

