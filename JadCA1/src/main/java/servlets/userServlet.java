package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
		RequestDispatcher dispatcher;
		HttpSession session = request.getSession();

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
		int custID = UserServices.login(user, password);
		if (custID > 0) {
			session.setAttribute("userID", custID);
			String contextPath = request.getContextPath();
			response.sendRedirect(contextPath + "/");
		} else {
			request.setAttribute("error", "user credentials are incorrect.");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher dispatcher;
		try {
			String action = request.getParameter("action");
			System.out.println(action);
			if (action != null) {
				switch (action) {
				case "login":
					doGet(request, response);
					break;
				case "register":
					String userName = request.getParameter("username");
					String email = request.getParameter("email");
					String phoneNumStr = request.getParameter("phoneNum");
					String password = request.getParameter("password");
					int phoneNum;
					if (userName == null || userName.isEmpty() || userName.isBlank()) {
						request.setAttribute("error", "Username is required.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
						return;
					}
					if (email == null || email.isBlank() || email.isEmpty()) {
						request.setAttribute("error", "email is required.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
						return;
					}
					try {
						phoneNum = Integer.parseInt(phoneNumStr);
					} catch (NumberFormatException e) {
						request.setAttribute("error", "Phone number must be a valid number.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
						return;
					}
					if (password == null || password.isBlank() || password.isEmpty()) {
						request.setAttribute("error", "email is required.");
						dispatcher = request.getRequestDispatcher("register.jsp");
						dispatcher.forward(request, response);
						return;
					}
					String message = UserServices.registerUser(userName, email, phoneNum, password);
					request.setAttribute("message", message);
					dispatcher = request.getRequestDispatcher("login.jsp");
					dispatcher.forward(request, response);
					break;
				case "logout":
					System.out.println("Log out function is called");
					HttpSession session = request.getSession();
					session.invalidate();
					response.sendRedirect(request.getContextPath()+"/index.jsp");
					break;
				}
			}
			
		}catch (Exception e) {
			request.setAttribute("error", "Internal server error.");
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
		// TODO Auto-generated method stub
		

	}

	
}
