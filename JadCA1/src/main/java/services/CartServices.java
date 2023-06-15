package services;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import models.Book;
import models.Order;

public class CartServices {
	public static String addToCart(Book buyItem,int custID,List<Order> cartList,HttpSession session) {
		String message = "";
		;
		try {
			
			boolean bookExists = false;
			boolean userCartExist = false;
			List<Book> userBookList = new ArrayList<Book>();
			for (Order order : cartList) {
				if(order.getCustomerID() == custID) {
					userBookList = order.getBookList();
					userCartExist = true;
					for(Book book: userBookList) {
						if (book.getBookID() == buyItem.getBookID()) {
							book.setQuantity(book.getQuantity() + buyItem.getQuantity()); // Update the quantity
							bookExists = true;
							break;
						}
					}
				}
				
			}
			System.out.println(bookExists);

			
			
			// If the book doesn't exist in the bookList, add it as a new entry
			if (!bookExists) {
				userBookList.add(buyItem);
				
			}
			
			if(!userCartExist) {
				cartList.add(new Order(custID,userBookList));
			}
			session.setAttribute("Cart", cartList);
			message="Book is added to cart";
		} catch (Exception e) {
			e.printStackTrace();
			message= "Error in adding book to cart.";
		}
		return message;
	}
	public static void deleteCart(int bookID) {
		
	}
	
	public static List<Book> getCartItems(int custID, List<Order> cart){
		List<Book> cartItems = new ArrayList<Book>();
		if(cart != null) {
			for(Order order: cart) {
				if(order.getCustomerID() == custID) {
					cartItems = order.getBookList();
				}
			}
		}
		
		return cartItems;
	}
}
