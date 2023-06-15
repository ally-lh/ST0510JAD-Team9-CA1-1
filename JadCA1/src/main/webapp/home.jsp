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
<body><%
	boolean loggedIn = false;
	try { 
		int custID = (int) request.getSession().getAttribute("userID");
		if (custID != 0){ 
			loggedIn = true;
		}
	} catch (Exception e){ 
		
	}
	%>
	<%@ page import="java.util.List"%>
	<%@ page import="models.*"%>
	
	
	<%
	List<Book> searchResults = (List<Book>) request.getAttribute("searchResults");
	List<Category> categoryResults = (List<Category>) request.getAttribute("categoryResults");
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

