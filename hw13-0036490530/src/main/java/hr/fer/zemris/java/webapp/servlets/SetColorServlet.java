package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet stores the selected color in the session's attributes and
 * redirect's the application back to home page.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getSession().setAttribute("pickedBgCol", req.getParameter("pickedBgCol"));
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}
