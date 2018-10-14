package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.forms.RegisterForm;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.blog.util.HashUtil;


/**
 * <p>The servlet is used for registration of new {@link BlogUser}'s.
 * A user must input all relevant information - first name, last name, email, username,
 * password, the data is cheched by {@link RegisterForm}.</p>
 * <p>
 * If all data is in correct format and the user doesn't already exist, a new user is created
 * and inserted into the database. The registration is then considered successful and the user
 * is redirected to logged in an eventually redirected to his/her blogs page.</p>
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("current.user.id")!=null) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Can't register if you are logged in");
			return;
		}
		req.getRequestDispatcher("/WEB-INF/pages/register_form.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		RegisterForm form = new RegisterForm();
		form.fillFromRequest(req);
		
		form.validate();
		
		if(form.hasErrors()) {
			if(form.hasError("password")) {
				form.setPassword("");
			}
			req.setAttribute("form", form);
			req.getRequestDispatcher("/WEB-INF/pages/register_form.jsp").forward(req, resp);
			return;
		}
		
		BlogUser user = new BlogUser();
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		user.setNick(form.getNick());
		user.setPasswordHash(HashUtil.hashPassword(form.getPassword()));
		
		DAOProvider.getDAO().addBlogUser(user);
		
		MainServlet.loginUser(user, req.getSession());
		req.setAttribute("userHome", "author/"+user.getNick());
		req.getRequestDispatcher("/WEB-INF/pages/register_success.jsp").forward(req, resp);
	}
}
