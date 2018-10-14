package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * The class represent a basic stack implementation with methods push, pop, peek, clear etc.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ObjectStack {
	
	/**
	 * Stack is implemented using this collection.
	 */
	private ArrayIndexedCollection collection;
	
	/**
	 * Default constructor for the stack.
	 */
	public ObjectStack() {
		collection = new ArrayIndexedCollection();
	}

	
	/**
	 * Checks if the stack if empty.
	 * @return true if the stack is empty, false otherwise
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}
	
	/**
	 * Returns the current size of the stack.
	 * @return the current size of the stack
	 */
	public int size() {
		return collection.size();
	}
	
	/**
	 * Pushes a given value to the stack.
	 * @param value - value to push to stack
	 * @throws NullPointerException if value is null
	 */
	public void push(Object value) {
		value = Objects.requireNonNull(value);
		collection.add(value);
	}
	
	/**
	 * Removes the last value that was pushed on the stack and returns it.
	 * @return value at the top of the stack
	 * @throws EmptyStackException if the stack is already empty
	 */
	public Object pop() {

		Object value = this.peek();
		collection.remove(value);
		
		return value;
	}
	
	/**
	 * Returns the last element that was pushed on the stack but does not delete it 
	 * from the stack.
	 * @return value at the top of the stack
	 * @throws EmptyStackException if the stack is empty
	 */
	public Object peek() {
		if(this.isEmpty()) {
			throw new EmptyStackException();
		}
		
		Object value = collection.get(collection.size()-1);
		return value;
	}
	
	/**
	 * Removes all elements from the stack.
	 */
	public void clear() {
		collection.clear();
	}
	
}
