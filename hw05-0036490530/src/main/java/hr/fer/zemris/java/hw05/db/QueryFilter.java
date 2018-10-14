package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Used to filter {@link StudentDatabase} using a list of
 * conditional expressions obtained from the input query using {@link QueryParser}.
 * @author Dorian Ivankovic
 *
 */
public class QueryFilter implements IFilter{

	/**
	 * Conditonal expressions obtained from the input query.
	 */
	private List<ConditionalExpression> expressions;
	

	/**
	 * Constructs a <code>QueryFilter</code> using expressions
	 * obtained from the input query.
	 * @param expressions - conditional expressions
	 */
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	/**
	 * Checks if the {@link StudentRecord} satisfied all <code>expressions</code>.
	 * @return true if the record satisfies all expressions, false otherwise
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		
		for(ConditionalExpression expression : expressions) {
			String fieldValue = expression.getFieldGetter().get(record);
			if(!expression.getComparisonOperator().satisfied(fieldValue, expression.getLiteral())){
				return false;
			}
		}
		
		return true;
	}
	
	

}
