package hr.fer.zemris.java.hw05.collections;

import org.junit.Test;


import hr.fer.zemris.java.hw05.collections.SimpleHashTable.TableEntry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleHashTableTest {

	public SimpleHashTable<String,Integer> map;
	
	@Test
	public void testConsturctor() {
		map = new SimpleHashTable<>(30);
		assertEquals(32, map.getCapacity());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalConstructor() {
		map = new SimpleHashTable<>(-2);
	}
	
	@Test
	public void testPut() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 2);
		map.put("Jasna", 2);
		map.put("Kristina", 5);
		assertEquals(4, map.size());
	}
	
	@Test(expected = NullPointerException.class)
	public void testIllegalPut() {
		map = new SimpleHashTable<>();
		map.put(null, 3);
	}
	
	@Test
	public void testIncreaseCapacity() {
		map = new SimpleHashTable<>(2);
		map.put("Ivana", 2);
		map.put("Ante", 2);
		map.put("Jasna", 2);
		map.put("Kristina", 5);
		assertEquals(8, map.getCapacity());
	}
	
	@Test
	public void testContainsKey() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 2);
		map.put("Jasna", 2);
		map.put("Kristina", 5);
		assertTrue(map.containsKey("Ivana"));
		assertFalse(map.containsKey("Jura"));
	}
	
	@Test
	public void testContainsValue() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 5);
		map.put("Jasna", 3);
		assertTrue(map.containsValue(2));
		assertFalse(map.containsValue(6));
	}

	
	@Test
	public void testGet() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 5);
		map.put("Jasna", 3);
		map.put("Joža", null);
		assertEquals(Integer.valueOf(2), map.get("Ivana"));
		assertEquals(Integer.valueOf(5), map.get("Ante"));
		assertEquals(null, map.get("Joža"));
		assertEquals(null,map.get("Jura"));
	}
	
	@Test
	public void testRemove() {
		map = new SimpleHashTable<>(2);
		map.put("Ivana", 2);
		map.put("Ante", 5);
		map.put("Jasna", 3);
		map.remove("Štef");
		assertEquals(3,map.size());
		map.remove("Ivana");
		assertEquals(null,map.get("Ivana"));
		assertEquals(2,map.size());
		map.remove("Ante");
		assertEquals(null,map.get("Ante"));
		assertEquals(1,map.size());
	}
	
	@Test
	public void testClear() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 5);
		map.put("Jasna", 3);
		map.clear();
		assertEquals(0,map.size());
	}
	
	
	@Test
	public void iteratorHasNextTest() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 5);
		Iterator<TableEntry<String, Integer>> iterator = map.iterator();
		assertTrue(iterator.hasNext());
		assertTrue(iterator.next()!=null);
		assertTrue(iterator.hasNext());
		iterator.next();
		assertFalse(iterator.hasNext());
	
	}
	
	@Test(expected = NoSuchElementException.class)
	public void iteratorNextTest() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 5);
		Iterator<TableEntry<String, Integer>> iterator = map.iterator();
		int count = 0;
		while(iterator.hasNext()) {
			iterator.next();
			count++;
		}
		
		assertEquals(2,count);
		iterator.next();
		
	}
	
	@Test
	public void iteratorRemoveTest() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 5);
		Iterator<TableEntry<String, Integer>> iterator = map.iterator();
		
		while(iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		
		assertEquals(0,map.size());
	}
	
	@Test(expected = IllegalStateException.class)
	public void iteratorIllegalRemoveTest() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 5);
		Iterator<TableEntry<String, Integer>> iterator = map.iterator();
		
		while(iterator.hasNext()) {
			iterator.next();
			iterator.remove();
			iterator.remove();
		}
	}
	
	@Test(expected = ConcurrentModificationException.class)
	public void iteratorModificationInIterationTest() {
		map = new SimpleHashTable<>();
		map.put("Ivana", 2);
		map.put("Ante", 5);
		map.put("Jasna", 3);
		
		for(TableEntry<String,Integer> entry : map) {
			if(entry.getKey().equals("Ivana")) {
				map.put("Ivan", 1);
			}
		}
	}
	
	
	
	
	
	
	
	
	
}
