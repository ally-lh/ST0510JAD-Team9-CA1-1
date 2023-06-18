<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import= "models.*" %>
    <%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Book Details</title>
<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/updateUser.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/root/js/bootstrap.min.js"></script>

</head>
<body>
<%@ include file="header.jsp" %>
    <%
    Book bookData =null;
    if(session.getAttribute("role")!=null){
    	if(((String) session.getAttribute("role")).equalsIgnoreCase("admin")){
    		
    	    if(request.getAttribute("bookResult")!= null){
    	    	 bookData =(Book) request.getAttribute("bookResult");
    	    }else {
    	    	request.getRequestDispatcher("admin").forward(request,response);
    	    	return;
    	    }
    	}else{
    		response.sendRedirect("login.jsp");
    	}
    }else{
    	response.sendRedirect("login.jsp");
    }
    %>
    <% if(request.getAttribute("message")!= null){
        String message = (String)request.getAttribute("message");
        %>
        <script>
       
        alert('<%= message %>');
        </script>
    <%
    }
    %>
      <div class="form">
      <div class="container">
        <h1>Update Book Details</h1>

   <form action="admin" method="post" enctype="multipart/form-data">
  <input type="hidden" name="action" value="updateBook">
  <input type="hidden" name="bookID" value="<%= bookData.getBookID() %>">

  <div class="mb-3">
    <label for="title" class="form-label">Title:</label><br>
    <input type="text" id="title" name="title" value="<%= bookData.getTitle() %>" class="form-control" required><br>
  </div>

  <div class="mb-3">
    <label for="author" class="form-label">Author:</label><br>
    <input type="text" id="author" name="author" value="<%= bookData.getAuthor() %>" class="form-control" required><br>
  </div>

  <div class="mb-3">
    <label for="price" class="form-label">Price:</label><br>
    <input type="text" id="price" name="price" value="<%= bookData.getPrice() %>" class="form-control" required><br>
  </div>

  <div class="mb-3">
    <label for="publisher" class="form-label">Publisher:</label><br>
    <input type="text" id="publisher" name="publisher" value="<%= bookData.getPublisher() %>" class="form-control" required><br>
  </div>

  <div class="mb-3">
    <label for="pubDate" class="form-label">Publication Date:</label><br>
    <input type="date" id="pubDate" name="pubDate" value="<%= bookData.getPubDate() %>" class="form-control" required><br>
  </div>

  <div class="mb-3">
    <label for="isbn" class="form-label">ISBN:</label><br>
    <input type="text" id="isbn" name="ISBN" value="<%= bookData.getISBN() %>" class="form-control" required><br>
  </div>

  <div class="mb-3">
    <label for="rating" class="form-label">Rating:</label><br>
    <input type="text" id="rating" name="rating" value="<%= bookData.getRating() %>" class="form-control" required><br>
  </div>

  <div class="mb-3">
    <label for="description" class="form-label">Description:</label><br>
    <input type="text" id="description" name="description" value="<%= bookData.getDescription() %>" class="form-control" required><br>
  </div>

  <h3>Current image</h3>
  <img alt="" src="<%= "https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + bookData.getImageUrl() + ".jpg" %>" class="img-fluid"><br>
  <input type="hidden" name="currentImage" value="<%=bookData.getImageUrl() %>">

  <div class="mb-3">
    <label for="newImage" class="form-label">Upload new image:</label><br>
    <input type="file" id="newImage" name="newImage" class="form-control"><br>
  </div>

  <div class="mb-3">
    <label for="category" class="form-label">Category:</label><br>
    <select name="category" class="form-select" required>
      <option value="">Select a category</option>
      <%
      List<Category> categoryResults = (List<Category>) request.getAttribute("categoryResults");
      if (categoryResults != null) {
          for (Category category : categoryResults) {
              String categoryName = category.getCategoryName();
              int categoryID = category.getCategoryID();
              String selected = "";
              if (bookData.getCategoryID() == categoryID) {
                  selected = "selected";
              }
      %>
      <option value="<%=categoryID%>" <%=selected%>><%=categoryName%></option>
      <%
          }
      }
      %>
    </select>
  </div>

  <div class="mb-3">
    <label for="quantity" class="form-label">Book Quantity:</label><br>
    <input type="number" name="quantity" step="1" value="<%=bookData.getQuantity() %>" class="form-control" required><br>
  </div>

  <button type="submit" class="btn btn-primary">Update Book</button>
</form>
</div>
</div>
<%@include file="footer.jsp"%>
</body>
</html>
