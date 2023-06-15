<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>JAD-BookStore(Index)</title>


<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/index.css" %>
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
</head>

<body>
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

	<!-- beginning of home page -->
	<div class="headLineSection">
		<header class="w-auto">
			<h1 class="text-center">
				Every book deserves <br />to be read.
			</h1>
			<p class="text-center mt-3">
				We aim to provide you with the best books a the best price. Please
				scroll <br />through our selection.
			</p>
		</header>
		<div class="headerBooks">
			<div class="bookList d-flex justify-content-center">
				<div class="book">
					<img class="bookCover" src="./img/SMMA(T).jpg" alt="smma" />
					<h3 class="text-center">Book Title</h3>
				</div>
				<div class="book">
					<img class="bookCover" src="./img/SMMA(T).jpg" alt="smma" />
					<h3 class="text-center">Book Title</h3>
				</div>
				<div class="book">
					<img class="bookCover" src="./img/SMMA(T).jpg" alt="smma"/>
					
					<h3 class="text-center">Book Title</h3>
				</div>
			</div>
			<a href="/JadCA1/search"><button class="btn viewMore">View
					More</button></a>
		</div>
	</div>
	<div class="bookOfMonth d-flex justify-content-evenly">
		<div class="text">
			<h5>Book of the month</h5>
			<h2 class="fst-italic" id="bookOfMonthTitle">Book Title</h2>
			<p class="fst-italic" id="bookOfMonthAuthor">By Author Name</p>
			<p id="bookOfMonthDesc">Lorem ipsum dolor sit amet consectetur
				adipisicing elit. Corporis necessitatibus vel, libero impedit
				tempore repellendus fugiat doloribus laudantium quasi? Itaque iure,
				optio ex temporibus tempora obcaecati similique. Vel, aperiam
				architecto!</p>
		</div>
		<img src="./img/SMMA(T).jpg" alt="smma" />
	</div>
	<footer class="text-center bg-dark text-white p-4"> Copyright
		@ JAD-Bookstore </footer>
</body>
</html>
