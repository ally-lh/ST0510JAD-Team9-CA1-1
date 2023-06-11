package services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import category.Category;
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
}
