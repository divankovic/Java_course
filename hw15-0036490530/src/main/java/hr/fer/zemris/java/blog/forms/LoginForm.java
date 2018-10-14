package hr.fer.zemris.java.blog.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.blog.util.HashUtil;
import hr.fer.zemris.java.blog.web.servlets.MainServlet;

/**
 * Form used for validation and data transfer in {@link MainServlet} where user login action
 * is performed.
 * @author Dorian Ivankovic
 *
 */
public class LoginForm extends Form{
	
	/**
	 * Inputted username.
	 */
	private String username;
	
	/**
	 * Inputted password.
	 */
	private String password;
	
	@Override
	public void fillFromRequest(HttpServletRequest req) {
		this.username = prepareText(req.getParameter("username"));
		this.password = prepareText(req.getParameter("password"));
	}

	@Override
	public void validate() {
		errors.clear();
		
		BlogUser user = DAOProvider.getDAO().getUserByNick(username); 
		if(user==null){
			errors.put("nick", "User with specified username doesn't exist");
			return;
		}
		
		if(!HashUtil.hashPassword(password).equals(user.getPasswordHash())) {
			errors.put("password", "Password is incorrect");
		}
	}

	/**
	 * Gets the input username;
	 * @return input username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username in the form
	 * @param username - new username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the password from the form.
	 * @return password from the form
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password in the form.
	 * @param password - new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
