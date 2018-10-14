package hr.fer.zemris.java.hw05.db;

/**
 * Obtains a requested field value from a given
 * {@link StudentRecord}.
 * @author Dorian Ivankovic
 *
 */
public interface IFieldValueGetter {
	
	/**
	 * Returns the requested field from a <code>StudentRecord</code>-
	 * @param record - student record to extract field from
	 * @return value of the requested field
	 */
	public String get(StudentRecord record);
}
