package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Used to present an error in parsing when using {@link NameBuilderParser}.
 * @author Dorian Ivankovic
 *
 */
public class NameBuilderParserException extends RuntimeException{

	/**
	 * Unique identifier of the exception
	 */
	private static final long serialVersionUID = 2908316176483335788L;

	/**
	 * Constructs a new expection using a message that specifies the details about exception
	 * @param message - detail message describing the cause of the exception
	 */
	public NameBuilderParserException(String message) {
		super(message);
	}

	
	
}
