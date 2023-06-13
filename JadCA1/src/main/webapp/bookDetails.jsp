<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.Book"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Details</title>
<script>
	function decrementQuantity() {
		var quantityInput = document.getElementById("quantity");
		var currentQuantity = parseInt(quantityInput.value);
		if (currentQuantity > 1) {
			quantityInput.value = currentQuantity - 1;
		}
	}

	function incrementQuantity() {
		var quantityInput = document.getElementById("quantity");
		var currentQuantity = parseInt(quantityInput.value);
		quantityInput.value = currentQuantity + 1;
	}
</script>
</head>
<body>
	<%
	Book book = (Book) request.getAttribute("results");
	if (book != null) {
		// Display book details
	%>
	<h1>Book Details</h1>
	<img
		src=<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + book.getImageUrl() + ".jpg"%>
		alt="Image Description" width="200px">
	<p>
		Title:
		<%=book.getTitle()%></p>
	<p>
		Author:
		<%=book.getAuthor()%></p>
	<p>
		Description:
		<%=book.getDescription()%></p>
	<p>
		Price: $<%=book.getPrice()%></p>
	<p>
		Publisher:
		<%=book.getPublisher()%></p>
	<p>
		Rating:
		<%=book.getRating()%></p>
	<p>
		Publish Date:
		<%=book.getPubDate()%></p>
	<p>
		Category Title:
		<%=book.getCategory()%></p>


	<form action="Cart" method="post">
		<input type="hidden" name="bookID" value="<%= book.getBookID() %>">
		<input type="hidden" name="title" value="<%=book.getTitle()%>">
		<input type="hidden" name="imageUrl" value="<%=book.getImageUrl()%>">
		<input type="hidden" name="price" value="<%=book.getPrice()%>">
		<button type="button" onclick="decrementQuantity()">-</button>
		<input type="text" id="quantity" name="quantity" value="1" readonly>
		<button type="button" onclick="incrementQuantity()">+</button>
		<input type="submit" value="add to cart">
	</form>
	<%
	} else {
	// Book not found or not available
	%>
	<p>Book not found or not available.</p>
	<%
	}
	%>
</body>
</html>
