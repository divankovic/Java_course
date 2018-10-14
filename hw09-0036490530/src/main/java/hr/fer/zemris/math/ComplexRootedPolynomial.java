package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A polynom on complex numbers in form (z-z1)*(z-z2)*(z-z3)*...*(z-zn). <br>
 * z1,z2,...,zn are polynom's complex roots.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ComplexRootedPolynomial {

	/**
	 * Roots of the complex polynom.
	 */
	private List<Complex> roots;

	/**
	 * Constructs a new <code>ComplexRootedPolynomial</code> using it's roots.
	 * 
	 * @param roots
	 *            - roots of the complex polynom
	 * @throws NullPointerException
	 *             if roots is null
	 */
	public ComplexRootedPolynomial(Collection<Complex> roots) {
		Objects.requireNonNull(roots, "Polynom roots must not be null");
		this.roots = new ArrayList<>(roots);
	}

	/**
	 * Returns the order of the polynom.
	 * @return order of the polynom
	 */
	public int order() {
		return roots.size();
	}
	
	/**
	 * Calculates the value of the polynom in z.
	 * 
	 * @param z
	 *            - complex number to determine value of polynom
	 * @return - value of polynom in z
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Complex number must not be null.");

		Complex result = Complex.ONE;

		for (Complex root : roots) {
			result = result.multiply(z.sub(root));
		}

		return result;
	}

	/**
	 * Converts this polynom into {@link ComplexPolynomial}. The conversion is done
	 * by "multiplying" all brackets from the original form of the polynom.
	 * 
	 * @return {@link ComplexPolynomial} form of this polynom
	 */
	public ComplexPolynomial toComplexPolynom() {
		List<Complex> factors = new ArrayList<>();

		factors.add(Complex.ONE);

		for (int k = 1, n = roots.size(); k <= n; k++) {
			Complex factor = Complex.ZERO;
			int base = k % 2 == 0 ? 1 : -1;
			PermutationIterator iterator = new PermutationIterator(n, k);
			
			while (true) {
				int[] indexes = iterator.nextPermutation();
				if (indexes == null)
					break;
				Complex element = getElement(indexes);
				factor = factor.add(base == 1 ? element : element.negate());
			}
			
			factors.add(factor);
		}

		return new ComplexPolynomial(factors);
	}

	/**
	 * Returns the product of roots at indexes.
	 * 
	 * @param indexes
	 *            - indexes of roots to multiply
	 * @return product of roots at indexes
	 */
	private Complex getElement(int[] indexes) {
		Complex element = Complex.ONE;
		
		for (int index : indexes)
			element = element.multiply(roots.get(index));
		
		return element;
	}

	@Override
	public String toString() {
		StringBuilder rootedPolBuilder = new StringBuilder();
		
		for (Complex root : roots) {
			rootedPolBuilder.append("(z-(").append(root).append("))");
		}
		
		return rootedPolBuilder.toString();
	}

	/**
	 * Finds index of the closest root for given complex number z that is within
	 * threshold, and if there is no such root returns -1.
	 * 
	 * @param z
	 *            - complex number to find closest root from
	 * @param threshold
	 *            - minimum distance between z and the root
	 * @return index of the closest root within threshold, or -1 if there is no such
	 *         root
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		int index = -1;
		double closest = threshold;
		
		for (int i = 0, n = roots.size(); i < n; i++) {
			double distance = roots.get(i).sub(z).module();
		
			if (distance < closest) {
				index = i;
				closest = distance;
			}
			
		}
		
		return index;
	}

	/**
	 * Iterator used to produce
	 * <a href="https://en.wikipedia.org/wiki/Binomial_coefficient">C(n,k)</a>
	 * combinations of k elements withing an array of size n.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	protected static class PermutationIterator {

		/**
		 * Current indexes of elements.
		 */
		private int[] indexes;

		/**
		 * Total number of elements.
		 */
		private int n;

		/**
		 * Number of selected elements.
		 */
		private int k;

		/**
		 * Constructs a new <code>PermutationIterator</code> given the size of elements
		 * n and the number of elements to select -k.
		 * 
		 * @param n
		 *            - number of elements
		 * @param k
		 *            - number of elements to select
		 */
		public PermutationIterator(int n, int k) {
			this.n = n;
			this.k = k;
		}

		/**
		 * Returns the next permutation of k elements from all elements.
		 * 
		 * @return next permutation
		 */
		public int[] nextPermutation() {
			if (indexes == null) {
				indexes = new int[k];
				set(0, 0);
			} else {
				indexes[k - 1]++;
				if (indexes[k - 1] == n) {	
					int from = k - 2;
					if (from < 0) return null;
					
					int initial = ++indexes[from];
					
					while (initial == n || initial + (k - from - 1) >= n) {
						from--;
						if (from < 0) return null;
						initial = ++indexes[from];
					}

					set(from, initial);
				}
			}
			return indexes;
		}

		/**
		 * Sets the appropriate index values, value at position from is set to initial,
		 * value at position from+1 is set to initial+1,...
		 * 
		 * @param from
		 *            - begin index
		 * @param initial
		 *            - inital value at begin index
		 */
		private void set(int from, int initial) {

			for (int i = from; i < indexes.length; i++) {
				indexes[i] = initial++;
			}

		}
	}

}
