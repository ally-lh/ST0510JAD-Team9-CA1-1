package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;
import services.*;
/**
 * Servlet implementation class bookDataServlet
 */
@WebServlet("/book")
public class bookDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public bookDataServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher;
		String action = request.getParameter("action");
		System.out.println(action);
		if(action == null) {
			return;
		}
		switch (action) {
		
		case "byID":
			int id = Integer.parseInt(request.getParameter("bookID"));
			Book bookData = BookServices.fetchBookDataByID(id);

			request.setAttribute("results", bookData);
			dispatcher = request.getRequestDispatcher("bookDetails.jsp");
			dispatcher.forward(request, response);
			break;
		case "byGenre":
			String[] categoryIDs = request.getParameterValues("categoryID");
	        List<Integer> categoryIdList = new ArrayList<>();
	        if (categoryIDs != null) {
	            for (String categoryId : categoryIDs) {
	            	System.out.println(categoryId);
	                int categoryId_int = Integer.parseInt(categoryId);
	                categoryIdList.add(categoryId_int);
	            }
	        }
			List<Book> categoryDataResult = BookServices.getBooksByCategory(categoryIdList);
			List<Category> categoryData = CategoryServices.getAllCategory();
			request.setAttribute("bookResults", categoryDataResult);
			request.setAttribute("categoryResults", categoryData);
			dispatcher = request.getRequestDispatcher("home.jsp");
			dispatcher.forward(request, response);
			break;
		default:
			break;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
