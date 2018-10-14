package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * Linked list backed collection of elements with list node containing
 * pointer to the next and previous element (double linked list).
 * Duplicate elements are allowed in the list, storage of null references is not allowed.
 * @author Dorian Ivankovic
 *
 */
public class LinkedListIndexedCollection extends Collection {

	private static final int NOT_FOUND = -1;

	/**
	 * Class represents a list node containing references to the previous 
	 * and the next node as well as value stored in the node.
	 * @author Dorian Ivankovic
	 *
	 */
	private static class ListNode {
		ListNode previous;
		ListNode next;
		Object value;
	}

	private int size;
	
	private ListNode first;
	private ListNode last;

	
	/**
	 * Default constructor for linked list.
	 */
	public LinkedListIndexedCollection() {
		size = 0;
		first = last = null;
	}

	/**
	 * Constructor that copies elements from another collection into the new one.
	 * @param other - collection to copy elements from
	 * @throws NullPointerException if other is null
	 */
	public LinkedListIndexedCollection(Collection other) {
		this.addAll(other);
	}

	
	/**
	 * The method returns the current size of the list.
	 * @return size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object at the end of the collection with complexity O(1).
	 * @param value - value to add in the collection
	 * @throws NullPointerException if value is null
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

	
	/**
	 * Insert the given value at the specified position in linked-list, but does not
	 * replace the element at position but shifts all elements starting from position
	 * one place to the right.
	 * The average complexity of this method is O(n/4)
	 * @param value - value to add in the collection
	 * @param position - position in which to insert value
	 * @throws NullPointerException if value is null
	 * @throws IndexOutOfBoundsException if position is &lt; 0 or &gt; size
	 *  
	 */
	public void insert(Object value, int position) {

		value = Objects.requireNonNull(value);
		checkPositionIndex(position);

		ListNode node = new ListNode();
		node.value = value;

		if (position == size) {

			if (first == null) {
				// when the list is empty
				first = last = node;
			} else {
				last.next = node;
				node.previous = last;
				last = node;
			}

		} else {

			ListNode positionNode = getNode(position);
			ListNode prev = positionNode.previous;

			node.next = positionNode;
			positionNode.previous = node;

			
			if (prev != null) {
				node.previous = prev;
				prev.next = node;
			}else {
				//adding to the beginning of the list
				first = node;
			}

		}

		size++;

	}

	/**
	 * Returns the object that is stored in linked list at position index.
	 * The complexity of this method is O(n/2).
	 * @param index - index of the object to retrieve
	 * @return the object at position index in the list
	 * @throws IndexOutOfBoundsException if index is &lt; 0 or &gt; size-1
	 */
	public Object get(int index) {
		checkElementIndex(index);
		ListNode node = getNode(index);
		return node.value;
	}

	
	/**
	 * This method is used to retrieve the ListNode at position index in the list.
	 * @param index - index of the desired node
	 * @return list node at position index
	 */
	private ListNode getNode(int index) {

		ListNode startNode;
		int i;

		if (index > size / 2) {
			startNode = last;
			i = size - 1;

			while (i != index) {
				startNode = startNode.previous;
				i--;
			}

		} else {
			startNode = first;
			i = 0;

			while (i != index) {
				startNode = startNode.next;
				i++;
			}
		}

		return startNode;

	}

	
	/**
	 * Searches the list and returns the index of the first occurrence of the given value.
	 * The average complexity of the method is O(n/4).
	 * @param value - value to search for in the list
	 * @return position of the first occurrence of the value or -1 if the value is not found
	 */
	public int indexOf(Object value) {
		int index = 0;
		ListNode node = first;

		while (node != null) {
			if (node.value.equals(value)) {
				return index;
			} else {
				node = node.next;
				index++;
			}
		}

		return NOT_FOUND;
	}

	/**
	 * The method checks if the list contains the specified value.
	 * @param value - value to search for in the list
	 * @return true if the value if found in the list, false otherwise
	 */
	@Override
	public boolean contains(Object value) {
		if (indexOf(value) != NOT_FOUND) {
			return true;
		}
		return false;
	}

	/**
	 * Removes the element at the specified index from the list.
	 * Elements after index are shifted one position to the left, so now
	 * element that was on position index+1 is now on position index etc.
	 * @param index - index to remove element from
	 * @throws IndexOutOfBoundsException if index is &lt; 0 or &gt; size-1
	 */
	public void remove(int index) {
		checkElementIndex(index);

		ListNode positionNode = getNode(index);
		ListNode prev = positionNode.previous;
		ListNode next = positionNode.next;

		if (next != null) {
			positionNode.next = null;
			next.previous = prev;
		} else {
			last = prev;
		}

		if (prev != null) {
			positionNode.previous = null;
			prev.next = next;
		} else {
			first = next;
		}

		positionNode.value = null;
		size--;

	}


	/**
	 * Removes the first occurrence of the value from the list.
	 * The complexity of the method is O(n/2).
	 * @param value - value to remove from the list (first occurence)
	 * @return true if the value is removed fform the list, false if the list doesn't contain the value
	 */
	@Override
	public boolean remove(Object value) {
		if (!this.contains(value)) {
			return false;
		}
		
		remove(indexOf(value));
		return true;
	}
	

	/**
	 * Converts the list into an array of objects.
	 * @return array of objects from the collection
	 */
	@Override
	public Object[] toArray() {
		Object[] elements = new Object[size];
		int i = 0;
		
		for(ListNode node = first;node!=null;node=node.next) {
			elements[i++] = node.value;
		}
		
		return elements;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void forEach(Processor processor) {
		for(ListNode node = first;node!=null;node=node.next) {
			processor.process(node.value);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;

	}

	/**
	 * The method checks if the given index is a valid index of some element in the list.
	 */
	private void checkElementIndex(int index) {
		if (index < 0 || index > (size - 1)) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
		}
	}

	/**
	 * The method checks if the given index is a valid position for adding in the list.
	 */
	private void checkPositionIndex(int index) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(outOfBoundsMessage(index));
		}
	}

	/**
	 * The method returns a String containing a message specifying the invalid index and the size
	 * of the list. 
	 */
	private String outOfBoundsMessage(int index) {
		return String.format("Invalid index : %d, size : %d.", index, size);
	}

}
