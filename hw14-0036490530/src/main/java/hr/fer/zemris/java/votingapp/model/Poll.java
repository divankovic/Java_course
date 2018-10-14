package hr.fer.zemris.java.votingapp.model;

/**
 * Represents a single voting poll in the application.
 * @author Dorian Ivankovic
 *
 */
public class Poll {
	
	/**
	 * Poll id.
	 */
	private long id;
	
	/**
	 * Title of the poll){
	 */
	private String title;
	
	/**
	 * A message describing the poll.
	 */
	private String message;
	
	/**
	 * Empty poll constructor.
	 */
	public Poll() {
	}
	
	

	/**
	 * Construct a poll with specified title and message describing the poll.
	 * @param title - title of the poll
	 * @param message - a message describing the poll
	 */
	public Poll(String title, String message) {
		this.title = title;
		this.message = message;
	}


	/**
	 * Returns the id of the poll.
	 * @return id of the poll.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id of the poll.
	 * @param id - new id of the poll
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the title of the poll.
	 * @return title of teh pool.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title of the Poll.
	 * @param title - title of the poll.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Returns the message describing the poll.
	 * @return message describing the poll
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message of the poll.
	 * @param message - new message of the poll.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
