package hr.fer.zemris.java.custom.collections.hw03;


/**
 * The class represents a general collection of objects. You can find out
 * collection's size, clear it, find out if it contains a certain value as well
 * as add and remove values from the collection etc.
 * 
 * @author Dorian Ivankovic
 *
 */
public class Collection {

	/**
	 * Default constructor for the Collection.
	 */
	protected Collection() {

	}

	/**
	 * The method checks if the collection is empty.
	 * 
	 * @return true if the collection is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (this.size() == 0) {
			return true;
		}

		return false;
	}

	/**
	 * Returns the number of values stored in the collection.
	 * 
	 * @return number of values in the collection
	 */
	public int size() {
		return 0;
	}

	/**
	 * Adds the given object into the collection.
	 * 
	 * @param value
	 *            - value to add
	 */
	public void add(Object value) {

	}

	/**
	 * The method checks if the collection contains a certain value.
	 * 
	 * @param value
	 *            - value to check
	 * @return true if the collection contains the value, false otherwise
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * Removes one occurence of the value in the collection, if it exists in the
	 * collection.
	 * 
	 * @param value
	 *            - value to remove from the collection
	 * @return true if the value is removed, false if the collection doesn't contain
	 *         the value
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * Copies the content of the collection in an array with the size of the
	 * collection.
	 * 
	 * @return array of values in the collection
	 * @throws UnsupportedOperationException always
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method processes all elements in the collection using processor.
	 * 
	 * @param processor
	 *            - method .process used to process all elements in the collection
	 */
	public void forEach(Processor processor) {

	}

	/**
	 * The method adds all elements from the given collection into the current
	 * collection, while the given collection remains unchanged.
	 * 
	 * @param other
	 *            - other collection from which the method adds all elements to the current collection
	 * @throws NullPointerException if other is null
	 */
	public void addAll(Collection other) {

		class InternalProcessor extends Processor {

			@Override
			public void process(Object value) {
				Collection.this.add(value);
			}

		}
		
		other.forEach(new InternalProcessor());

	}

	/**
	 * Removes all elements from the collection.
	 */
	public void clear() {

	}

}
