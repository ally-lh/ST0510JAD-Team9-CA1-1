package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;
import models.Book;
import models.Order;
import services.CartServices;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/Cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartServlet() {
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
		HttpSession session = request.getSession();
		List<Order> cart = (List<Order>) session.getAttribute("Cart");
		Integer userID = (Integer) session.getAttribute("userID");
		List<Book> cartItems = CartServices.getCartItems(userID, cart);
		request.setAttribute("CartItems",cartItems);
		request.getRequestDispatcher("cart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		HttpSession session = request.getSession();
		Integer userID = (Integer) session.getAttribute("userID");
		String bookIDStr = request.getParameter("bookID");
		String title = request.getParameter("title");
		String imageUrl = request.getParameter("imageUrl");
		String priceStr = request.getParameter("price");
		String qtyStr = request.getParameter("quantity");
		if (userID == null) {
			request.setAttribute("message", "User not logged in");
			request.getRequestDispatcher("login.jsp").forward(request, response);
			return;
		}
		if (bookIDStr == null || bookIDStr.isBlank() || bookIDStr.isEmpty() || title == null || title.isBlank()
				|| title.isEmpty() || imageUrl == null || imageUrl.isBlank() || imageUrl.isEmpty() || priceStr == null
				|| priceStr.isBlank() || priceStr.isEmpty() || qtyStr == null || qtyStr.isBlank() || qtyStr.isEmpty()) {
			request.setAttribute("message", "Invalid book details");
			request.getRequestDispatcher("/").forward(request, response);
			return;
		}

		int bookID = 0, quantity = 0;double price = 0.0;

		try {
			bookID = Integer.parseInt(bookIDStr);
		} catch (NumberFormatException n) {
			System.out.println(n);
			request.setAttribute("message", "invalid BookID");
			request.getRequestDispatcher(request.getContextPath() + "/home?action=byID&bookID=" + bookID)
					.forward(request, response);
			return;
		}
		try {
			price = Double.parseDouble(priceStr);
		} catch (NumberFormatException n) {
			System.out.println(n);
			request.setAttribute("message", "invalid price");
			request.getRequestDispatcher(request.getContextPath() + "/home?action=byID&bookID=" + bookID)
					.forward(request, response);
			return;
		}

		try {
			quantity = Integer.parseInt(qtyStr);
		} catch (NumberFormatException n) {
			System.out.println(n);
			request.setAttribute("message", "invalid quantity");
			request.getRequestDispatcher(request.getContextPath() + "/home?action=byID&bookID=" + bookID)
					.forward(request, response);
			return;
		}
		List<Order> cartList;
		if (session.getAttribute("Cart") == null) {
			cartList = new ArrayList<Order>();
		} else {
			cartList = (List<Order>) session.getAttribute("Cart");
		}
		try {
			String message = CartServices.addToCart(new Book(bookID, title, price, imageUrl, quantity), userID,
					cartList,session);
			String url =request.getContextPath()+ "/home?action=byID&bookID=" + bookID;
			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
