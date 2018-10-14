package hr.fer.zemris.java.hw07.shell.commands.namebuilder;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw07.shell.commands.MassrenameCommand;

/**
 * The parser is used to parse the extra EXPR parameter used in
 * {@link MassrenameCommand} to determine new files names.
 * 
 * @author Dorian Ivankovic
 *
 */
public class NameBuilderParser {

	/**
	 * The expression to be parsed
	 */
	private String expression;

	/**
	 * Constructs a <code>NameBuilderParser</code> using an expression to parse.
	 * 
	 * @param expression - expression to parse
	 */
	public NameBuilderParser(String expression) {
		this.expression = expression;
	}

	/**
	 * Parses the expression and returns a {@link CompositeNameBuilder} that
	 * contains multiple <code>NameBuilder</code>'s
	 * 
	 * @return {@link CompositeNameBuilder}
	 * @throws NameBuilderParserException
	 *             - if the expression cannot be appropriately parsed
	 */
	public NameBuilder getNameBuilder() {
		NameBuilderLexer lexer = new NameBuilderLexer(expression);
		List<NameBuilder> builders = new ArrayList<>();

		try {
			while (true) {
				String token = lexer.nextToken();
				if (token == null)
					break;

				if (token.startsWith("${")) {
					String content = token.substring(2, token.length() - 1);
					String[] elems = content.trim().split(",");
					if (elems.length != 1 && elems.length != 2) {
						throw new NameBuilderParserException("Illegal tag arguments, one or two arguments expected.");
					}

					int index;
					if (elems[0].trim().matches("\\d+")) {
						index = Integer.parseInt(elems[0].trim());
					} else {
						throw new NameBuilderParserException("First tag argument must be a group index.");
					}

					if (elems.length == 2) {
						String elem = elems[1].trim();
						boolean zeroPad = false;

						if (elem.startsWith("0")) {
							zeroPad = true;
						}

						if (elem.matches("\\d+")) {
							int minLength = Integer.parseInt(elem);
							builders.add(new GroupNameBuilder(index, minLength, zeroPad));
						} else {
							throw new NameBuilderParserException(
									"Second tag argument must be a positive number, optionally with 0 as prefix(zero padding).");
						}

					} else {
						builders.add(new GroupNameBuilder(index));
					}

				} else {
					builders.add(new ConstantNameBuilder(token));
				}

			}
			return new CompositeNameBuilder(builders);
		} catch (IllegalArgumentException ex) {
			throw new NameBuilderParserException(ex.getMessage());
		}

	}

	/**
	 * Helper class which this parser uses for lexical analysis of the input text.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	private class NameBuilderLexer {

		/**
		 * Data - from input message.
		 */
		private char[] data;

		/**
		 * Current position from which data is processed
		 */
		private int currentIndex;

		/**
		 * Constructs a <code>NameBuilderLexer</code> using input text to parse.
		 * 
		 * @param text
		 *            - text to parse
		 */
		public NameBuilderLexer(String text) {
			this.data = text.toCharArray();
		}

		/**
		 * Extracts the next token from the input text if it exists.
		 * 
		 * @throwsIllegalArgumentException - if text cannot be appropriately parsed
		 * @return
		 */
		public String nextToken() {
			int end = currentIndex;
			if (end >= data.length)
				return null;

			if (data[end] == '$' && end + 1 <= data.length && data[end + 1] == '{') {

				end += 2;
				while (end < data.length) {
					if (data[end] == '}') {
						break;
					}
					end++;
				}
				if (end >= data.length)
					throw new IllegalArgumentException("Tag ${ must be closed by }");
				end++;
			} else {
				while (end < data.length) {
					if (data[end] == '$' && end + 1 <= data.length && data[end + 1] == '{') {
						break;
					}
					end++;
				}
			}

			String token = new String(data, currentIndex, end - currentIndex);
			currentIndex = end;
			return token;
		}

	}

}
