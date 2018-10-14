package hr.fer.zemris.math;

import hr.fer.zemris.math.ComplexRootedPolynomial.PermutationIterator;

/**
 * Demonstrates the use of {@link PermutationIterator}.
 * @author Dorian Ivankovic
 *
 */
public class PermutationIteratorDemo {
	
	/**
	 * This method is called once the program is run
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		PermutationIterator iterator = new ComplexRootedPolynomial.PermutationIterator(10,5);
		int count = 0;
		while(true) {
			int[] indexes = iterator.nextPermutation();
			if(indexes == null) break;
			count++;
			for(int idx:indexes)System.out.print(idx);
			System.out.print("\n");
		}
		System.out.println("Total: "+count);
	}
}
