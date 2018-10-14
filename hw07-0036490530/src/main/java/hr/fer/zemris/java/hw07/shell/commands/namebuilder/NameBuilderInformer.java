package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Used for constructing parts of the new name referencing groups obtained by
 * {@link Matcher} when parsing the original name.
 * 
 * @author Dorian Ivankovic
 *
 */
public class NameBuilderInformer implements NameBuilderInfo {

	/**
	 * Used for concatenating parts of the new file name;
	 */
	private StringBuilder stringBuilder;

	/**
	 * The existing groups in current file name obtained by {@link Matcher}.
	 */
	private List<String> groups;

	/**
	 * Constructs a new <code>NameBuilderInformer</code> using a string builder and
	 * a list of groups
	 * 
	 * @param builder
	 *            - used for concatenating parts of the new file name
	 * @param groups
	 *            - the existing groups in current file name obtained by matcher
	 */
	public NameBuilderInformer(StringBuilder builder, List<String> groups) {
		stringBuilder = builder;
		this.groups = new ArrayList<>(groups);
	}

	@Override
	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	@Override
	public String getGroup(int index) {

		return groups.get(index);
	}

}
