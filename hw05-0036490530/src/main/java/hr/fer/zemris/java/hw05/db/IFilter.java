package hr.fer.zemris.java.hw05.db;

/**
 * Filter that determines if {@link StudentRecord} satisfies a
 * certain condition.
 * @author Dorian Ivankovic
 *
 */
public interface IFilter {
	
	/**
	 * Checks if <code>record</code> satisfies a certain condition.
	 * @param record - record to check
	 * @return true if the record satisfies the condition, false otherwise
	 */
	public boolean accepts(StudentRecord record);
}
