package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element that contains a single operator.
 * @author Dorian Ivankovic
 *
 */
public class ElementOperator extends Element {
	private String symbol;

	/**
	 * Constructor using the operator symbol.
	 * @param symbol - symbol of the operator
	 * @throws NullPointerException if symbol is null
	 */
	public ElementOperator(String symbol) {
		Objects.requireNonNull(symbol);
		this.symbol = symbol;
	}

	/**
	 * Returns the symbol of the stored operator.
	 * @return the symbol of the stored operator
	 */
	public String getSymbol() {
		return symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
		
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		
		return true;
	
	}
	
	
	
	
}
