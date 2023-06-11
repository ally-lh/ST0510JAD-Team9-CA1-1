<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<%
    String errorMessage = (String) request.getAttribute("error");
    if (errorMessage != null) {
%>
        <script>
            alert("<%= errorMessage %>");
        </script>
<%
    }
%><body>
	<form action="user" method="POST">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required><br><br>
    
    <label for="phoneNum">Phone:</label>
    <input type="tel" id="phoneNum" name="phoneNum" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="confirm-password">Confirm Password:</label>
    <input type="password" id="confirm-password" name="confirm-password" required><br><br>

    <input type="submit" value="Register">
  </form>
</body>
</html>