package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Exception used to indicate that there has been an semantic error when executing an input 
 * smartscript document using <code>SmartScriptEngine</code>.
 * @author Dorian Ivankovic
 *
 */
public class SmartScriptEngineException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6611496374341276545L;

	/**Default constructor of the SmartScriptException not specyfing the
	 * message nor the cause of the exception.
	 */
	public SmartScriptEngineException() {
		super();
	}

	/**
	 * Constructs a new SmartScriptException with the specified detail message and the cause of the exception.
	 * @param message - detail message
	 * @param cause - cause of the exception
	 */
	public SmartScriptEngineException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new SmartScriptException with the specified detail message.
	 * @param message - detail message
	 */
	public SmartScriptEngineException(String message) {
		super(message);
	}

	/**
	 * Constructs a new SmartScriptException with the specified cause of the exception.
	 * @param cause - cause of the exception
	 */
	public SmartScriptEngineException(Throwable cause) {
		super(cause);
	}
	
	
	
	
}
