package hr.fer.zemris.java.gui.layouts;

/**
 * This exception is thrown when adding to illegal positions using
 * {@link CalcLayout#addLayoutComponent(java.awt.Component, Object)}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = -3503721957041493250L;

	/**
	 * Constructs a new <code>CalcLayoutExceptin</code> with the specified error
	 * message.
	 * 
	 * @param message - error message
	 */
	public CalcLayoutException(String message) {
		super(message);
	}

}
