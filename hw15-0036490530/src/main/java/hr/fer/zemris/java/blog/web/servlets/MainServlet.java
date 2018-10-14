package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.forms.LoginForm;
import hr.fer.zemris.java.blog.model.BlogUser;

/**
 * The main servlet displays a login form where existing users can login using valid username
 * and password. The data is checked by {@link LoginForm}, and if everything is valid, relevant data
 * about the logged in user in saved in session and the user is redirected to a page that
 * displays his/her blogs.
 * The servlet also displays a logout button if the user is logged in, and a list of all
 * registered users with links to their blogs.
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet{

	/**
	 * serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
				
		req.setAttribute("users", users);		
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		LoginForm form = new LoginForm();
		form.fillFromRequest(req);
		form.validate();
		
		if(form.hasErrors()) {
			if(form.hasError("password")){
					form.setPassword("");
			}

			req.setAttribute("form", form);
			this.doGet(req, resp);
			return;
		}
		
		BlogUser user = DAOProvider.getDAO().getUserByNick(form.getUsername());
		loginUser(user, req.getSession());
		resp.sendRedirect(req.getContextPath() + "/servleti/author/"+user.getNick());
		
	}
	
	protected static void loginUser(BlogUser user, HttpSession session) throws IOException {
		session.setAttribute("current.user.id", user.getId());
		session.setAttribute("current.user.fn", user.getFirstName());
		session.setAttribute("current.user.ln", user.getLastName());
		session.setAttribute("current.user.nick", user.getNick());
	}
}
