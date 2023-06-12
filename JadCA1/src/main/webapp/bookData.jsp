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
		int pageSize = 6; // Number of books to display per page
		int totalBooks = bookResults.size();
		int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
		String pageNumber = request.getParameter("page");
		int currentPage = (pageNumber != null) ? Integer.parseInt(pageNumber) : 1;

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
	<div class="pagination">
    <% if (currentPage > 1) { %>
        <a href="?page=<%= currentPage - 1 %>">&laquo; Previous</a>
    <% } %>

    <% for (int pageCurrent = 1; pageCurrent <= totalPages; pageCurrent++) { %>
        <% if (pageCurrent == currentPage) { %>
            <span class="active"><%= page %></span>
        <% } else { %>
            <a href="?page=<%= page %>"><%= page %></a>
        <% } %>
    <% } %>

    <% if (currentPage < totalPages) { %>
        <a href="?page=<%= pageNumber + 1 %>">Next &raquo;</a>
    <% } %>
</div>
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
