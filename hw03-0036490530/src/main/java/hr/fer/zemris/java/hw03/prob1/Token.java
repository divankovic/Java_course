package hr.fer.zemris.java.hw03.prob1;

/**
 * Basic token used in <code>Lexer</code> parsing.
 * @author Dorian Ivankovic
 *
 */
public class Token {
	
	/**
	 * Type of the token
	 */
	private TokenType type;
	
	/**
	 * Value of the token
	 */
	private Object value;
	
	/**
	 * Default constructor for the token using token type and value.
	 * @param type - type of the token
	 * @param value - value of the token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Returns the value of the token.
	 * @return token value
	 */
	public Object getValue() {
		return value;
	}
	
	
	/**
	 * Returns the type of the token.
	 * @return token type
	 */
	public TokenType getType() {
		return type;
	}
}
