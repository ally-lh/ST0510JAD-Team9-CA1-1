<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="models.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Customer Details</title>
</head>
<body>
<% User customerDetails =(User) request.getAttribute("customerDetails"); %>
<% if(request.getAttribute("message")!= null){
		String message = (String)request.getAttribute("message");
		%>
		<script>
            alert('<%= message %>');
        </script>
    <%
	}
	%>
<h2>Update Customer Details</h2>

<form action="user" method="post">
	<input type = "hidden" name="action" value="update">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username" value="<%= customerDetails.getUserName() %>" required><br>
    <label for="email">Email:</label><br>
    <input type="email" id="email" name="email" value="<%= customerDetails.getEmail() %>" required><br>
    <label for="phoneNum">Phone Number:</label><br>
    <input type="tel" id="phoneNum" name="phoneNum"  pattern="^\d{10}$" title ="phone must be 10 digits."value="<%= customerDetails.getPhone() %> " required><br>
    <label for="password">Password:</label><br>
    <input type="text" id="password" name="password" value="<%= customerDetails.getPassword() %>" required><br>
    <input type="submit" value="Update">
</form>

</body>
</html>