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
import services.BookServices;
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
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();

		if (session.getAttribute("userID") != null) {
			List<Book> cart = new ArrayList<Book>();
			if (session.getAttribute("Cart") != null) {
				cart = (List<Book>) session.getAttribute("Cart");
			} else {
				cart = CartServices.getCartItems((Integer) session.getAttribute("userID"));
				session.setAttribute("Cart", cart);
			}
			request.setAttribute("CartItems", cart);
			request.getRequestDispatcher("cart.jsp").forward(request, response);
		} else {
			response.sendRedirect("login.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@SuppressWarnings({ "unchecked" })
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		HttpSession session = request.getSession();
		if (session.getAttribute("userID") != null) {
			Integer userID = (Integer) session.getAttribute("userID");
			String action = request.getParameter("action");
			if (action != null) {
				switch (action) {
				case "addToCart": {
					if (session.getAttribute("role").equals("admin")) {
						response.sendRedirect("index.jsp");
						return;
					}
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
					if (bookIDStr == null || bookIDStr.isBlank() || bookIDStr.isEmpty() || title == null
							|| title.isBlank() || title.isEmpty() || imageUrl == null || imageUrl.isBlank()
							|| imageUrl.isEmpty() || priceStr == null || priceStr.isBlank() || priceStr.isEmpty()
							|| qtyStr == null || qtyStr.isBlank() || qtyStr.isEmpty()) {
						System.out.println("Invalid book details.");
						request.setAttribute("message", "Invalid book details");
						request.getRequestDispatcher("index.jsp").forward(request, response);
						return;
					}

					int bookID = 0, quantity = 0;
					double price = 0.0;

					try {
						bookID = Integer.parseInt(bookIDStr);
					} catch (NumberFormatException n) {
						System.out.println(n);
						request.setAttribute("message", "invalid BookID");
						request.getRequestDispatcher("index.jsp")
								.forward(request, response);
						return;
					}
					try {
						price = Double.parseDouble(priceStr);
					} catch (NumberFormatException n) {
						System.out.println(n);
						request.setAttribute("message", "invalid price");
						request.getRequestDispatcher("index.jsp")
								.forward(request, response);
						return;
					}

					try {
						quantity = Integer.parseInt(qtyStr);
					} catch (NumberFormatException n) {
						System.out.println(n);
						request.setAttribute("message", "invalid quantity");
						request.getRequestDispatcher("index.jsp")
								.forward(request, response);
						return;
					}
					List<Book> cartList;
					if (session.getAttribute("Cart") == null) {
						cartList = new ArrayList<Book>();
					} else {
						cartList = (List<Book>) session.getAttribute("Cart");
					}
					try {
						String message = CartServices.addToCart(new Book(bookID, title, price, imageUrl, quantity),
								userID, cartList, session);
						System.out.println(message);
						String url = request.getContextPath() + "/bookDetails.jsp?bookID=" + bookID;
						response.sendRedirect(url);
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
				case "deleteFromCart": {
					if (session.getAttribute("Cart") != null) {
						List<Book> cartList = (List<Book>) session.getAttribute("Cart");
						String bookIDStr = request.getParameter("bookID");
						if (bookIDStr == null) {
							request.setAttribute("message", "invalid bookID");
							doGet(request, response);
						}
						try {
							int bookID = Integer.parseInt(bookIDStr);
							String message = CartServices.deleteCartItem(userID, bookID, cartList, session);
							request.setAttribute("message", message);
							doGet(request, response);
						} catch (Exception e) {
							e.printStackTrace();
							request.setAttribute("message", "Invalid bookID");
							doGet(request, response);
						}
					} else {
						response.sendRedirect("login.jsp");
						return;
					}
					break;
				}
				case "checkOut": {
					if (session.getAttribute("Cart") != null) {
						List<Book> cartList = (List<Book>) session.getAttribute("Cart");
						if (cartList.size() > 0) {
							try {
								Boolean isInStock = BookServices.checkInventory(userID,cartList,session);
								if (isInStock) {
									String message = CartServices.addOrder(userID, cartList, session);
									System.out.print(message);
									request.setAttribute("message", message);
									request.getRequestDispatcher("index.jsp").forward(request, response);
									break;
								} else {
									request.setAttribute("message", "some books in your cart is no stock. Please");
									doGet(request,response);
								}
							} catch (Exception e) {
								System.out.print(e);
								request.setAttribute("message", e);
								doGet(request, response);
							}
						} else {
							request.setAttribute("message", "Please add books to checkout");
							doGet(request, response);
						}
					} else

						response.sendRedirect("login.jsp");
					break;
				}
				case "clearOrder": {

					try {
						String message = CartServices.clearOrders(userID);
						System.out.print(message);
						request.setAttribute("message", message);
						request.getRequestDispatcher("user").forward(request, response);

					} catch (Exception e) {
						System.out.print(e);
						request.setAttribute("message", e.getMessage());
						RequestDispatcher dispatcher = request.getRequestDispatcher("user");
						dispatcher.forward(request, response);
					}

					break;

				}
				}
			} else {
				response.sendRedirect("index.jsp");
				return;
			}
		} else {
			response.sendRedirect("login.jsp");
			return;
		}

	}

}
