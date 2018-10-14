package hr.fer.zemris.java.hw06.observer1;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a subject which holds the integer value and when it changes
 * notifies all observers interested in the value using method
 * {@link IntegerStorageObserver#valueChanged(IntegerStorage)}. It is possible
 * to add and remove observers, as well as clear - remove them all.
 * 
 * @author Dorian Ivankovic
 *
 */
public class IntegerStorage {

	/**
	 * Currently stored integer value.
	 */
	private int value;

	/**
	 * Observers that need to be notified when value changes.
	 */
	private List<IntegerStorageObserver> observers;

	/**
	 * Constructs a new <code>IntegerStorage</code> using it's initial value.
	 * 
	 * @param initialValue - initial value of the <code>IntegerStorage</code>
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds a new observer that need to be notified every time the value stored
	 * changes.
	 * 
	 * @param observer - new observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (observers == null)
			observers = new ArrayList<>();
		else if (observers.contains(observer))
			return;
		observers.add(observer);
	}

	/**
	 * Removes the specific observer from the list of observers interested in the
	 * value stored.
	 * 
	 * @param observer  - observer to remove
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		if (observers == null)
			return;
		observers.remove(observer);
	}

	/**
	 * Removes all observers interested in this <code>IntegerStorage</code>.
	 */
	public void clearObservers() {
		if (observers == null)
			return;
		observers.clear();
	}

	/**
	 * Returns the current stored value.
	 * 
	 * @return value currently stored
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the new value and notifies all observers that the value has changed.
	 * 
	 * @param value  - new value to store
	 */
	public void setValue(int value) {

		if (this.value != value) {
			this.value = value;
			if (observers != null) {
				ArrayList<IntegerStorageObserver> observersCopy = new ArrayList<>(observers);
				for (IntegerStorageObserver observer : observersCopy) {
					observer.valueChanged(this);
				}
			}
		}

	}

}
