package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Represent an end tag node.
 * @author Dorian Ivankovic
 *
 */
public class EndNode extends TextNode{

	/**
	 * Constructs a new {@link EndNode} by using its
	 * text value.
	 * @param text - value of the end node
	 */
	public EndNode(String text) {
		super(text);
	}

	
	@Override
	public String toString() {
		return this.getText();
	}
}
