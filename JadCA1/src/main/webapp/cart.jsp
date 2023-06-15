<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="models.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
	List<Book> cart = (List<Book>) request.getAttribute("CartItems");
	//int bookAmount = (int) request.getAttribute("bookAmount");
	if (cart != null) {
	%>
	<%
	if (cart.isEmpty()) {
	%>
	<p>There is no item inside your cart.</p>
	<%
	} else {
	//int pageSize = 6; // Number of books to display per page
	//int totalPages = (int) Math.ceil((double) bookAmount / pageSize);
	//String pageNumber = request.getParameter("page");
	//int currentPage = (pageNumber != null) ? Integer.parseInt(pageNumber) : 1;
	%>
	<table>
		<tr>
			<th>My Cart(<%=cart.size()%>)
			</th>
			<th>Name</th>
			<th>Price</th>
			<th>quantity</th>
			<th>Total</th>
			<th></th>
		</tr>
		<%
		for (Book book : cart) {
		%>
		<tr>
			<td><img
				src=<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + book.getImageUrl() + ".jpg"%>
				alt="Image Description" width="200px"></td>
			<td><%=book.getTitle()%></td>
			<td>$<%=book.getPrice()%></td>
			<td><%=book.getQuantity()%></td>
			<td>$<%=book.getPrice() * book.getQuantity()%></td>
			<td><form action="Cart" method="post">
			<input type="hidden" name="bookID" value="<%=book.getBookID() %>">
			<input type="submit" value="delete">
			</form></td>
		</tr>
		<%
		}
		%>
	</table>
	<%
	}
	%>
	<%
	} else {
	%>
	<p>There is nothing inside your cart</p>
	<%
	}
	%>
</body>
</html>