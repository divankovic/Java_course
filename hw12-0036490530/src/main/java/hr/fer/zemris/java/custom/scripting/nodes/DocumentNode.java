package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.List;


/**
 * A node representing an entire document.
 * @author Dorian Ivankovic
 *
 */
public class DocumentNode extends Node {

	@Override
	public boolean equals(Object other) {
		
		if(this==other) return true;
		
		if(!(other instanceof DocumentNode)) return false;

		DocumentNode another = (DocumentNode)other;
		
		List<Node> myChildren = getChildren();
		List<Node> anotherChildren = another.getChildren();
		
		if(myChildren.size()!=anotherChildren.size()) {
			return false;
		}
		
		for(int i=0,n=myChildren.size();i<n;i++) {
			if(!myChildren.get(i).equals(anotherChildren.get(i))) {
				return false;
			}
		}
		
		return true;
		
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
	
}
