package hr.fer.zemris.java.custom.collections.hw03;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/**
 * Resizeable array-backed collection of objects, storage of duplicate elements
 * is allowed, while storage of null elements is not allowed. The default
 * capacity is 16 if not said otherwise. It is possible to add new values,
 * remove values, get values at specific index, copy collection to an array,
 * clear collection, etc.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ArrayIndexedCollection extends Collection implements Iterable<Object>{

	private static final int MIN_SIZE = 1;
	public static final int DEFAULT_CAPACITY = 16;
	private static final int NOT_FOUND = -1;

	private int size;
	private Object[] elements;

	/**
	 * Default constructor, creates a new instance of the collection with capacity
	 * set to DEFAULT_CAPACITY.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor using the initial capacity of the ArrayIndexedCollection.
	 * 
	 * @param initialCapacity
	 *            - initial capacity of the collection
	 * @throws IllegalArgumentException
	 *             if initialCapacity is &lt; 1
	 */
	public ArrayIndexedCollection(int initialCapacity) {

		if (initialCapacity < MIN_SIZE) {
			throw new IllegalArgumentException("Capacity of the collection must be greater or equal than" + MIN_SIZE
					+ ", your requirement was " + initialCapacity + ".");
		}

		elements = new Object[initialCapacity];

	}

	/**
	 * Creates a new ArrayIndexedCollection and copies all the elements from
	 * Collection other into this newly constructed collection.
	 * 
	 * @param other
	 *            - collection to copy elements from
	 * @throws NullPointerException
	 *             - if other is null
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * Creates a new ArrayIndexedCollection and copies all the elements from
	 * Collection other into this newly constructed collection. The capacity of the
	 * collection is set to initialCapacity if it is larger than the size of the
	 * collection other, and set to size of the collection other otherwise.
	 * 
	 * @param other
	 *            - collection to copy elements from
	 * @param initialCapacity
	 *            - initial capacity of the collection, if &gt; other.size
	 * @throws NullPointerException
	 *             - if other is null
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {

		other = Objects.requireNonNull(other);
		
		if(other.isEmpty() && initialCapacity == 0) {
			initialCapacity = DEFAULT_CAPACITY;
		}else if (initialCapacity < other.size()) {
			initialCapacity = other.size();
		}

		elements = new Object[initialCapacity];
		
		this.addAll(other);

	}

	/**
	 * Returns the collection's size.
	 * 
	 * @return size of the collection
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Returns the value stored in collection at position index with
	 * average complexity O(1).
	 * @param index - index of the value
	 * @return value at the given index in the collection
	 * @throws IndexOutOfBoundsException if index is &lt; 0 or &gt; size-1
	 */
	public Object get(int index) {

		checkElementIndex(index);
		return elements[index];

	}
	

	/**
	 * Searches for the specified value in the collection and returns
	 * true if the collection contains the value and false otherwise.
	 * @param value - value to search for in the collection
	 * @return true is the value is found, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (this.indexOf(value) == NOT_FOUND) {
			return false;
		}
		return true;
	}

	
	/**
	 * Searches the collection and returns the index of the first occurrence of the 
	 * given value or -1 if the value is not found.
	 * The average complexity of this method is O(n/2).
	 * @param value - value to search for in the collection
	 * @return index of the first occurrence of the value, or -1 if the value is not found
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}

		return NOT_FOUND;
	}

	/**
	 * Adds the given object into the last position of this collection with
	 * complexity O(1).
	 * If the collection is full, it's first reallocated using the double size of the current collection.
	 * 
	 * @param value
	 *            - value to add
	 * @throws NullPointerException
	 *             if value is null
	 */
	@Override
	public void add(Object value) {
		this.insert(value, size);
	}

	/**
	 * Insert the value at the given position in the collection with average
	 * complexity O(n/2). The value at position before inserting is not replaced,
	 * all elements after position are shifted, therefore creating an empty place
	 * for the new value.
	 * If the collection is full, it's first reallocated using the double size of the current collection.
	 * 
	 * @param value
	 *            - value to add
	 * @param position
	 *            - position into which value is inserted
	 * @throws IndexOutOfBoundsException if position is &lt; 0 or &gt; size 
	 * 		   NullPointerException if value is null
	 */
	public void insert(Object value, int position) {
		checkPositionIndex(position);

		value = Objects.requireNonNull(value);

		size++;
		if (size > elements.length) {
			int capacity = elements.length*2;
			elements = Arrays.copyOf(elements, capacity);
		}

		// shifting the values in elements from position by 1 towards the end
		// therefore creating an empty place at position for the new value
		for (int i = size - 1; i > position; i--) {
			elements[i] = elements[i - 1];
		}

		elements[position] = value;

	}


	/**
	 * Removes the element at the specified index from the collection.
	 * Elements after index are shifted one place to the left, so the element
	 * that previously was on location index+1 is now on location index,...
	 * The average complexity of this method is O(n/2).
	 * @param index - index of the element to remove
	 * @throws IndexOutOfBoundsException if index is &lt; 0 or &gt; size-1
	 */
	public void remove(int index) {
		checkElementIndex(index);

		// removing the value at position index by shifting all values after index
		// by 1 to the left
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}

		elements[size - 1] = null;
		size--;

	}
	
	/**
	 * Removes the first occurrence of the given value from the collection, with
	 * average complexity O(n).
	 * @param value - value to remove from the collection
	 * @return true if the value is removed, false if the collection doesn't contain the value
	 */
	@Override
	public boolean remove(Object value) {
		int index = this.indexOf(value);

		if (index == NOT_FOUND) {
			return false;
		}

		remove(index);

		return true;

	}


	/**
	 * Converts the collection into an array of objects.
	 * @return array of objects from the collection
	 */
	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.process(elements[i]);
		}
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * The method checks if the given index is a valid index of some element in the collection.
	 */
	private void checkElementIndex(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
		}
	}

	/**
	 * The method checks if the given index is a valid position for adding in the collection.
	 */
	private void checkPositionIndex(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
		}
	}

	/**
	 * The method returns a String containing a message specifying the invalid index and the size
	 * of the array. 
	 */
	private String outOfBoundsMessage(int index) {
		return String.format("Invalid index : %d, size : %d.", index, size);
	}

	@Override
	public Iterator<Object> iterator() {
		return new ArrayIndexedCollectionIterator();
	}
	
	
	private class ArrayIndexedCollectionIterator implements Iterator<Object>{
		
		private int current;
		
		@Override
		public boolean hasNext() {
			if(current<size) {
				return true;
			}
			return false;
		}

		@Override
		public Object next() {
			if(hasNext()) {
				return elements[current++];
			}
			
			return null;
		}
		
	}

}
