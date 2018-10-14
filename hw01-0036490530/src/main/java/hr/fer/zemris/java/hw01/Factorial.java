package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The program illustrates calculating factorial of input numbers.
 * 
 * @author Dorian Ivankovic
 */
public class Factorial {
	
	private static short MAX_FACTORIAL = 20;

	/**
	 * This method is called once the program is run, user inputs numbers for
	 * factorial calculation.
	 * 
	 * @param args
	 *            - command line arguments
	 */
	public static void main(String[] args) {

		final String END_INPUT = "kraj";

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Unesite broj > ");
			
			String input = scanner.nextLine();
			
			if (input.equals(END_INPUT)) {
				System.out.println("Doviđenja.");
				break;
			}

			try {
				int number = Integer.parseInt(input);

				if (number < 0 || number > MAX_FACTORIAL) {
					System.out.format("\'%d\' nije broj u dozvoljenom rasponu.\n", number);
				} else {
					long fact = calculateFactorial(number);
					System.out.format("%d! = %d\n", number, fact);
				}

			} catch (NumberFormatException ex) {
				System.out.format("\'%s\' nije cijeli broj.\n", input);
			}
		}
		scanner.close();
	}

	/**
	 * Calculates the factorial (!) of the input number with complexity O(n).
	 * 
	 * @param number
	 * @return factorial of the input number if the number is greater or equal to 0
	 * @throws IllegalArgumentException
	 *             if the number is less than 0
	 */
	public static long calculateFactorial(int number) {
		if (number < 0 || number > MAX_FACTORIAL) {
			throw new IllegalArgumentException("Broj nije u dozvoljenom rasponu, mora biti >0 i <= "+MAX_FACTORIAL);
		}

		long result = 1L;

		for (int i = 2; i <= number; i++) {
			result *= i;
		}

		return result;
	}
}
