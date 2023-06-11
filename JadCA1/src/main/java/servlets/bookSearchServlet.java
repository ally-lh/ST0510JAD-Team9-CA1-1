package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import books.Book;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class publicBookServlet
 */
@WebServlet("/search")
public class bookSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bookSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		String searchTerm = request.getParameter("searchTerm");
		List<Book> searchResults = performSearch(searchTerm); // Implement your search logic here

        request.setAttribute("results", searchResults);
        RequestDispatcher dispatcher = request.getRequestDispatcher("searchResults.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private List<Book> performSearch(String searchTerm){
		searchTerm = "%"+searchTerm+"%";
		List<Book> searchResults = new ArrayList<>();
		String jdbcUrl = "jdbc:mysql://jadbookstoredb.cpjd7st4o4nt.us-east-2.rds.amazonaws.com:3306/jadBookStore";
		String username = "admin";
		String password = "1392001ksp";
		try {
			// Step1: Load JDBC Driver
	        Class.forName("com.mysql.jdbc.Driver");  

	        // Step 2: Define Connection URL
	        //String connURL = "jdbc:mysql://jadbookstoredb.cpjd7st4o4nt.us-east-2.rds.amazonaws.com:3306/?user=admin&password=1392001ksp&serverTimezone=UTC";

	        // Step 3: Establish connection to URL
	        Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
			
		    // Step 5: Execute SQL Command
		    String sqlStr = "select * from Book inner join Category on Category.CategoryID= Book.CategoryID where Book.Title like ? OR Book.Author like ?";
		    PreparedStatement pstmt = conn.prepareStatement(sqlStr);
		    pstmt.setString(1,searchTerm);
		    pstmt.setString(2, searchTerm);
		    
		    ResultSet rs = pstmt.executeQuery();
		    while (rs.next()) {
	            // Retrieve all the necessary fields from the result set
	            int bookId = rs.getInt("BookID");
	            String title = rs.getString("Title");
	            double price = rs.getDouble("Price");
	            String author = rs.getString("Author");
	            String publisher = rs.getString("Publisher");
	            Date pubDate = rs.getDate("PubDate");
	            String isbn = rs.getString("ISBN");
	            float rating = rs.getFloat("Rating");
	            String description = rs.getString("Description");
	            String imageUrl = rs.getString("Image");
	            String categoryName= rs.getString("CategoryName");
	            // Create a Book object and set the retrieved values
	            Book book = new Book(bookId,title,author,price,publisher,pubDate,isbn,rating,description,imageUrl,categoryName);
	            System.out.println(title);
	            // Add the book to the search results list
	            searchResults.add(book);
	        }
		 // Step 6: Close the resources
	        rs.close();
	        pstmt.close();
	        conn.close();

		} catch(Exception e){
			e.printStackTrace();
		}
		return searchResults;
		
	}
}
