<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/search.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
</style>
<%
	boolean loggedIn = false;
boolean adminIN = false;
	try { 
		int custID = (int) request.getSession().getAttribute("userID");
		if (custID != 0){ 
			loggedIn = true;
		}
		String role = (String) request.getSession().getAttribute("role");
		if (role.equals("admin")){ 
			adminIN = true;
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
						<%if(!adminIN){ 
							%> <li class="nav-item">
						
              <a class="nav-link" aria-current="page" href="/JadCA1/Cart">Cart</a>
            </li>
            <% } if (loggedIn){ 
            	if(!adminIN){
            	%>
            	<li class="nav-item">
              <a class="nav-link" href="/JadCA1/user">Profile</a>
            </li>
            
            	<% }
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
			if(adminIN){ 
				%>
				<li class="nav-item"><a class="nav-link smallNavText"
					aria-current="page" href="/JadCA1/admin">Admin</a></li><% 
			}
			%>
			<li class="nav-item"><form action="user" method="post"
					class="nav-link smallNavText" aria-current="page">
					<input type=hidden name="action" value="logout">
					<input type=submit value="Log Out">
					</form></li>
			<% 
		} else { 
		%>
			<li class="nav-item"><a class="nav-link smallNavText"
					aria-current="page" href="/JadCA1/register.jsp">Sign Up</a></li>
				<% 
		}
		%>
			
		</ul>
	</div>