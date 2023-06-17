<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
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

<form action = "admin" method="POST">
<input type= "hidden" name="action" value="updateCategory">
<input type= "hidden" name="categoryID" value="<%=categoryID%>">
<input type="text" name= "categoryName" value="<%=categoryName %>" required>
<input type="submit" value="Update">
</form>
</body>
</html>