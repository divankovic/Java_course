package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element that contains a String value.
 * @author Dorian Ivankovic
 *
 */
public class ElementString extends ElementConstant {
	
	/**
	 * String value of the element.
	 */
	private String value;
	
	/**
	 * Constructor using string value of the element.
	 * @param value - string value of the element
	 * @throws NullPointerException if value is null
	 */
	public ElementString(String value) {
		Objects.requireNonNull(value);
		this.value = value;
	}

	/**
	 * Getter for string value of the element.
	 * @return string value of the element
	 */
	public String getValue() {
		return value;
	}
	
	@Override 
	public String asText() {
		return value;
	}
	
	/**
	 * The method converts the value into the original representation
	 * of that value before parsing with <code>SmartScriptParser</code>.
	 * @return original text
	 */
	public String getOriginalText() {
		String valueOrg = value.replace("\\", "\\\\");
		valueOrg = valueOrg.replace("\"", "\\\"");
		valueOrg = valueOrg.replace("\n", "\\n");
		valueOrg = valueOrg.replace("\t", "\\t");
		valueOrg = valueOrg.replace("\r", "\\r");
		return "\"" + valueOrg + "\"";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		
		ElementString other = (ElementString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		
		return true;
	
	}
	
	
}
