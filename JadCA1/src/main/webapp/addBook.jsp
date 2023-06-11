<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="category.Category"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add New Book</title>
</head>
<body>
	<h1>Add New Book</h1>

	<form action="admin" method="post"
		enctype="multipart/form-data">
		<input type="hidden" name="action" value="addBook">
		<label for="title">Title:</label> 
		<input type="text" name="title" required><br> 
		<label for="author">Author:</label> 
		<input type="text" name="author" required><br> 
		<label for="price">Price:</label> 
		<input type="number" name="price" step="0.01" required><br> 
		<label for="publisher">Publisher:</label>
		<input type="text" name="publisher" required><br> 
		<label for="pubDate">Publication Date:</label> 
		<input type="date" name="pubDate" required><br> 
		<label for="ISBN">ISBN:</label>
		<input type="text" name="ISBN" required><br> 
		<label for="rating">Rating:</label> 
		<input type="number" name="rating" step="0.1" required><br> 
		<label for="description">Description:</label>
		<textarea name="description" required></textarea><br> 
		<label for="image">Image:</label> <input type="file" name="image" required><br> 
		<label for="category">Category:</label>
		<select name="category" required>
			<option value="">Select a category</option>
			<%
			List<Category> categoryResults = (List<Category>) request.getAttribute("categoryResults");
			if (categoryResults != null) {
				for (Category category : categoryResults) {
					String categoryName = category.getCategoryName();
					int categoryID = category.getCategoryID();
			%>
			<option value="<%=categoryID%>"><%=categoryName%></option>
			<%
			}
			}
			%>
		</select><br> 
		<label for="quantity">Book Quantity:</label> 
		<input type="number" name="quantity" step="1" required><br> 
		<input type="submit" value="Add Book">
	</form>
</body>
</html>
