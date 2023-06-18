package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.Book;
import models.Category;
import services.BookServices;
import services.CategoryServices;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.tuple.Pair;
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
		String searchCat= request.getParameter("categoryID");
		String pageNumberStr = request.getParameter("bookPageNumber");
		System.out.println(searchTerm);
		System.out.println(searchCat);
		if(pageNumberStr == null) {
			pageNumberStr = "1";
		}
		int pageNumber = Integer.parseInt(pageNumberStr);
		Pair<List<Book>, Integer> searchResultsPair = Pair.of(null,0);
		if(searchTerm!=null || searchCat!=null) {
			searchResultsPair = BookServices.performSearch(searchTerm,searchCat,pageNumber); // Implement your search logic here
			
		}
		List<Category> categoryData = CategoryServices.getAllCategory();
		request.setAttribute("searchTermAttr", searchTerm);
		request.setAttribute("categoryIDAttr", searchCat);
        request.setAttribute("categoryResults", categoryData);
        request.setAttribute("searchResultsPair", searchResultsPair);
        RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
