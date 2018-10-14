package hr.fer.zemris.java.hw06.observer2;

import hr.fer.zemris.java.hw06.observer2.IntegerStorageObserver;

/**
 * {@link IntegerStorageObserver} that writes to the standard output
 * double value (value*2) of the current value which is stored  in subject,
 * but only the first n times since its registration with the subject.
 * After writing the double value for the n-th time, the observer automatically
 * de-registers itself from the subject.
 * @author Dorian Ivankovic
 *
 */
public class DoubleValue implements IntegerStorageObserver {
	
	/**
	 * Counter for determining how many more times the calculation is needed.
	 */
	private int n;
	
	/**
	 * Constructs a new <code>DoubleValue</code> using the number
	 * of times the observer will react to value changed in the subject.
	 * @param n - number of times to react to the value change in the subject
	 */
	public DoubleValue(int n) {
		this.n = n;
	}
	
	/**
	 * Prints out to the standard output value stored in subject *2,
	 * and if n - number of times to react to value change in subject comes
	 * down to 0, then the observer deregisters itself from the subject.
	 * @param istorage - <code>IntegerStorageChange</code> that holds the subject and the
	 * 						previous value of the subject
	 */
	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println("Double value: "+2*istorage.getValue());
		n--;
		if(n==0) {
			istorage.removeObserver(this);
		}
	}

}
