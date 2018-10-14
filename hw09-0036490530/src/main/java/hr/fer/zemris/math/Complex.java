package hr.fer.zemris.math;

import static java.lang.Math.PI;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represent an unmodifiable complex number meaning that each operation on it
 * returns a new complex number.
 * 
 * @author Dorian Ivankovic
 *
 */
public class Complex {

	/**
	 * Complex number (0,0)
	 */
	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * Complex number (1,0)
	 */
	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Complex number (-1,0)
	 */
	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Complex number (0,1)
	 */
	public static final Complex IM = new Complex(0, 1);

	/**
	 * Complex number (0,-1)
	 */
	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Real part of the complex number.
	 */
	private double re;

	/**
	 * Imaginary part of the complex number.
	 */
	private double im;

	/**
	 * Default constructor for <code>Complex</code>, constructs a complex number
	 * with real and imaginary part set to 0.
	 */
	public Complex() {
		re = 0;
		im = 0;
	}

	/**
	 * Constructs a new complex number using it's real and imaginary part.
	 * 
	 * @param re
	 *            - real part of the complex number
	 * @param im
	 *            - imaginary part of the complex number
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Calculates the module of the complex number using formula sqrt(x^2 + y^2).
	 * 
	 * @return module of the complex nubmer
	 */
	public double module() {
		return Math.sqrt(re * re + im * im);
	}

	/**
	 * Multiplies this complex number with complex number <code>c</code> and returns
	 * a new complex number as a result.
	 * 
	 * @param c
	 *            - complex number to multiply with
	 * @return a new complex number that is a result of multiplication between
	 *         current complex number and complex number <code>c</code>
	 * @throws NullPointerException
	 *             if <code>c</code> is null
	 */
	public Complex multiply(Complex c) {
		Objects.requireNonNull(c, "Complex number must not be null.");
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	/**
	 * Divides this complex number with complex number <code>c</code> and returns a
	 * new complex number as a result.
	 * 
	 * @param c
	 *            - complex number to divide with
	 * @return a new complex number that is a result of division between current
	 *         complex number and complex number <code>c</code>
	 * @throws NullPointerException
	 *             if <code>c</code> is null
	 * @throws IllegalArgumentException
	 *             if module of c is 0
	 */
	public Complex divide(Complex c) {
		Objects.requireNonNull(c, "Complex number must not be null.");

		if (c.module() == 0)
			throw new IllegalArgumentException("Complex c must not be 0, can't divide with 0.");

		Complex nominator = multiply(new Complex(c.re, -1 * c.im));
		double denominator = c.re * c.re + c.im * c.im;

		return new Complex(nominator.re / denominator, nominator.im / denominator);

	}

	/**
	 * Adds complex number <code>c</code> to this complex number and returns a new
	 * complex number as a result.
	 * 
	 * @param c
	 *            - complex number to add to current complex number
	 * @return a new complex number that is a result of addition between current
	 *         complex number and complex number <code>c</code>
	 * @throws NullPointerException
	 *             if <code>c</code> is null
	 */
	public Complex add(Complex c) {
		Objects.requireNonNull(c, "Complex number must not be null.");
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * Subtracts complex number <code>c</code> from this complex number and returns
	 * a new complex number as a result.
	 * 
	 * @param c
	 *            - complex number to subtract from current complex number
	 * @return a new complex number that is a result of subtraction between current
	 *         complex number and complex number <code>c</code>
	 * @throws NullPointerException
	 *             if <code>c</code> is null
	 */
	public Complex sub(Complex c) {
		Objects.requireNonNull(c, "Complex number must not be null.");
		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * Negates the current complex number and returns a new complex number as a
	 * result.
	 * 
	 * @return negated complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Calculates the nth power of the current complex number.
	 * 
	 * @param n
	 *            - power
	 * @return current complex number powered to n
	 * @throws IllegalArgumentException
	 *             if n<0
	 */
	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n must be a positive number, was :" + n);

		if (n == 0) {
			return ONE;
		}

		double powerRe = re;
		double powerIm = im;
		n--;

		while (n > 0) {
			double powerRe1 = powerRe * re - powerIm * im;
			double powerIm1 = powerRe * im + powerIm * re;
			powerRe = powerRe1;
			powerIm = powerIm1;
			n--;
		}

		return new Complex(powerRe, powerIm);

	}

	/**
	 * Calculates the roots of the complex number using
	 * <a href="https://en.wikipedia.org/wiki/De_Moivre%27s_formula">de Moivre's
	 * formula</a>.
	 * 
	 * @param n
	 *            - desired root of the current complex number
	 * @return roots of the current complex number
	 * @throws IllegalArgumentException
	 *             if n less or equal to 0
	 */
	public List<Complex> root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be a positive number, was: " + n);

		double rootModule = Math.pow(this.module(), 1. / n);
		double angle = this.getAngle();

		List<Complex> roots = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			roots.add(Complex.fromModuleAndAngle(rootModule, (angle + 2 * i * PI) / n));
		}

		return roots;

	}

	/**
	 * Creates a new complex number using it's module and angle using formula real =
	 * magnitude*cos(angle), imaginary = magnitude*sin(angle)
	 * 
	 * @param module
	 *            - module of the new complex number
	 * @param angle
	 *            - angle of the new complex number
	 * @return a new complex number with module and value set to specified values
	 */
	public static Complex fromModuleAndAngle(double module, double angle) {
		return new Complex(module * Math.cos(angle), module * Math.sin(angle));
	}

	/**
	 * Calculates the angle of the complex number in radians in range [0, 2π] using
	 * formula inversetangent(Im(complex number)/Re(complex number)).
	 * 
	 * @return angle of the complex number in radians [0, 2π]
	 */
	public double getAngle() {
		double angle = Math.atan2(im, re);

		if (angle < 0) {
			angle = 2 * PI + angle;
		}

		return angle;
	}

	/**
	 * Returns the real part of the complex number.
	 * 
	 * @return the real part of the complex number.
	 */
	public double getRe() {
		return re;
	}

	/**
	 * Returns the imaginary part of the complex number.
	 * 
	 * @return imaginary part of the complex number
	 */
	public double getIm() {
		return im;
	}

	@Override
	public String toString() {
		return String.format("%.6f%s%.6fi", re, im >= 0 ? "+" : "", im);
	}

}
