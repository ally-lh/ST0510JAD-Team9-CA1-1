<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
 <h2>Add User</h2>
    <form action="admin" method="post">
        <input type = "hidden" name="action" value="addUser">
        <label for="username">Username:</label><br>
        <input type="text" id="username" name="username" required><br>
        <label for="email">Email:</label><br>
        <input type="email" id="email" name="email" required><br>
        <label for="phoneNum">Phone:</label><br>
    	<input type="tel" id="phoneNum" name="phoneNum"><br>
        <label for="password">Password:</label><br>
        <input type="password" id="password" name="password" required><br>
        <label>Role:</label><br>
        <input type="radio" id="admin" name="isAdmin" value="admin" >
        <label for="admin">Admin</label><br>
        <input type="radio" id="customer" name="isAdmin" value="customer" checked>
        <label for="customer">Customer</label><br>
        <input type="submit" value="Add User">
    </form>
</body>
</html>
