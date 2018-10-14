package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;


import hr.fer.zemris.java.custom.scripting.elems.*;

import static hr.fer.zemris.java.custom.scripting.lexer.LexerState.*;

/**
 * Lexer used in parsing documents using SmartScriptParser.
 * @author Dorian Ivankovic
 *
 */
public class SmartScriptLexer {

	/**
	 * Input data of the document.
	 */
	private char[] data;
	
	/**
	 * Last generated element of the input data.
	 */
	private Element element;
	
	/**
	 * Index of the part of the data that was not yet parsed.
	 */
	private int currentIndex;
	
	/**
	 * Current state of the lexer.
	 */
	private LexerState state;

	/**
	 * Tags used for changing the state of the lexer.
	 */
	/**
	 * Starting tag.
	 */
	public static final String tagStart = "{$";
	
	/**
	 * Ending tag.
	 */
	public static final String tagEnd = "$}";

	/**
	 * Constuctor inputing text of the document.
	 * @param text - text of the document
	 * @throws NullPointerException if text is null
	 */
	public SmartScriptLexer(String text) {
		Objects.requireNonNull(text);
		this.data = text.toCharArray();
		currentIndex = 0;
		state = TEXT;
		element = new ElementString("");
	}

	/**
	 * Extracts the next element from the input data.
	 * @return the next element of the input data
	 * @throws LexerException if there is an error in input data
	 */
	public Element nextElement() {
		extractNextElement();
		return element;
	}

	/**
	 * Returns the last generated element from the input data.
	 * @return last generated element
	 */
	public Element getElement() {
		return element;
	}

	/**
	 * Sets the state of the lexer.
	 * @param state - new state of the lexer
	 * @throws NullPointerException if state is null
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}

	/**
	 * Returns the current state of the lexer
	 * @return current state of the lexer
	 */
	public LexerState getState() {
		return state;
	}

	/**
	 * Extracts next element of the input data based on the current state of the lexer.
	 * @throws LexerException if the input data cannot be parsed to elements or no more elements are available
	 */
	public void extractNextElement() {

		if (element == null) {
			throw new LexerException("No more elements available.");
		}

		if (state == TAG) {
			tagExtraction();
		} else {
			textExtraction();
		}

	}

	/**
	 * Extracts the next element from the input data when the lexer is in state <code>TAG</code>
	 * @throws LexerException - if the tag cannot be appropriately parsed
	 */
	private void tagExtraction() {

		skipBlanks();

		if (currentIndex >= data.length) {
			element = null;
			return;
		}

		char current = data[currentIndex];

		if (Character.isLetter(current)) {
			extractVariable();

		} else if (current == '@') {
			extractFunction();

		} else if (Character.isDigit(current)) {
			extractNumber();

		} else if (current == '"') {
			extractString();

		} else if (isOperator(current)) {

			if (current == '-' && checkNegativeNumber()) {
				extractNumber();
			} else {
				element = new ElementOperator(String.valueOf(current));
				currentIndex++;
			}

		} else if (current == '$') {

			currentIndex++;
			if (currentIndex >= data.length || data[currentIndex] != '}') {
				throw new LexerException("Symbol $ must be followed by } when closing a tag.");
			}
			element = new ElementString(tagEnd);
			currentIndex++;
		} else {
			throw new LexerException("Symbol : " + current + "is not allowed inside a tag.");
		}

	}

	/**
	 * Extracts the <code>ElementVariable</code> from the input.
	 * A variable begins with a letter and is followed by 0 or more letters, digits or underscores("_").
	 */
	private void extractVariable() {
		int end = currentIndex + 1;

		while (end < data.length) {
			char current = data[end];

			if (Character.isLetter(current) || Character.isDigit(current) || current == '_') {
				end++;
			} else {
				break;
			}

		}

		String value = new String(data, currentIndex, end - currentIndex);
		element = new ElementVariable(value);
		currentIndex = end;

	}

	/**
	 * Extracts the <code>ElementFunction</code> from the input.
	 * A function starts with "@" followed by a letter, and then 0 or more letters, digits or underscores("_").
	 *@throws LexerException if the function name is invalid
	 */
	private void extractFunction() {

		int end = ++currentIndex;

		if (end >= data.length || !Character.isLetter(data[end])) {
			throw new LexerException("Function name must have a letter after @, "
					+ "and then zero or more letters, digits or underscores.");
		}

		end++;

		while (end < data.length) {
			char current = data[end];

			if (Character.isLetter(current) || Character.isDigit(current) || current == '_') {
				end++;
			} else {
				break;
			}
		}

		element = new ElementFunction(new String(data, currentIndex, end - currentIndex));
		currentIndex = end;

	}

