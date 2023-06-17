package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Book;
import models.Category;
import services.BookServices;
import services.CategoryServices;

/**
 * Servlet implementation class updateBookServlet
 */
@WebServlet("/updateBook")
public class updateBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
//		if(request.getParameter("bookID")!=null) {
//			System.out.println("updateBookServlet is called");
//			int bookID = Integer.parseInt(request.getParameter("bookID"));
//			Book book = BookServices.fetchBookDataByID(bookID);
//			List<Category> categoryData = CategoryServices.getAllCategory();
//			request.setAttribute("categoryResults", categoryData);
//			request.setAttribute("bookResult", book);
//			request.getRequestDispatcher("updateBook.jsp").forward(request, response);
//			return;
//		}else {
//			request.getRequestDispatcher("admin").forward(request, response);
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		if(request.getParameter("bookID")!=null) {
			System.out.println("updateBookServlet is called");
			int bookID = Integer.parseInt(request.getParameter("bookID"));
			Book book = BookServices.fetchBookDataByID(bookID);
			List<Category> categoryData = CategoryServices.getAllCategory();
			request.setAttribute("categoryResults", categoryData);
			request.setAttribute("bookResult", book);
			request.getRequestDispatcher("updateBook.jsp").forward(request, response);
			return;
		}else {
			request.getRequestDispatcher("admin").forward(request, response);
		}
	}

}
