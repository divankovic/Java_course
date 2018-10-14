package hr.fer.zemris.java.hw05.db;

/**
 * Compares two values using a specific operator.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface IComparisonOperator {
	
	/**
	 * Compares <code>value1</code> and <code>value2</code> using a 
	 * specific operator
	 * @param value1 - first value
	 * @param value2 - second value
	 * @return true if values satisfy the operation provided by the operator, false otherwise
	 */
	public boolean satisfied(String value1, String value2);
}
