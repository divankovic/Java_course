package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

import hr.fer.zemris.java.hw07.shell.commands.MassrenameCommand;

/**
 * Stores basic information when building a new file name using
 * {@link MassrenameCommand}.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface NameBuilderInfo {

	/**
	 * Returns the <code>StringBuilder</code> that contains the current progress of
	 * constructing a new name.
	 * 
	 * @return string builder
	 */
	StringBuilder getStringBuilder();

	/**
	 * Returns the matcher group from the original file name at the specified index.
	 * 
	 * @param index
	 *            - index of the matcher group
	 * @return the matcher group at the specified index
	 */
	String getGroup(int index);
}
