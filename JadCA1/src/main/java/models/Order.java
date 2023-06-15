package models;

import java.util.List;

public class Order {
	public Order(int customerID, List<Book> bookList) {
		super();
		this.customerID = customerID;
		this.bookList = bookList;
	}
	private int customerID;
	private List<Book> bookList;
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public List<Book> getBookList() {
		return bookList;
	}
	public void setBookList(List<Book> bookList) {
		this.bookList = bookList;
	}
	
}
