package hr.fer.zemris.java.custom.collecitons;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Test;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;


public class ObjectStackTest {

	private ObjectStack stack = new ObjectStack();
	
	@After
	public void tearDown() {
		stack.clear();
	}
	
	@Test
	public void pushTest() {
		assertTrue(stack.isEmpty());
		
		stack.push(1);
		stack.push(2);
		stack.push(3);
		
		assertEquals(3,stack.size());
	}
	
	@Test
	public void popTest() {
		
		stack.push(1);
		stack.push(2);
		
		assertEquals(2,stack.pop());
		assertEquals(1, stack.size());
	}
	
	@Test(expected = EmptyStackException.class)
	public void popIllegalTest() {
		stack.pop();
	}
	
	@Test
	public void peekTest() {
		stack.push(1);
		stack.push(2);
		
		assertEquals(2, stack.peek());
		assertEquals(2, stack.size());
	}
	
	@Test(expected = EmptyStackException.class)
	public void peekIllegalTest() {
		stack.peek();
	}
}
