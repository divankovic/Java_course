package hr.fer.zemris.java.blog.forms;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.blog.model.BlogEntry;

/**
 * Form used for validation of adding or editing a {@link BlogEntry}.
 * @author Dorian Ivankovic
 *
 */
public class BlogForm extends Form {

	/**
	 * Inputted title.
	 */
	private String title;
	
	/**
	 * Inputted blog content.
	 */
	private String content;
	
	@Override
	public void fillFromRequest(HttpServletRequest req) {
		this.title = prepareText(req.getParameter("title"));
		this.content = prepareText(req.getParameter("content"));
	}

	@Override
	public void validate() {
		errors.clear();
		
		if(title.isEmpty()) {
			errors.put("title", "Title must not be empty.");
		}
		
		if(content.isEmpty()) {
			errors.put("content", "Content must not be empty.");
		}
	}

	/**
	 * Returns the input title.
	 * @return input title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the input title.
	 * @param title - input title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the input content.
	 * @return the input content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Sets the input content.
	 * @param content - input content
	 */
	public void setContent(String content) {
		this.content = content;
	}
}
