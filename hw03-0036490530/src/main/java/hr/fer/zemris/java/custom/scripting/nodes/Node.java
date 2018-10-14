package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.hw03.ArrayIndexedCollection;
import java.util.Objects;

/**
 * Base class of nodes used in parsing using <code>SmartScriptParser</code>.
 * @author Dorian Ivankovic
 *
 */
public class Node {
	private ArrayIndexedCollection childrenNodes;
	
	/**
	 * Adds the child to children nodes of the current node.
	 * @param child - node to add to children of the current node
	 * @throws NullPointerException if child is null
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child);
		
		if(childrenNodes == null) {
			childrenNodes = new ArrayIndexedCollection();
		}
		
		childrenNodes.add(child);
	}
	
	/**
	 * Returns the number of current children of the current node.
	 * @return number of current children
	 */
	public int numberOfChildren() {
		if(childrenNodes == null) {
			return 0;
		}
		
		return childrenNodes.size();
	}
	
	/**
	 * Returns the child Node at the specified position.
	 * @param index - index of the child node
	 * @return node at position index
	 * @throws IllegalArgumentException if index is &lt;0 or &gt;numberOfchildren -1
	 */
	public Node getChild(int index) {
		if(index <0 || index>numberOfChildren()-1) {
			throw new IllegalArgumentException("Index must be >0 and <size-1, was : "+index);
		}
		
		return (Node)childrenNodes.get(index);
	}
	
	/**
	 * Returns the children nodes of the current node.
	 * @return children nodes of the current node
	 */
	public ArrayIndexedCollection getChildren() {
		return childrenNodes;
	}
	
	@Override
	public String toString() {
		return "";
	}
	
	
}
