package hr.fer.zemris.java.hw04.custom.collections;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.Before;

public class DictionaryTest {
	private static Dictionary dictionary;
	
	@Before
	public void setUp() {
		dictionary = new Dictionary();
		dictionary.put(1, "jedan");
		dictionary.put(2, "dva");
		dictionary.put(3, "tri");
	}
	
	@Test
	public void testGet() {
		dictionary.put(5, null);
		
		assertEquals(4, dictionary.size());
		assertEquals("jedan",dictionary.get(1));
		assertEquals(null, dictionary.get(5));
		assertEquals(null, dictionary.get(6));
		
	}
	
	
	@Test(expected = NullPointerException.class)
	public void testIllegalPut() {
		dictionary.put(null, 2);
	}
	
	
	@Test
	public void testPut() {
		dictionary.put(4, "četiri");
		assertEquals(4, dictionary.size());
		
		dictionary.put(1, "one");
		dictionary.put(2, "two");
		
		assertEquals("one",dictionary.get(1));
		assertEquals("two",dictionary.get(2));
	}
}
