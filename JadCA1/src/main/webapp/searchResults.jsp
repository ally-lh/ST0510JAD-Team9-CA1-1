<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="models.Book" %>
<%
    List<Book> results = (List<Book>) request.getAttribute("results");
%>

 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>Search Results</h1>

    <% if (results.isEmpty()) { %>
        <p>No results found</p>
    <% } else { %>
        <table>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Price</th>
                <th>Description</th>
                <th>Release Date</th>
            </tr>
            <% for (Book book : results) { %>
                <tr>
                    <td><%= book.getBookID() %></td>
                    <td><%= book.getTitle()%></td>
                    <td><%= book.getPrice() %></td>
                    <td><%= book.getDescription() %></td>
                    <td><%= book.getPubDate() %></td>
                </tr>
            <% } %>
        </table>
    <% } %>
</body>
</html>