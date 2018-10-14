package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Element containing a constant double
 * @author Dorian Ivankovic
 *
 */
public class ElementConstantDouble extends ElementConstant{
	
	/**
	 * Used for comparison of double values in method <code>equals</code>.
	 */
	public static double EPS = 1E-6;
	
	/**
	 * Double value of the element.
	 */
	private double value;

	/**
	 * Constructor using value of the constant double.
	 * @param value - value of the constant double
	 */
	public ElementConstantDouble(double value) {
		this.value = value;
	}

	/**
	 * Getter for property value.
	 * @return value of the constant double
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)return true;
		
		if (!(obj instanceof ElementConstantDouble)) return false;
		
		ElementConstantDouble other = (ElementConstantDouble) obj;
		if (Math.abs(this.value-other.value)>EPS)	return false;
		
		return true;
	}
	
	
}
