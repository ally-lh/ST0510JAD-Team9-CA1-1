package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.*;
import services.BookServices;
import services.CategoryServices;
/**
 * Servlet implementation class indexLoadServlet
 */
@WebServlet("/")
public class indexLoadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public indexLoadServlet() {
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
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher;
		String pageNumberStr = request.getParameter("pageNumber");
		String recordsPerPageStr = request.getParameter("recordPerPage");
		int pageNumber,recordsPerPage;
		if(pageNumberStr == null || recordsPerPageStr == null ) {
			pageNumber = 1;
			recordsPerPage = 6;
		}
		else {
			pageNumber = Integer.parseInt(pageNumberStr);
			recordsPerPage = Integer.parseInt(recordsPerPageStr);
		}
		List<Book> bookDataResults = BookServices.fetchBookData(pageNumber,recordsPerPage);
		int bookAmount = BookServices.fetchBookNumbers();
		List<Category> categoryDataResult = CategoryServices.getAllCategory();
		request.setAttribute("bookResults", bookDataResults);
		request.setAttribute("categoryResults", categoryDataResult);
		request.setAttribute("bookAmount", bookAmount);
		dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);
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
