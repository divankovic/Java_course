package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

/**
 * Used to build a new file name by adding element by element from the
 * expression that was parsed using {@link NameBuilderParser}.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface NameBuilder {

	/**
	 * Writes the appropriate content into the <code>StringBuilder</code> of the
	 * info.
	 * 
	 * @param info
	 *            - stores the new name progress construction(as a string builder)
	 */
	void execute(NameBuilderInfo info);
}