	/**
	 * Extract the integer or decimal number from the input.
	 * @throws LexerException if the number is too big and cannot be converted to the appropriate type
	 */
	private void extractNumber() {

		int end = currentIndex;
		if (data[currentIndex] == '-') {
			end++;
		}

		
		boolean decimal = false;

		while (end < data.length) {
			char current = data[end];

			if (Character.isDigit(data[end])) {
				end++;
			} else if (current == '.' && !decimal) {
				decimal = true;
				end++;
			} else {
				break;
			}

		}

		String value = new String(data, currentIndex, end - currentIndex);
		
		try {
			if (!decimal) {
				element = new ElementConstantInteger(Integer.parseInt(value));
			} else {
				element = new ElementConstantDouble(Double.parseDouble(value));
			}
		}catch(NumberFormatException ex) {
			throw new LexerException("Number value "+ value +" is too large, max value for integer numbers is : "+Integer.MAX_VALUE);
		}
		

		currentIndex = end;

	}

	
	/**
	 * Extract the String from the input data, string is bounded by ".
	 * Valid escape sequences in a string are \" and \\.
	 * @throws LexerException if \ is followed by something other than \ or ".
	 */
	private void extractString() {
		int end = currentIndex + 1;
		StringBuilder value = new StringBuilder();

		while (end < data.length) {
			char c = data[end];

			if (c != '"') {
				if (c == '\\') {
					if (checkStringEscape(end)) {
						end++;
						
						switch(data[end]) {
						case 't':
							value.append('\t');
							break;
						case 'r':
							value.append('\r');
							break;
						case 'n':
							value.append('\n');
							break;
						default:
							value.append(data[end]);
						}
						
					} else {
						throw new LexerException("\\ must be followed by \\ or \" in a string.");
					}
				}else {
					value.append(data[end]);
				}
				end++;
			} else {
				break;
			}
		}
		end++; // move from " to the next one

		element = new ElementString(value.toString());
		currentIndex = end;

	}

	/**
	 * Extracts the text from the data when the lexer is in state <code>TEXT</code>.
	 * @throws LexerException if \ is followed by something other than \ or {.
	 */
	private void textExtraction() {

		if (currentIndex >= data.length) {
			element = null;
			return;
		}

		int end = currentIndex;
		StringBuilder value = new StringBuilder();

		if (data[end] == '{') {
			if (checkOpeningTag(end)) {
				element = new ElementString(tagStart);
				currentIndex += 2;
				return;
			}
			value.append(data[end]);
			end++;
		}

		while (end < data.length) {
			char current = data[end];
			if (current == '{') {
				if (checkOpeningTag(end)) {
					break;
				}
			}
			if (current == '\\') {
				if (checkTextEscape(end)) {
					end++;
				} else {
					throw new LexerException("\\ must be followed by \\ or { in text.");
				}
			}
			value.append(data[end]);
			end++;

		}

		element = new ElementString(value.toString());
		currentIndex = end;

	}

	/**
	 * Checks if the symbol "$" if after symbol "{".
	 * @param index index of the "{" symbol
	 * @return true if the sequence is an opening tag, false otherwise
	 */
	private boolean checkOpeningTag(int index) {
		int end = index + 1;
		if (end < data.length && data[end] == '$') {
			return true;
		}
		return false;
	}

	/**
	 * Checks escape characters in a string.
	 * @param end - index of the character to check
	 * @return true if the character is \ or ", false otherwise
	 */
	private boolean checkStringEscape(int end) {
		end++;
		if (end >= data.length || (data[end] != '\\' && data[end] != '"'
				&& data[end]!='n' && data[end]!='t' && data[end]!='r')) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the - symbol is followed directly by a number.
	 * @return true if the - symbol is followed directly by a number, false otherwise
	 */
	private boolean checkNegativeNumber() {
		if (data[currentIndex] != '-') {
			return false;
		}
		int end = currentIndex + 1;
		if (end >= data.length || !Character.isDigit(data[end])) {
			return false;
		}

		return true;
	}

	/**
	 * Checks if the character c is an operator : +, -, *, /, ^, =.
	 * @param c - character to check
	 * @return true if c is an operator, false otherwise
	 */
	private boolean isOperator(char c) {
		if (c == '+' || c == '-' || c == '*' || c == '/' || c == '^' || c == '=') {
			return true;
		}

		return false;
	}

	/**
	 * Checks the escapa characters in a text.
	 * @param end - index of character to check.
	 * @return true if the character is \ or {, false otherwise
	 */
	private boolean checkTextEscape(int end) {
		end++;
		if (end >= data.length || (data[end] != '\\' && data[end] != '{')) {
			return false;
		}
		return true;
	}

	/**
	 * The method skips blanks in the input text - \r, \n, \t and spaces.
	 */
	private void skipBlanks() {

		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
			} else {
				break;
			}
		}

	}

}
