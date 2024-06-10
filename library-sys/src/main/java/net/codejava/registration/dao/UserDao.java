package net.codejava.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.util.Pair;
import net.codejava.registration.model.User;

public class UserDao {
    
    private static final String DB_URL = "jdbc:derby://localhost:1527/library";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin";

    public Pair<Boolean, Integer> validateAndGetUserId(User user) throws ClassNotFoundException {
        boolean status = false;
        int userId = -1; // Default value for userId

        Class.forName("org.apache.derby.jdbc.ClientDriver");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users.userInfo WHERE username = ? AND password = ?")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                // If the user exists, retrieve the userId
                userId = rs.getInt("id");
                status = true; // Set status to true indicating successful validation
            }
        } catch (SQLException e) {
            // Handle SQL exception
            handleSQLException(e);
        }

        return new Pair<>(status, userId);
    }

    public Pair<Integer, Integer> registerUser(User user) throws ClassNotFoundException {

        String INSERT_USER_SQL = "INSERT INTO users.userInfo (username, email, password, contact, admin) VALUES (?, ?, ?, ?, ?)";

        int result = 0;
        int userId = -1; // Default value for userId

        Class.forName("org.apache.derby.jdbc.ClientDriver");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getContact());
            preparedStatement.setBoolean(5, user.isAdmin());

            // Execute the update
            result = preparedStatement.executeUpdate();

            // Get the generated keys (including the auto-generated user ID)
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userId = generatedKeys.getInt(1); // Retrieve the generated user ID
                }
            }

        } catch (SQLException e) {
            // Handle SQL exception
            handleSQLException(e);
        }
        // Return a Pair object containing the result and the generated user ID
        return new Pair<>(result, userId);
    }
    
    public boolean isAdmin(String username) throws ClassNotFoundException {
        boolean isAdmin = false;

        // retrieve admin status based sa username
        String sql = "SELECT admin FROM users.userInfo WHERE username = ?";
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            // check rs is returned and retrieve the admin status
            if (resultSet.next()) {
                isAdmin = resultSet.getBoolean("admin");
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return isAdmin;
    }


    private void handleSQLException(SQLException ex) {
        // Log or handle the exception as per your application's requirements
        ex.printStackTrace();
    }
    
    public boolean isUsernameTaken(String username) throws ClassNotFoundException {
        boolean isTaken = false;

        String sql = "SELECT COUNT(*) FROM users.userInfo WHERE username = ?";
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if a result is returned and if count is greater than 0, then username is taken
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isTaken = count > 0;
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return isTaken;
    }

    public boolean isEmailTaken(String email) throws ClassNotFoundException {
        boolean isTaken = false;

        String sql = "SELECT COUNT(*) FROM users.userInfo WHERE email = ?";
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isTaken = count > 0;
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return isTaken;
    }

    public boolean isContactNoTaken(String contact) throws ClassNotFoundException {
        boolean isTaken = false;

        String sql = "SELECT COUNT(*) FROM users.userInfo WHERE contact = ?";
        
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, contact);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                isTaken = count > 0;
            }
        } catch (SQLException e) {
            handleSQLException(e);
        }

        return isTaken;
    }
    
    public static String getUsername(int userId) throws ClassNotFoundException {
        String username = "";
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            PreparedStatement stmt = conn.prepareStatement("SELECT username FROM users.userInfo WHERE id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("username");
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return username;
    }
}
