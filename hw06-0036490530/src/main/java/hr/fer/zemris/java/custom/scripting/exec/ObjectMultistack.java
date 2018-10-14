package hr.fer.zemris.java.custom.scripting.exec;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Objects;

/**
 * A custom map like data structure which maps multiple elements to one key.
 * <br>Elements mapped to the same key are arranged in a stack. <br>
 * Keys for the <code>ObjectMultistack</code> are <code>String</code>'s and
 * values are {@link ValueWrapper}'s.
 * Neither keys nor vales stored can be null.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ObjectMultistack {

	/**
	 * Internal storage of <code>ObjectMultistack</code>. The map map a key(String)
	 * to {@link MultiStackEntry}.
	 */
	private HashMap<String, MultiStackEntry> map;

	/**
	 * Constructs a new <code>ObjectMultistack</code>
	 */
	public ObjectMultistack() {
		map = new HashMap<>();
	}

	/**
	 * Puts a new value - <code>valueWrapper</code> into the
	 * <code>ObjectMultistack</code>, and maps it to <code>name</code>. The new
	 * value is added to the top of the stack mapped to <code>name</code>.
	 * <br>The complexity of this method is O(1).
	 * 
	 * @param name - key for mapping
	 * @param valueWrapper - new value to add to <code>ObjectMultistack</code> to the top of
	 *            			the stack mapped to <code>name</code>
	 * @throws NullPointerException if <code>name</code> or <code>valueWrapper</code> is null.
	 */
	public void push(String name, ValueWrapper valueWrapper) {

		Objects.requireNonNull(name, "name must not be null");
		Objects.requireNonNull(valueWrapper, "valueWrapper must not be null");

		MultiStackEntry current = map.get(name);

		MultiStackEntry entry = new MultiStackEntry(valueWrapper, current);
		map.put(name, entry);
	}

	/**
	 * Returns the value at the top of the stack mapped to <code>name</code> and
	 * removes the value from the top of the stack.
	 * <br>The complexity of this method is O(1).
	 * @param name - key of the stack to pop the value from
	 * @return value at the top of the stack mapped to <code>name</code>
	 * @throws NullPointerException if <code>name</code> is null
	 * @throws EmptyStackException if the stack mapped to <code>name</code> is empty
	 */
	public ValueWrapper pop(String name) {
		ValueWrapper value = peek(name);
		map.put(name, map.get(name).next);
		return value;

	}

	/**
	 * Returns the value at the top of the stack mapped to <code>name</code>.
	 * <br>The complexity of this method is O(1).
	 * @param name - key of the stack to peek the value from
	 * @return value at the top of the stack mapped to <code>name</code>
	 * @throws NullPointerException if <code>name</code> is null
	 * @throws EmptyStackException if the stack mapped to <code>name</code> is empty
	 */
	public ValueWrapper peek(String name) {

		Objects.requireNonNull(name, "name must not be null");
		MultiStackEntry current = map.get(name);

		if (current == null)
			throw new EmptyStackException();
		return current.value;
	}

	/**
	 * Checks if the stack mapped to <code>name</code> is empty.
	 * 
	 * @param name - key of the stack to check if empty
	 * @return true if the stack mapped to <code>name</code> is empty or there is no
	 *         mapping for <code>name</code> in the <code>ObjectMultistack</code> ,
	 *         false otherwise
	 */
	public boolean isEmpty(String name) {
		return map.get(name) == null;
	}

	/**
	 * Represents a single entry of the <code>ObjectMultistack</code>.
	 * Each key in <code>ObjectMultiStack</code> is mapped to a single
	 * <code>MultiStackEntry</code> which stores a {@link ValueWrapper} value and
	 * has a reference to the next <code>MultiStackEntry</code>
	 * which enables the achievement of the virtual stack.
	 * @author Dorian Ivankovic
	 *
	 */
	private static class MultiStackEntry {

		/**
		 * Stored value.
		 */
		private ValueWrapper value;
		
		/**
		 * Next element in the list.
		 */
		private MultiStackEntry next;

		/**
		 * Constructs a new MultiStackEntry using its value and the next element in the list.
		 * @param value - value of the <code>MultiStackEntry</code>
		 * @param next - new <code>MultiStackEntry</code> in the list
		 */
		public MultiStackEntry(ValueWrapper value, MultiStackEntry next) {
			this.value = value;
			this.next = next;
		}

	}

}
