<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/signup.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
</style>
</head>
<body>

	<% String error = (String) request.getAttribute("error");
	if(error == null ){ 
		error ="";
	}
	
	
	%>
  	<%@ include file= "header.jsp" %>

    <!-- beginning of home page -->
    <div class="main">
      <div class="container">
        <div class="card">
          <h3>Log In</h3>
          <div class="bgline"></div>
          <div class="errorMsg"><%=error%></div>
          <form action="user" method="post">
          	<input type="hidden" name="action" value="login">
            <input type="text"  name="UserIdentification" placeholder="Email" />

            <input type="password" name="password" placeholder="Password" />

            <button type="submit" class="btn signUpbtn">Login</button>
          </form>
          <a href="/JadCA1/signup.jsp" class="forgotPassword"
            >Don't have an account?</a
          >
        </div>
      </div>
    </div>
    <footer class="text-center bg-dark text-white p-4">
      Copyright @ JAD-Bookstore
    </footer>

</body>
</html>