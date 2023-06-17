<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="models.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/updateUser.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/root/js/bootstrap.min.js"></script>

</head>
<body>
<%
User user = null;
if(session.getAttribute("role")!= null){
	if(((String) session.getAttribute("role")).equalsIgnoreCase("admin")){
		  if (request.getAttribute("userDetails") != null) {
		    user = (User) request.getAttribute("userDetails");
		  } else {
		    request.getRequestDispatcher("admin").forward(request, response);
		    return;
		  }
	}
	else{
		response.sendRedirect("login.jsp");
		return;
	}
}else {
	response.sendRedirect("login.jsp");
	return;
}
  
%>
	<%@ include file="header.jsp" %>
  <div class="form">
      <div class="container">
        <h1>Update User</h1>
        <form action="admin" method="post">
          <input type="hidden" name="action" value="updateUser" />
          <input type="hidden" name="userID" value="<%=user.getUserID()%>" />

          <div class="mb-3">
            <label for="username" class="form-label">Username:</label>
            <input
              type="text"
              id="username"
              name="username"
              value="<%=user.getUserName()%>"
              class="form-control"
              required
            />
          </div>

          <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input
              type="email"
              id="email"
              name="email"
              value="<%=user.getEmail()%>"
              class="form-control"
              required
            />
          </div>

          <div class="mb-3">
            <label for="phoneNum" class="form-label">Phone:</label>
            <input
              type="tel"
              id="phoneNum"
              name="phoneNum"
              class="form-control"
            />
          </div>

          <div class="mb-3">
            <label for="password" class="form-label">Password:</label>
            <input
              type="password"
              id="password"
              name="password"
              value="<%=user.getPassword()%>"
              class="form-control"
              required
            />
          </div>

          <div class="mb-3">
            <label class="form-label">Role:</label><br />
            <div class="form-check form-check-inline">
              <input type="radio" id="admin" name="isAdmin" value="admin"
              class="form-check-input"
              <%=user.getRole().equalsIgnoreCase("admin") ? "checked" : ""%>>
              <label for="admin" class="form-check-label">Admin</label>
            </div>
            <div class="form-check form-check-inline">
              <input type="radio" id="customer" name="isAdmin" value="customer"
              class="form-check-input"
             <%=user.getRole().equalsIgnoreCase("customer") ? "checked" : ""%>>
              <label for="customer" class="form-check-label">Customer</label>
            </div>
          </div>

          <button type="submit" class="btn btn-primary">Update User</button>
        </form>
      </div>
    </div>
<%@include file="footer.jsp"%>
</body>
</html>
