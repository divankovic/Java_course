package hr.fer.zemris.java.hw06.observer2;

import hr.fer.zemris.java.hw06.observer2.IntegerStorage;
import hr.fer.zemris.java.hw06.observer2.IntegerStorageObserver;

/**
 * The program demonstrates the use of
 * <a href="https://en.wikipedia.org/wiki/Observer_pattern">Observer_pattern</a>
 * using {@link IntegerStorage} and {@link IntegerStorageObserver}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ObserverExample {
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		IntegerStorage istorage = new IntegerStorage(20);

		IntegerStorageObserver observer = new SquareValue();

		istorage.addObserver(observer);
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(1));
		istorage.addObserver(new DoubleValue(2));
		istorage.addObserver(new DoubleValue(2));

		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		istorage.removeObserver(observer);

		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);

	}
}
