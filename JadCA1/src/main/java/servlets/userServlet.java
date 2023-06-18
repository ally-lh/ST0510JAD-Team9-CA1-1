package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.*;
import services.CartServices;
import services.UserServices;

/**
 * Servlet implementation class userServlet
 */
@WebServlet("/user")
public class userServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public userServlet() {
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
		if (session.getAttribute("userID") != null) {
	
			int custID = (Integer) session.getAttribute("userID");
			System.out.print(custID);
			User customerDetails = UserServices.getCustomerDetails(custID);
			List<Order> orderDetails = CartServices.getOrders(custID);
			System.out.print("hell");
			System.out.println(customerDetails);
			request.setAttribute("customerDetails", customerDetails);
			request.setAttribute("orderDetails", orderDetails);
			request.getRequestDispatcher("profile.jsp").forward(request, response);
		} else {
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();
		try {
			String action = request.getParameter("action");
			System.out.println(action);
			if (action != null) {
				switch (action) {
				case "login": {
					System.out.println("Login is called");
					session.invalidate();
					String user = request.getParameter("UserIdentification");
					String password = request.getParameter("password");
					if (user == null || user.isEmpty() || user.isBlank()) {
						request.setAttribute("error", "Username is required.");
						dispatcher = request.getRequestDispatcher("login.jsp");
						dispatcher.forward(request, response);
						return;
					}
					if (password == null || password.isBlank() || password.isEmpty()) {
						request.setAttribute("error", "email is required.");
						dispatcher = request.getRequestDispatcher("login.jsp");
						dispatcher.forward(request, response);
						return;
					}
					User loginUser = UserServices.login(user, password);
					if (loginUser != null) {
						session = request.getSession();
						session.setAttribute("userID", loginUser.getUserID());
						session.setAttribute("role", loginUser.getRole());
						String contextPath = request.getContextPath();
						response.sendRedirect(contextPath + "/index.jsp");
					} else {
						request.setAttribute("error", "user credentials are incorrect.");
						dispatcher = request.getRequestDispatcher("login.jsp");
						dispatcher.forward(request, response);
					}
					break;
				}
				case "register": {
					String confirmPW = request.getParameter("confirm-password");
					String userName = request.getParameter("username");
					String email = request.getParameter("email");
					String phoneNum = request.getParameter("phoneNum");
					String password = request.getParameter("password");
					if (userName == null || userName.isEmpty() || userName.isBlank()) {
						request.setAttribute("message", "Username is required.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
						return;
					}
					if (email == null || email.isBlank() || email.isEmpty()) {
						request.setAttribute("message", "email is required.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
						return;
					}
					if(phoneNum == null || phoneNum.isBlank() || phoneNum.isEmpty()) {
						request.setAttribute("message", "Phone number must be a valid number.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
						return;
					}
					if (password == null || password.isBlank() || password.isEmpty()) {
						request.setAttribute("message", "email is required.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
						return;
					}
					if (!password.equals(confirmPW)) { 
						request.setAttribute("message", "Passwords do not match.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
					}
					try {
						String message = UserServices.addCustomer(userName, email, phoneNum, password);
						request.setAttribute("message", message);
						dispatcher = request.getRequestDispatcher("login.jsp");
						dispatcher.forward(request, response);
					} catch (Exception e) {
						request.setAttribute("message", e.getMessage());
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
					}
					break;
				}
				case "update": {
					String userName = request.getParameter("username");
					String email = request.getParameter("email");
					String phoneNum = request.getParameter("phoneNum");
					String password = request.getParameter("password");
					if (session.getAttribute("userID") != null) {
						int custID = (Integer) session.getAttribute("userID");
						if (userName == null || userName.isEmpty() || userName.isBlank()) {
							request.setAttribute("error", "Username is required.");
							doGet(request,response);
						}
						if (email == null || email.isBlank() || email.isEmpty()) {
							request.setAttribute("error", "email is required.");
							doGet(request,response);						}
						if(phoneNum == null || phoneNum.isBlank() || phoneNum.isEmpty()) {
							request.setAttribute("error", "Phone number must be a valid number.");
							doGet(request,response);
						}
						if (password == null || password.isBlank() || password.isEmpty()) {
							request.setAttribute("error", "email is required.");
							doGet(request,response);
						}
						try {
							String message = UserServices.updateCustomer(custID, userName, email, phoneNum, password);
							request.setAttribute("message", message);
							System.out.println(message);
							doGet(request,response);
						} catch (Exception e) {
							request.setAttribute("message", e.getMessage());
							doGet(request,response);
						}
					} else {
						request.getRequestDispatcher("login.jsp").forward(request, response);
					}

					break;
				}
				case "logout":{
					System.out.println("Log out function is called");
					session.invalidate();
					response.sendRedirect("index.jsp");
					break;
				}
				case "clearOrder":{
					doGet(request,response);
					break;
				}
			}
			}else {
                request.setAttribute("message", "Invalid action");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

		} catch (Exception e) {
			request.setAttribute("error", "Internal server error.");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		// TODO Auto-generated method stub

	}

}
