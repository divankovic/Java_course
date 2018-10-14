package hr.fer.zemris.java.hw06.observer2;

import hr.fer.zemris.java.hw06.observer2.IntegerStorage;

/**
* Observer interface, all observers interested in {@link IntegerStorage}
* must implement this interface.
* @author Dorian Ivankovic
*
*/
public interface IntegerStorageObserver {
	
	/**
	 * When the value stored in istorage changes, all observers
	 * interested are notified using this method.
	 * @param  istorage - <code>IntegerStorageChange</code> that holds the subject and the
	 * 						previous value of the subject
	 */
	public void valueChanged(IntegerStorageChange istorage);
}
