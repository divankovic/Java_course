package hr.fer.zemris.java.hw05.db;

/**
 * Exception used to indicate that there has been an error when parsing an input
 * query using {@link QueryParser}.
 * @author Dorian Ivankovic
 *
 */
public class QueryParserException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8241677712795882753L;

	/**Default constructor of the QueryParserException not specifying the
	 * message or the cause of the exception.
	 */
	public QueryParserException() {

	}

	/**
	 * Constructs a new QueryParserException with the specified detail message and the cause of the exception.
	 * @param message - detail message
	 * @param cause - cause of the exception
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructs a new QueryParserException with the specified detail message of the exception.
	 * @param message - detail message
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Constructs a new QueryParserException with the cause of the exception.
	 * @param cause - cause of the exception
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}
	
	
}
