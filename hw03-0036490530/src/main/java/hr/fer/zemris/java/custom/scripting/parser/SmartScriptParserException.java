package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Exception used to indicate that there has been an error when parsing an input document using <code>SmartScriptParser</code>.
 * @author Dorian Ivankovic
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6611496374341276545L;

	/**Default constructor of the SmartScriptException not specyfing the
	 * message or the cause of the exception.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructs a new SmartScriptException with the specified detail message and the cause of the exception.
	 * @param message - detail message
	 * @param cause - cause of the exception
	 */
	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new SmartScriptException with the specified detail message.
	 * @param message - detail message
	 */
	public SmartScriptParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a new SmartScriptException with the specified cause of the exception.
	 * @param cause - cause of the exception
	 */
	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}
	
	
	
	
}
