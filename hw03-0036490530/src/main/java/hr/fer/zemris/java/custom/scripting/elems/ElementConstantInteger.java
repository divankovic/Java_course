package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element that represents a constant integer.
 * @author Dorian Ivankovic
 *
 */
public class ElementConstantInteger extends Element {
	private int value;
	
	/**
	 * Constructor using value of the constant integer.
	 * @param value - value of the constant integer
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	/**
	 * Getter for value of the constant integer.
	 * @return value of the constant integer
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		
		if (!(obj instanceof ElementConstantInteger))return false;
		
		ElementConstantInteger other = (ElementConstantInteger) obj;
		if (value != other.value)return false;
		
		return true;
	}
	
	
}
