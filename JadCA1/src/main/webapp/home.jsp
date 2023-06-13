<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ page import="java.util.List"%>
	<%@ page import="models.*"%>
	
	<%
	List<Category> categoryResults = (List<Category>) request.getAttribute("categoryResults");
	%>

	<form action="search" method="GET">
		<input type="text" name="searchTerm"
			placeholder="Enter your search term"> <input type="submit"
			value="Search">
	</form>

	<h1>All Books</h1>
	<%@ include file="bookData.jsp" %>
	

	<h2>Filter by Category</h2>
    <form id="categoryForm" action="home" method="GET">
    <input type="hidden" name="action" value="byGenre">
    <% for (Category category : categoryResults) { %>
        <label>
            <input type="checkbox" name="categoryID" value="<%= category.getCategoryID() %>">
            <%= category.getCategoryName() %>
        </label><br>
    <% } %>
    <input type="submit" value="Apply">
    
    
</form>

</body>
</html>
