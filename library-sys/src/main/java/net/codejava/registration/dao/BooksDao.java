package net.codejava.registration.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.codejava.registration.model.Books;

public class BooksDao {
	private static final String DB_URL = "jdbc:derby://localhost:1527/library";
    private static final String DB_USER = "admin";
    private static final String DB_PASSWORD = "admin";
    
    private static final Logger logger = LogManager.getLogger(BooksDao.class);
    
    //statements
    
    protected static Connection getConnection() {
    	Connection connection = null;
    	try {
    		Class.forName("org.apache.derby.jdbc.ClientDriver");
    		connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    	} catch (SQLException e) {
    		logger.error("SQLException occurred while getting connection: {}", e.getMessage());

    	} catch (ClassNotFoundException e) {
    		logger.error("ClassNotFoundException occurred while getting connection: {}", e.getMessage());
    	}
    	return connection;
    }
    
    //create book info
    private static final String INSERT_BOOKS_SQL = "INSERT INTO books.bookInfo (title, author, description, category, isavailable) VALUES (?, ?, ?, ?, ?)";
    public void createBook(Books book) throws SQLException {
    	try(Connection connection = getConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOOKS_SQL)){
    		preparedStatement.setString(1, book.getTitle());
    		preparedStatement.setString(2, book.getAuthor());
    		preparedStatement.setString(3, book.getDescription());
    		preparedStatement.setString(4, book.getCategories());
    		preparedStatement.setBoolean(5, book.getIsAvailable());
    		preparedStatement.executeUpdate();
    	} catch (Exception e) {
    		logger.error("SQLException occurred while creating book: {}", e.getMessage());
    	}
    }
    
    
    //update book info
    private static final String UPDATE_BOOK_INFO_SQL = "UPDATE books.bookInfo set title = ?, author = ?, description = ?, category = ?, isavailable = ? where bookid = ?";
    public boolean updateBookInfo(Books book) throws SQLException {
    	boolean rowUpdated;
    	try(Connection connection = getConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_INFO_SQL)){
    		preparedStatement.setInt(6, book.getBookId());
    		preparedStatement.setString(1, book.getTitle());
    		preparedStatement.setString(2, book.getAuthor());
    		preparedStatement.setString(3, book.getDescription());
    		preparedStatement.setString(4, book.getCategories());
    		preparedStatement.setBoolean(5, book.getIsAvailable());
    		
    		rowUpdated = preparedStatement.executeUpdate() > 0;
    	}
    	return rowUpdated;
    }
    
    //select user by id
    private static final String SELECT_BOOK_BY_ID = "SELECT bookid, title, author, description, category, isavailable from books.bookinfo where bookid = ?";
    public static Books selectBook(int id) {
    	Books book = null;
    	
    	try(Connection connection = getConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_ID)){
    	preparedStatement.setInt(1, id);
    	System.out.println(preparedStatement);
    	ResultSet rs = preparedStatement.executeQuery();	
    	
    	while (rs.next()) {
    		int bookid = rs.getInt("bookid");
    		String title = rs.getString("title");
    		String author = rs.getString("author");
    		String description = rs.getString("description");
    		String category = rs.getString("category");
    		boolean isAvailable = rs.getBoolean("isavailable");
    		book = new Books(bookid, title,author, description, category, isAvailable);
    	}
    	
    	} catch (Exception e) {
    		logger.error("SQLException occurred while selecting book: {}", e.getMessage());
    	}
    	return book;
    	
    }
    
    //select all
    private static final String SELECT_BOOK_BY_LIMIT = "SELECT * FROM (SELECT ROW_NUMBER() OVER() AS rownum, books.bookInfo.* FROM books.bookInfo) AS tmp WHERE rownum > ? and rownum <= ?";
    public static List<Books> listBooks(int page, int pageSize){
        List<Books> list = new ArrayList<Books>();
        try(Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BOOK_BY_LIMIT)){
            
        	
            int offset = (page - 1) * pageSize;
            int multi = pageSize * page;
            preparedStatement.setInt(1, offset);
            
            if(page == 1) {
            	preparedStatement.setInt(2, pageSize);
            	System.out.println(pageSize);
            } else {
            	preparedStatement.setInt(2, multi);
            	System.out.println(multi);
            }
            
            
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int bookid = rs.getInt("bookid");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String description = rs.getString("description");
                    String category = rs.getString("category");
                    boolean isAvailable = rs.getBoolean("isavailable");
                    Books book = new Books(bookid, title, author, description, category, isAvailable);
                    
                    list.add(book);
                }
            }
        } catch (SQLException e) {
            logger.error("Error occurred while selecting list from limit: {}", e.getMessage());
        }
        return list;
    }

    
    //delete book;
    private static final String DELETE_BOOK_SQL = "DELETE FROM BOOKS.BOOKINFO WHERE bookid = ?";
    public boolean deleteBook(int id) throws SQLException {
    	boolean rowDeleted = false;
    	try (Connection connection = getConnection();
    			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_BOOK_SQL)){
    		preparedStatement.setInt(1, id);
    		rowDeleted = preparedStatement.executeUpdate() > 0;
    	} catch (SQLException e) {
    		logger.error("SQLException occurred while deleting book: {}", e.getMessage());
    	}
    	return rowDeleted;
    }
    
    public static int getTotalBooksCount() throws SQLException {
        int totalBooks = 0;
        String query = "SELECT COUNT(*) AS total FROM BOOKS.BOOKINFO";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                totalBooks = resultSet.getInt("total");
            }
        }

        return totalBooks;
    }
    
 // update book availability
    private static final String UPDATE_BOOK_AVAILABILITY_SQL = "UPDATE books.bookInfo SET isavailable = ? WHERE bookid = ?";

    public static boolean updateBookAvailability(int bookId, boolean isAvailable) throws SQLException {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOOK_AVAILABILITY_SQL)) {
            preparedStatement.setBoolean(1, isAvailable);
            preparedStatement.setInt(2, bookId);

            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.error("SQLException occurred while updating book availability: {}", e.getMessage());
        }
        return rowUpdated;
    }
    
    private static final String SELECT_AVAILABLE_BOOKS_BY_LIMIT = "SELECT * FROM (SELECT ROW_NUMBER() OVER() AS rownum, books.bookInfo.* FROM books.bookInfo WHERE isavailable = true) AS tmp WHERE rownum > ? and rownum <= ?";

    public static List<Books> listAvailableBooks(int page, int pageSize) {
        List<Books> list = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_AVAILABLE_BOOKS_BY_LIMIT)) {

            int offset = (page - 1) * pageSize;
            int multi = pageSize * page;
            preparedStatement.setInt(1, offset);

            if (page == 1) {
                preparedStatement.setInt(2, pageSize);
                System.out.println(pageSize);
            } else {
                preparedStatement.setInt(2, multi);
                System.out.println(multi);
            }

            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    int bookid = rs.getInt("bookid");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String description = rs.getString("description");
                    String category = rs.getString("category");
                    boolean isAvailable = rs.getBoolean("isavailable");
                    Books book = new Books(bookid, title, author, description, category, isAvailable);
                    
                    list.add(book);
                }
            }
        } catch (SQLException e) {
            logger.error("Error occurred while selecting list of available books from limit: {}", e.getMessage());
        }
        return list;
    }

 
}
