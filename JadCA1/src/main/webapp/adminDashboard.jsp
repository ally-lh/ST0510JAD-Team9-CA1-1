<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="models.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>
<style type="text/css">
    <%@include file="/css/navbar.css" %>
    <%@include file="/css/admin.css" %>
    <%@include file="/root/css/bootstrap.min.css" %>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/root/js/bootstrap.min.js"></script>

<%
    String message = (String) request.getAttribute("message");
    if (message != null) {
%>
        <script>
            alert("<%= message %>");
        </script>
<%} %>
<% 
String userPageNumber = (String) request.getParameter("userPageNumber");
String navLinkClassID = (userPageNumber != null)  ? "active" : "";
String navLinkClassPane = (userPageNumber != null) ? "show active" : "";
String navLinkClassIDBooks = (userPageNumber != null)  ? "" : "active";
String navLinkClassPaneBooks = (userPageNumber != null) ? "" : "show active";
%>
</head>
<body>
	<%@ include file="header.jsp" %>
	
	 <div class="ManageAdmin">
      <h1 class="text-center">Admin Dashboard</h1>
      <div class="container">
        <div class="d-flex ManageAdminContainer">
          <div
            class="nav flex-column nav-pills me-3"
            id="v-pills-tab"
            role="tablist"
            aria-orientation="vertical"
          >
            <button
              class="nav-link <%=navLinkClassIDBooks%>"
              id="v-pills-books-tab"
              data-bs-toggle="pill"
              data-bs-target="#v-pills-books"
              type="button"
              role="tab"
              aria-controls="v-pills-books"
              aria-selected="true"
            >
              Manage Books
            </button>
            <button
              class="nav-link"
              id="v-pills-cat-tab"
              data-bs-toggle="pill"
              data-bs-target="#v-pills-cat"
              type="button"
              role="tab"
              aria-controls="v-pills-cat"
              aria-selected="false"
            >
              Manage Categories
            </button>
            <button
              class="nav-link <%=navLinkClassID%>"
              id="v-pills-user-tab"
              data-bs-toggle="pill"
              data-bs-target="#v-pills-user"
              type="button"
              role="tab"
              aria-controls="v-pills-user"
              aria-selected="false"
            >
              Manage Users
            </button>
          </div>
          <!-- tab content -->
          <div class="tab-content" id="v-pills-tabContent">
            <!-- personal Details tab content -->
            <div
              class="tab-pane fade <%=navLinkClassPaneBooks%> PD"
              id="v-pills-books"
              role="tabpanel"
              aria-labelledby="v-pills-books-tab"
              tabindex="0"
            >
              <div class="d-flex justify-content-end pb-4">
              <button class="btn addBookBtn" data-bs-toggle="modal"
                    data-bs-target="#addBookModal">+ Add Book</button>
              </div>
              <div class="table-responsive">
                <table class="table table-responsive table-borderless">
            <thead>
              <tr class="bg-light">

                <th scope="col" width="20%">ISBN-13</th>
                <th scope="col" width="20%">Title</th>
                <th scope="col" width="10%">Author</th>
                <th scope="col" width="20%">Stock</th>
                <th scope="col" class="text-end" width="20%"></th>
                <th scope="col" class="text-end" width="20%"></th>
              </tr>
            </thead>
            <tbody>
            <%
		List<Book> bookResults = (List<Book>) request.getAttribute("bookResults");
		for (Book book : bookResults) {
		%>
              <tr>
 
                <td><%=book.getISBN()%></td>
                <td><%=book.getTitle()%></td>
                <td>
                 <%=book.getAuthor()%>
                </td>
                <td>
                 <%=book.getQuantity()%> 
                </td>
        
                <td class="text-end">
                 <form action="updateBook" method="post">
					<input type="hidden" name="bookID" value="<%=book.getBookID()%>">
					<input class ="btn btn-outline-dark"type="submit" value="Update">
					
				</form>
				</td>
				<td>
				<form action="admin" method="POST">
					<input type="hidden" name="action" value="deleteBook"> <input
						type="hidden" name="bookID" value="<%=book.getBookID()%>">
						<input type="hidden" name="imageUrl" value="<%= book.getImageUrl() %>">
					<input class ="btn btn-outline-danger" type="submit" value="Delete">
				</form>
                  
                </td>
              </tr>
	<%
		}
		%>
            </tbody>
          </table>
		          <% int pageSize = 6; // Number of books to display per page
		   int bookAmount = (int) request.getAttribute("bookAmount");
		   int totalBookPages = (int) Math.ceil((double) bookAmount / pageSize);
		   String bookPageNumber = request.getParameter("bookPageNumber");
		       
		   int currentBookPage = (bookPageNumber != null) ? Integer.parseInt(bookPageNumber) : 1;
		
		   //Calculate start and end pages for pagination
		   int startBookPage = Math.max(1, currentBookPage - 2);
		   int endBookPage = Math.min(totalBookPages, startBookPage + 4); // +4 because we want to show 5 pages
		   startBookPage = Math.max(1, endBookPage - 4); // recalculate in case endPage is lower than startPage + 4
		
		%>
		<nav aria-label="Page navigation example">
		  <ul class="pagination justify-content-center">
		    <% if (currentBookPage > 1) { %>
		        <li class="page-item"><a class="page-link" href="?bookPageNumber=1" class=""><<</a></li>
		        <li class="page-item"><a class="page-link" href="?bookPageNumber=<%=currentBookPage-1%>" class=""><</a></li>
		        <% if (startBookPage > 1) { %> 
		            <li class="page-item"><span class="page-link">...</span></li> 
		        <% } %>
		    <% }
		    
		    for (int i = startBookPage; i <= endBookPage; i++) {
		        // Add the active class to the current page
		        String activeClass = (i == currentBookPage) ? "active" : "";
		    %>
		        <li class="page-item <%=activeClass%>"><a class="page-link" href="?bookPageNumber=<%=i%>" class=""><%=i%></a></li>
		    <%
		    }
		    
		    if (currentBookPage < totalBookPages) {
		        if (endBookPage < totalBookPages) { %> 
		            <li class="page-item"><span class="page-link">...</span></li> 
		        <% } %>
		        <li class="page-item"><a class="page-link" href="?bookPageNumber=<%=currentBookPage+1%>" class="">></a></li>
		        <li class="page-item"><a class="page-link" href="?bookPageNumber=<%=totalBookPages%>" class="">>></a></li>
		    <% } %>	
		  </ul>
		</nav>

              </div>
            </div>
            <div
              class="tab-pane fade"
              id="v-pills-cat"
              role="tabpanel"
              aria-labelledby="v-pills-cat-tab"
              tabindex="0"
            >
              <div class="container">
               <div class="d-flex justify-content-end pb-4">
                  <button class="btn addBookBtn" data-bs-toggle="modal"
                    data-bs-target="#addCategory Modal">+ Add Category</button>
                </div>
                <div class="table-responsive">
                  <table class="table table-responsive table-borderless">
                    <thead>
                      <tr class="bg-light">
                        <th scope="col" width="50%">Category</th>
                        <th scope="col" width="20%"></th>
                            <th scope="col" width="20%"></th>
                        
                        
                      </tr>
                    </thead>
                    <tbody>
                     <%
		List<Category> categoryResults = (List<Category>) request.getAttribute("categoryResults");
		for (Category category : categoryResults) {
		%>
		<tr>
			<td><%=category.getCategoryName()%></td>
			<td>
				<form action="updateCategory.jsp" method="POST">
					<input type="hidden" name="categoryID" value="<%=category.getCategoryID()%>"> 
					<input type="hidden" name="categoryName" value="<%=category.getCategoryName()%>">
					<input class="btn btn-outline-dark"type="submit" value="Update">
				</form>
				
			</td>
			<td>
			<form action="admin" method="POST">
					<input type="hidden" name="action" value="deleteCategory"> 
					<input type="hidden" name="categoryID" value="<%=category.getCategoryID()%>">
					<input class="btn btn-outline-danger" type="submit" value="Delete">
				</form></td>
		</tr>
		<%
		}
		%>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div
              class="tab-pane fade <%=navLinkClassPane%>"
              id="v-pills-user"
              role="tabpanel"
              aria-labelledby="v-pills-user-tab"
              tabindex="0"
            >
              <div class="container">
                <div class="d-flex justify-content-end pb-4">
                  <button class="btn addBookBtn" data-bs-toggle="modal"
                    data-bs-target="#addUserModal">+ Add User</button>
                </div>
                <div class="table-responsive">
                  <table class="table table-responsive table-borderless">
                    <thead>
                      <tr class="bg-light">
                        <th scope="col" width="20%">Name</th>
                        <th scope="col" width="20%">Email</th>
                        <th scope="col" width="20%">Phone</th>
                        <th scope="col" width="20%">Role</th>
                        <th scope="col" width="20%"></th>
                        <th scope="col" width="20%"></th>
                      </tr>
                    </thead>
                    <tbody>
                     <%
		List<User> userData = (List<User>) request.getAttribute("userData");
		for (User user : userData) {
		%>
		<tr>
			<td><%=user.getUserName()%></td>
			<td><%=user.getEmail()%></td>
			<td><%=user.getPhone()%></td>
			<td><%=user.getRole() %></td>
			<td>
				<form action="updateUser" method="GET">
					<input type="hidden" name="userID" value="<%=user.getUserID()%>">
					<input class="btn btn-outline-dark" type="submit" value="Update">
				</form>
				</td>
				<td>
				<form action="admin" method="POST">
					<input type="hidden" name="action" value="deleteUser"> <input
						type="hidden" name="userID" value="<%=user.getUserID()%>">
					<input  class="btn btn-outline-danger" type="submit" value="Delete">
				</form>
			</td>
		</tr>
		<%
		}
		%>
                    </tbody>
                  </table>
					                  <% 
					   int userAmount = (int) request.getAttribute("userAmount");
					   int totalPages = (int) Math.ceil((double) userAmount / 6);
					   userPageNumber = request.getParameter("userPageNumber");
					       
					   int currentPage = (userPageNumber != null) ? Integer.parseInt(userPageNumber) : 1;
					
					   //Calculate start and end pages for pagination
					   int startPage = Math.max(1, currentPage - 2);
					   int endPage = Math.min(totalPages, startPage + 4); // +4 because we want to show 5 pages
					   startPage = Math.max(1, endPage - 4); // recalculate in case endPage is lower than startPage + 4
					
					%>
					<nav aria-label="Page navigation example">
					  <ul class="pagination justify-content-center">
					    <% if (currentPage > 1) { %>
					        <li class="page-item"><a class="page-link" href="?userPageNumber=1" class=""><<</a></li>
					        <li class="page-item"><a class="page-link" href="?userPageNumber=<%=currentPage-1%>" class=""><</a></li>
					        <% if (startPage > 1) { %> 
					            <li class="page-item"><span class="page-link">...</span></li> 
					        <% } %>
					    <% }
					    
					    for (int i = startPage; i <= endPage; i++) {
					        // Add the active class to the current page
					        String activeClass = (i == currentPage) ? "active" : "";
					    %>
					        <li class="page-item <%=activeClass%>"><a class="page-link" href="?userPageNumber=<%=i%>" class=""><%=i%></a></li>
					    <%
					    }
					    
					    if (currentPage < totalPages) {
					        if (endPage < totalPages) { %> 
					            <li class="page-item"><span class="page-link">...</span></li> 
					        <% } %>
					        <li class="page-item"><a class="page-link" href="?userPageNumber=<%=currentPage+1%>" class="">></a></li>
					        <li class="page-item"><a class="page-link" href="?userPageNumber=<%=totalPages%>" class="">>></a></li>
					    <% } %>   
					  </ul>
					</nav>
                  
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
	
	
	<!-- add user modal -->
	 <div
      class="modal fade"
      id="addUserModal"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabindex="-1"
      aria-labelledby="addUser"
      aria-hidden="true"
    >
      <div class="modal-dialog modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="addUserLabel">Add User</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
           
       <form action="admin" method="post">
    <input type="hidden" name="action" value="addUser">

    <div class="mb-3">
        <label for="username" class="form-label">Username:</label>
        <input type="text" id="username" name="username" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="email" class="form-label">Email:</label>
        <input type="email" id="email" name="email" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="phoneNum" class="form-label">Phone:</label>
        <input type="number" id="phoneNum" name="phoneNum" pattern="^\d{10}$" class="form-control">
    </div>

    <div class="mb-3">
        <label for="password" class="form-label">Password:</label>
        <input type="password" id="password" name="password" class="form-control" required>
    </div>

    <div class="mb-3">
        <label class="form-label">Role:</label>
        <br>
        <div class="form-check form-check-inline">
            <input type="radio" id="admin" name="isAdmin" value="admin" class="form-check-input">
            <label for="admin" class="form-check-label">Admin</label>
        </div>
        <div class="form-check form-check-inline">
            <input type="radio" id="customer" name="isAdmin" value="customer" class="form-check-input" checked>
            <label for="customer" class="form-check-label">Customer</label>
        </div>
    </div>

    <button type="submit" class="btn btn-primary">Add User</button>
