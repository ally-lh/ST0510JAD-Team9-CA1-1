<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
    <%@ page import="models.*" %>
    <%@ page import="services.*" %>
    <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>JAD-BookStore(Index)</title>


<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/indexPage.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
</style>

<%
List<Book> bookList = new ArrayList<Book>();
for(int i =8;i<11;i++) {
	bookList.add(BookServices.fetchBookDataByID(i));
}
Book bookOfMonth = BookServices.getBookOfTheMonth();
%>
</head>

<body>
	<%@ include file="header.jsp" %>
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
			<%for (Book book : bookList){ 
				%>
				<a href="/JadCA1/bookDetails.jsp?bookID=<%=book.getBookID()%>">
				<div class="bookDisplay">
					<img class="bookCover" src="<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + book.getImageUrl() + ".jpg"%>" alt="smma" />
					<h3 class="text-center"><%=book.getTitle() %></h3>
				</div>
				</a>
			<%} %>
				
			</div>
			<a href="/JadCA1/search"><button class="btn viewMore">View
					More</button></a>
		</div>
	</div>
	<div class="bookOfMonth d-flex justify-content-evenly">
		<div class="text">
			<h5>Book of the month</h5>
			<h2 class="fst-italic" id="bookOfMonthTitle"><%=bookOfMonth.getTitle()%></h2>
			<p class="fst-italic" id="bookOfMonthAuthor"><%=bookOfMonth.getAuthor()%></p>
			<p id="bookOfMonthDesc"><%=bookOfMonth.getDescription()%></p>
		</div>
		<img src=<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + bookOfMonth.getImageUrl() + ".jpg"%> alt="smma" />
	</div>
<%@include file="footer.jsp"%>
</body>
</html>
