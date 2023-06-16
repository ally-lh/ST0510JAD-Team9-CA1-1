package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import config.DataBaseConfig;
import models.User;

public class UserServices {
	public static String addCustomer(String userName, String email, int phoneNum, String password) throws Exception {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String registerUserQuery = "INSERT INTO Customer (Email,Password,Username,Phone) VALUES (?,?,?,?);";
			PreparedStatement pstmt = conn.prepareStatement(registerUserQuery);
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			pstmt.setString(3, userName);
			pstmt.setInt(4, phoneNum);
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
				message = "Your email or password or User name are being used by others. Please choose the other values.";
			} else {
				message = "Error in adding Customer Details.";
			}
			throw new Exception(message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error occurred while registering user.";
			throw new Exception(message);
		}
		return message;
	}

	public static int login(String user, String password) {
		int custID = 0;
		try {
			Connection conn = DataBaseConfig.getConnection();
			String fetchUserQuery = "SELECT CustID FROM Customer WHERE (Username=? OR Email=?) AND Password=?";
			PreparedStatement pstmt = conn.prepareStatement(fetchUserQuery);
			pstmt.setString(1, user);
			pstmt.setString(2, user);
			pstmt.setString(3, password);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				custID = rs.getInt("CustID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return custID;
	}

	public static User getCustomerDetails (int custID) {
		User customer = null;
		try {
			Connection conn = DataBaseConfig.getConnection();
			String fetchUserDetailsQuery = "SELECT Username,"
					+ " Email, "
					+ "Phone, "
					+ "Password "
					+ "FROM Customer "
					+ "WHERE CustID = ?;";
			PreparedStatement pstmt = conn.prepareStatement(fetchUserDetailsQuery);
			pstmt.setInt(1, custID);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				String userName = rs.getString("UserName");
				String email = rs.getString("Email");
				int phoneNum = Integer.parseInt(rs.getString("Phone"));
				String password = rs.getString("Password");
				customer = new User(userName,email,password,phoneNum);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
		
	}
	public static String updateCustomer(int custID, String userName, String email, int phoneNum, String password)
			throws Exception {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String updateCustomerQuery = "UPDATE Customer SET Username=? , Email = ?, Phone = ?, Password = ? WHERE CustID = ?";
			PreparedStatement pstmt = conn.prepareStatement(updateCustomerQuery);
			pstmt.setString(1, userName);
			pstmt.setString(2, email);
			pstmt.setInt(3, phoneNum);
			pstmt.setString(4, password);
			pstmt.setInt(5, custID);
			int rowAffected = pstmt.executeUpdate();
			if (rowAffected > 0) {
				message = "Customer Details updated successfully.";
			} else {
				message = "Fail to update Customer details.";
			}
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

}
