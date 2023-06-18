<%@page import="java.util.List"%>
<%@page import="services.CartServices"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="models.User" %>
        <%@ page import="models.Order" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/profile.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
    
</style>
<%@ page import="models.User" %>
 <script type="text/javascript" src="<%=request.getContextPath()%>/root/js/bootstrap.min.js"></script>

<% User customerDetails =(User) request.getAttribute("customerDetails"); %>
<%
if(request.getAttribute("message")!= null){
		String message = (String)request.getAttribute("message");
		%>
		<script>
            alert('<%= message %>');
        </script>
    <%
	}
	%>
</head>
<body>
<%@ include file="header.jsp" %>
 <div class="profileTabsSec">
      <div class="container">
        <div class="d-flex profileTabsContainer">
          <div
            class="nav flex-column nav-pills me-3"
            id="v-pills-tab"
            role="tablist"
            aria-orientation="vertical"
          >
            <button
              class="nav-link active"
              id="v-pills-PD-tab"
              data-bs-toggle="pill"
              data-bs-target="#v-pills-PD"
              type="button"
              role="tab"
              aria-controls="v-pills-PD"
              aria-selected="true"
            >
              Personal Details
            </button>
            <button
              class="nav-link"
              id="v-pills-OH-tab"
              data-bs-toggle="pill"
              data-bs-target="#v-pills-OH"
              type="button"
              role="tab"
              aria-controls="v-pills-OH"
              aria-selected="false"
            >
              Order History
            </button>
          </div>
          <!-- tab content -->
          <div class="tab-content" id="v-pills-tabContent">
            <!-- personal Details tab content -->
            <div
              class="tab-pane fade show active PD"
              id="v-pills-PD"
              role="tabpanel"
              aria-labelledby="v-pills-PD-tab"
              tabindex="0"
            >
              <form action="user" method="post" class="d-flex flex-column">
     <input type = "hidden" name="action" value="update">
                <div class="input-group">
              
                  <span class="input-group-text">Username</span>
                  <input
                    type="text"
                    aria-label="Username"
                    class="form-control"
                    name="username" value="<%= customerDetails.getUserName() %>" required
                  />
                </div>
                <div class="input-group">
                  <span class="input-group-text">Email</span>
                  <input type="text" aria-label="Email" class="form-control"
                  name="email" value="<%= customerDetails.getEmail() %>" required/>
                </div>
                <div class="input-group">
                  <span class="input-group-text">Phone</span>
                  <input type="text" aria-label="Phone" class="form-control" 
                  name="phoneNum"pattern="^\d{10}$" value="<%= customerDetails.getPhone() %>" required"/>
                </div>
                <div class="linebreak"></div>
                <div class="input-group">
                  <span class="input-group-text">Password</span>
                  <input
                    type="text"
                    aria-label="Password"
                    class="form-control"
                    name="password" value="<%= customerDetails.getPassword() %>" required />
          
                </div>
  				<input type="submit" value="Update">
                <button class="btn-outline-danger logOut">Log Out</button>
              </form>
            </div>
            <!-- Order history tab content -->
            <div
              class="tab-pane fade"
              id="v-pills-OH"
              role="tabpanel"
              aria-labelledby="v-pills-OH-tab"
              tabindex="0"
            >
              <form action="Cart" method="post">
              <input type="hidden" name="action" value="clearOrder">
              <input type="submit" value="Clear History" class="btn btn-outline-danger">
              
              </form>
  
        
              <table class="table table-hover">
                <thead>
                  <tr>
                    <th scope="col">ISBN-13</th>
                    <th scope="col">Book Title</th>
                    <th scope="col">Qty</th>
                    <th scope="col">Date Purchased</th>
                  </tr>
                </thead>
                <tbody>
                <% List<Order> orderList = (List<Order>) request.getAttribute("orderDetails");
                	if(orderList.size() > 0){ 
                		for(Order order : orderList){ 
                	
                %>
                  <tr>
                    <th scope="row"><%=order.getISBN()%></th>
                    <td><%=order.getTitle()%></td>
                    <td><%=order.getQuantity()%></td>
                    <td><%=order.getOrderDate()%></td>
                  </tr>
                  <%}
                		} else { 
                		%>
                		 <tr>
                    <th scope="row">You have no orders.</th>
                    <td></td>
                    <td></td>
                    <td></td>
                  </tr>
                		<%} %>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
</body>
</html>