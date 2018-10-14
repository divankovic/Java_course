package hr.fer.zemris.java.votingapp.dao;

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
	 * Empty exception constructor
	 */
	public DAOException() {
	}

	/**
	 * Constructs a {@link DAOException} using it's error message, cause and flags to determine
	 * more detailed information.
	 * @param message - error message
	 * @param cause - cause of the exception
	 * @param enableSuppression - flag that determines whether or not is the suppresion enabled
	 * @param writableStackTrace - flag that determines what type of stack trace to use.
	 */
	public DAOException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	
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

	
	/**
	 * Constructs a new {@link DAO} using the cause of it's error.
	 * @param cause - cause of the exception.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}