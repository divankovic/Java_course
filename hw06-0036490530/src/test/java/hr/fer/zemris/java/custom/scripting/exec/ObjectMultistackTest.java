package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.EmptyStackException;

import org.junit.Test;

public class ObjectMultistackTest {

	private ObjectMultistack multiStack;
	
	@Test
	public void pushTest() {
		multiStack = new ObjectMultistack();
		multiStack.push("price", new ValueWrapper(100));
		
		assertFalse(multiStack.isEmpty("price"));
	}
	
	@Test(expected = NullPointerException.class)
	public void pushIllegalTest() {
		multiStack = new ObjectMultistack();
		multiStack.push(null, new ValueWrapper(200));
	}
	
	@Test(expected = NullPointerException.class)
	public void pushIllegalTest2() {
		multiStack = new ObjectMultistack();
		multiStack.push("price", null);
	}
	
	@Test
	public void popTest() {
		multiStack = new ObjectMultistack();
		multiStack.push("price", new ValueWrapper(100));
		multiStack.push("price", new ValueWrapper(120));
		multiStack.push("price", new ValueWrapper(140));
		
		assertEquals(140, multiStack.pop("price").getValue());
		
		multiStack.push("price", new ValueWrapper(160));
		assertEquals(160, multiStack.pop("price").getValue());
		assertEquals(120, multiStack.pop("price").getValue());
		assertEquals(100, multiStack.pop("price").getValue());
	}
	
	@Test(expected = NullPointerException.class)
	public void popIllegalTest() {
		multiStack = new ObjectMultistack();
		multiStack.pop(null);
	}
	
	@Test(expected = EmptyStackException.class)
	public void popEmptyTest() {
		multiStack = new ObjectMultistack();
		multiStack.pop("price");
	}
	
	@Test
	public void peekTest() {
		
		multiStack = new ObjectMultistack();
		multiStack.push("price", new ValueWrapper(100));
		multiStack.push("price", new ValueWrapper(120));
		multiStack.push("price", new ValueWrapper(140));
		
		assertEquals(140, multiStack.peek("price").getValue());
		assertEquals(140, multiStack.peek("price").getValue());
		
		multiStack.push("price", new ValueWrapper(160));
		assertEquals(160, multiStack.peek("price").getValue());
	}
	
	@Test(expected = EmptyStackException.class)
	public void peekEmptyTest() {
		multiStack = new ObjectMultistack();
		multiStack.peek("price");
	}
	
	@Test
	public void isEmptyTest() {
		multiStack = new ObjectMultistack();
		multiStack.push("price", new ValueWrapper(100));
		multiStack.push("price", new ValueWrapper(120));
		multiStack.push("price", new ValueWrapper(140));
		
		assertFalse(multiStack.isEmpty("price"));
		assertTrue(multiStack.isEmpty("year"));
		assertTrue(multiStack.isEmpty(null));
	}
	
	
	
}
