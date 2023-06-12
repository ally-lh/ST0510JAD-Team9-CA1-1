<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="user" method="Post">
		<input name="UserIdentification" type='text' required> <br>
		<br><input name="password" type='password' required>
		<br> <br> <input type="submit" value="login"
			name="action"> <input type="reset" value="Reset"
			name="btnReset">
	</form>
</body>
</html>