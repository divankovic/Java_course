package hr.fer.zemris.java.hw05.db;

/**
 * A single token used in lexical analysis of the query using {@link QueryLexer}.
 * @author Dorian Ivankovic
 *
 */
public class QueryToken {
	
	/**
	 * Type of the token.
	 */
	private QueryTokenType type;
	
	/**
	 * Value of the token.
	 */
	private String value;

	/**
	 * Constructs a <code>QueryToken</code> using token's type and it's value.
	 * @param type - type of the token
	 * @param value - value of the token
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.type = type;
		this.value = value;
	}
	
	
	/**
	 * Constructor a <code>QueryToken</code> using only it's type, it's value is set to null.
	 * @param type - type of the token
	 */
	public QueryToken(QueryTokenType type) {
		this(type,null);
	}

	
	/**
	 * Returns the type of the token.
	 * @return type of the token
	 */
	public QueryTokenType getType() {
		return type;
	}

	/**
	 * Returns the value of the token
	 * @return value of the token
	 */
	public String getValue() {
		return value;
	}
	
}
