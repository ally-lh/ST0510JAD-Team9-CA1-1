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
	boolean loggedIn = false;
	if(error == null ){ 
		error ="";
	}
	try { 
		int custID = (int) request.getSession().getAttribute("userID");
		if (custID != 0){ 
			loggedIn = true;
		}
	} catch (Exception e){ 
		
	}
	
	%>
  	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container">
			<a class="navbar-brand" href="/JadCA1/index.jsp">JAD-Bookstore</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="collapse navbar-collapse d-flex justify-content-end"
				id="navbarNav">
				  <ul class="navbar-nav">
          <li class="nav-item"><a class="nav-link" aria-current="page"
						href="/JadCA1/search">Search</a></li>
						 <li class="nav-item">
              <a class="nav-link" aria-current="page" href="/JadCA1/Cart">Cart</a>
            </li>
            <% if (loggedIn){ 
            	%>
            	<li class="nav-item">
              <a class="nav-link" href="/JadCA1/profile.jsp">Profile</a>
            </li>
            
            	<% 
            } else { 
            	%>
            	   <li class="nav-item">
              <a class="nav-link" href="/JadCA1/login.jsp">Login</a>
            </li>
            	<%
            }
%>
          </ul>
			</div>
		</div>
	</nav>
   <div class="bottomNav navbar-expand">
		<ul class="navbar-nav container d-flex justify-content-end">
		<%
		if(loggedIn){ 
			%>
			<li class="nav-item"><a class="nav-link smallNavText"
				aria-current="page" href="/JadCA1/logOut.jsp">Log Out</a></li>
			<% 
		} else { 
		%>
			<li class="nav-item"><a class="nav-link smallNavText"
					aria-current="page" href="/JadCA1/signup.jsp">Sign Up</a></li>
				<% 
		}
		%>
			
		</ul>
	</div>

    <!-- beginning of home page -->
    <div class="main">
      <div class="container">
        <div class="card">
          <h3>Log In</h3>
          <div class="bgline"></div>
          <div class="errorMsg"><%=error%></div>
          <form action="user" method="Get">
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