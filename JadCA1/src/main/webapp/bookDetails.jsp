<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "books.Book"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<% Book book = (Book) request.getAttribute("results");
 if (book != null) {
        // Display book details
%>
        <h1>Book Details</h1>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Release Date</th>
            </tr>
            <tr>
                <td><%= book.getBookID() %></td>
                <td><%= book.getTitle() %></td>
                <td><%= book.getPrice() %></td>
                <td><%= book.getDescription() %></td>
                <td><%= book.getPubDate() %></td>
            </tr>
        </table>
        
<%
    } else {
        // Book not found or not available
%>
        <p>Book not found or not available.</p>
<%
    }
%>
</body>
</html>