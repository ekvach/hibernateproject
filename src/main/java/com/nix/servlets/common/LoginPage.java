package com.nix.servlets.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nix.entity.User;
import com.nix.hibernatedao.HibernateUserDao;
import com.nix.utils.IsUserExistValidator;

@WebServlet("/loginpage")
public class LoginPage extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		RequestDispatcher rd;

		String login = request.getParameter("uname");
		String pass = request.getParameter("pass");

		HibernateUserDao userDao = new HibernateUserDao();
		User user = userDao.findUserByLoginAndPass(login, pass);


		if (IsUserExistValidator.validate(login, pass)) {
			Long roleId = user.getUserRole().getId();
			session.setAttribute("roleId", roleId);
			session.setAttribute("user", user);

			switch (String.valueOf(roleId)) {
			case "1":
				response.sendRedirect("homepageadmin");
				break;
			case "2":
			case "3":
				rd = session.getServletContext().getRequestDispatcher("/user/homepageuser.jsp");
				rd.forward(request, response);
				break;
			default:
				throw new IllegalArgumentException("Invalid User Role");
			}
		} else {
			rd = request.getServletContext().getRequestDispatcher("/index.html");
			rd.include(request, response);
			out.print("<h3 style=\"color: red\" align=\"center\">Password or username is incorrect</h3>");
		}
	}
}
