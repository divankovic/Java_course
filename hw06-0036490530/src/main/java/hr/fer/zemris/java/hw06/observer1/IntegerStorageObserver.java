package hr.fer.zemris.java.hw06.observer1;

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
	 * @param istorage - monitored <code>IntegerStorage</code>
	 */
	public void valueChanged(IntegerStorage istorage);
}
