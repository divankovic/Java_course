package hr.fer.zemris.java.hw05.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A simple hash table which stores pairs(key,value). Each key must be unique,
 * therefore only one value is mapped to each key. In this hash table key's must
 * not be null, but values can. Pairs that are have the same hash
 * code%table_size fall into the same slot of the table so each slot has a list
 * of pairs. For optimising accessing complexity, the hash table's size is
 * doubled once 75% of the slots are full.
 * 
 * @author Dorian Ivankovic
 *
 * @param <K>
 *            - type of the key
 * @param <V>
 *            - type of the value
 */

public class SimpleHashTable<K, V> implements Iterable<SimpleHashTable.TableEntry<K, V>> {

	/**
	 * Table that represents slots of the table. Each slot contains the head of the
	 * linked list in that slot.
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Current size - number of stored pairs in the <code>SimpleHashTable</code>
	 */
	private int size;

	/**
	 * Used for determining whether or not the <code>SimpleHashTable</code>'s
	 * internal structure has been changed. Every time this happens, the value is
	 * increased by 1.
	 */
	private int modificationCount;

	/**
	 * Default capacity of the <code>SimpleHashMap</code>. If no specific capacity
	 * is given in {@link #SimpleHashTable(int capacity)}, then the capacity is set
	 * to this value.
	 */
	public static final int DEFAULT_CAPACITY = 16;

	/**
	 * Value used for determining whether or not the <code>SimpleHashTable</code>
	 * capacity needs to be increased. When the number of full slots/total slots is
	 * greater than this value, than the capacity is increased.
	 */
	public static final double MAX_FILL = 0.75;

	/**
	 * A single pair stored in {@link SimpleHashTable}.
	 * 
	 * @author Dorian Ivankovic
	 *
	 * @param <K>
	 *            - type of the key
	 * @param <V>
	 *            - type of the value
	 */
	public static class TableEntry<K, V> {

		/**
		 * Key of the pair, can't be null.
		 */
		private K key;

		/**
		 * Value of the pair, can be null.
		 */
		private V value;

		/**
		 * A reference to the next pair in the same slot of the {@link SimpleHashTable}.
		 */
		private TableEntry<K, V> next;

		/**
		 * Constructor for <code>TableEntry</code> using key and value of the new
		 * <code>TableEntry</code>.
		 * 
		 * @param key
		 *            - key of the new <code>TableEntry</code>
		 * @param value
		 *            - value of the new <code>TableEntry</code>
		 * @throws NullPointerException
		 *             if key is null
		 */
		public TableEntry(K key, V value) {
			Objects.requireNonNull(key, "Key must not be null.");
			this.key = key;
			this.value = value;
		}

		/**
		 * Returns the key of this <code>TableEntry</code>.
		 * 
		 * @return key of this <code>TableEntry</code>
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Sets the value of this <code>TableEntry</code>.
		 * 
		 * @param value
		 *            - new value of this <code>TableEntry</code>
		 */
		public void setValue(V value) {
			this.value = value;
		}

		/**
		 * Returns the value of this <code>TableEntry</code>.
		 * 
		 * @return value of this <code>TableEntry</code>
		 */
		public V getValue() {
			return value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(key, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;

			if (!(obj instanceof SimpleHashTable.TableEntry<?, ?>))
				return false;

			TableEntry<K, V> other = (TableEntry<K, V>) obj;

			if (!key.equals(other.key))
				return false;

			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;

			return true;
		}

		@Override
		public String toString() {
			return key.toString() + " = " + value.toString();
		}

	}

	/**
	 * Default no argument constructor for <code>SimpleHashTable</code>. The
	 * capacity is set to <code>DEFAULT_CAPACITY</code>.
	 */
	public SimpleHashTable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor for <code>SimpleHashTable</code> using initalCapacity.<br>
	 * The capacity of the <code>SimpleHashTable</code> is set to the closest power
	 * of 2 bigger than the <code>initialCapacity</code>. So if initialCapacity is
	 * 30, the capacity is set to 32.
	 * 
	 * @param initialCapacity
	 *            - initial capacity of the <code>SimpleHashTable</code>
	 * @throws IllegalArgumentException
	 *             if <code>initialCapacity</code> is less than 1
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Inital capacity must be greater than 0, was " + initialCapacity + " .");
		}

