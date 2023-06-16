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
</head>
<body>
	<%@ include file= "header.jsp" %>
	<% if(request.getAttribute("message")!= null){
		String message = (String)request.getAttribute("message");
		%>
		<script>
            alert('<%= message %>');
        </script>
    <%
	}
	%>
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
		  if(cart.size()>0){
			  for (Book book : cart) {
				    
			        totalPrice += book.getPrice() * book.getQuantity();%> 
			        <tr>
	                <td><img src=<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + book.getImageUrl() + ".jpg"%> style="width: 150px"alt="smma" /></td>
	                <td><%=book.getTitle() %></td>
	      
	                <td>
	                  <%=book.getQuantity()%>
	                </td>
	                <td><%=book.getPrice() %></td>
	                <td class="text-end">
	                  <form action="Cart" method="post">
	                  <input type="hidden" name="action" value="deleteFromCart">
				<input type="hidden" name="bookID" value="<%=book.getBookID() %>">
				<input class="removeBook btn-outline-danger" type="submit" value="delete">
				</form>
	                </td>
	              </tr><% 
			    }
		  }else{%>
			  <tr><td>There is nothing inside your cart.</td></tr>
			  <%
		  }
		%>
		
		   </tbody>
          </table>
          <div class="checkOutSec d-flex justify-content-end">
            <h4 id="totalPrice"><%=totalPrice %> </h4>
           	<form action="Cart" method="post">
           	<input type="hidden" name="action" value="checkOut">
           	<input type="submit" value="Check Out" class="btn btn-success">
           	</form>
          </div>
          </div>
         


</body>
</html>