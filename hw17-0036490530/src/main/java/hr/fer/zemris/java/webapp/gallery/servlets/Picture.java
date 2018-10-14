package hr.fer.zemris.java.webapp.gallery.servlets;

import java.util.Set;

/**
 * Models the picture containing all it's relevant information- name, description and tags.
 * @author Dorian Ivankovic
 *
 */
public class Picture {
	
	/**
	 * Name of the picture.
	 */
	private String name;
	
	/**
	 * Description of the picture.
	 */
	private String description;
	
	/**
	 * Tags of the picture.
	 */
	private Set<String> tags;
	
	/**
	 * Constructor.
	 * @param name - name of the picture
	 * @param description - description of the picture
	 * @param tags - tags of the picture
	 */
	public Picture(String name, String description, Set<String> tags) {
		super();
		this.name = name;
		this.description = description;
		this.tags = tags;
	}

	/**
	 * Returns the name of the picture.
	 * @return the name of the picture
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the picture.
	 * @param name - name of the picture
	 */
	public void setName(String name) {
		this.name = name;
	}

	
	/**
	 * Returns the description of the picture.
	 * @return the description of the picture
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description of the picture.
	 * @param description - description of the picture
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	
	/**
	 * Returns the tags of the picture.
	 * @return the tags of the picture
	 */
	public Set<String> getTags() {
		return tags;
	}

	
	/**
	 * Sets the tags of the picture.
	 * @param tags - tags of the picture
	 */
	public void setTags(Set<String> tags) {
		this.tags = tags;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((tags == null) ? 0 : tags.hashCode());
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
		Picture other = (Picture) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (tags == null) {
			if (other.tags != null)
				return false;
		} else if (!tags.equals(other.tags))
			return false;
		return true;
	}
	
}
