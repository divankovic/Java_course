package hr.fer.zemris.java.hw05.db;

/**
 * Provides multiple operators implementing {@link IComparisonOperator}.
 * @author Dorian Ivankovic
 *
 */
public class ComparisonOperators {

	/**
	 * Returns true if value1 is less than value2 determined by {@link String#compareTo(String anotherString)}
	 */
	public static final IComparisonOperator LESS = 
			(value1, value2) -> value1.compareTo(value2) < 0;

	/**
	* Returns true if value1 is less or equal than value2 determined by {@link #compareTo(String anotherString)}
	*/
	public static final IComparisonOperator LESS_OR_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) <= 0;

	/**
	* Returns true if value1 is greater than value2 determined by {@link #compareTo(String anotherString)}
	*/
	public static final IComparisonOperator GREATER = 
			(value1, value2) -> value1.compareTo(value2) > 0;
		
	/**
	* Returns true if value1 is greater or equal than value2 determined by {@link #compareTo(String anotherString)}
	*/
	public static final IComparisonOperator GREATER_OR_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) >= 0;
			
	/**
	* Returns true if value1 is equal to value2 determined by {@link #compareTo(String anotherString)}
	*/
	public static final IComparisonOperator EQUALS = 
			(value1, value2) -> value1.compareTo(value2) == 0;
			
	/**
	* Returns true if value1 is not equal to value2 determined by {@link #compareTo(String anotherString)}
	*/
	public static final IComparisonOperator NOT_EQUALS = 
			(value1, value2) -> value1.compareTo(value2) != 0;

	/**
	 * This operator is described in {@link StudentDB}
	 */
	public static final IComparisonOperator LIKE = (value, pattern) -> {
		int count = 0;
		char[] data = pattern.toCharArray();
		for(char c : data) {
			if(c=='*') count++;
		}
		if(count>1) throw new IllegalArgumentException("pattern must contain only one *");
		
		pattern = pattern.replaceAll("\\*", "\\.\\*");
		return value.matches(pattern);
	};
	
	/**
	 * Returns {@link IComparisonOperator} from it's String representation.
	 * @param value - string representation of <code>IComparisonOperator</code>
	 * @return <code>IComparisonOperator</code> from it's String representation
	 */
	public static IComparisonOperator getFromString(String value) {
		switch(value) {
			case "<": return LESS;
			case ">": return GREATER;
			case "<=": return LESS_OR_EQUALS;
			case ">=": return GREATER_OR_EQUALS;
			case "=": return EQUALS;
			case "!=": return NOT_EQUALS;
			case "LIKE": return LIKE;
			default : return null;
		}
	}



}
