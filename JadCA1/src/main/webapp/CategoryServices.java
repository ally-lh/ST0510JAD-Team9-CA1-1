package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Category;
import config.DataBaseConfig;

public class CategoryServices {
	public static List<Category> getAllCategory() {
		List<Category> categoryData = new ArrayList<>();
		try {
			Connection conn = DataBaseConfig.getConnection();
			Statement statement = conn.createStatement();
			String fetchCategoryQuery = "SELECT * FROM Category";

			ResultSet rs = statement.executeQuery(fetchCategoryQuery);
			while (rs.next()) {
				int categoryID = rs.getInt("CategoryID");
				String categoryName = rs.getString("CategoryName");
				Category category = new Category(categoryID, categoryName);
				categoryData.add(category);
			}
			rs.close();
			statement.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryData;
	}

	public static String addCategory(String categoryName) {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String addCategoryQuery = "INSERT INTO Category (CategoryName) VALUES(?)";
			PreparedStatement pstmt = conn.prepareStatement(addCategoryQuery);
			pstmt.setString(1, categoryName);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				message = "Category is successfully added";
			} else {
				message = "Fail to add Category";
			}
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) { // 1062 is the MySQL error code for duplicate entry
				message = "Category value is duplicated";
			} else {
				message = "Error in adding Category.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error in adding Category";
		}
		return message;
	}

	public static String updateCategory(String categoryName, int categoryID) {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String updateCategoryQuery = "UPDATE Category SET CategoryName = ? WHERE CategoryID=?";
			PreparedStatement pstmt = conn.prepareStatement(updateCategoryQuery);
			pstmt.setString(1, categoryName);
			pstmt.setInt(2, categoryID);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				message = "Category is updated successfully.";
			} else {
				message = "Fail to update category";
			}

		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) { // 1062 is the MySQL error code for duplicate entry
				message = "Category value is duplicated";
			} else {
				message = "Error in updating Category.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "Error in updating Category";
		}
		return message;
	}

	public static String deleteCategory(int categoryID) {
		String message = "";
		try {
			Connection conn = DataBaseConfig.getConnection();
			String deleteCategoryQuery = "DELETE FROM Category WHERE CategoryID = ?";
			PreparedStatement pstmt = conn.prepareStatement(deleteCategoryQuery);
			pstmt.setInt(1, categoryID);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				message = "Category is successfully deleted";
			} else {
				message = "Fail to delete Category";
			}
		} catch (java.sql.SQLIntegrityConstraintViolationException e) {
            // handle exception here, for example:
            e.printStackTrace();
            message = "cannot delete category that have books";
        }catch (Exception e) {
			e.printStackTrace();
			message = "Error in deleting Category";
		}
		return message;
	}
}