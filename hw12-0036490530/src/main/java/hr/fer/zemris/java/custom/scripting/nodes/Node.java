package hr.fer.zemris.java.custom.scripting.nodes;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Base class of nodes used in parsing using <code>SmartScriptParser</code>.
 * @author Dorian Ivankovic
 *
 */
public abstract class Node {
	
	/**
	 * Children nodes of the node.
	 */
	private List<Node> childrenNodes;
	
	/**
	 * Adds the child to children nodes of the current node.
	 * @param child - node to add to children of the current node
	 * @throws NullPointerException if child is null
	 */
	public void addChildNode(Node child) {
		Objects.requireNonNull(child);
		
		if(childrenNodes == null) {
			childrenNodes = new ArrayList<>();
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
	 * @throws IllegalArgumentException if index is &lt; 0 or &gt; numberOfchildren -1
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
	public List<Node> getChildren() {
		return childrenNodes;
	}
	
	@Override
	public String toString() {
		return "";
	}
	
	/**
	 * The method used for processing nodes by calling visitor's 
	 * appropriate visit() method.
	 * @param visitor - visitor that processes all the nodes
	 */
	public abstract void accept(INodeVisitor visitor);
	
}
