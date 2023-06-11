<%@ page import="books.Book" %>
<%@ page import="java.util.List"%>
<%
List<Book> bookResults = (List<Book>) request.getAttribute("bookResults");
if (bookResults != null) {
%>
<%
	if (bookResults.isEmpty()) {
	%>
	<p>No results found</p>
	<%
	} else {
	%>
	<table>
		<tr>
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
