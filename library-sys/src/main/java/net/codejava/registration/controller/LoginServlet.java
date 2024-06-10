package net.codejava.registration.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javafx.util.Pair;
import net.codejava.registration.dao.UserDao;
import net.codejava.registration.model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        try {
            Pair<Boolean, Integer> validationResult = userDao.validateAndGetUserId(user);
            boolean status = validationResult.getKey();
            int userId = validationResult.getValue();

            if (status) {
                // User authenticated successfully, create a session
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);

                // Retrieve additional user information such as user type (admin or regular user)
                boolean isAdmin = userDao.isAdmin(username);

                // Redirect based on user's role
                if (isAdmin) {
                	response.sendRedirect(request.getContextPath() + "/admin/books?page=1"); // Redirect to admin page
                } else {
                	response.sendRedirect(request.getContextPath() + "/user/books?page=1"); // Redirect to regular user page
                }
            } else {
                // Invalid credentials, set an error attribute and forward back to login page
                request.setAttribute("error", "Invalid username or password. Please try again.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Handle exception appropriately
            e.printStackTrace();
            
            String errorMessage = "An error occurred while processing your request.";
            request.setAttribute("errorMessage", errorMessage);
            request.getRequestDispatcher("error.jsp").forward(request, response);
            response.sendRedirect("error.jsp");
        }
    }
}
