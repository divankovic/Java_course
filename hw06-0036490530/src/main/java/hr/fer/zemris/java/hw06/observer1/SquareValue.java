package hr.fer.zemris.java.hw06.observer1;

/**
 * {@link IntegerStorageObserver} that prints out the square of the value stored
 * in {@link IntegerStorage} to the standard output.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SquareValue implements IntegerStorageObserver {

	/**
	 * Prints out to the standard output the square of the value stored in istorage.
	 * @param istorage - monitored <code>IntegerStorage</code>
	 */
	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.println("Provided new value: " + value + ", square is " + value * value);
	}

}
