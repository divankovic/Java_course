package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;

/**
 * A node representing a command which generates some textual
 * output dinamically.
 * @author Dorian Ivankovic
 *
 */
public class EchoNode extends Node {
	private Element[] elements;

	/**
	 * Constructor using elements of the echo node.
	 * @param elements - elements of the node
	 * @throws NullPointerException if elements is null
	 */
	public EchoNode(Element[] elements) {
		Objects.requireNonNull(elements);
		
		this.elements = new Element[elements.length];
		for(int i =0;i<elements.length;i++) {
			this.elements[i] = elements[i];
		}
		
	}
	
	/**
	 * Returns the elements of the node.
	 * @return the elements of the node
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public boolean equals(Object other) {
		if(this == other) return true;
		
		if(!(other instanceof EchoNode)) return false;
	
		EchoNode another = (EchoNode)other;
		
		if(this.elements.length!=another.elements.length) return false;
		
		for(int i =0;i<elements.length;i++) {
			if(!elements[i].equals(another.getElements()[i])) {
				return false;
			}
		}
		
		return true;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{$=");
		
		for(Element element : elements) {
			if(element instanceof ElementString) {
				result.append(" ").append(((ElementString)element).getOriginalText());
			}else {
				result.append(" ").append(element.asText());
			}
		}
		
		result.append("$}");
		
		return result.toString();
	}

	
	
}
