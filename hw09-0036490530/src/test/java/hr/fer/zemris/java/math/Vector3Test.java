package hr.fer.zemris.java.math;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hr.fer.zemris.math.Vector3;

public class Vector3Test {
	public static final double EPS = 1e-6;

	@Test
	public void normTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		assertEquals(Math.sqrt(14), vector.norm(), EPS);
	}

	@Test
	public void normalizedTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		Vector3 normalized = vector.normalized();
		double norm = vector.norm();
		assertEquals(1 / norm, normalized.getX(), EPS);
		assertEquals(2 / norm, normalized.getY(), EPS);
		assertEquals(3 / norm, normalized.getZ(), EPS);
	}

	@Test
	public void addTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		Vector3 result = vector.add(new Vector3(1, 2, 2));
		assertEquals(2, result.getX(), EPS);
		assertEquals(4, result.getY(), EPS);
		assertEquals(5, result.getZ(), EPS);
	}

	@Test(expected = NullPointerException.class)
	public void illegalAddTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		vector.add(null);
	}

	@Test
	public void subTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		Vector3 result = vector.sub(new Vector3(1, 2, 2));
		assertEquals(0, result.getX(), EPS);
		assertEquals(0, result.getY(), EPS);
		assertEquals(1, result.getZ(), EPS);
	}

	@Test(expected = NullPointerException.class)
	public void illegalSubTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		vector.sub(null);
	}

	@Test
	public void dotTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		double res = vector.dot(new Vector3(1, 2, 2));
		assertEquals(11, res, EPS);
	}

	@Test
	public void crossTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		Vector3 result = vector.cross(new Vector3(2, 1, 5));
		assertEquals(7, result.getX(), EPS);
		assertEquals(1, result.getY(), EPS);
		assertEquals(-3, result.getZ(), EPS);
	}

	@Test
	public void scaleTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		Vector3 result = vector.scale(0.5);
		assertEquals(0.5, result.getX(), EPS);
		assertEquals(1, result.getY(), EPS);
		assertEquals(1.5, result.getZ(), EPS);
	}

	@Test
	public void cosTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		double result = vector.cosAngle(new Vector3(2, 1, 5));
		assertEquals(result, 0.92710507, EPS);
	}

	@Test(expected = IllegalArgumentException.class)
	public void illegalCosTest() {
		Vector3 vector = new Vector3(1, 2, 3);
		vector.cosAngle(new Vector3(0, 0, 0));
	}

}
