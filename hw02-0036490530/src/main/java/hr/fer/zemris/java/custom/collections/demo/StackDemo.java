package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;


/**
 * Command line application which accepts a single command-line argument : expression to be evaluated.
 * The expression must be in postfix notation : <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">https://en.wikipedia.org/wiki/Reverse_Polish_notation</a>,
 * opened and closed by " and arguments separated by one or more spaces.
 * @author Dorian Ivankovic
 *
 */
public class StackDemo {
	private static ArrayIndexedCollection supportedOperations;
	
	static{
		supportedOperations = new ArrayIndexedCollection();
		supportedOperations.add("+");
		supportedOperations.add("-");
		supportedOperations.add("/");
		supportedOperations.add("*");
		supportedOperations.add("%");
	}
	
	/**
	 * This method is called once the program is run
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			generalErrorMessage();
			return;
		}

		String[] values = parseInput(args[0]);

		ObjectStack stack = new ObjectStack();

		for (String value : values) {

			try {
				performOperation(stack, value);

			} catch (IllegalArgumentException ex) {
				System.out.println(ex.getMessage());
				generalErrorMessage();
				return;
			} catch (EmptyStackException ex) {
				invalidExpressionMessage();
				return;
			} catch (UnsupportedOperationException ex) {
				System.out.println(ex.getMessage());
				return;
			}

		}

		if (stack.size() != 1) {
			invalidExpressionMessage();
		} else {
			System.out.println("Expression evaluates to : "+stack.pop());
		}

	}

	/**The method is used to parse the input - postfix notation expression
	 * into an array of values and operations.
	 * @param expression - expression to parse
	 * @return array of values and operations
	 */
	private static String[] parseInput(String expression) {
		expression.trim();
		expression.substring(1, expression.length() - 1);

		String[] values = expression.split("\\s+");

		return values;
	}

	
	/**The method performs a single operation on a stack using value.
	 * If the value is a whole number, it is pushed to the stack, if it is a supported
	 * operation, then the parameters are pulled from the stack, operation is executed and result is pushed back on the stack.
	 * @param stack - stack used for performing operations
	 * @param value - value to indicate which operation to perform
	 * @throws IllegalArgumentException if the value is not a whole number or a supported operation
	 * 		   EmptyStackException if the stack becomes empty during operation
	 * 		   UnsupportedOperationException if division by 0 is attempted
	 */
	private static void performOperation(ObjectStack stack, String value) {
		
		if(value.matches("[-]?\\d+")) {
			int num = Integer.parseInt(value);
			stack.push(num);
		}else {
			
			if(!supportedOperations.contains(value)) {
				throw new IllegalArgumentException(value+" is not a valid operator.");
			}
			
			int value2 = (int) stack.pop();
			int value1 = (int) stack.pop();

			int result;
			
			switch(value) {
			case "+":
				result = value1 + value2;
				break;
			case "-":
				result = value1 - value2;
				break;
			case "/":
				checkDivisionBy(value2);
				result = value1 / value2;
				break;
			case "*":
				result = value1 * value2;
				break;
			default: //%
				checkDivisionBy(value2);
				result = value1 % value2;
			}
			
			stack.push(result);
		
		}
	}

	/**
	 * Checks if value is zero and throws an UnsupportedOperationException if it is.
	 * @param value - value to check
	 * @throws UnsupportedOperationException - if the value is zero
	 */
	private static void checkDivisionBy(int value) {
		if (value == 0) {
			throw new UnsupportedOperationException("Division by zero occurred");
		}
	}
	
	/**
	 * Prints out a general error message specifying the command line input.
	 */
	private static void generalErrorMessage() {
		System.out.println("\nYou must input an argument which represents a postfix notation calculation command\n"
				+"on whole numbers, opened and closed by \" and numbers and operators separated by one or more spaces.");
	}

	/**
	 * Prints out a message specifying the number of values and operations used in postfix expression calculation.
	 */
	private static void invalidExpressionMessage() {
		System.out.println("Number of values and operations is inappropriate, number of operations"
				+ " should be number of values -1.");
	}
}
