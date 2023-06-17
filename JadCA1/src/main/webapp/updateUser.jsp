<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="models.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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
<form action="admin" method="post">
  <input type="hidden" name="action" value="updateUser">
  <input type="hidden" name="userID" value="<%=user.getUserID() %>">
  <label for="username">Username:</label><br>
  <input type="text" id="username" name="username" value="<%= user.getUserName() %>" required><br>
  <label for="email">Email:</label><br>
  <input type="email" id="email" name="email" value="<%= user.getEmail() %>" required><br>
  <label for="phoneNum">Phone:</label><br>
  <input type="tel" id="phoneNum" name="phoneNum"><br>
  <label for="password">Password:</label><br>
  <input type="text" id="password" name="password" value="<%= user.getPassword() %>" required><br>
  <label>Role:</label><br>
  <input type="radio" id="admin" name="isAdmin" value="admin" <%= user.getRole().equalsIgnoreCase("admin") ? "checked" : "" %>>
  <label for="admin">Admin</label><br>
  <input type="radio" id="customer" name="isAdmin" value="customer" <%= user.getRole().equalsIgnoreCase("customer") ? "checked" : "" %>>
  <label for="customer">Customer</label><br>
  <input type="submit" value="Update User">
</form>
</body>
</html>
