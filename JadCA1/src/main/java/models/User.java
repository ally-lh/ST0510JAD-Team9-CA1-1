package models;

public class User {
	public User(String userName, String email, String password, String phone,int userID,String role ) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.userID = userID;
		this.role = role;
	}
	public User(String userName, String email, String password, String phone) {
		this(userName, email, password, phone,0,null);
	}
	
	public User(int userID,String role) {
		this(null,null,null,null,userID,role);
	}
	
	public User(String userName,String email,String password,String phone, String role) {
		this(userName,email,password,phone,0,role);
	}
	
	private String userName;
	private String email;
	private String password;
	private String phone;
	private int userID;
	private String role;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
