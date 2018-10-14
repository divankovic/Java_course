package hr.fer.zemris.java.blog.forms;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Represents an abstract form used for validating and creating database object in the application.
 * It also holds a <code>Map</code> of errors that specify in detail errors for each requested field.
 * @author Dorian Ivankovic
 *
 */
public abstract class Form {
	
	/**
	 * Unicode-numeric pattern.
	 */
	public static Pattern UNICODE_NUMERIC = Pattern.compile("[\\p{L}0-9]+", Pattern.CASE_INSENSITIVE);
	
	
	/**
	 * Map used for storing errors specific for a certain field.
	 */
	Map<String, String> errors = new HashMap<>();
	
	/**
	 * Gets the error message for the requested field.
	 * 
	 * @param field - name of the field for which errors are searched
	 * @return error message or null if field doesn't have an error
	 */
	public String getError(String field) {
		return errors.get(field);
	}
	
	/**
	 * Checks if any of the existent fields has an error or not.
	 * 
	 * @return <code>true</code> if some fields have an error, <code>false</code> otherwise.
	 */
	public boolean hasErrors() {
		return !errors.isEmpty();
	}
	
	/**
	 * Checks if the specified field has an error. 
	 * 
	 * @param field - name of the field for which errors are searched
	 * @return <code>true</code> if there are errors for the specified fields, <code>false</code> otherwise.
	 */
	public boolean hasError(String field) {
		return errors.containsKey(field);
	}
	
	/**
	 * Fills the form using the parameters of {@link HttpServletResponse}.
	 * 
	 * @param req - request with parameters
	 */
	public abstract void fillFromRequest(HttpServletRequest req);

	/**
	 * The method validates the form after it is appropriately filled.
	 * All fields are checked and possible errors are stored in the error map.
	 */
	public abstract void validate();
	
	/**
	 * Helper method which converts <code>null</code> Strings into empty Strings, which is
	 * more effective for use in web applications.
	 * @param s string
	 * @return given string if it is !=null, false otherwise.
	 */
	protected String prepareText(String s) {
		if(s==null) return "";
		return s.trim();
	}
}
