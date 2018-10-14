package hr.fer.zemris.java.hw06.demo2;

import java.util.Iterator;

/**
 * The class is used to generate first n
 * <a href="https://en.wikipedia.org/wiki/Prime_number">prime numbers</a>. A new
 * prime number is generated only when requested in {@link Iterator#next}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Number of prime numbers to generate.
	 */
	private int n;

	/**
	 * Constructs a new <code>PrimesCollection</code> using the number of prime
	 * number to generate.
	 * 
	 * @param n - number of prime numbers to generate
	 */
	public PrimesCollection(int n) {
		this.n = n;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesCollectionIterator();
	}

	
	/**
	 * {@link Iterator} for this <code>PrimesCollection</code>.
	 * @author Dorian Ivankovic
	 *
	 */
	private class PrimesCollectionIterator implements Iterator<Integer> {

		/**
		 * Number of prime numbers left to generate.
		 */
		private int count;
		
		/**
		 * Last generated prime number.
		 */
		private int lastPrime;

		/**
		 * Constructs a new <code>PrimesCollectionIterator</code>
		 * and set count - number of primes to generate to n.
		 */
		public PrimesCollectionIterator() {
			this.count = n;
		}

		@Override
		public boolean hasNext() {
			return count > 0;
		}

		@Override
		public Integer next() {

			if (--count < 0)
				throw new IllegalStateException("No more elements");

			if (lastPrime == 2)
				return ++lastPrime;

			do {
				lastPrime += 2;
			} while (!isPrime(lastPrime));

			return lastPrime;
		}

		/**
		 * The method checks if the lastPrime number is prime.
		 * @param lastPrime - number to check if prime
		 * @return true if lastPrime is prime, false otherwise.
		 */
		private boolean isPrime(int lastPrime) {

			for (int i = 3; i * i <= lastPrime; i += 2) {
				if (lastPrime % i == 0)
					return false;
			}

			return true;

		}

	}

}
