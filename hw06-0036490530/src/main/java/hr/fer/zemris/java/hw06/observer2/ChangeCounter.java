package hr.fer.zemris.java.hw06.observer2;

import hr.fer.zemris.java.hw06.observer1.IntegerStorage;
import hr.fer.zemris.java.hw06.observer2.IntegerStorageObserver;

/**
 * {@link IntegerStorageObserver} that counts and writes to the standard output
 * the number of times the value stored has been changed since the registration.
 * @author Dorian Ivankovic
 *
 */
public class ChangeCounter implements IntegerStorageObserver {
	
	/**
	 * Counter for storing the number of times the value stored in {@link IntegerStorage}
	 * has been modified.
	 */
	private int counter;
	
	/**
	 * Increases the number of times the value stored in istorage has changed 
	 * and writes the counte to standar output.
	 * @param istorage - <code>IntegerStorageChange</code> that holds the subject and the
	 * 						previous value of the subject
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Number of value changes since tracking: " + ++counter);
	}

}
