package hr.fer.zemris.java.custom.scripting.elems;

/**
 * A basic element for <code>SmartScriptLexer</code>
 * @author Dorian Ivankovic
 *
 */
public abstract class Element {
	
	/**
	 * Returns the string representation of the element.
	 * @return string representation of the element
	 */
	public abstract String asText();
}
