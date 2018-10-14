package hr.fer.zemris.java.custom.collecitons;

import static org.junit.Assert.assertArrayEquals;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class LinkedListIndexedCollectionTest {
LinkedListIndexedCollection collection = new LinkedListIndexedCollection();
	
	@Before
	public void setUp() {	
		collection.add("A");
		collection.add("B");
		collection.add("C");
	}
	
	@After
	public void tearDown() {
		collection.clear();
	}
	@Test
	public void getTest() {
		assertEquals("B", collection.get(1));
	}
	
	@Test(expected= IndexOutOfBoundsException.class)
	public void getIllegalIndexTest() {
		try {
			collection.get(-2);
		}catch(IndexOutOfBoundsException ex){
			collection.get(collection.size());
		}
	}
	
	@Test
	public void containsTest() {
		assertTrue(collection.contains("A"));
		assertFalse(collection.contains("D"));
	}
	
	@Test
	public void indexOfTest() {
		collection.add("B");
		assertEquals(1,collection.indexOf("B"));
		assertEquals(-1,collection.indexOf(3));
	}
	
	@Test
	public void addTest() {
		assertEquals(3, collection.size());
	}
	
	@Test
	public void insertTest() {
		
		collection.insert("B",0);
		collection.insert("C",collection.size());
		collection.insert("D",2);
		
		Assert.assertArrayEquals(collection.toArray(), new Object[]{"B","A","D","B","C","C"});
		
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void insertIllegalPositionTest() {
		
		try {
			collection.insert(1,-2);
		}catch(IndexOutOfBoundsException ex) {
			collection.insert(1, collection.size()+1);
		}
		
	}
	
	@Test
	public void removeAtIndexTest() {
		collection.remove(1);
		assertArrayEquals(collection.toArray(), new Object[] {"A","C"});
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void removeAtIllegalIndexTest() {
		try {
			collection.remove(-2);
		}catch(IndexOutOfBoundsException ex) {
			collection.remove(collection.size());
		}
	}
	
	@Test
	public void removeObjectTest() {
		collection.add("A");
		collection.remove("A");
		assertArrayEquals(collection.toArray(), new Object[] {"B","C","A"});
		assertFalse(collection.remove("D"));
	}
	
	@Test
	public void clearTest() {
		collection.clear();
		assertTrue(collection.isEmpty());
	}
	
	
	@Test
	public void addAllTest() {
		
		LinkedListIndexedCollection other = new LinkedListIndexedCollection();
		other.add("D");
		other.add("E");
		
		collection.addAll(other);
	
		Assert.assertArrayEquals(collection.toArray(), new Object[] {"A","B","C","D","E"});
		Assert.assertArrayEquals(other.toArray(), new Object[] {"D","E"});
	}
}
