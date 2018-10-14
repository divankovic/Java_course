package hr.fer.zemris.java.hw05.db;

/**
 * Models the complete conditional expression of the database query.
 * Conditional expressions are described in {@link StudentDB}.
 * @author Dorian Ivankovic
 *
 */
public class ConditionalExpression {

	/**
	 * Field getter of the certain parameter of the {@link StudentRecord}
	 */
	private IFieldValueGetter fieldGetter;
	
	/**
	 * Operator used to compare the field and the literal.
	 */
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Literal to compare field to.
	 */
	private String literal;
	
	/**
	 * Constructs a <code>ConditionalExpression</code> using a fieldGetter, comparisonOperator and String literal.
	 * @param fieldGetter - field getter
	 * @param comparisonOperator - used to compare field and literal
	 * @param literal - expression to compare field to
	 */
	public ConditionalExpression(IFieldValueGetter fieldGetter, IComparisonOperator comparisonOperator, String literal) {
		this.fieldGetter = fieldGetter;
		this.comparisonOperator = comparisonOperator;
		this.literal = literal;
	}

	/**
	 * Returns the field getter.
	 * @return field getter
	 */
	public IFieldValueGetter getFieldGetter() {
		return fieldGetter;
	}

	/**
	 * Returns the literal.
	 * @return the literal of the conditional expression
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the comparison operator.
	 * @return comparison operator.
	 */
	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
	
}
