<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
int categoryID = 0;
String categoryName = null;
if(request.getParameter("categoryID")!= null && request.getParameter("categoryName")!=null){
	categoryID = Integer.parseInt(request.getParameter("categoryID"));
	categoryName = request.getParameter("categoryName");
}
else {
	request.getRequestDispatcher("admin").forward(request,response);
	return;
}
%>
	<%@ include file="header.jsp" %>
<div class="form">
      <div class="container">
        <h1>Update Category</h1>
      <form action="admin" method="post">
  <input type="hidden" name="action" value="updateCategory">
  <input type="hidden" name="categoryID" value="<%=categoryID%>">

  <div class="mb-3">
    <input type="text" name="categoryName" value="<%=categoryName %>" class="form-control" required>
  </div>

  <button type="submit" class="btn btn-primary">Update</button>
</form>
      </div>
      </div>
<%@include file="footer.jsp"%>
</body>
</html>