package servlets;

import java.io.File;
import java.io.IOException;
import java.io.*;
import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.text.SimpleDateFormat;
import models.*;
import services.BookServices;
import services.CategoryServices;
import services.UserServices;
import config.*;
import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

/**
 * Servlet implementation class adminServlet
 */
@WebServlet("/admin")
@MultipartConfig(location = "/tmp", fileSizeThreshold = 1024 * 1024, // 1MB
		maxFileSize = 1024 * 1024 * 10, // 15MB
		maxRequestSize = 1024 * 1024 * 50 // 50MB
)
public class adminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public adminServlet() {
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
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("role"));
		if (session.getAttribute("role") == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		String role = (String) session.getAttribute("role");
		if (role.equalsIgnoreCase("admin")) {
			String bookPageNumberStr = request.getParameter("bookPageNumber");
			String userPageNumberStr = request.getParameter("userPageNumber");
			System.out.print(bookPageNumberStr);
			
			if (bookPageNumberStr == null) {
				bookPageNumberStr = "1";
	            System.out.println("pageNumber is null");
	        }
			if (userPageNumberStr == null) {
				userPageNumberStr = "1";
	            System.out.println("pageNumber is null");
	        }
	        
	        int bookPageNumber = Integer.parseInt(bookPageNumberStr);
	        int userPageNumber = Integer.parseInt(userPageNumberStr);
	        System.out.println(bookPageNumber);
			List<Book> bookDataResults = BookServices.fetchBookData(bookPageNumber, 6);
			List<Category> categoryDataResult = CategoryServices.getAllCategory();
			List<User> userResult = UserServices.getAllUsers(userPageNumber);
			int bookAmount = BookServices.fetchBookNumbers();
			int userCount = UserServices.getTotalUserCount();
			request.setAttribute("bookAmount", bookAmount);
			request.setAttribute("bookResults", bookDataResults);
			request.setAttribute("categoryResults", categoryDataResult);
			request.setAttribute("userData", userResult);
			request.setAttribute("userAmount",userCount);
			request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
		} else {
			response.sendRedirect("login.jsp");
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		System.out.println("Admin Servlet is called");
		System.out.println(session.getAttribute("role"));
		if (session.getAttribute("role") == null) {
			response.sendRedirect("login.jsp");
			return;
		}
		String role = (String) session.getAttribute("role");
		if (role.equalsIgnoreCase("admin")) {
			RequestDispatcher dispatcher;
			String action = null;
			String contentType = request.getContentType();
			if (contentType != null && contentType.startsWith("multipart/form-data")) {
				action = getValue(request.getPart("action"));
			} else {
				action = request.getParameter("action");
			}
			System.out.println(action);
			if (action != null) {
				switch (action) {
				case "addBook": {
					try {
						String title = getValue(request.getPart("title"));
						String author = getValue(request.getPart("author"));
						double price = Double.parseDouble(getValue(request.getPart("price")));
						String publisher = getValue(request.getPart("publisher"));
						String dateString = getValue(request.getPart("pubDate"));
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date utilPubDate = dateFormat.parse(dateString);
						java.sql.Date pubDate = new java.sql.Date(utilPubDate.getTime());
						String ISBN = getValue(request.getPart("ISBN"));
						float rating = Float.parseFloat(getValue(request.getPart("rating")));
						String description = getValue(request.getPart("description"));
						int categoryID = Integer.parseInt(getValue(request.getPart("category")));
						int quantity = Integer.parseInt(getValue(request.getPart("quantity")));
						Part imagePart = request.getPart("image");
						InputStream inputStream = imagePart.getInputStream();
						File tempFile = File.createTempFile("temp", ".jpg");
						try (OutputStream outputStream = new FileOutputStream(tempFile)) {
							byte[] buffer = new byte[4096];
							int bytesRead;
							while ((bytesRead = inputStream.read(buffer)) != -1) {
								outputStream.write(buffer, 0, bytesRead);
							}
						}
						Cloudinary cloudinary = CloudinaryConfig.getCloudinaryInstance();
						@SuppressWarnings("unchecked")
						Map<String, Object> uploadResult = cloudinary.uploader().upload(tempFile,
								ObjectUtils.emptyMap());
						String imageUrl = (String) uploadResult.get("public_id");
						tempFile.delete();
						System.out.println(imageUrl);
						Book newBook = new Book(title, author, price, publisher, pubDate, ISBN, rating, description,
								imageUrl, categoryID, quantity);
						String message = BookServices.addBook(newBook);
						request.setAttribute("message", message);
						doGet(request, response);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("message", e);
						dispatcher = request.getRequestDispatcher("addBook.jsp");
						dispatcher.forward(request, response);
					}
					break;
				}
				case "updateBook": {
					try {
						int bookID = Integer.parseInt(getValue(request.getPart("bookID")));
						String title = getValue(request.getPart("title"));
						String author = getValue(request.getPart("author"));
						double price = Double.parseDouble(getValue(request.getPart("price")));
						String publisher = getValue(request.getPart("publisher"));
						String dateString = getValue(request.getPart("pubDate"));
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						java.util.Date utilPubDate = dateFormat.parse(dateString);
						java.sql.Date pubDate = new java.sql.Date(utilPubDate.getTime());
						String ISBN = getValue(request.getPart("ISBN"));
						float rating = Float.parseFloat(getValue(request.getPart("rating")));
						String description = getValue(request.getPart("description"));
						int categoryID = Integer.parseInt(getValue(request.getPart("category")));
						int quantity = Integer.parseInt(getValue(request.getPart("quantity")));
						String currentImageUrl = getValue(request.getPart("currentImage"));
						String imageUrl = null;
						if (request.getPart("newImage") != null) {
							Part imagePart = request.getPart("newImage");
							InputStream inputStream = imagePart.getInputStream();
							File tempFile = File.createTempFile("temp", ".jpg");
							try (OutputStream outputStream = new FileOutputStream(tempFile)) {
								byte[] buffer = new byte[4096];
								int bytesRead;
								while ((bytesRead = inputStream.read(buffer)) != -1) {
									outputStream.write(buffer, 0, bytesRead);
								}
							}
							Cloudinary cloudinary = CloudinaryConfig.getCloudinaryInstance();
							@SuppressWarnings("unchecked")
							Map<String, Object> uploadResult = cloudinary.uploader().upload(tempFile,
									ObjectUtils.emptyMap());
							imageUrl = (String) uploadResult.get("public_id");
							cloudinary.uploader().destroy(currentImageUrl, ObjectUtils.emptyMap());
							tempFile.delete();
						} else {
							imageUrl = currentImageUrl;
						}

						Book updateBook = new Book(bookID, title, author, price, publisher, pubDate, ISBN, rating,
								description, imageUrl, categoryID, quantity);
						String message = BookServices.updateBook(updateBook);
						request.setAttribute("message", message);
						doGet(request, response);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("message", e.getMessage());
						dispatcher = request.getRequestDispatcher("updateBook");
						dispatcher.forward(request, response);
					}
					break;
				}
				case "deleteBook": {
					try {
						int bookID = Integer.parseInt(request.getParameter("bookID"));
						String imageUrl = request.getParameter("imageUrl");
						String message = BookServices.deleteBook(bookID, imageUrl);
						request.setAttribute("message", message);
						doGet(request, response);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("message", e);
						doGet(request, response);
					}
					break;
				}
				case "addCategory": {
					String message = null;
					if (request.getParameter("categoryName") != null) {
						String categoryName = request.getParameter("categoryName");
						message = CategoryServices.addCategory(categoryName);
					} else {
						message = "Invalid category name";
					}
					request.setAttribute("message", message);
					doGet(request, response);
					break;
				}
				case "updateCategory": {
					String message = null;
					if (request.getParameter("categoryName") != null && request.getParameter("categoryID") != null) {
						try {
							String categoryName = request.getParameter("categoryName");
							int categoryID = Integer.parseInt(request.getParameter("categoryID"));
							message = CategoryServices.updateCategory(categoryName, categoryID);
						} catch (Exception e) {
							message = "Invalid category ID";
						}

					} else {
						message = "Invalid categoryName or categoryID";
					}
					request.setAttribute("message", message);
					doGet(request, response);
					break;
				}
				case "deleteCategory": {
					String message = null;
					if (request.getParameter("categoryID") != null) {
						try {
							int categoryID = Integer.parseInt(request.getParameter("categoryID"));
							message = CategoryServices.deleteCategory(categoryID);
						} catch (Exception e) {
							message = "Invalid category ID";
						}
					} else {
						message = "Invalid category ID";
					}
					request.setAttribute("message", message);
					doGet(request, response);
					break;
				}
				case "addUser": {
					String userName = request.getParameter("username");
					String email = request.getParameter("email");
					String phoneNum = request.getParameter("phoneNum");
					String password = request.getParameter("password");
					String userRole = request.getParameter("isAdmin");
					if (userName == null || userName.isBlank() || userName.isEmpty()) {
						request.setAttribute("message", "Invalid UserName");
						doGet(request, response);
						break;
					}
					if (email == null || email.isBlank() || email.isEmpty()) {
						request.setAttribute("message", "Invalid email");
						doGet(request, response);
						break;
					}
					if (password == null || password.isBlank() || password.isEmpty()) {
						request.setAttribute("message", "Invalid password");
						doGet(request, response);
						break;
					}
					if (userRole == null || userRole.isBlank() || userRole.isEmpty()) {
						request.setAttribute("message", "Invalid userRole");
						doGet(request, response);
						break;
					}
					try {
						String message = UserServices.addUser(userName, email, phoneNum, userRole, password);
						request.setAttribute("message", message);
						doGet(request, response);
						break;
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("message", e.getMessage());
						doGet(request, response);
					}
					break;
				}
				case "updateUser": {
					String userIDStr = request.getParameter("userID");
					String userName = request.getParameter("username");
					String email = request.getParameter("email");
					String phoneNum = request.getParameter("phoneNum");
					String password = request.getParameter("password");
					String userRole = request.getParameter("isAdmin");
					int userID=0;
					try {
						userID = Integer.parseInt(userIDStr);
						System.out.println(userIDStr);
						System.out.println(userID);
						System.out.println(phoneNum);
					}catch(Exception e) {
						e.printStackTrace();
						request.setAttribute("message", "Invalid userID");
					}
					if (userName == null || userName.isBlank() || userName.isEmpty()) {
						request.setAttribute("message", "Invalid UserName");
						doGet(request, response);
					}
					if (email == null || email.isBlank() || email.isEmpty()) {
						request.setAttribute("message", "Invalid email");
						doGet(request, response);
					}
					if (password == null || password.isBlank() || password.isEmpty()) {
						request.setAttribute("message", "Invalid UserName");
						doGet(request, response);
					}
					if (userRole == null || userRole.isBlank() || userRole.isEmpty()) {
						request.setAttribute("message", "Invalid UserName");
						doGet(request, response);
					}
					try {
						String message = UserServices.updateUser(userID,userName, email, phoneNum, userRole, password);
						request.setAttribute("message", message);
						doGet(request, response);
					} catch (Exception e) {
						e.printStackTrace();
						request.setAttribute("message", e.getMessage());
						doGet(request, response);
					}
					break;
				}
				case "deleteUser":
					String message = null;
					if (request.getParameter("userID") != null) {
						try {
							int userID = Integer.parseInt(request.getParameter("userID"));
							message = UserServices.deleteUser(userID);
						} catch (Exception e) {
							message = e.getMessage();
						}
					} else {
						message = "Invalid user ID";
					}
					request.setAttribute("message", message);
					doGet(request, response);
					break;
				default:
					String errorMessage = "Invalid action specified.";
					request.setAttribute("message", errorMessage);
					doGet(request, response);
					break;
				}
			} else {
				String errorMessage = "Invalid action specified.";
				request.setAttribute("message", errorMessage);
				doGet(request, response);
			}
		} else

		{
			response.sendRedirect("login.jsp");
		}
	}

	private String getValue(Part part) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), "UTF-8"));
		StringBuilder value = new StringBuilder();
		char[] buffer = new char[1024];
		for (int length = 0; (length = reader.read(buffer)) > 0;) {
			value.append(buffer, 0, length);
		}
		return value.toString();
	}
}
