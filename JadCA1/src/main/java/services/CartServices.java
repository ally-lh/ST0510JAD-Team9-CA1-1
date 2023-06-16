package services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpSession;

import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import config.DataBaseConfig;
import models.Book;

public class CartServices {
	public static String addToCart(Book buyItem, int custID, List<Book> bookList, HttpSession session) {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String addToCartQuery = "INSERT INTO Cart (CustID, BookID, Qty) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE Qty = Qty+ VALUES(Qty);";
			PreparedStatement pstmt = conn.prepareStatement(addToCartQuery);
			pstmt.setInt(1, custID);
			pstmt.setInt(2, buyItem.getBookID());
			pstmt.setInt(3, buyItem.getQuantity());
			int rowAffected = pstmt.executeUpdate();
			if (rowAffected > 0) {
				boolean bookExists = false;
				for (Book book : bookList) {
					if (book.getBookID() == buyItem.getBookID()) {
						book.setQuantity(book.getQuantity() + buyItem.getQuantity()); // Update the quantity
						bookExists = true;
						break;
					}
				}

				// If the book doesn't exist in the bookList, add it as a new entry
				if (!bookExists) {
					bookList.add(buyItem);
				}
				session.setAttribute("Cart", bookList);
				message = "Book is added to cart";
			} else {
				message = "Failed to add Book cart";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error in adding book to cart.";
		}
		return message;
	}

	public static void deleteCart(int bookID) {

	}

	public static String deleteCartItem(int custID, int bookID, List<Book> bookList, HttpSession session) {
		String message = "";
		try {

			Connection conn = DataBaseConfig.getConnection();
			String deleteCartQuery = "DELETE FROM Cart WHERE CustID = ? AND BookID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(deleteCartQuery);
			pstmt.setInt(1, custID);
			pstmt.setInt(2, bookID);
			int rowAffected = pstmt.executeUpdate();
			if (rowAffected > 0) {
				// Use Iterator.remove for safe removal during iteration
				Iterator<Book> it = bookList.iterator();
				while (it.hasNext()) {
					Book book = it.next();
					if (book.getBookID() == bookID) {
						it.remove(); // Removes the book from the cart
						message = "Book is removed from cart";
						break;
					}
				}
				session.setAttribute("Cart", bookList);
				message = "Book removed from cart successfully.";
			} else {
				message = "Fail to remove Book from Cart";
			}

		} catch (Exception e) {
			e.printStackTrace();
			message = "Error in deleting book from cart.";
		}
		return message;
	}

	public static List<Book> getCartItems(int custID) {
		List<Book> bookList = new ArrayList<Book>();
		try {
			Connection conn = DataBaseConfig.getConnection();
			String getCartItemsQuery = "SELECT Book.BookID," + "Book.Title, " + "Book.Price," + "Book.Image,"
					+ "Cart.Qty " + "FROM Cart " + "INNER JOIN Book " + "ON Cart.BookID = Book.BookID "
					+ "WHERE Cart.CustID =?;";
			PreparedStatement pstmt = conn.prepareStatement(getCartItemsQuery);
			pstmt.setInt(1, custID);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int bookID = rs.getInt("BookID");
				String title = rs.getString("Title");
				Double price = rs.getDouble("Price");
				String imageUrl = rs.getString("Image");
				int qty = rs.getInt("Qty");
				bookList.add(new Book(bookID, title, price, imageUrl, qty));
				
			}
			// Step 6: Close the resources
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bookList;
	}

	public static String addOrder(int custID, List<Book> cart, HttpSession session) throws Exception {
		Connection conn = null;
		PreparedStatement orderStatement = null;
		PreparedStatement itemStatement = null;
		ResultSet rs = null;
		String message = "Order added successfully";
		try {
			conn = DataBaseConfig.getConnection();
			System.out.println("Begin Transcation ...");
			// Start transaction
			conn.setAutoCommit(false);

			String addOrderQuery = "INSERT INTO Orders (CustID, OrderDate, TotalAmt) VALUES (?, NOW(), ?)";

			// Add order and get the generated key
			orderStatement = conn.prepareStatement(addOrderQuery, Statement.RETURN_GENERATED_KEYS);
			orderStatement.setInt(1, custID);
			// Assuming you calculate totalAmt here
			double totalAmt = calculateTotalAmount(cart);
			orderStatement.setDouble(2, totalAmt);
			System.out.println("Statement executing >>> " + orderStatement);
			int rowAffected = orderStatement.executeUpdate();
			System.out.println(rowAffected);
			rs = orderStatement.getGeneratedKeys();
			if (rs.next()) {
				int orderId = rs.getInt(1);
				System.out.println(orderId);
				String addOrderItemsQuery = "INSERT INTO OrderItem (OrderID, BookID, Quantity) VALUES (?, ?, ?)";

				// Add order items for the same order
				itemStatement = conn.prepareStatement(addOrderItemsQuery);
				for (Book book : cart) {
					itemStatement.setInt(1, orderId);
					itemStatement.setInt(2, book.getBookID());
					itemStatement.setInt(3, book.getQuantity());
					itemStatement.addBatch(); // Add to the batch
				}
				System.out.println("Statement executing >>> " + itemStatement);
				int[] updateCounts = itemStatement.executeBatch(); // Execute all the statements as a batch
				for (int count : updateCounts) {
					if (count == PreparedStatement.EXECUTE_FAILED) {
						message = "Failed to add some order items";
						break;
					}
				}
			}

			conn.commit();
		} catch (BatchUpdateException bue) {
			System.out.println("Batch Update Error");
			// Roll back transaction if anything fails
			if (conn != null) {
				try {

					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			bue.printStackTrace();
			throw new Exception("Failed to add order items", bue);
		} catch (Exception e) {
			System.out.println("Batch Update Error");
			// Roll back transaction if anything fails
			if (conn != null) {
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new Exception(e);
		} finally {
			// close resources in finally block to ensure they are always closed
			if (rs != null) {
				rs.close();
			}
			if (orderStatement != null) {
				orderStatement.close();
			}
			if (itemStatement != null) {
				itemStatement.close();
			}
			if (conn != null) {
				conn.close();
			}
			session.removeAttribute("Cart");
		}
		return message;
	}

	// Helper function to calculate total amount
	private static double calculateTotalAmount(List<Book> userBookList) {
		double total = 0;
		for (Book book : userBookList) {
			total += book.getPrice() * book.getQuantity();
		}
		return total;
	}

}
