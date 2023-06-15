<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="models.*"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/cart.css" %>
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
		
	}%>
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
				aria-current="page" href="/JadCA1/logOut.jsp">Log Out</a></li>
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
	 <div class="cartSec">
      <div class="container">
      <div class="table-responsive">
          <table class="table table-responsive table-borderless">
            <thead>
              <tr class="bg-light">
                <th scope="col" width="20%"></th>
                <th scope="col" width="20%">Title</th>
        
                <th scope="col" width="20%">Qty</th>
                <th scope="col" width="20%">Price</th>
                <th scope="col" class="text-end" width="20%"></th>
              </tr>
            </thead>
            <tbody>
	
	<% 
	double totalPrice = 0;
	List<Book> cart = (List<Book>) request.getAttribute("CartItems");
		  for (Book book : cart) {
		    
		        totalPrice += book.getPrice() * book.getQuantity();%> 
		        <tr>
                <td><img src=<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + book.getImageUrl() + ".jpg"%> style="width: 100px"alt="smma" /></td>
                <td><%=book.getTitle() %></td>
      
                <td>
                  <%=book.getQuantity()%>
                </td>
                <td><%=book.getPrice() %></td>
                <td class="text-end">
                  <form action="Cart" method="post">
			<input type="hidden" name="bookID" value="<%=book.getBookID() %>">
			<input class="removeBook btn-outline-danger type="submit" value="delete">
			</form>
                </td>
              </tr><% 
		    }
		%>
		
		   </tbody>
		 <div class="checkOutSec d-flex justify-content-end">
            <h4 id="totalPrice"><%=totalPrice %></h4>
            <button class="btn btn-success">Check out</button>
          </div>
          </table>
          </div>
         


</body>
</html>