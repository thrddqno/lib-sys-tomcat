package net.codejava.registration.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import net.codejava.registration.dao.BooksDao;
import net.codejava.registration.model.Books;

@WebServlet("/admin/getbook")
public class GetBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private BooksDao booksDao;

    public GetBookServlet() {
        super();
        booksDao = new BooksDao();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get the book ID from the request parameter
        int bookId = Integer.parseInt(request.getParameter("id"));

        // Retrieve the book details from the database
		Books book = booksDao.selectBook(bookId);

		// Convert the book object to JSON format
		Gson gson = new Gson();
		String json = gson.toJson(book);

		// Set the response content type to JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// Write the JSON response back to the client
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
