package hr.fer.zemris.java.hw07.shell;

/**
 * This exception is thrown when {@link Environment} cannot access
 * the given input and output streams used for communication with the user.
 * @author Dorian Ivankovic
 *
 */
public class ShellIOException extends RuntimeException {

	/**
	 * Unique identifier of the exception.
	 */
	private static final long serialVersionUID = 7468551514538581028L;

	/**Default constructor of the <code>ShellIOException</code> not specifying the
	 * message or the cause of the exception.
	 */
	public ShellIOException() {
		super();
	}
	
}
