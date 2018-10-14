package hr.fer.zemris.java.hw05.db;

/**
 * Responsible for obtaining a requested field value from a given
 * {@link StudentRecord}.
 * @author Dorian Ivankovic
 *
 */
public class FieldValueGetters {

	/**
	 * Obtains the student's first name.
	 */
	public static final IFieldValueGetter FIRST_NAME =
			record->record.getFirstName();
			
	/**
	 * Obtains the student's last name.
	 */
	public static final IFieldValueGetter LAST_NAME =
			record->record.getLastName();

			
	/**
	 * Obtains the student's jmbag.
	 */
	public static final IFieldValueGetter JMBAG = 
			record->record.getJmbag();

	/**
	 * Returns the corresponding {@link IFieldValueGetter} from it's String representation.
	 * @param value - string representation of the <code>IFieldValueGetter</code>
	 * @return corresponding <code>IFieldValueGetter</code> from it's String representation
	 */
	public static IFieldValueGetter getFromString(String value) {
		switch(value) {
			case "jmbag":
				return JMBAG;
			case "firstName":
				return FIRST_NAME;
			case "lastName":
				return LAST_NAME;
			default:
				return null;
		}
	}
}
