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
<script type="text/javascript" src="<%=request.getContextPath()%>/root/js/bootstrap.min.js"></script>
</head>

<% if(request.getAttribute("message")!= null){
		String message = (String)request.getAttribute("message");
		%>
		<script>
            alert('<%= message %>');
        </script>
    <%
	}
	%><body>
	<%@ include file="header.jsp" %>
		<div class="main">
      <div class="container">
        <div class="card">
          <h3>Sign Up</h3>
          <div class="bgline"></div>
          <form action="user" method="POST">
          	<input type="hidden" name="action" value="register">
            <input type="text" name="username" placeholder="Username" required/>
            <input type="text" name="email" placeholder="Email" required/>
            <input type="text" name="phoneNum" placeholder="Phone" pattern="^\d{10}$" title ="phone must be 10 digits." required/>
            <input type="password" name="password" placeholder="Password" required />
            <input
              type="password"
              name="confirm-password"
              placeholder="Confirm Password" required
            />
            <button type="submit" class="btn signUpbtn" name ="action">Sign Up</button>
          </form>
          <a href="/JadCA1/login.jsp" class="forgotPassword">Already have an Account?</a>
        </div>
      </div>
    </div>
<%@include file="footer.jsp"%>
</body>
</html>