package hr.fer.zemris.java.hw01;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static hr.fer.zemris.java.hw01.Factorial.calculateFactorial;

public class FactorialTest {

	@Test
	public void testZero() {
		assertEquals(1, calculateFactorial(0));
	}
	@Test
	public void testOne() {
		assertEquals(1, calculateFactorial(1));
	}
	
	@Test
	public void testRegular() {
		assertEquals(3628800, Factorial.calculateFactorial(10));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testNegative() {
		calculateFactorial(-1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testLarge() {
		calculateFactorial(50);
	}
	
}
