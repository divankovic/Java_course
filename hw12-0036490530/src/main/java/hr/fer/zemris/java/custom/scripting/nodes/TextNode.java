package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

/**
 * Node representing a piece of textual data.
 * @author Dorian Ivankovic
 *
 */
public class TextNode extends Node {
	
	/**
	 * Text value of the node.
	 */
	private String text;

	/**
	 * Constructor using text of the text node.
	 * @param text - text of the node
	 * @throws NullPointerException if text is null
	 */
	public TextNode(String text) {
		Objects.requireNonNull(text);
		this.text = text;
	}
	
	/**
	 * Returns the text stored in the node.
	 * @return the text stored in the node
	 */
	public String getText() {
		return text;
	}
	
	@Override
	public boolean equals(Object other) {
		if(this == other) return true;
		
		if(!(other instanceof TextNode)) return false;
	
		TextNode another = (TextNode)other;
		
		return this.text.equals(another.getText());
	}

	@Override
	public int hashCode() {
		return text.hashCode();
	}
	
	@Override
	public String toString() {
		String textOrg = text.replace("\\", "\\\\");
		textOrg = textOrg.replace("{", "\\{");
		return textOrg;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
	
	
	
}
