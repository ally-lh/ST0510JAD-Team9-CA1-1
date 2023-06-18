package models;
import java.sql.Date;
public class Book {
	public Book(int bookID, String title, String author, double price, String publisher, Date pubDate, String iSBN,
			float rating, String description, String imageUrl, String category, int categoryID,int quantity) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.author = author;
		this.price = price;
		this.publisher = publisher;
		this.pubDate = pubDate;
		ISBN = iSBN;
		this.rating = rating;
		this.description = description;
		this.imageUrl = imageUrl;
		this.category = category;
		this.categoryID = categoryID;
		this.quantity = quantity;
	}
	
	public Book(int bookID, String title, String author, double price, String publisher, Date pubDate, String iSBN,
			float rating, String description, String imageUrl, String category,int quantity) {
		this(bookID,title,author,price,publisher,pubDate,iSBN,rating,description,imageUrl,category,0,quantity);
	}
	
	public Book(String title, String author, double price, String publisher, Date pubDate, String iSBN,
			float rating, String description, String imageUrl, int categoryID,int quantity) {
		this(0,title,author,price,publisher,pubDate,iSBN,rating,description,imageUrl,null,categoryID,quantity);
	}
	
	public Book(int bookID,String title, String author, double price, String publisher, Date pubDate, String iSBN,
			float rating, String description, String imageUrl, String category) {
		this(bookID,title,author,price,publisher,pubDate,iSBN,rating,description,imageUrl,category,0,0);
	}
	
	public Book(int bookID,String title,double price, String imageUrl, int quantity) {
		this(bookID,title,null,price,null,null,null,0,null,imageUrl,null,0,quantity);
	}
	
	public Book(int bookID,String title, String author, double price, String publisher, Date pubDate, String iSBN,
			float rating, String description, String imageUrl, int categoryID,int quantity) {
		this(bookID,title,author,price,publisher,pubDate,iSBN,rating,description,imageUrl,null,categoryID,quantity);
	}
	
	
	
	


	private int bookID;
	private String title;
	private String author;
	private double price;
	private String publisher;
	private Date pubDate;
	private String ISBN;
	private float rating;
	private String description;
	private String imageUrl;
	private String category;
	private int categoryID;
	private int quantity;
	public int getBookID() {
		return bookID;
	}
	public void setBookID(int bookID) {
		this.bookID = bookID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Date getPubDate() {
		return pubDate;
	}
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public int getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
