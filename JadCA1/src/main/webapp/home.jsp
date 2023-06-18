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
	<%@ page import="org.apache.commons.lang3.tuple.Pair"%>
	<%@ page import="java.net.URLEncoder" %>
	
	
	<%
	String searchTerm ="";
	String categoryID="";
	if(request.getAttribute("searchTermAttr")!= null){
		 searchTerm = (String) request.getAttribute("searchTermAttr");
	}
	if(request.getAttribute("categoryIDAttr")!= null){
		 categoryID =(String) request.getAttribute("categoryIDAttr");
		 System.out.println(categoryID);
	}
	Pair<List<Book>, Integer> searchResultsPair = (Pair<List<Book>, Integer>) request.getAttribute("searchResultsPair");
	List<Book> searchResults = searchResultsPair.getLeft();
	Integer totalItems = searchResultsPair.getRight();	
	List<Category> categoryResults = (List<Category>) request.getAttribute("categoryResults");
	%>
	<%@ include file= "header.jsp" %>

<div class="searchBarSec">
		<form action="search" method="GET" class="container px-5">
			<div class="row mb-3">
				<div class="form-group" style="display: flex">
					<div class="input-group border-bottom">
						<input type="search" name=searchTerm placeholder="Search title or author ..."
			
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
		<div class="box">
		<%
		 if (searchResults != null) {
		%>
		Showing All <%=totalItems%> Books:
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
		} else if (searchResults == null) {
			%>
			<p>No results found</p>
			<%
			} 
%>
</div>
<% if(totalItems >0){int pageSize = 6; // Number of books to display per page
		   String bookPageNumber = request.getParameter("bookPageNumber");
		   int totalBookPages = (int) Math.ceil((double)(totalItems + pageSize - 1) / pageSize);    
		   int currentBookPage = (bookPageNumber != null) ? Integer.parseInt(bookPageNumber) : 1;
		
		   //Calculate start and end pages for pagination
		   int startBookPage = Math.max(1, currentBookPage - 2);
		   int endBookPage = Math.min(totalBookPages, startBookPage + 4); // +4 because we want to show 5 pages
		   startBookPage = Math.max(1, endBookPage - 4); // recalculate in case endPage is lower than startPage + 4
		
		%>
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <% if (currentBookPage > 1) { %>
		        <li class="page-item"><a class="page-link" href="?bookPageNumber=1&searchTerm=<%=searchTerm%>&categoryID=<%=categoryID%>" class=""><<</a></li>
		        <li class="page-item"><a class="page-link" href="?bookPageNumber=<%=currentBookPage-1%>&searchTerm=<%=searchTerm%>&categoryID=<%=categoryID%>" class=""><</a></li>
		        <% if (startBookPage > 1) { %> 
		            <li class="page-item"><span class="page-link">...</span></li> 
		        <% } %>
		    <% }
		    
		    for (int j = startBookPage; j <= endBookPage; j++) {
		        // Add the active class to the current page
		        String activeClass = (j == currentBookPage) ? "active" : "";
		    %>
		        <li class="page-item <%=activeClass%>"><a class="page-link" href="?bookPageNumber=<%=j%>&searchTerm=<%=searchTerm%>&categoryID=<%=categoryID%>" class=""><%=j%></a></li>
		    <%
		    }
		    
		    if (currentBookPage < totalBookPages) {
		        if (endBookPage < totalBookPages) { %> 
		            <li class="page-item"><span class="page-link">...</span></li> 
		        <% } %>
		        <li class="page-item"><a class="page-link" href="?bookPageNumber=<%=currentBookPage+1%>&searchTerm=<%=searchTerm%>&categoryID=<%=categoryID%>" class="">></a></li>
		        <li class="page-item"><a class="page-link" href="?bookPageNumber=<%=totalBookPages%>&searchTerm=<%=searchTerm%>&categoryID=<%=categoryID%>" class="">>></a></li>
		    <% } }%>	
		  </ul>
		</nav>
		</div>
		<%@include file="footer.jsp"%>
		</body>
		</html>
