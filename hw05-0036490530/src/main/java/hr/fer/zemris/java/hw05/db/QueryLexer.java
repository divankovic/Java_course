package hr.fer.zemris.java.hw05.db;

/**
 * Performs the lexical analysis of the input query.<br>
 * Query syntax is described in {@link StudentDB}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class QueryLexer {
	
	/**
	 * Data of the query.
	 */
	private char[] queryData;
	
	/**
	 * Last extracted token.
	 */
	private QueryToken token;
	
	/**
	 * Current index to analyse queryData from.
	 */
	private int currentIndex;
	
	
	/**
	 * Constructs a new <code>QueryLexer</code> using query input.
	 * @param query input query
	 */
	public QueryLexer(String query) {
		this.queryData = query.toCharArray();
	}
	
	
	/**
	 * Returns the last extracted token.
	 * @return the last extracted token
	 */
	public QueryToken getToken() {
		return token;
	}
	

	/**
	 * Extracts and returns the next token in the query.
	 * @return next token
	 * @throws UnsupportedOperationException if the lexer has reached the end of the query
	 * @throws IllegalArgumentException if the query cannot be parsed
	 */
	public QueryToken nextToken() {
		
		skipBlanks();
		
		if(token!=null && token.getType()==QueryTokenType.END) throw new UnsupportedOperationException();
			
		if(currentIndex >= queryData.length) {
			token = new QueryToken(QueryTokenType.END);
			return token;
		}
		
		char current = queryData[currentIndex];
		
		if(Character.isLetter(current)) {
			
			int end = currentIndex;
			do {
				end++;
			}while(end<queryData.length && Character.isLetter(queryData[end]));
			
			String value = new String(queryData, currentIndex, end - currentIndex);
			if(value.equals("firstName") || value.equals("lastName") || value.equals("jmbag")) {
				token = new QueryToken(QueryTokenType.FIELD,value);
			}else if(value.toUpperCase().equals("AND")) {
				token = new QueryToken(QueryTokenType.AND);
			}else if(value.equals("LIKE")) {
				token = new QueryToken(QueryTokenType.OPERATOR,value); 
			}else {
				throw new IllegalArgumentException(value+" cannot be parsed");
			}
			currentIndex = end;
			
		}else if(isOperatorStart(current)) {
			String value = String.valueOf(current);
		
			if(currentIndex<queryData.length-1) {
				char next = queryData[currentIndex+1];
				if(current!='=' && next=='=') {
					value+=next;
					currentIndex++;
				}
			}
			
			token = new QueryToken(QueryTokenType.OPERATOR,value);
			currentIndex++;
			
		}else if(current=='"') {
			extractStringLiteral();
		}else {
			throw new IllegalArgumentException("Illegal character : "+current);
		}
		
		
		return token;
	}
	
	/**
	 * Extracts the next String literal.
	 * @throws IllegalArgumentException if the literal cannot be parsed
	 */
	private void extractStringLiteral() {
		
		int end = ++currentIndex;
		boolean wildcard = false;
		
		 do{
			
			if(end>=queryData.length-1) throw new IllegalArgumentException("String literal must be closed by \"");
			
			if(queryData[end]=='*') {
				if(!wildcard) wildcard = true;
				else throw new IllegalArgumentException("Only one wildcard (*) can appear in String literal");
			}
			
			end++;
		}while(queryData[end]!='"');
		
		String value = new String(queryData,currentIndex,end-currentIndex);
		token = new QueryToken(QueryTokenType.LITERAL,value);
		currentIndex = ++end;
	
	}
	
	/**
	 * Checks if c is the start of an operator.
	 * @param c - start character
	 * @return true if c is the start of an operator, false otherwise
	 */
	private boolean isOperatorStart(char c) {
		return c=='<' || c=='>' || c=='=' || c=='!';
	}
	
	/**
	 * The method skips blanks in the input text - \r, \n, \t and spaces.
	 */
	private void skipBlanks() {
		
		while (currentIndex < queryData.length) {
			char c = queryData[currentIndex];
			if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
			} else {
				break;
			}
		}

	}
}
