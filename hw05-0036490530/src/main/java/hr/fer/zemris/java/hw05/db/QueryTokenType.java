package hr.fer.zemris.java.hw05.db;

/**
 * Token types used in query lexical analysis using {@link QueryParser}.
 * All fields are described in detail in {@link StudentDB}.
 * @author Dorian Ivankovic
 *
 */
public enum QueryTokenType {
	FIELD, 
	OPERATOR, 
	LITERAL, 
	AND,
	END;
}
