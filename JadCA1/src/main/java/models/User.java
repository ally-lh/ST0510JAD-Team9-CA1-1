package models;

public class User {
	public User(String userName, String email, String password, int phone) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
		this.phone = phone;
	}
	private String userName;
	private String email;
	private String password;
	private int phone;
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
	public int getPhone() {
		return phone;
	}
	public void setPhone(int phone) {
		this.phone = phone;
	}
}