		initialCapacity = nextPower2(initialCapacity);
		table = (TableEntry<K, V>[]) new TableEntry[initialCapacity];

	}

	/**
	 * Calculates the closest power 2 number that is bigger than
	 * <code>capacity</code>.
	 * 
	 * @param capacity
	 *            - initial capacity
	 * @return the closest power 2 number that is bigger than <code>capacity</code>
	 */
	private int nextPower2(int capacity) {
		int cap = 1;
		while (cap < capacity) {
			cap *= 2;
		}
		return cap;
	}

	/**
	 * Returns the current number of stored pairs - the size of the
	 * <code>SimpleHashTable</code>.
	 * 
	 * @return current number of stored pairs in the <code>SimpleHashTable</code>
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks whether or not the <code>SimpleHashTable</code> is empty.
	 * 
	 * @return true is the <code>SimpleHashTable</code> is empty, false otherwise
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the current capacity of the <code>SimpleHashTable</code>.
	 * 
	 * @return current capacity of the <code>SimpleHashTable</code>
	 */
	public int getCapacity() {
		return table.length;
	}

	/**
	 * Checks if the <code>SimpleHashTable</code> contains a {@link TableEntry} with
	 * the specified key.
	 * 
	 * @param key
	 *            - key to find in the <code>SimpleHashTable</code>
	 * @return true if the <code>SimpleHashTable</code> contains the key, false
	 *         otherwise
	 */
	public boolean containsKey(Object key) {
		int hash = hash(key);
		if (getEntry(hash, key) != null)
			return true;
		return false;
	}

	/**
	 * Checks if the <code>SimpleHashTable</code> contains a {@link TableEntry} with
	 * the specified value.<br>
	 * The complexity of this method is O(n).
	 * 
	 * @param value
	 *            - value to find in the <code>SimpleHashTable</code>
	 * @return true if the <code>SimpleHashTable</code> contains the value, false
	 *         otherwise
	 */
	public boolean containsValue(Object value) {

		for (TableEntry<K, V> tableEntry : this) {
			if ((tableEntry.value == null && value == null) || tableEntry.value.equals(value)) {
				return true;
			}
		}

		return false;

	}

	/**
	 * Puts a new pair(key, value) as a {@link TableEntry} in the
	 * <code>SimpleHashTable</code>.<br>
	 * If the pair with the specified key already exists in the
	 * <code>SimpleHashTable</code>, then the value of the pair is updated to the
	 * new value. If the pair with the specified key doesn't exist in the
	 * <code>SimpleHashTable</code>, then the pair is added to the end of the list
	 * in the specific slot of the <code>SimpleHashTable</code>.<br>
	 * The slot of the table is calculated using {@link #hash(Object)}.<br>
	 * When the size/slots exceeds <code>MAX_FILL</code>, the capacity of the table
	 * is increased using {@link #increaseCapacity()}
	 * 
	 * @param key
	 *            - key of the new pair to store
	 * @param value
	 *            - value of the new pair to store
	 * @throws NullPointerException
	 *             if key is null
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key, "Key must not be null!");

		int hash = hash(key);
		TableEntry<K, V> entry = getEntry(hash, key);

		if (entry != null) {
			entry.setValue(value);
		} else {
			TableEntry<K, V> tableEntry = table[hash];

			// first entry in the slot

			if (tableEntry == null) {
				table[hash] = new TableEntry<K, V>(key, value);
			} else {
				// adding to the end of the list in the slot
				while (tableEntry.next != null) {
					tableEntry = tableEntry.next;
				}
				tableEntry.next = new TableEntry<K, V>(key, value);
			}
			size++;
			if (size / (double) table.length >= MAX_FILL) {
				increaseCapacity();
			}
			modificationCount++;
		}
	}

	/**
	 * Increases the <code>SimpleHashTable</code>'s capacity by doubling it. All
	 * <code>TableEntry</code>es are cleared from the <code>SimpleHashTable</code>
	 * and then added in the <code>SimpleHashTable</code> with double capacity using
	 * method {@link #put(Object, Object)}
	 */
	@SuppressWarnings("unchecked")
	private void increaseCapacity() {
		TableEntry<K, V>[] entries = (TableEntry<K, V>[]) new TableEntry[2 * table.length];

		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				
				int hash = Math.abs(entry.getKey().hashCode()) % entries.length;
				TableEntry<K, V> currentEntry = entries[hash];
				
				TableEntry<K,V> next = entry.next;
				entry.next = null;
				
				if (currentEntry == null) {
					entries[hash] = entry;
				} else {
					while (currentEntry.next != null) {
						currentEntry = currentEntry.next;
					}
					currentEntry.next = entry;
				}
				
				entry = next;
			}
		}
		
		this.table = entries;

		modificationCount++;
	}

	/**
	 * Removes all <code>TableEntry</code>es from the <code>SimpleHashTable</code>.
	 */
	public void clear() {

		for (int i = 0; i < table.length; i++) {
			table[i] = null;
		}

		size = 0;

		modificationCount++;
	}

	/**
	 * Returns the value associated with the key stored in the
	 * <code>SimpleHashTable</code>.<br>
	 * If the key doesn't exist in the <code>SimpleHashTable</code>
	 * <code>null</code> is returned. If the method returned <code>null</code> that
	 * doesn't mean that the key doesn't exist in the <code>SimpleHashTable</code>,
	 * <code>null</code> can also be mapped to the specified key since values of the
	 * {@link TableEntry} can be <code>null</code>.
	 * 
	 * @param key
	 *            - key of the <code>TableEntry</code> stored.
	 * @return value associated with the key stored in the
	 *         <code>SimpleHashTable</code>
	 */
	public V get(Object key) {
		int hash = hash(key);
		TableEntry<K, V> entry = getEntry(hash, key);

		if (entry != null) {
			return entry.getValue();
		}

		return null;

	}

	/**
	 * Removes the {@link TableEntry} with the specified key from
	 * <code>SimpleHashTable</code>, does nothing if the <code>TableEntry</code>
	 * with the specified key does not exist in the <code>SimpleHashTable</code>.
	 * 
	 * @param key
	 *            - key of the <code>TableEntry</code> to remove
	 */
	public void remove(Object key) {
		int hash = hash(key);

		TableEntry<K, V> entry = table[hash];

		if (entry == null)
			return;

		// entry with key is first in table slot
		if (entry.key.equals(key)) {
			table[hash] = entry.next;
		} else {
			while (entry.next != null) {
				if (entry.next.key.equals(key)) {
					break;
				}
				entry = entry.next;
			}

			if (entry.next == null)
				return;
			entry.next = entry.next.next;

		}

		size--;
		modificationCount++;
	}

	/**
	 * Calculates the hash of the <code>key</code> - table index using
	 * absolute_value(key.hash())%capacity of the table of
	 * <code>SimpleHashTable</code>.
	 * 
	 * @param key
	 *            - key to calculate hash from
	 * @return table slot of the {@link TableEntry} with the specified key
	 */
	private int hash(Object key) {
		if (key == null)
			return 0;
		return Math.abs(key.hashCode()) % table.length;
	}

	/**
	 * Returns the entry stored in the list at the position <code>hash</code> in the
	 * <code>table</code>.
	 * 
	 * @param hash
	 *            - index of the slot in the <code>table</code>, calculated using @
	 * @param key
	 *            - key to find
	 * @return <code>TableEntry</code> with the specified key
	 */
	private TableEntry<K, V> getEntry(int hash, Object key) {

		TableEntry<K, V> entry = table[hash];

		while (entry != null) {
			if (entry.key.equals(key)) {
				return entry;
			}
			entry = entry.next;
		}

		return null;
	}

	@Override
	public String toString() {

		StringBuilder pairs = new StringBuilder();

		for (TableEntry<K, V> entry : this) {
			pairs.append(entry.toString()).append(", ");
		}

		return "[" + pairs.toString().substring(0, pairs.length() - 2) + "]";
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new SimpleHashTableIterator();
	}

	/**
	 * Iterator used for iterating over {@link TableEntry}es of the
	 * <code>SimpleHashTable</code>.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	private class SimpleHashTableIterator implements Iterator<TableEntry<K, V>> {

		/**
		 * Index of the current slot in the table of the <code>SimpleHashTable</code>.
		 */
		private int index;

		/**
		 * Current <code>TableEntry</code>.
		 */
		private TableEntry<K, V> current;

		/**
		 * Used to limit {@link #remove()} to be called only one time after
		 * {@link #next()} has been called.
		 */
		private TableEntry<K, V> lastReturned;

		/**
		 * Used to track the number of returned elements.
		 */
		private int count;

		/**
		 * Used to determine whether or not the <code>SimpleHashTable</code> has been
		 * modified during iteration. If it has, {@link ConcurrentModificationException}
		 * is thrown.
		 */
		private int modificationCount;

		/**
		 * Default constructor for the <code>SimpleHashTableIterator</code>.
		 */
		public SimpleHashTableIterator() {
			this.modificationCount = SimpleHashTable.this.modificationCount;
			index = -1;
			count = 0;
		}

		/**
		 * Returns true if the iterator has the next <code>TableEntry</code>, false
		 * otherwise.
		 * 
		 * @return true if the iterator has the next <code>TableEntry</code>, false
		 *         otherwise
		 * @throws ConcurrentModificationException
		 *             if the <code>SimpleHashTable</code> has been modified during
		 *             iteration.
		 */
		@Override
		public boolean hasNext() {
			checkModificationCount();
			return count < size;
		}

		/**
		 * Returns the next <code>TableEntry</code> in the iteration.
		 * 
		 * @return next <code>TableEntry</code> in the iteration
		 * @throws ConcurrentModificationException
		 *             if the <code>SimpleHashTable</code> has been modified during
		 *             iteration.
		 * @throws NoSuchElementException
		 *             if there are no more elements, determined by {@link #hasNext()}
		 */
		@Override
		public TableEntry<K, V> next() {

			checkModificationCount();

			if (!hasNext())
				throw new NoSuchElementException("There are no more elements");

			if (current == null) {
				findNext();
			} else {
				current = current.next;
				if (current == null) {
					findNext();
				}
			}

			count++;
			lastReturned = current;
			return current;
		}

		/**
		 * Removes the last returns <code>TableEntry</code> from the
		 * <code>SimpleHashTable</code>. Can only be called once after {@link #next()}
		 * has been called
		 * 
		 * @throws ConcurrentModificationException
		 *             if the <code>SimpleHashTable</code> has been modified during
		 *             iteration.
		 * @throws IllegalStateException
		 *             if called more than once after {@link #next()} has been called
		 */
		public void remove() {
			checkModificationCount();

			if (lastReturned == null)
				throw new IllegalStateException("next method has not yet been called.");

			SimpleHashTable.this.remove(current.key);
			modificationCount = SimpleHashTable.this.modificationCount;
			count--;
			lastReturned = null;
		}

		/**
		 * Finds the next <code>TableEntry</code> in the <code>table</code> slot.
		 */
		private void findNext() {
			while (true) {
				if (current == null) {
					current = table[++index];
				} else {
					break;
				}
			}
		}

		/**
		 * Checks if the <code>SimpleHashTable</code> has been modified during
		 * iteration.
		 * 
		 * @throws ConcurrentModificationException
		 *             if the <code>SimpleHashTable</code> has been modified during
		 *             iteration
		 */
		private void checkModificationCount() {
			if (modificationCount != SimpleHashTable.this.modificationCount) {
				throw new ConcurrentModificationException("SimpleHashTable has changed during iteration.");
			}
		}
	}
}
