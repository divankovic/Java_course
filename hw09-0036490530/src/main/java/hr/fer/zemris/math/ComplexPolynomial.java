package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * A complex number polynom in form zn*z^n + zn-1*z^(n-1)+...+z1*z + z0. <br>
 * z0,z1,...zn are coeficients of the polynom.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ComplexPolynomial {

	/**
	 * Factors of the polynom.
	 */
	private List<Complex> factors;

	/**
	 * Constructs a new <code>ComplexPolynomial</code> using it's factors.
	 * 
	 * @param factors
	 *            - factors of the polynomial
	 */
	public ComplexPolynomial(Collection<Complex> factors) {
		this.factors = new ArrayList<>(factors);
	}

	/**
	 * Returns the order of the polynomial.
	 * 
	 * @return order of the polynomial
	 */
	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Multiplies this polynom with the given polynom.
	 * 
	 * @param p
	 *            - polynom to multiply with
	 * @return product of this and given polynom
	 * @throws NullPointerException
	 *             if p is null
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {

		Objects.requireNonNull(p, "Complex polynomial must not be null.");

		int n = factors.size() - 1;
		int m = p.factors.size() - 1;
		int degree = n + m;

		List<Complex> mulFactors = new ArrayList<>();

		for (int i = 0; i <= degree; i++) {
			Complex factor = Complex.ZERO;
			for (int j = 0; j <= i; j++) {
				if (j > n || (i - j) > m)
					continue;
				factor = factor.add(factors.get(j).multiply(p.factors.get(i - j)));
			}
			mulFactors.add(factor);
		}

		return new ComplexPolynomial(mulFactors);
	}

	/**
	 * Returns the first derivation of this polynom.
	 * 
	 * @return the derivation of this polynom.
	 */
	public ComplexPolynomial derive() {
		List<Complex> factorsDerived = new ArrayList<>();

		for (int i = 0, n = factors.size(); i < n - 1; i++) {
			factorsDerived.add(factors.get(i).multiply(new Complex(n - 1 - i, 0)));
		}
		return new ComplexPolynomial(factorsDerived);
	}

	/**
	 * Computes polynomial value at given point z.
	 * 
	 * @param z
	 *            - point to calculate value of the polynom.
	 * @return value of the polynom in point z.
	 * @throws NullPointerException
	 *             if z is null
	 */
	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "Complex number must not be null.");

		Complex result = Complex.ZERO;

		for (int i = 0, n = factors.size(); i < n; i++) {
			result = result.add(factors.get(i).multiply(z.power(n - 1 - i)));
		}

		return result;
	}

	@Override
	public String toString() {
		StringBuilder polBuilder = new StringBuilder();
		for (int i = 0, n = factors.size(); i < n; i++) {
			polBuilder.append("(").append(factors.get(i)).append(")");
			if (i != n - 1)
				polBuilder.append("z^").append(n - i - 1).append("+");
		}
		return polBuilder.toString();
	}

}
