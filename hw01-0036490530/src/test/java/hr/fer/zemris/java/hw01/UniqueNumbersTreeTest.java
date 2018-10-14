package hr.fer.zemris.java.hw01;

import static hr.fer.zemris.java.hw01.UniqueNumbers.addNode;
import static hr.fer.zemris.java.hw01.UniqueNumbers.treeSize;
import static hr.fer.zemris.java.hw01.UniqueNumbers.containsValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.BeforeClass;
import org.junit.Test;

public class UniqueNumbersTreeTest {

	private static TreeNode head;

	@BeforeClass
	public static void setUp() {
		
		head = addNode(head, 42);
		head = addNode(head, 76);
		head = addNode(head, 21);
		head = addNode(head, 76);
		head = addNode(head, 35);
	
	}

	@Test
	public void addToTreeTest() {
		
		assertEquals(42, head.value);
		assertEquals(21, head.left.value);
		assertEquals(35, head.left.right.value);
		assertEquals(76, head.right.value);
	
	}
	
	@Test
	public void treeContainsTest() {
		
		assertTrue(containsValue(head, 21));
		assertTrue(containsValue(head, 35));
		assertFalse(containsValue(head, 56));
		
	}
	
	@Test
	public void treeSizeTest() {
		assertEquals(4,treeSize(head));
	}

}
