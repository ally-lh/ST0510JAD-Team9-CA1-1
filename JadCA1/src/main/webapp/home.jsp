<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Search Form</title>
</head>
<body>
    <form action="bookSearchServlet" method="GET">
        <input type="text" name="searchTerm" placeholder="Enter your search term">
        <input type="submit" value="Search">
    </form>
</body>
</html>