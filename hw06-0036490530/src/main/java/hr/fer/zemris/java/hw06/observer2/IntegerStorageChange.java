package hr.fer.zemris.java.hw06.observer2;

import java.util.Objects;

/**
 * Used to encapsulate the {@link IntegerStorage} containing the new state, and
 * the previous state of the subject, providing more detailed information to the observers.
 * @author Dorian Ivankovic
 *
 */
public class IntegerStorageChange {
	
	/**
	 * Monitored <code>IntegerStorage</code>
	 */
	private IntegerStorage storage;
	
	/**
	 * Last value stored in <code>IntegerStorage</code> before the value change.
	 */
	private int lastValue;
	
	/**
	 * Constructs a new <code>IntegerStorageChange</code> using monitored {@link IntegerStorage}
	 * and the last value stored before the change.
	 * @param storage - monitored <code>IntegerStorage</code>
	 * @param lastValue - last value stored in subject before the change
	 * @throws NullPointerException if storage is null
	 */
	public IntegerStorageChange(IntegerStorage storage, int lastValue) {
		Objects.requireNonNull(storage, "storage must not be null.");
		this.storage = storage;
		this.lastValue = lastValue;
	}
	
	/**
	 * Returns the monitored {@link IntegerStorage}.
	 * @return monitored {@link IntegerStorage}
	 */
	public IntegerStorage getStorage() {
		return storage;
	}
	
	/**
	 * Returns the last value that was stored in {@link IntegerStorage} before the value change.
	 * @return last value stored in <code>IntegerStorage</code>
	 */
	public int getLastValue() {
		return lastValue;
	}
	
	/**
	 * Returns the value stored in {@link IntegerStorage}.
	 * @return value stored in <code>IntegerStorage</code>
	 */
	public int getValue() {
		return storage.getValue();
	}
	
	/**
	 * Removes the observer from the {@link IntegerStorage}.
	 * @param observer - observer to remove from <code>IntegerStorage</code>
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		storage.removeObserver(observer);
	}
	
	
	
}
