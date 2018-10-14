package hr.fer.zemris.java.math;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;

public class ComplexPolynomialTest {

	public static final double EPS = 1e-6;
	public static ComplexPolynomial polynom;

	static {
		List<Complex> factors = new ArrayList<>();
		factors.add(new Complex(1, 1));
		factors.add(new Complex(1, -1));
		factors.add(new Complex(2, 3));
		factors.add(new Complex(2, -1));
		factors.add(new Complex(3, 0));
		polynom = new ComplexPolynomial(factors);
	}

	@Test
	public void orderTest() {
		assertEquals(4, polynom.order());
	}

	@Test
	public void multiplyTest() {
		List<Complex> otherFactors = new ArrayList<>();
		otherFactors.add(Complex.IM_NEG);
		otherFactors.add(new Complex(2, 5));
		otherFactors.add(new Complex(1, -2));
		otherFactors.add(new Complex(2, 2));

		ComplexPolynomial other = new ComplexPolynomial(otherFactors);

		List<Complex> resultFactors = new ArrayList<>();
		resultFactors.add(new Complex(1, -1));
		resultFactors.add(new Complex(-4, 6));
		resultFactors.add(new Complex(13, 0));
		resultFactors.add(new Complex(-13, 15));
		resultFactors.add(new Complex(21, 4));
		resultFactors.add(new Complex(4, 20));
		resultFactors.add(new Complex(9, -4));
		resultFactors.add(new Complex(6, 6));

		ComplexPolynomial expectedResult = new ComplexPolynomial(resultFactors);

		assertEquals(expectedResult.toString(), polynom.multiply(other).toString());
	}

	@Test
	public void deriveTest() {
		List<Complex> resultFactors = new ArrayList<>();
		resultFactors.add(new Complex(4, 4));
		resultFactors.add(new Complex(3, -3));
		resultFactors.add(new Complex(4, 6));
		resultFactors.add(new Complex(2, -1));

		ComplexPolynomial expectedResult = new ComplexPolynomial(resultFactors);

		assertEquals(expectedResult.toString(), polynom.derive().toString());
	}

	@Test
	public void applyTest() {
		Complex z = new Complex(2, 5);
		Complex res = polynom.apply(z);
		assertEquals(584, res.getRe(), EPS);
		assertEquals(-737, res.getIm(), EPS);
	}

}
