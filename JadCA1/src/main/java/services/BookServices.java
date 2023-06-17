package services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Book;
import config.*;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

public class BookServices {
	public static List<Book> fetchBookData(int pageNumber, int recordsPerPage) {
	    List<Book> bookData = new ArrayList<>();
	    try {
	        Connection conn = DataBaseConfig.getConnection();
	        System.out.println("Fetch all books data is running");

	        // Calculate the offset based on the page number and records per page
	        int offset = (pageNumber - 1) * recordsPerPage;
	        System.out.print("offset" + offset);
	        System.out.print("pageNumber" + pageNumber);
	        System.out.print("recordsPerPage" + recordsPerPage);
	        // Create the SQL query with pagination
	        String fetchAllBookDataQuery = 
	        		"SELECT * FROM Book "
	        		+ "INNER JOIN Category ON Category.CategoryID = Book.CategoryID "
	        		+ "INNER JOIN Inventory ON Inventory.BookID = Book.BookID "
	        		+ "LIMIT ? "
	        		+ "OFFSET ?";
	        PreparedStatement pstmt = conn.prepareStatement(fetchAllBookDataQuery);
	        pstmt.setInt(1, recordsPerPage);
	        pstmt.setInt(2, offset);
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
	            int quantity = rs.getInt("Qty");
	            // Create a Book object and set the retrieved values
	            Book book = new Book(bookId, title, author, price, publisher, pubDate, isbn, rating, description,
	                    imageUrl, categoryName,quantity);
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
	
	public static int fetchBookNumbers() {
		int bookNum= 0;
		try {
			Connection conn = DataBaseConfig.getConnection();
			String fetchBookNumberQuery =
					"SELECT COUNT(BookID) "
					+ "as bookAmount "
					+ "FROM Book";
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(fetchBookNumberQuery);
			while(rs.next()) {
				bookNum = rs.getInt("bookAmount");
			}
		}catch (Exception e) {
			e.printStackTrace();
		} 
		return bookNum;
	}
	public static Book fetchBookDataByID(int id) {
		Book bookData = null;
		try {
			Connection conn = DataBaseConfig.getConnection();
			String fetchBookDetailQuery = 
					"SELECT * FROM Book "
					+ "INNER JOIN Category on Category.CategoryID=Book.CategoryID "
					+ "INNER JOIN Inventory on Inventory.BookID = Book.BookID "
					+ "WHERE Book.BookID=?";
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
				int categoryID = rs.getInt("CategoryID");
				int quantity = rs.getInt("Qty");
				// Create a Book object and set the retrieved values
				bookData = new Book(bookId, title, author, price, publisher, pubDate, isbn, rating, description,
						imageUrl, categoryName,categoryID,quantity);
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
	
	public static List<Book> performSearch(String searchTerm, String searchCat){

		System.out.print("Searching for book^^");
		List<Book> searchResults = new ArrayList<>();
		try {
	        Connection conn = DataBaseConfig.getConnection();

		    String sqlStr;
			if(!searchTerm.isEmpty() && (searchCat.isEmpty() || searchCat.equals("Category") )) { 
				searchTerm = "%"+searchTerm+"%";
				sqlStr = "select * from Book inner join Category on Category.CategoryID= Book.CategoryID where Book.Title like ? OR Book.Author like ? ";
			} else if(searchTerm.isEmpty() && (!searchCat.isEmpty() && !(searchCat.equals("Category")))) {
	
				 sqlStr = "select * from Book inner join Category on Category.CategoryID= Book.CategoryID where Category.CategoryID = ? ";
			} else if(!searchTerm.isEmpty() && !searchCat.isEmpty()){ 
				searchTerm = "%"+searchTerm+"%";
				sqlStr = "select * from Book inner join Category on Category.CategoryID= Book.CategoryID where Book.Title like ? OR Book.Author like ? AND Category.CategoryID = ?  ";
			} else if(searchTerm.isEmpty() && searchCat.equals("Category")) { 
				sqlStr = "SELECT * FROM Book INNER JOIN Category on Category.CategoryID=Book.CategoryID";
			} else { 
				sqlStr = "";
			}
			
		    PreparedStatement pstmt = conn.prepareStatement(sqlStr);
		    Statement statement = conn.createStatement();
		    ResultSet rs;
		    if(!searchTerm.isEmpty() && (searchCat.isEmpty() || searchCat.equals("Category") )) { 
				System.out.print("This");
		    	pstmt.setString(1,searchTerm);
				    pstmt.setString(2, searchTerm);
				    rs = pstmt.executeQuery();
				    
			} else if(searchTerm.isEmpty() && (!searchCat.isEmpty() && !(searchCat.equals("Category")))) {
				System.out.print("1This");
				pstmt.setString(1,searchCat);
				 rs = pstmt.executeQuery();
				    
			} else if(!searchTerm.isEmpty() && (!searchCat.isEmpty() && !searchCat.equals("Category"))){ 
				System.out.print("2This");
				pstmt.setString(1,searchTerm);
				    pstmt.setString(2, searchTerm);
				    pstmt.setString(3,searchCat);
				     rs = pstmt.executeQuery();
				    
			} else if(searchTerm.isEmpty() && searchCat.equals("Category")) {
				System.out.print("3This");
				System.out.print(sqlStr);
				rs = statement.executeQuery(sqlStr);
			} else { 
				System.out.print("6This");
				rs = statement.executeQuery(sqlStr);
			}
	
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
	            String categoryName= rs.getString("CategoryName");
	            // Create a Book object and set the retrieved values
	            Book book = new Book(bookId,title,author,price,publisher,pubDate,isbn,rating,description,imageUrl,categoryName);
	            System.out.println(title);
	            System.out.println(imageUrl);
	            // Add the book to the search results list
	            searchResults.add(book);
	        }
		 // Step 6: Close the resources
	        rs.close();
	        pstmt.close();
	        statement.close();
	        conn.close();
	        

		} catch(Exception e){
			e.printStackTrace();
		}
		return searchResults;
		
	}

	public static List<Book> getBooksByCategory(List<Integer> categoryIDs) {
		List<Book> bookData = new ArrayList<>();
		try {

			Connection conn = DataBaseConfig.getConnection();
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append(
					"SELECT * FROM Book "
					+ "INNER JOIN Category ON Category.CategoryID = Book.CategoryID "
					+ "WHERE Book.CategoryID IN (");
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
	
	public static String addBook(Book newBook) {
	    String message = "";
	    Connection conn = null;
	    PreparedStatement bookStatement = null;
	    PreparedStatement inventoryStatement = null;
	    try {
	        conn = DataBaseConfig.getConnection();
	        conn.setAutoCommit(false); // Set auto-commit to false

	        String addBookQuery = "INSERT INTO Book (Title,Author,Price,Publisher,PubDate,ISBN,Rating,Description,Image,CategoryID) VALUES (?,?,?,?,?,?,?,?,?,?)";
	        bookStatement = conn.prepareStatement(addBookQuery, Statement.RETURN_GENERATED_KEYS);
	        bookStatement.setString(1, newBook.getTitle());
	        bookStatement.setString(2, newBook.getAuthor());
	        bookStatement.setDouble(3, newBook.getPrice());
	        bookStatement.setString(4, newBook.getPublisher());
	        bookStatement.setDate(5, newBook.getPubDate());
	        bookStatement.setString(6, newBook.getISBN());
	        bookStatement.setDouble(7, newBook.getRating());
	        bookStatement.setString(8, newBook.getDescription());
	        bookStatement.setString(9, newBook.getImageUrl());
	        bookStatement.setInt(10, newBook.getCategoryID());

	        int rowsAffected = bookStatement.executeUpdate();
	        if (rowsAffected > 0) {
	            ResultSet generatedKeys = bookStatement.getGeneratedKeys();
	            if (generatedKeys.next()) {
	                int bookId = generatedKeys.getInt(1);

	                String addInventoryQuery = "INSERT INTO Inventory (BookID, Qty) VALUES (?, ?)";
	                inventoryStatement = conn.prepareStatement(addInventoryQuery);
	                inventoryStatement.setInt(1, bookId);
	                inventoryStatement.setInt(2, newBook.getQuantity());
	                rowsAffected = inventoryStatement.executeUpdate();
	                if (rowsAffected > 0) {
	                    conn.commit(); // Commit the transaction if both inserts are successful
	                    message = "Book added successfully with ID: " + bookId;
	                } else {
	                    conn.rollback(); // Rollback the transaction if the inventory insert fails
	                    message = "Failed to insert inventory quantity for the new book.";
	                }
	            }
	            generatedKeys.close();
	        } else {
	            message = "Failed to add new book.";
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        message = "Error occurred while adding new book.";
	        try {
	            if (conn != null) {
	                conn.rollback(); // Rollback the transaction if an error occurs
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    } finally {
	        try {
	            if (bookStatement != null) {
	                bookStatement.close();
	            }
	            if (inventoryStatement != null) {
	                inventoryStatement.close();
	            }
	            if (conn != null) {
	                conn.setAutoCommit(true); // Set auto-commit back to true
	                conn.close();
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	        }
	    }
	    return message;
	}

	
	public static String deleteBook(int bookID,String imageUrl) {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			Cloudinary cloudinary = CloudinaryConfig.getCloudinaryInstance();
			String deleteBookQuery = 
					"DELETE FROM Book "
					+ "WHERE BookID=?";
			PreparedStatement pstmt = conn.prepareStatement(deleteBookQuery);
			pstmt.setInt(1, bookID);
			int rowsAffected = pstmt.executeUpdate();
	        if (rowsAffected > 0) {
	        	cloudinary.uploader().destroy(imageUrl, ObjectUtils.emptyMap());
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
	
	public static String updateBook(Book updateBook) {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String updateBookQuery = 
					"UPDATE Book "
					+ "SET Title = ?, "
					+ "Author = ?, "
					+ "Price = ?, "
					+ "Publisher = ?, "
					+ "PubDate = ?, "
					+ "ISBN = ?, "
					+ "Rating = ?, "
					+ "Description = ?, "
					+ "Image = ?, "
					+ "CategoryID = ? "
					+ "WHERE BookID = ?";
	        String updateInventoryQuery = 
	        		"UPDATE Inventory "
	        		+ "SET Qty = ? "
	        		+ "WHERE BookID = ?";
	        
	        conn.setAutoCommit(false); // Disable auto-commit

	        // Update the book details
	        PreparedStatement pstmtBook = conn.prepareStatement(updateBookQuery);
	        pstmtBook.setString(1, updateBook.getTitle());
	        pstmtBook.setString(2, updateBook.getAuthor());
	        pstmtBook.setDouble(3, updateBook.getPrice());
	        pstmtBook.setString(4, updateBook.getPublisher());
	        pstmtBook.setDate(5, updateBook.getPubDate());
	        pstmtBook.setString(6, updateBook.getISBN());
	        pstmtBook.setDouble(7, updateBook.getRating());
	        pstmtBook.setString(8, updateBook.getDescription());
	        pstmtBook.setString(9, updateBook.getImageUrl());
	        pstmtBook.setInt(10, updateBook.getCategoryID());
	        pstmtBook.setInt(11, updateBook.getBookID());
	        int rowAffectedBook = pstmtBook.executeUpdate();

	        // Update the inventory quantity
	        PreparedStatement pstmtInventory = conn.prepareStatement(updateInventoryQuery);
	        pstmtInventory.setInt(1, updateBook.getQuantity());
	        pstmtInventory.setInt(2, updateBook.getBookID());
	        int rowAffectedInventory = pstmtInventory.executeUpdate();

	        if (rowAffectedBook > 0 && rowAffectedInventory > 0) {
	            conn.commit(); // Commit the transaction
	            message = "Book and inventory updated successfully";
	        } else {
	            conn.rollback(); // Rollback the transaction
	            message = "Failed to update the book and inventory";
	        }

	        conn.setAutoCommit(true); // Enable auto-commit
	        pstmtBook.close();
	        pstmtInventory.close();
	        conn.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	        message = "Error occurred while updating the book and inventory.";
	    }
	    return message;
	}
}
