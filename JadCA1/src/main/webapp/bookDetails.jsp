<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="models.Book"%>
   <%@ page import="services.BookServices" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Book Details</title>
<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/bookDetail.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
</style>
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
	int bookID = (int) Integer.parseInt(request.getParameter("bookID"));
	Book book = BookServices.fetchBookDataByID(bookID);
	if (book != null) {
		// Display book details
	%>

	<%@ include file= "header.jsp" %>
 <div class="bookMain">
      <div class="container d-flex">
       <img
		src=<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + book.getImageUrl() + ".jpg"%>
		alt="Image Description" width="200px">
        <div class="details text-white">
          <div id="title"><%=book.getTitle() %></div>
          <div id="rating"><%=book.getRating() %></div>
          <div id="author"><%= book.getAuthor()%></div>
          <div id="category"><%=book.getCategory() %></div>
          <div class="tinyDeets d-flex">
            <div id="ISBN"><%=book.getISBN() %></div>
            <div id="publisher"><%=book.getPublisher() %></div>
            <div id="publishDate"><%=book.getPubDate() %></div>
          </div>
        </div>
      </div>
    </div>
    <div class="bookDesc">
      <div class="container">
        <h3>Description</h3>
        <p>
   <%=book.getDescription() %>
        </p>
      </div>
    </div>
    <div class="priceBar container d-flex justify-content-end">
      <div id="price">$<%=book.getPrice() %></div>
      <form action="Cart" method="post">
      	<input type="hidden" name="action" value="addToCart">
		<input type="hidden" name="bookID" value="<%= book.getBookID() %>">
		<input type="hidden" name="title" value="<%=book.getTitle()%>">
		<input type="hidden" name="imageUrl" value="<%=book.getImageUrl()%>">
		<input type="hidden" name="price" value="<%=book.getPrice()%>">
		<button type="button" onclick="decrementQuantity()">-</button>
		<input type="text" id="quantity" name="quantity" value="1" readonly>
		<button type="button" onclick="incrementQuantity()">+</button>
		<input type="submit" value="add to cart">
	</form>
    </div>
    <footer class="text-center bg-dark text-white p-4">
      Copyright @ JAD-Bookstore
    </footer>

	
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
