package hr.fer.zemris.java.blog.dao;


/**
 * Used to indicate an error occured when comunicating with data from the {@link DAO} layer.
 * @author Dorian Ivankovic
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a {@link DAOException} using it's error message and it's cause.
	 * @param message - error message
	 * @param cause - cause of the error
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs u DAOException using its error message.
	 * @param message - errror message
	 */
	public DAOException(String message) {
		super(message);
	}
}