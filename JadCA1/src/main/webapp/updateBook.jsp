<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import= "models.*" %>
    <%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Update Book Details</title>
</head>
<body>
    <%Book bookData =null;
    if(request.getAttribute("bookResults")!= null){
    	 bookData =(Book) request.getAttribute("bookResult");
    }else {
    	request.getRequestDispatcher("admin").forward(request,response);
    	return;
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
    <h2>Update Book Details</h2>

    <form action="admin" method="post" enctype="multipart/form-data">
        <input type="hidden" name="action" value="updateBook">
        <input type="hidden" name="bookID" value="<%= bookData.getBookID() %>">

        <label for="title">Title:</label><br>
        <input type="text" id="title" name="title" value="<%= bookData.getTitle() %>" required><br>

        <label for="author">Author:</label><br>
        <input type="text" id="author" name="author" value="<%= bookData.getAuthor() %>" required><br>

        <label for="price">Price:</label><br>
        <input type="text" id="price" name="price" value="<%= bookData.getPrice() %>" required><br>

        <label for="publisher">Publisher:</label><br>
        <input type="text" id="publisher" name="publisher" value="<%= bookData.getPublisher() %>" required><br>

        <label for="pubDate">Publication Date:</label><br>
        <input type="date" id="pubDate" name="pubDate" value="<%= bookData.getPubDate() %>" required><br>

        <label for="isbn">ISBN:</label><br>
        <input type="text" id="isbn" name="ISBN" value="<%= bookData.getISBN() %>" required><br>

        <label for="rating">Rating:</label><br>
        <input type="text" id="rating" name="rating" value="<%= bookData.getRating() %>" required><br>

        <label for="description">Description:</label><br>
        <input type="text" id="description" name="description" value="<%= bookData.getDescription() %>" required><br>
		
        <h3>>Current image</h3>
       	<img alt="" src=<%="https://res.cloudinary.com/dgf2upkwf/image/upload/v1686673253/" + bookData.getImageUrl() + ".jpg"%>><br>
		<input type = "hidden" name="currentImage" value="<%=bookData.getImageUrl() %>">

        <label for="newImage">Upload new image:</label><br>
        <input type="file" id="newImage" name="newImage" ><br>
		<select name="category" required>
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
	</select><br>

		<label for="quantity">Book Quantity:</label> 
		<input type="number" name="quantity" step="1" value="<%=bookData.getQuantity() %>" required><br> 
        <input type="submit" value="Update Book">
    </form>

</body>
</html>
