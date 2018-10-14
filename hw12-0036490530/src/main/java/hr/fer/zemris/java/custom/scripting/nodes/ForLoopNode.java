package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Objects;


import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstant;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * A node representing a single for loop construct.
 * @author Dorian Ivankovic
 *
 */
public class ForLoopNode extends Node {
	
	/**
	 * Variable used for iteration in the for loop.
	 */
	private ElementVariable variable;
	
	/**
	 * Start expression of the loop;
	 */
	private ElementConstant startExpression;

	/**
	 * End expression of the loop.
	 */
	private ElementConstant endExpression;
	
	/**
	 * Step expression of the loop.
	 */
	private ElementConstant stepExpression;
	
	
	/**
	 * Constructor using variable, startExpression, endExpression and stepExpression.
	 * @param variable - variable used in iteration
	 * @param startExpression - starting value of the variable
	 * @param endExpression - ending value of the variable
	 * @param stepExpression - step increment of the variable
	 * @throws NullPointerException if variable, startExpression or endExpression is null
	 */
	public ForLoopNode(ElementVariable variable, ElementConstant startExpression, ElementConstant endExpression,
			ElementConstant stepExpression) {
		
		Objects.requireNonNull(variable);
		Objects.requireNonNull(startExpression);
		Objects.requireNonNull(endExpression);

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	
	}
	
	/**
	 * Returns the variable used in for loop.
	 * @return	the variable used in for loop
	 */
	public ElementVariable getVariable() {
		return variable;
	}
	
	/**
	 * Returns the start expression used in for loop.
	 * @return	the start expression used in for loop
	 */
	public ElementConstant getStartExpression() {
		return startExpression;
	}
	
	/**
	 * Returns the end expression used in for loop.
	 * @return	the end expression used in for loop
	 */
	public ElementConstant getEndExpression() {
		return endExpression;
	}
	
	/**
	 * Returns the step expression used in for loop.
	 * @return	the step expression used in for loop
	 */
	public ElementConstant getStepExpression() {
		return stepExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endExpression == null) ? 0 : endExpression.hashCode());
		result = prime * result + ((startExpression == null) ? 0 : startExpression.hashCode());
		result = prime * result + ((stepExpression == null) ? 0 : stepExpression.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (!(obj instanceof ForLoopNode)) return false;

		ForLoopNode other = (ForLoopNode) obj;
		if(!startExpression.equals(other.startExpression))
			return false;
		
		if (!endExpression.equals(other.endExpression))
			return false;
		
		if (!stepExpression.equals(other.stepExpression))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		
		return true;
	}
	
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("{$FOR ");
		
		
		result.append(variable.asText()).append(" ");
		result.append(getOriginalText(startExpression)).append(" ");
		result.append(getOriginalText(endExpression)).append(" ");
		if(stepExpression!=null) {
			result.append(getOriginalText(stepExpression));
		}
		
		result.append("$}");
		
		return result.toString();
	}
	
	/**
	 * Returns the original text from the document.
	 * @param element - element to get original text from
	 * @return original text of the element
	 */
	private String getOriginalText(Element element) {
		if(element instanceof ElementString) {
			return ((ElementString)element).getOriginalText();
		}else {
			return element.asText();
		}
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
	}
	
	
}
