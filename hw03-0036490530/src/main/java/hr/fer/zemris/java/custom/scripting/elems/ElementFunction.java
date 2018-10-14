package hr.fer.zemris.java.custom.scripting.elems;

import java.util.Objects;

/**
 * Element that contains a function.
 * @author Dorian Ivankovic
 *
 */
public class ElementFunction extends Element {
	private String name;

	/**
	 * Constructor using name of the function.
	 * @param name - name of the function
	 * @throws NullPointerException if name is null
	 */
	public ElementFunction(String name) {
		Objects.requireNonNull(name);
		this.name = name;
	}

	/**
	 * Returns the name of the stored function.
	 * @return name of the stored function.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String asText() {
		return '@'+name;
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
	
		ElementFunction other = (ElementFunction) obj;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	
	}
	
	
	
}
