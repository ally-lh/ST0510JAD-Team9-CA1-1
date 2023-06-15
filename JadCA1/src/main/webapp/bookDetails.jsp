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
<%
boolean loggedIn = false;
try { 
	int custID = (int) request.getSession().getAttribute("userID");
	if (custID != 0){ 
		loggedIn = true;
	}
} catch (Exception e){ 
	
}
%>
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

	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container">
			<a class="navbar-brand" href="/JadCA1/index.jsp">JAD-Bookstore</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse d-flex justify-content-end"
				id="navbarNav">
				  <ul class="navbar-nav">
          <li class="nav-item"><a class="nav-link" aria-current="page"
						href="/JadCA1/search">Search</a></li>
						 <li class="nav-item">
              <a class="nav-link" aria-current="page" href="/JadCA1/Cart">Cart</a>
            </li>
            <% if (loggedIn){ 
            	%>
            	<li class="nav-item">
              <a class="nav-link" href="/JadCA1/profile.jsp">Profile</a>
            </li>
            
            	<% 
            } else { 
            	%>
            	   <li class="nav-item">
              <a class="nav-link" href="/JadCA1/login.jsp">Login</a>
            </li>
            	<%
            }
%>
          </ul>
			</div>
		</div>
	</nav>
   <div class="bottomNav navbar-expand">
		<ul class="navbar-nav container d-flex justify-content-end">
		<%
		if(loggedIn){ 
			%>
			<li class="nav-item"><a class="nav-link smallNavText"
				aria-current="page" href="/JadCA1/user?action=logout">Log Out</a></li>
			<% 
		} else { 
		%>
			<li class="nav-item"><a class="nav-link smallNavText"
					aria-current="page" href="/JadCA1/signup.jsp">Sign Up</a></li>
				<% 
		}
		%>
			
		</ul>
	</div>

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
