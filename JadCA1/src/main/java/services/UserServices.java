package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import config.DataBaseConfig;
public class UserServices {
	public static String registerUser(String userName, String email, int phoneNum, String password) {
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
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error occurred while registering user.";
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
	
}
