<%@ page import="books.Book" %>
<%@ page import="java.util.List"%>

<%
List<Book> bookResults = (List<Book>) request.getAttribute("bookResults");
//int bookAmount = (int) request.getAttribute("bookAmount");
if (bookResults != null) {
%>
<%
	if (bookResults.isEmpty()) {
	%>
	<p>No results found</p>
	<%
	} else {
		//int pageSize = 6; // Number of books to display per page
		//int totalPages = (int) Math.ceil((double) bookAmount / pageSize);
		//String pageNumber = request.getParameter("page");
		//int currentPage = (pageNumber != null) ? Integer.parseInt(pageNumber) : 1;
		
	%>
	<table>
		<tr>
			<th></th>
			<th>ID</th>
			<th>Name</th>
			<th>Price</th>
			<th>Description</th>
			<th>Release Date</th>
			<th>Action</th>
		</tr>
		<%
		for (Book book : bookResults) {
		%>
		<tr>
			<td><img src=<%= "https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/"+book.getImageUrl()+".jpg" %> alt="Image Description" width="200px"></td>
			<td><%=book.getBookID()%></td>
			<td><%=book.getTitle()%></td>
			<td><%=book.getPrice()%></td>
			<td><%=book.getDescription()%></td>
			<td><%=book.getPubDate()%></td>
			<td>
				<form action="home" method="GET">
					<input type="hidden" name="action" value="byID"> <input
						type="hidden" name="bookID" value="<%=book.getBookID()%>">
					<input type="submit" value="View Details">
				</form>
			</td>
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
<p>No book details available</p>
<%
}
%>
