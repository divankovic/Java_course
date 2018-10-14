package hr.fer.zemris.java.hw04.custom.collections;

import java.util.Objects;


/**
 * The class represents a simple map that memorises a single value
 * connected to a unique key.
 * Keys must not be null, but values can.
 * 
 * @author Dorian Ivankovic
 *
 */
public class Dictionary {
	
	/**
	 * Collection used to achieve the functionalities of the Dictionary.
	 */
	private ArrayIndexedCollection records;

	/**
	 * Default constructor for the Dictionary.
	 */
	public Dictionary() {
		this.records= new ArrayIndexedCollection();
	}
	
	
	/**
	 * Returns true if the <code>Dictionary</code> is empty and false otherwise.
	 * @return true if the <code>Dictionary</code> is empty and false otherwise
	 */
	public boolean isEmpty() {
		return records.isEmpty();
	}
	
	/**
	 * Returns the number of currently stored records in the dictionary
	 * e.g. size of the dictionary.
	 * @return size of the dictionary
	 */
	public int size() {
		return records.size();
	}
	
	
	/**
	 * Clears the <code>Dictionary</code>.
	 */
	public void clear() {
		records.clear();
	}
	
	
	/**
	 * Adds the new record into the <code>Dictionary</code>, if the record
	 * associated with <code>key</code> already exists, a new <code>value</code> is inserted
	 * in place of the old <code>value</code>.
	 * @param key - key of the new record to add
	 * @param value - value of the new record to add
	 * @throws NullPointerException if key is null
	 */
	public void put(Object key, Object value) {
		
		DictionaryRecord record = new DictionaryRecord(key, value);
		
		int index = records.indexOf(record);
		
		if(index!=-1) {
			DictionaryRecord existing = (DictionaryRecord) records.get(index);
			existing.setValue(value);
		}else {
			records.add(record);
		}
	
	}
	
	
	
	/**
	 * Returns the <code>value</code> associated with the <code>key</code>
	 * of the record, returns null if the record doesn't exist or the <code>value</code> is null.
	 * @param key - key to get the associated value from
	 * @return value associated with the key, or null if the record doesn't exist
	 */
	public Object get(Object key) {
		DictionaryRecord record = new DictionaryRecord(key);
		int index = records.indexOf(record);
		
		if(index==-1) {
			return null;
		}else {
			return ((DictionaryRecord)records.get(index)).getValue();
		}
	
	}
	
	
	/**
	 * Represent a single record of the <code>Dictionary</code>, containing a
	 * key and a value associated with that key.
	 * @author Dorian Ivankovic
	 *
	 */
	private static class DictionaryRecord{
		
		/**
		 * Key of the record.
		 */
		private Object key;
		
		/**
		 * Value of the record. Can be null.
		 */
		private Object value;
		
		
		/**
		 * Constructor for the record.
		 * @param key - key of the record
		 * @param value - value associated with the key of the record
		 * @throws NullPointerException if <code>key</code> is null
		 */
		public DictionaryRecord(Object key, Object value) {
			Objects.requireNonNull(key, "Key must not be null!");
			this.key = key;
			this.value = value;
		}
		
		/**
		 * Constructor using only key, value is set to null.
		 * @param key - key of the record
		 */
		public DictionaryRecord(Object key) {
			this(key,null);
		}

		/**
		 * Returns the <code>value</code> of the record.
		 * @return <code>value</code> of the record
		 */
		public Object getValue() {
			return value;
		}

		/**
		 * Sets the value of the record.
		 * @param value - new value of the record
		 */
		public void setValue(Object value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(key);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;

			if (!(obj instanceof DictionaryRecord))
				return false;
			
			DictionaryRecord other = (DictionaryRecord) obj;
			
			if (!key.equals(other.key))
				return false;
	
			return true;
		}
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
