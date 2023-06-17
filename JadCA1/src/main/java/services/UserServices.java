package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.sql.Statement;

import config.DataBaseConfig;
import models.User;

public class UserServices {
	public static String addCustomer(String userName, String email, String phoneNum, String password) throws Exception {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String registerUserQuery = "INSERT INTO User (Email,Password,Username,Phone) VALUES (?,?,?,?);";
			PreparedStatement pstmt = conn.prepareStatement(registerUserQuery);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setString(3, userName);
			pstmt.setString(4, phoneNum);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				message = "Customer registered successfully.";
			} else {
				message = "Failed to register customer.";
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) { // 1062 is the MySQL error code for duplicate entry
				message = "Your email or password or User name are being used by others. Please choose the other values.";
			} else {
				message = "Error occurred while registering customer.";
			}
			throw new Exception(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error occurred while registering customer.";
			throw new Exception(message);
		}
		return message;
	}

	public static User login(String userInput, String password) {
		User loginUser = null;
		try {
			Connection conn = DataBaseConfig.getConnection();
			String fetchUserQuery = "SELECT UserID,Role FROM User WHERE (Username=? OR Email=?) AND Password=?";
			PreparedStatement pstmt = conn.prepareStatement(fetchUserQuery);
			pstmt.setString(1, userInput);
			pstmt.setString(2, userInput);
			pstmt.setString(3, password);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				int custID = rs.getInt("UserID");
				String role = rs.getString("role");
				loginUser= new User(custID,role);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loginUser;
	}

	public static User getCustomerDetails (int custID) {
		User customer = null;
		try {
			Connection conn = DataBaseConfig.getConnection();
			String fetchUserDetailsQuery = "SELECT Username,"
					+ " Email, "
					+ "Phone, "
					+ "Password "
					+ "FROM User "
					+ "WHERE UserID = ? "
					+ "AND Role = ?";
			PreparedStatement pstmt = conn.prepareStatement(fetchUserDetailsQuery);
			pstmt.setInt(1, custID);
			pstmt.setString(2, "customer");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String userName = rs.getString("UserName");
				String email = rs.getString("Email");
				String phoneNum = rs.getString("Phone");
				String password = rs.getString("Password");
				customer = new User(userName,email,password,phoneNum);
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
		
	}
	public static String updateCustomer(int custID, String userName, String email, String phoneNum, String password)
			throws Exception {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String updateCustomerQuery = "UPDATE User SET Username=? , Email = ?, Phone = ?, Password = ? WHERE UserID = ? AND Role = ?";
			PreparedStatement pstmt = conn.prepareStatement(updateCustomerQuery);
			pstmt.setString(1, userName);
			pstmt.setString(2, email);
			pstmt.setString(3, phoneNum);
			pstmt.setString(4, password);
			pstmt.setInt(5, custID);
			pstmt.setString(6,"customer");
			int rowAffected = pstmt.executeUpdate();
			if (rowAffected > 0) {
				message = "Customer Details updated successfully.";
			} else {
				message = "Fail to update Customer details.";
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) { // 1062 is the MySQL error code for duplicate entry
				message = "Your email or password or User name are being used by others. Please choose the other values.";
			} else {
				message = "Error in updating Customer Details.";
			}
			e.printStackTrace();
			throw new Exception(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error in updating Customer Details.";
			throw new Exception(message);
		}
		return message;
	}
	
	public static List<User> getAllUsers(){
		List<User> userList = new ArrayList<User>();
		try{
			Connection conn = DataBaseConfig.getConnection();
			String fetchAllUsers = "SELECT * FROM User";
			Statement stmt= conn.createStatement();
			ResultSet rs = stmt.executeQuery(fetchAllUsers);
			while(rs.next()) {
				int userID = rs.getInt("UserID");
				String userName = rs.getString("Username");
				String email = rs.getString("Email");
				String password = rs.getString("Password");
				String phoneNum = rs.getString("Phone");
				String role = rs.getString("Role");
				userList.add(new User(userName,email,password,phoneNum,userID,role));
			}
			rs.close();
			stmt.close();
			conn.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public static User getUserByID(int userID) {
		User user = null;
		try {
			Connection conn = DataBaseConfig.getConnection();
			String fetchUserByID = "SELECT * FROM User WHERE UserID = ?";
			PreparedStatement pstmt = conn.prepareStatement(fetchUserByID);
			pstmt.setInt(1, userID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String userName = rs.getString("Username");
				String email = rs.getString("Email");
				String password = rs.getString("Password");
				String phoneNum = rs.getString("Phone");
				String role = rs.getString("Role");
				user = new User(userName,email,password,phoneNum,userID,role);
			}
			rs.close();
			pstmt.close();
			conn.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}
	
	public static String addUser(String userName, String email, String phoneNum,String role, String password) throws Exception {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String registerUserQuery = "INSERT INTO User (Email,Password,Username,Phone,Role) VALUES (?,?,?,?,?);";
			PreparedStatement pstmt = conn.prepareStatement(registerUserQuery);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setString(3, userName);
			pstmt.setString(4, phoneNum);
			pstmt.setString(5, role);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				message = "User registered successfully.";
			} else {
				message = "Failed to register user.";
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) { // 1062 is the MySQL error code for duplicate entry
				message = "User Details are duplicated. Please choose the other values.";
			} else {
				message = "Error occurred while registering user.";
			}
			throw new Exception(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error occurred while registering user.";
			throw new Exception(message);
		}
		return message;
	}
	
	public static String updateUser(int userID, String userName, String email, String phoneNum,String role, String password)
			throws Exception {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String updateCustomerQuery = "UPDATE User SET Username=? , Email = ?, Phone = ?, Password = ? , Role = ? WHERE UserID = ? ";
			PreparedStatement pstmt = conn.prepareStatement(updateCustomerQuery);
			pstmt.setString(1, userName);
			pstmt.setString(2, email);
			pstmt.setString(3, phoneNum);
			pstmt.setString(4, password);
			pstmt.setString(5,role);
			pstmt.setInt(6, userID);
			System.out.println(pstmt);
			int rowAffected = pstmt.executeUpdate();
			if (rowAffected > 0) {
				message = "User Details updated successfully.";
			} else {
				message = "Fail to update User details.";
			}
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) { // 1062 is the MySQL error code for duplicate entry
				message = "User Details Duplicated. Please choose the other values.";
			} else {
				message = "Error in updating user Details.";
			}
			e.printStackTrace();
			throw new Exception(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error in updating user Details.";
			throw new Exception(message);
		}
		return message;
	}
	
	public static String deleteUser(int userID)throws Exception {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String deleteUserQuery = "DELETE FROM User WHERE UserID = ?";
			PreparedStatement pstmt = conn.prepareStatement(deleteUserQuery);
			pstmt.setInt(1, userID);
			int rowsAffected = pstmt.executeUpdate();
			if(rowsAffected > 0 ) {
				message = "User deleted successfully";
			}else {
				message = "Fail to delete user";
			}
		}catch (Exception e) {
			e.printStackTrace();
			message = "Error during deleting user";
			throw new Exception (message);
		}
		return message;
	}

}
