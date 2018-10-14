package hr.fer.zemris.java.hw06.demo2;

/**
 * The program demonstrates the use of {@link PrimesCollection}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class PrimesDemo2 {
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		PrimesCollection primesCollection = new PrimesCollection(5);
		for (Integer prime : primesCollection) {
			for (Integer prime2 : primesCollection) {
				System.out.println("Got prime pair: " + prime + ", " + prime2);
			}
		}
	}
}
