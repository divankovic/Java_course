package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * The program illustrates calculating perimeter and area of the rectangle.
 * User inputs arguments - height and width of the rectangle using command line or standard input.
 * 
 * @author Dorian Ivankovic
 *
 */
public class Rectangle {

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            - command line arguments
	 */
	public static void main(String[] args) {
		double width = 0;
		double height = 0;

		if (args.length == 2) {

			try {
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);

			} catch (Exception ex) {
				System.out.println("Morate unijeti dva pozitivna broja.");
			}

		} else if (args.length == 0) {
			Scanner scanner = new Scanner(System.in);

			width = inputParameter("širinu", scanner);
			height = inputParameter("visinu", scanner);

			scanner.close();
		} else {
			System.out.println("Morate unijeti širinu i visinu pravokutnika kao argumente komandne linije"
					+ "ili ih unijeti nakon pokretanja programa.");
			return;
		}
		
		printData(width, height);
	}

	
	/**
	 * The method is used for parameter(double) input, it ignores inappropriate
	 * values until the appropriate one is entered.
	 * 
	 * @param parameter
	 *            - input type : width, height
	 * @param scanner
	 *            - used for reading from input
	 * @return double - value of the parameter
	 */
	public static double inputParameter(String parameter, Scanner scanner) {
		double value;
		
		while (true) {
			System.out.print("Unesite " + parameter + " > ");
			String input = scanner.nextLine();

			try {
				value = Double.parseDouble(input);

				if (value < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
				} else if(value ==0){
					System.out.println("Unijeli ste 0.");
				}else {
					break;
				}

			} catch (Exception ex) {
				System.out.format("\'%s\' se ne može protumačiti kao broj.\n", input);
			}
		}
		
		return value;
	}

	
	/**
	 * The method prints out relevant information about a rectangle - width, height, 
	 * perimeter and area.
	 * 
	 * @param width
	 *            - width of the rectangle
	 * @param height
	 *            - height of the rectangle
	 */
	public static void printData(double width, double height) {
		double perimeter = perimeter(width, height);
		double area = area(width, height);
		
		System.out.format("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f i opseg %.1f.\n", width,
				height, area, perimeter);
	}

	
	/**
	 * The method calculates the area of the rectangle using formula width*height.
	 * 
	 * @param width
	 *            - width of the rectangle
	 * @param height
	 *            - height of the rectangle
	 * @return double - area of the rectangle
	 */
	public static double area(double width, double height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Širina i visina moraju biti veće od 0.");
		}
		return width * height;
	}

	
	/**
	 * The method calculates the perimeter using formula 2*width + 2*height.
	 * 
	 * @param width
	 *            - width of the rectangle
	 * @param height
	 *            - height of the rectangle
	 * @return double - height of the rectangle
	 */
	public static double perimeter(double width, double height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Širina i visina moraju biti veće od 0.");
		}
		return 2 * width + 2 * height;
	}
}
