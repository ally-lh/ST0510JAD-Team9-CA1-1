<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="category.Category"%>
<%@ page import="books.Book"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
        <script>
            alert("<%= message %>");
        </script>
<%
    }
%>
</head>
<body>
	<h1>Admin Dashboard</h1>

	<h2>Books</h2>
	<table>
		<tr>
			<th>Title</th>
			<th>Author</th>
			<th>Category</th>
			<th>Actions</th>
		</tr>
		<%
		List<Book> bookResults = (List<Book>) request.getAttribute("bookResults");
		for (Book book : bookResults) {
		%>
		<tr>
			<td><%=book.getTitle()%></td>
			<td><%=book.getAuthor()%></td>
			<td><%=book.getCategory()%></td>
			<td>
				<form action="updateBook.jsp" method="POST">
					<input type="hidden" name="bookID" value="<%=book.getBookID()%>">
					<input type="submit" value="Update">
				</form>
				<form action="admin" method="POST">
					<input type="hidden" name="action" value="deleteBook"> <input
						type="hidden" name="bookID" value="<%=book.getBookID()%>">
						<input type="hidden" name="imageUrl" value="<%= book.getImageUrl() %>">
					<input type="submit" value="Delete">
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>

	<h2>Categories</h2>
	<table>
		<tr>
			<th>Name</th>
			<th>Actions</th>
		</tr>
		<%
		List<Category> categoryResults = (List<Category>) request.getAttribute("categoryResults");
		for (Category category : categoryResults) {
		%>
		<tr>
			<td><%=category.getCategoryName()%></td>
			<td>
				<form action="updateCategory.jsp" method="POST">
					<input type="hidden" name="categoryID" value="<%=category.getCategoryID()%>"> 
					<input type="submit" value="Update">
				</form>
				<form action="admin" method="POST">
					<input type="hidden" name="action" value="deleteCategory"> 
					<input type="hidden" name="categoryID" value="<%=category.getCategoryID()%>">
					<input type="submit" value="Delete">
				</form>
			</td>
		</tr>
		<%
		}
		%>
	</table>
	<a href=<%= request.getContextPath()+"/addBook" %>><button>Add New Book</button></a>
</body>
</html>
