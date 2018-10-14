package hr.fer.zemris.java.blog.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.forms.BlogForm;
import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;

/**
 * This servlet handles all actions regarding {@link BlogEntry}es and {@link BlogComment}'s.
 * "/servleti/author/usernick" displays all blogs of usernick, where usernick is the nickname of {@link BlogUser}.
 * If this is the logged in user, he/she also has the ability to add and modify blogs.
 * Each blog can be read and commented by all registered and unregistered users.
 * Comments left by unregistered users are marked by "Anonimous". 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servleti/author/*")
public class BlogServlet extends HttpServlet {

	/**
	 * serial version UID.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The owner of the displayed and read blogs.
	 */
	private BlogUser user;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = req.getPathInfo();

		try {
			if (path.startsWith("/")) {
				path = path.substring(1);
			}

			String[] pathElements = path.split("/");
			this.user = DAOProvider.getDAO().getUserByNick(pathElements[0]);

			if (user == null) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "No user with username " + pathElements[0] + ".");
				return;
			}
			
			if (pathElements.length == 1) {
				getBlogs(req, resp);

			} else {
				if(pathElements.length!=2) {
					resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
					return;
				}
				
				String action = pathElements[1].toLowerCase();

				if (action.equals("new")) {
					addBlog(req, resp);
				} else if (action.equals("edit")) {
					long blogId = Long.parseLong(req.getParameter("blogId"));
					editBlog(blogId, req, resp);
				} else {
					long blogId = Long.parseLong(action);
					getBlog(blogId, req, resp);
				}

			}
		} catch (Exception ex) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL");
		}
	}
	
	/**
	 * The methods gets all blogs for the user.
	 * @param req - http request
	 * @param resp - https response
	 * @throws IOException
	 * @throws ServletException if the request can't be dispatched
	 */
	private void getBlogs(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		List<BlogEntry> blogs = DAOProvider.getDAO().getUserBlogs(user.getId());

		req.setAttribute("nick", user.getNick());
		req.setAttribute("blogs", blogs);

		Object n = req.getSession().getAttribute("current.user.nick");

		if (n != null && ((String) n).equals(user.getNick())) {
			req.setAttribute("canAdd", true);
		}

		req.getRequestDispatcher("/WEB-INF/pages/blogs.jsp").forward(req, resp);
	}

	/**
	 * The method retrieves a single blog identified by <code>blogId</code>, 
	 * and displays all relevant information about it as well as a form for commenting.
	 * @param blogId - id of the blog to retrieve
	 * @param req - http request
	 * @param resp - http response
	 * @throws ServletException - if the request can't be dispatched
	 * @throws IOException
	 */
	private void getBlog(long blogId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogEntry blog = DAOProvider.getDAO().getBlogEntry(blogId);
		if(blog==null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Blog with for user with id "+blogId+" doesn't exist.");
			return;
		}

		if (blog.getCreator().getNick().equals(req.getSession().getAttribute("current.user.nick"))) {
			req.setAttribute("canModify", true);
		}
		
		req.setAttribute("blog", blog);
		req.setAttribute("nick", user.getNick());
		req.setAttribute("formAction", "?blogId="+blog.getId());
		req.getRequestDispatcher("/WEB-INF/pages/blog_display.jsp").forward(req, resp);
	}

	
	/**
	 * The method is used for editing the blog.
	 * A simple form is displayed whose only contraint is that all fields must be filled.
	 * Only the owner of the blog can edit the blog.
	 * Once the blog has been updated the user is redirected back to blog contents.
	 * @param blogId - id of the blog to edit
	 * @param req - http request
	 * @param resp - http response
	 * @throws IOException
	 * @throws ServletException - if the request cannot be dispatched
	 */
	private void editBlog(long blogId, HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		BlogEntry blog = DAOProvider.getDAO().getBlogEntry(blogId);
		
		if(blog==null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Blog with for user with id "+blogId+" doesn't exist.");
			return;
		}
		
		
		if (!blog.getCreator().getNick().equals(req.getSession().getAttribute("current.user.nick"))) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN,
					"Cant modify this user's blogs because you are not allowed.");
			return;
		}
		
		
		req.setAttribute("blog", blog);
		req.setAttribute("formAction", "?blogId="+blog.getId());
		req.setAttribute("postAction","Update");
		
		req.getRequestDispatcher("/WEB-INF/pages/blog_form.jsp").forward(req, resp);
	}

	/**
	 * The method is used for adding a new blog.
	 * The method displays a simple form whose only contraint is that all fields are full.
	 * Only the owner of the group of blogs can add another blog into the group.
	 * Once the blog is added, the user is redirecte back to list of all his/her blogs.
	 * @param req - http request
	 * @param resp - http response
	 * @throws ServletException - if the request can't be dispatched
	 * @throws IOException
	 */
	private void addBlog(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (user == null || !user.getNick().equals(req.getSession().getAttribute("current.user.nick"))) {
			resp.sendError(HttpServletResponse.SC_FORBIDDEN,
					"Cant modify this user's blogs because you are not allowed.");
			return;
		}
		
		BlogEntry blog = new BlogEntry();
		
		req.setAttribute("blog", blog);
		req.setAttribute("formAction", "?blogId="+blog.getId());
		req.setAttribute("postAction","Add");
		
		req.getRequestDispatcher("/WEB-INF/pages/blog_form.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		
		String action = req.getParameter("action");
		
		if (action.equals("Add")) {
			
			String path = req.getPathInfo();
			path = path.substring(1);
			String nick = path.split("/")[0];
			if(!nick.equals(req.getSession().getAttribute("current.user.nick"))){
				resp.sendError(HttpServletResponse.SC_FORBIDDEN,
						"Cant modify this user's blogs because you are not allowed.");
				return;
			}
			
			BlogForm form = new BlogForm();
			form.fillFromRequest(req);
			form.validate();
			
			if(form.hasErrors()) {
				req.setAttribute("form", form);
				this.doGet(req, resp);
				return;
			}
			
			String title = form.getTitle();
			String text = form.getContent();

			BlogEntry entry = new BlogEntry();

			entry.setCreatedAt(new Date());
			entry.setLastModifiedAt(entry.getCreatedAt());
			entry.setTitle(title);
			entry.setText(text);
			entry.setCreator(user);

			DAOProvider.getDAO().addBlogEntry(entry);
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + user.getNick());
			
		}else if(action.equals("Update")){
			
			Long blogId = Long.parseLong(req.getParameter("blogId"));
			BlogEntry blog = DAOProvider.getDAO().getBlogEntry(blogId);
			
			if (!blog.getCreator().getNick().equals(req.getSession().getAttribute("current.user.nick"))) {
				resp.sendError(HttpServletResponse.SC_FORBIDDEN,
						"Cant modify this user's blogs because you are not allowed.");
				return;
			}
			
			BlogForm form = new BlogForm();
			form.fillFromRequest(req);
			form.validate();
			
			if(form.hasErrors()) {
				req.setAttribute("form", form);
				this.doGet(req, resp);
				return;
			}
			
			String title = form.getTitle();
			String text = form.getContent();

			blog.setTitle(title);
			blog.setText(text);
			
			blog.setLastModifiedAt(new Date());
			this.getBlog(blogId, req, resp);
		}else {
			String comment = req.getParameter("text");
			
			Long blogId = Long.parseLong(req.getParameter("blogId"));
			BlogEntry blog = DAOProvider.getDAO().getBlogEntry(blogId);
			
			Object nickObj = req.getSession().getAttribute("current.user.nick");
			
			String userNick = "Anonymous";
			if(nickObj!=null) {
				userNick = (String)nickObj;
			}
			
			BlogComment blogComment = new BlogComment();
			blogComment.setBlogEntry(blog);
			blogComment.setUserNick(userNick);
			blogComment.setMessage(comment);
			blogComment.setPostedOn(new Date());
			
			DAOProvider.getDAO().addBlogComment(blogComment);
			blog.getComments().add(blogComment);
			
			resp.sendRedirect(req.getContextPath() + "/servleti/author/" + blog.getCreator().getNick()+"/"+blogId);
		}
	}
}
