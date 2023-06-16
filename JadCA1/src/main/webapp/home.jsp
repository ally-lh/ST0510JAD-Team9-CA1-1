<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/search.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
</style>
</head>
<body>
	<%@ page import="java.util.List"%>
	<%@ page import="models.*"%>
	
	
	<%
	List<Book> searchResults = (List<Book>) request.getAttribute("searchResults");
	List<Category> categoryResults = (List<Category>) request.getAttribute("categoryResults");
	%>
	<%@ include file= "header.jsp" %>

<div class="searchBarSec">
		<form action="search" method="GET" class="container px-5">
			<div class="row mb-3">
				<div class="form-group" style="display: flex">
					<div class="input-group border-bottom">
						<input type="search" name=searchTerm placeholder="Search title or author ..."
					="button-addon3"
							class="form-control bg-none border-0 mb-2" autocomplete="off"
							autocomplete="off" />
						<button class="btn mb-2 searchsubmit" type="submit">
							Search</button>
					</div>
				</div>
			</div>
			<div class="filters">
				<p class="filterText text-center">Filters:</p>

				<select class="form-select" name="categoryID"
					aria-label="Default select example">
					<option hidden>Category</option>
					<%
					for (Category c : categoryResults) {
					%>
					<option value="<%=c.getCategoryID()%>"><%=c.getCategoryName()%></option>
					<%
					}
					%>

				</select>

			</div>
		</form>
	</div>
	<div id="results" class="container">
		<%
		 if (searchResults != null) {
		int count = searchResults.size();
		%>
		Showing All <%=count%> Books:
		<%
		for (Book i : searchResults) {
			
		%>
		<a href="/JadCA1/bookDetails.jsp?bookID=<%=i.getBookID()%>"
			class="d-block text-decoration-none text-dark">
			<div class="book">
				<img src=<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + i.getImageUrl() + ".jpg"%> style="width: 300px"alt="smma" />
				<div class="details">
					<div id="title"><%=i.getTitle()%></div>
					<div id="rating"><%=i.getRating()%></div>
					<div id="author"><%=i.getAuthor()%></div>
					<div id="description">
						<%=i.getDescription()%>
					</div>
				</div>
			</div>
		</a> 
		<%
		}
		} else if (searchResults.isEmpty() || searchResults == null) {
			%>
			<p>No results found</p>
			<%
			} 
%>
		

		</div>
		
		</body>
		</html>