</form>
            </div>
      
          </div>
      
      </div>
    </div>
    
    <!-- add book -->
     <div
      class="modal fade"
      id="addBookModal"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabindex="-1"
      aria-labelledby="addBook"
      aria-hidden="true"
    >
      <div class="modal-dialog  modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="addUserLabel">Add Book</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
  <form action="admin" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="addBook">

    <div class="mb-3">
        <label for="title" class="form-label">Title:</label>
        <input type="text" name="title" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="author" class="form-label">Author:</label>
        <input type="text" name="author" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="price" class="form-label">Price:</label>
        <input type="number" name="price" step="0.01" min="0.01" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="publisher" class="form-label">Publisher:</label>
        <input type="text" name="publisher" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="pubDate" class="form-label">Publication Date:</label>
        <input type="date" name="pubDate" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="ISBN" class="form-label">ISBN:</label>
        <input type="text" name="ISBN" class="form-control" pattern="^\d{13}$" title="ISBN must be 13 digits"required>
    </div>

    <div class="mb-3">
        <label for="rating" class="form-label">Rating:</label>
        <input type="number" name="rating" step="0.1" min="0" max="5" class="form-control" required>
    </div>

    <div class="mb-3">
        <label for="description" class="form-label">Description:</label>
        <textarea name="description" class="form-control" required></textarea>
    </div>

    <div class="mb-3">
        <label for="image" class="form-label">Image:</label>
        <input type="file" name="image" class="form-control" required>
    </div> 

    <div class="mb-3">
        <label for="category" class="form-label">Category:</label>
        <select name="category" class="form-select" required>
            <option value="">Select a category</option>
            <%
            if (categoryResults != null) {
                for (Category category : categoryResults) {
                    String categoryName = category.getCategoryName();
                    int categoryID = category.getCategoryID();
            %>
            <option value="<%=categoryID%>"><%=categoryName%></option>
            <%
                }
            }
            %>
        </select>
    </div>

    <div class="mb-3">
        <label for="quantity" class="form-label">Book Quantity:</label>
        <input type="number" name="quantity" step="1" class="form-control" required>
    </div>

    <button type="submit" class="btn btn-primary">Add Book</button>
</form>
            </div>
      
          </div>
   
      </div>
    </div>
    
    
    <!--  ADD CATEGory -->
        <div
      class="modal fade"
      id="addCategoryModal"
      data-bs-backdrop="static"
      data-bs-keyboard="false"
      tabindex="-1"
      aria-labelledby="addCategory"
      aria-hidden="true"
    >
      <div class="modal-dialog  modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="addUserLabel">Add Category</h5>
              <button
                type="button"
                class="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div class="modal-body">
  <form action="admin" method="post">
    <input type="hidden" name="action" value="addCategory">

    <div class="mb-3">
        <label for="categoryName" class="form-label">Category:</label>
        <input type="text" name="categoryName" class="form-control" required>
    </div>

    
    <button type="submit" class="btn btn-primary">Add Category</button>
</form>
            </div>
      
          </div>
   
      </div>
    </div>
    
<%@include file="footer.jsp"%>
</body>
</html>
