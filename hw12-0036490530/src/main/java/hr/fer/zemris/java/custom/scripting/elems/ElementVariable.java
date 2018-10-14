package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element representing a variable.
 * @author Dorian Ivankovic
 *
 */
public class ElementVariable extends Element {
	
	/**
	 * Variable name.
	 */
	private String name;
	
	/**
	 * Constructor using name.
	 * @param name - name of the variable
	 * @throws NullPointerException if name is null
	 */
	public ElementVariable(String name) {
		Objects.requireNonNull(name);
		this.name = name;
	}

	/**
	 * Getter for name of the variable.
	 * @return name of the variable
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText(){
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		
		ElementVariable other = (ElementVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	
	}
	
	
}
