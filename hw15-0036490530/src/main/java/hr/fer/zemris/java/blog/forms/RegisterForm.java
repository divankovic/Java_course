package hr.fer.zemris.java.blog.forms;


import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.blog.dao.DAOProvider;
import hr.fer.zemris.java.blog.model.BlogUser;
import hr.fer.zemris.java.blog.web.servlets.RegisterServlet;

/**
 * Form used in {@link BlogUser}'s registration performed in {@link RegisterServlet}.
 * @author Dorian Ivankovic
 *
 */
public class RegisterForm extends Form{

	/**
	 * Email pattern
	 */
	public static Pattern VALID_EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Input first name in the form.
	 */
	private String firstName;
	
	/**
	 * Input last name in the form.
	 */
	private String lastName;
	
	/**
	 * Input email in the form.
	 */
	private String email;
	
	/**
	 * Input  nick in the form.
	 */
	private String nick;
	
	/**
	 * Input password in the form.
	 */
	private String password;
	
	
	/**
	 * Default constructor.
	 */
	public RegisterForm() {
	
	}

	@Override
	public void fillFromRequest(HttpServletRequest req) {
		this.firstName = prepareText(req.getParameter("firstName"));
		this.lastName = prepareText(req.getParameter("lastName"));
		this.email = prepareText(req.getParameter("email"));
		this.nick = prepareText(req.getParameter("nick"));
		this.password = prepareText(req.getParameter("password"));
	}

	
	@Override
	public void validate() {
		errors.clear();
		
		if(this.firstName.isEmpty()) {
			errors.put("firstName", "First name must not be empty!");
		}else if(!this.firstName.matches("\\p{L}+")) {
			errors.put("firstName", "Only letters allowed in first name.");
		}else if(this.firstName.length()>BlogUser.FIRST_NAME_MAX_LENGHT) {
			errors.put("firstName", "First name maximal length is "+BlogUser.FIRST_NAME_MAX_LENGHT+".");
		}
		
		if(this.lastName.isEmpty()) {
			errors.put("lastName", "Last name must not be empty!");
		}else if(!this.lastName.matches("\\p{L}+")) {
			errors.put("lastName", "Only letters allowed in last name.");
		}else if(this.lastName.length()>BlogUser.LAST_NAME_MAX_LENGHT) {
			errors.put("lastName", "Last name maximal length is "+BlogUser.LAST_NAME_MAX_LENGHT+".");
		}

		validateEmail(email);
		validateNickname(nick);
		validatePassword(password);
	}
	
	
	/**
	 * Validates the email.
	 * @param email - email to validate
	 */
	private void validateEmail(String email) {
		if(this.email.isEmpty()) {
			errors.put("email", "Email must not be empty!");
		} else if(!VALID_EMAIL_PATTERN.matcher(email).matches()){
			errors.put("email", "Illegal email format");
		} else if(this.email.length()>BlogUser.EMAIL_MAX_LENGTH) {
			errors.put("email", "Email maximal length is "+BlogUser.EMAIL_MAX_LENGTH+".");
		}else if(DAOProvider.getDAO().getUserByEmail(email)!=null){
			errors.put("email", "User with specified email already exists.");
		}
	}
	
	/**
	 * Validates the password - password can consiste only of digits and unicode letters.
	 * @param password - password to validate
	 */
	private void validatePassword(String password) {
		if(password.isEmpty()) {
			errors.put("password", "Password must not be empty!");
		} else if(password.length()< BlogUser.MIN_PASSWORD_LENGTH) {
			errors.put("password", "Password is too short, it must contain at least "+BlogUser.MIN_PASSWORD_LENGTH+" characters.");
		}
	}

	/**
	 * Validates the nickName - nickName can consiste only of digits and unicode letters.
	 * @param password - password to validate
	 */
	private void validateNickname(String nick) {
		if(nick.isEmpty()) {
			errors.put("nick", "Username must not be empty!");
		} else if(!UNICODE_NUMERIC.matcher(nick).matches()){
			errors.put("nick", "Username can contain only letters and digits");
		} else if(nick.length()> BlogUser.NICK_MAX_LENGHT) {
			errors.put("nick", "Username max length is "+BlogUser.NICK_MAX_LENGHT+" characters.");
		}else if(DAOProvider.getDAO().getUserByNick(nick)!=null){
			errors.put("nick", "User with specified username already exists.");
		}
	}
	

	/**
	 * Returns the first name from the form.
	 * @return first name from the form.
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name in the form.
	 * @param firstName - new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	
	/**
	 * Returns the last name from the form.
	 * @return last name from the form.
	 */
	public String getLastName() {
		return lastName;
	}


	/**
	 * Sets the last name in the form.
	 * @param lastName - new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the email from the form.
	 * @return last name from the form.
	 */
	public String getEmail() {
		return email;
	}

	
	/**
	 * Sets the email in the form.
	 * @param email - new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the nick from the form.
	 * @return nick from the form.
	 */
	public String getNick() {
		return nick;
	}

	
	/**
	 * Sets the nick in the form.
	 * @param nick - new nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Returns the password from the form.
	 * @return password from the form.
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
