package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


public class ComparisonOperatorsTest {

	public IComparisonOperator operator;
	
	@Test
	public void testLess() {
		operator = ComparisonOperators.LESS;
		assertTrue(operator.satisfied("Aba", "Bba"));
		assertFalse(operator.satisfied("CCA", "BCA"));
	}
	
	@Test
	public void testLessOrEquals() {
		operator = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(operator.satisfied("Aba", "Bba"));
		assertTrue(operator.satisfied("A", "A"));
		assertFalse(operator.satisfied("CCA", "BCA"));
	}
	
	@Test
	public void testGreater() {
		operator = ComparisonOperators.GREATER;
		assertTrue(operator.satisfied("Bba", "Aba"));
		assertFalse(operator.satisfied("BCA", "CCA"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		operator = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(operator.satisfied("Bba", "Aba"));
		assertTrue(operator.satisfied("A", "A"));
		assertFalse(operator.satisfied("BCA", "CCA"));
	}
	
	@Test
	public void testEquals() {
		operator = ComparisonOperators.EQUALS;
		assertTrue(operator.satisfied("Ab", "Ab"));
		assertFalse(operator.satisfied("CCA", "BCA"));
	}
	
	@Test
	public void testNotEquals() {
		operator = ComparisonOperators.NOT_EQUALS;
		assertFalse(operator.satisfied("Ab", "Ab"));
		assertTrue(operator.satisfied("CCA", "BCA"));
	}
	
	
	@Test
	public void testLike() {
		
		operator = ComparisonOperators.LIKE;
		
		assertTrue(operator.satisfied("34343","*43"));
		assertFalse(operator.satisfied("Zagreb","Aba*"));
		assertFalse(operator.satisfied("AAA","AA*AA"));
		assertTrue(operator.satisfied("AAAA", "AA*AA"));
		
		assertTrue(operator.satisfied("AAA", "AAA"));
		
		assertTrue(operator.satisfied("Aabc", "*abc"));
		assertFalse(operator.satisfied("Aabc", "*abca"));
		assertFalse(operator.satisfied("Aabca", "*abc"));
		
		
		assertTrue(operator.satisfied("abaAAab", "aba*ab"));
		assertFalse(operator.satisfied("abaAAab", "aba*a"));
		
		assertTrue(operator.satisfied("abaABC", "aba*"));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalLike() {
		operator = ComparisonOperators.LIKE;
		operator.satisfied("ABc","A**");
	}
	
}
