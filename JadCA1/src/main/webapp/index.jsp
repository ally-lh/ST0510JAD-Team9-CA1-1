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
</head>

<body>
	<%@ include file= "header.jsp" %>
	<!-- beginning of home page -->
	<% if(request.getAttribute("message")!= null){
		String message = (String)request.getAttribute("message");
		%>
		<script>
            alert('<%= message %>');
        </script>
    <%
	}
	%>
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
