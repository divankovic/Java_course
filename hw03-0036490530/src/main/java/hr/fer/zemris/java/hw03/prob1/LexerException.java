package hr.fer.zemris.java.hw03.prob1;

/**
 * The exception is used to indicate an error in <code>Lexer</code> parsing.
 * @author Dorian Ivankovic
 *
 */
public class LexerException extends RuntimeException{

	/**
	 * Unique serial version identifier.
	 * @see Serializable#serialVersionUID
	 */
	private static final long serialVersionUID = 1946698942701078736L;

	/**Default constructor.
	 * 
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructs a new lexer exception with the specified detail message
	 * and cause.
	 * @param message - the detail message
	 * @param cause - the cause of the exception
	 */
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new lexer exception with the specified detail message.
	 * @param message - the detail message
	 */
	public LexerException(String message) {
		super(message);
	}

	/**
	 * Constructs a lexer exception with the specified cause.
	 * @param cause - cause of the exception
	 */
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	
}
