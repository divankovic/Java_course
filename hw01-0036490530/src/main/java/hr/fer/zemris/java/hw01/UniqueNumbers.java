package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The program is used to demonstrate basic tree structure's functionality.
 * User inputs values which are added to the tree structure if possible. When the user ends the input, values
 * of the tree structure are printed out in ascending and descending order.
 * 
 * @author Dorian Ivankovic
 *
 */
public class UniqueNumbers {

	/**
	 * This method is called once the program is run. 
	 * 
	 * @param args
	 *            - command line arguments
	 */
	public static void main(String[] args) {
		final String END_INPUT = "kraj";
		TreeNode head = null;

		Scanner scanner = new Scanner(System.in);

		while (true) {

			System.out.print("Unesite broj > ");

			String input = scanner.nextLine();

			if (input.equals(END_INPUT)) {
				System.out.print("Ispis od najmanjeg: ");
				printAscending(head);
				System.out.print("\n");

				System.out.print("Ispis od najvećeg: ");
				printDescending(head);
				System.out.print("\n");

				break;
			}

			try {
				int value = Integer.parseInt(input);

				if (!containsValue(head, value)) {
					head = addNode(head, value);
					System.out.println("Dodano.");
				} else {
					System.out.println("Broj već postoji. Preskačem.");
				}

			} catch (NumberFormatException ex) {
				System.out.format("\'%s\' nije cijeli broj.\n", input);
			}
		}

		scanner.close();
	}

	/**
	 * The method is used for adding a new value to the tree structure with
	 * complexity O(log2 n).
	 * 
	 * @param head
	 *            - head of the tree structure
	 * @param value
	 *            - new value to add
	 * @return TreeNode - new head of the tree structure
	 */
	public static TreeNode addNode(TreeNode head, int value) {
		if (head == null) {
			head = new TreeNode();
			head.value = value;
			return head;
		}
		
		if (!containsValue(head, value)) {
			
			if(value>head.value) {
				head.right = addNode(head.right, value);
			}else {
				head.left = addNode(head.left, value);
			}
			
		}
		return head;
	}

	/**
	 * The method checks if the tree structure already contains a value with
	 * complexity O(log2 n).
	 * 
	 * @param head
	 *            - head of the tree structure
	 * @param value
	 *            - checking the existence of this value
	 * @return true if the tree structure contains the value, false otherwise
	 */
	public static boolean containsValue(TreeNode head, int value) {

		if (head == null) {
			return false;
		}

		if (head.value == value) {
			return true;
		} else if (value > head.value) {
			return containsValue(head.right, value);
		} else {
			return containsValue(head.left, value);
		}
	}

	/**
	 * The method calculates the tree structure's size.
	 * 
	 * @param head
	 *            - head of the tree structure
	 * @return int - size of the tree structure
	 */
	public static int treeSize(TreeNode head) {
		if (head == null) {
			return 0;
		}

		return 1 + treeSize(head.left) + treeSize(head.right);
	}

	
	/**
	 * The method prints out elements of the tree structure in ascending order.
	 * 
	 * @param head
	 *            - head of the tree structure
	 */
	private static void printAscending(TreeNode head) {
		if (head != null) {
			printAscending(head.left);
			System.out.print(head.value + " ");
			printAscending(head.right);
		}
	}

	/**
	 * The method prints out elements of the tree structure in descending order.
	 * 
	 * @param head
	 *            - head of the tree structure
	 */
	private static void printDescending(TreeNode head) {
		if (head != null) {
			printDescending(head.right);
			System.out.print(head.value + " ");
			printDescending(head.left);
		}

	}
}
