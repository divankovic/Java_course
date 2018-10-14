package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

import java.util.regex.Matcher;

/**
 * Constructs the part of the new name referencing to groups obtained by
 * {@link Matcher} when parsing the original file name. Other than group index,
 * minimal length and padding (spaces or zeros) can also be specified.
 * 
 * @author Dorian Ivankovic
 *
 */
public class GroupNameBuilder implements NameBuilder {

	/**
	 * Matcher's group index
	 */
	private int groupIdx;

	/**
	 * Minimal length of the expression, if less than this then it has to be padded
	 * to this size using either zeros or spaces
	 */
	private int minLength;

	/**
	 * Determines whether zero padding or space padding is used
	 */
	private boolean zeroPad;

	/**
	 * Constructs a new <code>GroupNameBuilder</code> using group index.
	 * 
	 * @param groupIdx
	 *            - matcher's group index
	 */
	public GroupNameBuilder(int groupIdx) {
		this(groupIdx, 0, false);
	}

	/**
	 * Constructs a new <code>GroupNameBuilder</code> using group index, minimal
	 * length and boolean flag that determines whether zero or space padding is used
	 * 
	 * @param groupIdx
	 *            - matcher's group index
	 * @param minLength
	 *            - minimal lenght of the expression
	 * @param zeroPad
	 *            - determines whether zero or space padding is used
	 */
	public GroupNameBuilder(int groupIdx, int minLength, boolean zeroPad) {
		this.groupIdx = groupIdx;
		this.minLength = minLength;
		this.zeroPad = zeroPad;
	}

	@Override
	public void execute(NameBuilderInfo info) {

		StringBuilder sb = info.getStringBuilder();

		String group = info.getGroup(groupIdx);

		if (group.length() < minLength) {
			for (int i = 0, n = minLength - group.length(); i < n; i++) {
				sb.append(zeroPad ? 0 : " ");
			}
			sb.append(group);
		} else {
			sb.append(group);
		}

	}

}
