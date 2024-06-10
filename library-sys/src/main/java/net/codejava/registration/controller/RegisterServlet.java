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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao;

    public void init() {
        userDao = new UserDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String contactStr = request.getParameter("contact");
        String userTypeString = request.getParameter("userType");
        boolean isAdmin = Boolean.parseBoolean(userTypeString);

        // Check if username is taken
        try {
            if (userDao.isUsernameTaken(username)) {
                // Username is already taken, set an error message and redirect to signup page
                String errorMessage = "Username already taken.";
                request.setAttribute("error", errorMessage);
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                return; // Exit from the servlet method
            }

            // Check if email is taken
            if (userDao.isEmailTaken(email)) {
                // Email is already taken, set an error message and redirect to signup page
                String errorMessage = "Email already registered.";
                request.setAttribute("error", errorMessage);
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                return; // Exit from the servlet method
            }

            // Check if contact number is taken
            if (userDao.isContactNoTaken(contactStr)) {
                // Contact number is already taken, set an error message and redirect to signup page
                String errorMessage = "Contact number already in use.";
                request.setAttribute("error", errorMessage);
                request.getRequestDispatcher("signup.jsp").forward(request, response);
                return; // Exit from the servlet method
            }

            try {
                // All validations passed, proceed with registration
                User user = new User();
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
                user.setContact(contactStr);
                user.setAdmin(isAdmin);

                Pair<Integer, Integer> registrationResult = userDao.registerUser(user);

                int result = registrationResult.getKey();
                int userId = registrationResult.getValue();

                if (result > 0 && userId != -1) {
                    // Registration successful
                    // Set user ID in session
                    HttpSession session = request.getSession();
                    session.setAttribute("userId", userId);

                    // Redirect to appropriate page based on user type
                    if (user.isAdmin()) {
                        response.sendRedirect(request.getContextPath() + "/admin/books?page=1");
                        return; // Ensure no further processing after the redirect
                    } else {
                        response.sendRedirect(request.getContextPath() + "/user/books?page=1");
                        return; // Ensure no further processing after the redirect
                    }
                } else {
                    // Registration failed
                    String errorMessage = "Redirection Failed. Please Log in";
                    request.setAttribute("error", errorMessage);
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return; // Ensure no further processing after forwarding
                }
            } catch (Exception e) {
                // Handle registration failure
                e.printStackTrace(); // Log the exception for debugging
                response.sendRedirect("signup.jsp"); // Redirect to a specific error page
                return; // Ensure no further processing after the redirect
            }

        } catch (Exception e) {
            // Handle registration failure
            e.printStackTrace(); // Log the exception for debugging
            response.sendRedirect("home.jsp"); // Redirect to a generic error page
        }
    }
}
