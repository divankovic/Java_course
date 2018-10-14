package hr.fer.zemris.java.hw16.jvdraw.geometric.editors;

/**
 * This exception is thrown if editing done by {@link GeometricalObjectEditor}
 * is not valid.
 * 
 * @author Dorian Ivankovic
 *
 */
public class GeometricalEditingException extends RuntimeException {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs the exception with the specified error message and cause.
	 * 
	 * @param message
	 *            - error message
	 * @param cause
	 *            - exception cause
	 */
	public GeometricalEditingException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs the exception with the specified error message.
	 * 
	 * @param message
	 *            - error message
	 */
	public GeometricalEditingException(String message) {
		super(message);
	}

	/**
	 * Constructs the exception with the specified cause.
	 * 
	 * @param cause
	 *            - exception cause
	 */
	public GeometricalEditingException(Throwable cause) {
		super(cause);
	}

}
