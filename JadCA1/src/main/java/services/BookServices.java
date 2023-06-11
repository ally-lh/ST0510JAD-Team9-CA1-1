package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import books.Book;
import config.DataBaseConfig;

public class BookServices {
	public static List<Book> fetchBookData() {
		List<Book> bookData = new ArrayList<>();
		try {

			Connection conn = DataBaseConfig.getConnection();
			System.out.println("Fetch all books data is running");
			// Step 5: Execute SQL Command
			Statement statement = conn.createStatement();
			String fetchAllBookDataQuery = "SELECT * FROM Book INNER JOIN Category on Category.CategoryID=Book.CategoryID";

			ResultSet rs = statement.executeQuery(fetchAllBookDataQuery);
			while (rs.next()) {
				// Retrieve all the necessary fields from the result set
				int bookId = rs.getInt("BookID");
				String title = rs.getString("Title");
				double price = rs.getDouble("Price");
				String author = rs.getString("Author");
				String publisher = rs.getString("Publisher");
				Date pubDate = rs.getDate("PubDate");
				String isbn = rs.getString("ISBN");
				float rating = rs.getFloat("Rating");
				String description = rs.getString("Description");
				String imageUrl = rs.getString("Image");
				String categoryName = rs.getString("CategoryName");
				// Create a Book object and set the retrieved values
				Book book = new Book(bookId, title, author, price, publisher, pubDate, isbn, rating, description,
						imageUrl, categoryName);
				System.out.println(title);
				// Add the book to the search results list
				bookData.add(book);
			}
			// Step 6: Close the resources
			rs.close();
			statement.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookData;
	}
	public static Book fetchBookDataByID(int id) {
		Book bookData = null;
		try {
			Connection conn = DataBaseConfig.getConnection();
			String fetchBookDetailQuery = "SELECT * FROM Book INNER JOIN Category on Category.CategoryID=Book.CategoryID WHERE Book.BookID=?";
			PreparedStatement pstmt = conn.prepareStatement(fetchBookDetailQuery);
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				// Retrieve all the necessary fields from the result set
				int bookId = rs.getInt("BookID");
				String title = rs.getString("Title");
				double price = rs.getDouble("Price");
				String author = rs.getString("Author");
				String publisher = rs.getString("Publisher");
				Date pubDate = rs.getDate("PubDate");
				String isbn = rs.getString("ISBN");
				float rating = rs.getFloat("Rating");
				String description = rs.getString("Description");
				String imageUrl = rs.getString("Image");
				String categoryName = rs.getString("CategoryName");
				// Create a Book object and set the retrieved values
				bookData = new Book(bookId, title, author, price, publisher, pubDate, isbn, rating, description,
						imageUrl, categoryName);
				System.out.println(title);
				// Add the book to the search results list
			}
			// Step 6: Close the resources
			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookData;
	}

	public static List<Book> getBooksByCategory(List<Integer> categoryIDs) {
		List<Book> bookData = new ArrayList<>();
		try {

			Connection conn = DataBaseConfig.getConnection();
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT * FROM Book INNER JOIN Category ON Category.CategoryID = Book.CategoryID WHERE Book.CategoryID IN (");
			for (int i = 0; i < categoryIDs.size(); i++) {
			    queryBuilder.append("?");
			    if (i < categoryIDs.size() - 1) {
			        queryBuilder.append(",");
			    }
			}
			queryBuilder.append(")");

			String fetchBooksByCategoryQuery = queryBuilder.toString();
			System.out.println(fetchBooksByCategoryQuery);
			PreparedStatement pstmt = conn.prepareStatement(fetchBooksByCategoryQuery);
			for (int i = 0; i < categoryIDs.size(); i++) {
			    pstmt.setInt(i + 1, categoryIDs.get(i));
			}
			System.out.print(pstmt);

			ResultSet rs = pstmt.executeQuery();
			System.out.print(rs);
			while (rs.next()) {
				// Retrieve all the necessary fields from the result set
				int bookId = rs.getInt("BookID");
				String title = rs.getString("Title");
				double price = rs.getDouble("Price");
				String author = rs.getString("Author");
				String publisher = rs.getString("Publisher");
				Date pubDate = rs.getDate("PubDate");
				String isbn = rs.getString("ISBN");
				float rating = rs.getFloat("Rating");
				String description = rs.getString("Description");
				String imageUrl = rs.getString("Image");
				String categoryName = rs.getString("CategoryName");
				// Create a Book object and set the retrieved values
				Book book = new Book(bookId, title, author, price, publisher, pubDate, isbn, rating, description,
						imageUrl, categoryName);
				System.out.println(title);
				// Add the book to the search results list
				bookData.add(book);
			}
			// Step 6: Close the resources
			rs.close();
			pstmt.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookData;
	}
	public static String addBook(Book newBook, int quantity) {
	    String message = "";
	    try {
	        Connection conn = DataBaseConfig.getConnection();
	        String addBookQuery = "INSERT INTO Book (Title,Author,Price,Publisher,PubDate,ISBN,Rating,Description,Image,CategoryID) VALUES (?,?,?,?,?,?,?,?,?,?)";

	        // Enable retrieval of auto-generated keys
	        PreparedStatement pstmt = conn.prepareStatement(addBookQuery, Statement.RETURN_GENERATED_KEYS);
	        pstmt.setString(1, newBook.getTitle());
	        pstmt.setString(2, newBook.getAuthor());
	        pstmt.setDouble(3, newBook.getPrice());
	        pstmt.setString(4, newBook.getPublisher());
	        pstmt.setDate(5, newBook.getPubDate());
	        pstmt.setString(6, newBook.getISBN());
	        pstmt.setDouble(7, newBook.getRating());
	        pstmt.setString(8, newBook.getDescription());
	        pstmt.setString(9, newBook.getImageUrl());
	        pstmt.setInt(10, newBook.getCategoryID());

	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            ResultSet generatedKeys = pstmt.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int bookId = generatedKeys.getInt(1);
	                // Use the bookId to insert the new inventory quantity
	                boolean inventoryInserted = insertInventoryQuantity(bookId, quantity);
	                if (inventoryInserted) {
	                    message = "Book added successfully with ID: " + bookId;
	                } else {
	                    message = "Failed to insert inventory quantity for the new book.";
	                }
	            }
	            generatedKeys.close();
	        } else {
	            message = "Failed to add new book.";
	        }

	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        message = "Error occurred while adding new book.";
	    }
	    return message;
	}

	private static boolean insertInventoryQuantity(int bookId, int quantity) {
	    boolean inserted = false;
	    try {
	        Connection conn = DataBaseConfig.getConnection();
	        String addInventoryQuery = "INSERT INTO Inventory (BookID, Quantity) VALUES (?, ?)";
	        PreparedStatement pstmt = conn.prepareStatement(addInventoryQuery);
	        pstmt.setInt(1, bookId);
	        pstmt.setInt(2, quantity);
	        int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	            inserted = true;
	        }
	        pstmt.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return inserted;
	}
	
	public static String deleteBook(int bookID) {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String deleteBookQuery = "DELETE FROM Book WHERE BookID=?";
			PreparedStatement pstmt = conn.prepareStatement(deleteBookQuery);
			pstmt.setInt(1, bookID);
			int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	        	message= "Book deleted successfully";
	        }
	        else {
	        	message= "Failed to delete the book";
	        }
		}catch (Exception e) {
	        e.printStackTrace();
	        message = "Error occurred while deleting the book.";
	    }
		return message;
	}
}
