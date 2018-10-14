package hr.fer.zemris.java.math;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import hr.fer.zemris.math.Complex;

public class ComplexTest {
	private static Complex c1;
	private static double EPS = 1e-6;

	@BeforeClass
	public static void setUp() {
		c1 = new Complex(1, 2);
	}

	@Test
	public void moduleTest() {
		assertEquals(Math.sqrt(5), c1.module(), EPS);
	}

	@Test
	public void angleTest() {
		Complex c2 = new Complex(-1, 1);
		Complex c3 = new Complex(-1, -1);
		Complex c4 = new Complex(1, -1);

		assertEquals(Math.atan(2), c1.getAngle(), EPS);
		assertEquals(3 * Math.PI / 4, c2.getAngle(), EPS);
		assertEquals(5 * Math.PI / 4, c3.getAngle(), EPS);
		assertEquals(7 * Math.PI / 4, c4.getAngle(), EPS);
	}

	@Test
	public void multiplyTest() {
		Complex result = c1.multiply(new Complex(2, 3));
		assertEquals(-4, result.getRe(), EPS);
		assertEquals(7, result.getIm(), EPS);
	}

	@Test
	public void divideTest() {
		Complex result = c1.divide(new Complex(2, 3));
		assertEquals(8. / 13, result.getRe(), EPS);
		assertEquals(1. / 13, result.getIm(), EPS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalDivideTest() {
		c1.divide(new Complex(0, 0));
	}

	@Test
	public void addTest() {
		Complex result = c1.add(new Complex(2, 3));
		assertEquals(3, result.getRe(), EPS);
		assertEquals(5, result.getIm(), EPS);
	}

	@Test
	public void subTest() {
		Complex result = c1.sub(new Complex(2, 3));
		assertEquals(-1, result.getRe(), EPS);
		assertEquals(-1, result.getIm(), EPS);
	}

	@Test
	public void negateTest() {
		Complex result = c1.negate();
		assertEquals(-1, result.getRe(), EPS);
		assertEquals(-2, result.getIm(), EPS);
	}

	@Test
	public void powerTest() {
		Complex result = c1.power(3);
		assertEquals(-11, result.getRe(), EPS);
		assertEquals(-2, result.getIm(), EPS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalPowerTest() {
		c1.power(-1);
	}

	@Test
	public void rootTest() {
		double module = c1.module();
		double angle = c1.getAngle();
		Complex root = Complex.fromModuleAndAngle(Math.sqrt(module), angle / 2);
		List<Complex> roots = c1.root(2);
		assertEquals(root.getRe(), roots.get(0).getRe(), EPS);
		assertEquals(root.getIm(), roots.get(0).getIm(), EPS);

	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalRootTest() {
		c1.root(0);
	}

	@Test
	public void fromModuleAndAngleTest() {
		Complex result = Complex.fromModuleAndAngle(Math.sqrt(2), Math.PI / 4);
		assertEquals(1, result.getRe(), EPS);
		assertEquals(1, result.getIm(), EPS);
	}
}
