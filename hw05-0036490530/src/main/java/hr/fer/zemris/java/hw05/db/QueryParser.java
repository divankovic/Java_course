package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses the input query using {@link QueryLexer} and rules
 * that are specified and explained in {@link StudentDB}.
 * @author Dorian Ivankovic
 *
 */
public class QueryParser {
	
	
	/**
	 * Lexer used for lexical analysis of the input query.
	 */
	private QueryLexer lexer;
	
	/**
	 * Conditional expressions in the query.
	 */
	private List<ConditionalExpression> query;
	
	
	/**
	 * Constructor a new <code>QueryParser</code> using input query.
	 * @param query - input query
	 */
	public QueryParser(String query) {
		lexer = new QueryLexer(query);
		this.query = new ArrayList<>();
		parseQuery();
	}
	
	/**
	 * Checks whether or not the query is direct.
	 * A direct query contains only one conditional expression using jmbag and "=", for example : query jmbag="2".
	 * @return true if the query is direct, false otherwise
	 */
	public boolean isDirectQuery() {
		if(query.size()!=1) return false;
		
		ConditionalExpression expression = query.get(0);
		
		if(expression.getFieldGetter()!=FieldValueGetters.JMBAG) return false;
		if(expression.getComparisonOperator()!=ComparisonOperators.EQUALS) return false;
	
		return true;
	}
	
	/**
	 * Returns the queried jmbag if the query is direct.
	 * @return queried jmbag
	 * @throws IllegalStateException if the query is not direct - determined by {@link #isDirectQuery()}
	 */
	public String getQueriedJMBAG() {
		if(!isDirectQuery()) throw new IllegalStateException("Not a direct query.");
	
		return query.get(0).getLiteral();
	}
	
	/**
	 * Returns the conditional expression obtained from the query.
	 * @return conditional expression obtained from the query
	 */
	public List<ConditionalExpression> getQuery(){
		return query;
	}
	
	
	/**
	 * Parses the query into conditional expressions.
	 * @throws QueryParserException - if there has been an error in lexical analysis or the input
	 * syntax of the query is not correct
	 * @see {@link StudentDB}
	 */
	private void parseQuery() {
		
		boolean firstExpression = true;
		
		try {
			while(true) {
				
				QueryToken token = lexer.nextToken();
				if(token.getType()==QueryTokenType.END) break;
				
				
				if(!firstExpression) {
					if(token.getType()!=QueryTokenType.AND) {
						throw new QueryParserException("Expressions must be separated by AND");
					}
					token = lexer.nextToken();
				}else {
					firstExpression = false;
				}
				
				IFieldValueGetter fieldGetter;
				IComparisonOperator operator;
				String literal;
				
				if(token.getType()!=QueryTokenType.FIELD) {
					throw new QueryParserException("Expression must start with field jmbag, firstName or lastName.");
				}
				
				fieldGetter = FieldValueGetters.getFromString(token.getValue());
				
				token = lexer.nextToken();
				if(token.getType()!=QueryTokenType.OPERATOR) {
					throw new QueryParserException("Field must be followed by an operator in an expression.");
				}
				
				operator = ComparisonOperators.getFromString(token.getValue());
				
				token = lexer.nextToken();
				if(token.getType()!=QueryTokenType.LITERAL) {
					throw new QueryParserException("Operator must be followed by a string literal in an expression.");
				}
				
				literal = token.getValue();
				
				query.add(new ConditionalExpression(fieldGetter,operator,literal));
			}
			
			if(query.isEmpty()) throw new QueryParserException("Query must not be empty");
		
		}catch(IllegalArgumentException ex) {
			throw new QueryParserException(ex.getMessage());
		}catch(UnsupportedOperationException ex) {
			throw new QueryParserException("Illegal input format, expressions must be separated by AND and"
					+ " constist of field operator and string literal");
			
		}
		
	}
}
