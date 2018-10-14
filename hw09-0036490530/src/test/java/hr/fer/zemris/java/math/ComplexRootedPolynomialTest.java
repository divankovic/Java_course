package hr.fer.zemris.java.math;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

public class ComplexRootedPolynomialTest {

	public static final double EPS = 1e-6;

	public static ComplexRootedPolynomial polynom;
	static {
		List<Complex> roots = new ArrayList<>();
		roots.add(new Complex(1, 0));
		roots.add(new Complex(1, -1));
		roots.add(new Complex(2, 0));
		roots.add(new Complex(2, 2));
		roots.add(new Complex(3, 2));
		polynom = new ComplexRootedPolynomial(roots);
	}

	@Test
	public void applyTest() {
		Complex value = polynom.apply(new Complex(2, 4));
		assertEquals(-8, value.getRe(), EPS);
		assertEquals(376, value.getIm(), EPS);
	}

	@Test
	public void toComplexPolynomialTest() {
		List<Complex> factors = new ArrayList<>();
		factors.add(Complex.ONE);
		factors.add(new Complex(-9, -3));
		factors.add(new Complex(31, 18));
		factors.add(new Complex(-57, -41));
		factors.add(new Complex(58, 42));
		factors.add(new Complex(-24, -16));
		ComplexPolynomial expectedPol = new ComplexPolynomial(factors);

		ComplexPolynomial realPol = polynom.toComplexPolynom();
		assertEquals(expectedPol.toString(), realPol.toString());
	}

	@Test
	public void indexOfClosestRootTest() {
		Complex z = new Complex(2.42, 2);
		assertEquals(3, polynom.indexOfClosestRootFor(z, 0.5));
		assertEquals(-1, polynom.indexOfClosestRootFor(z, 0.1));
	}

}
