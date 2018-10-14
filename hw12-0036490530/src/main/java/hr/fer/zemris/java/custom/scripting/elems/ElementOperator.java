package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element that contains a single operator.
 * @author Dorian Ivankovic
 *
 */
public class ElementOperator extends Element {
	
	/**
	 * Operator.
	 */
	private String operator;

	/**
	 * Constructor using the operator operator.
	 * @param operator - operator of the operator
	 * @throws NullPointerException if operator is null
	 */
	public ElementOperator(String operator) {
		Objects.requireNonNull(operator);
		this.operator = operator;
	}

	/**
	 * Returns the operator of the stored operator.
	 * @return the operator of the stored operator
	 */
	public String getOperator() {
		return operator;
	}
	
	@Override
	public String asText() {
		return operator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		ElementOperator other = (ElementOperator) obj;
		
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		
		return true;
	
	}
	
	
	
	
}
