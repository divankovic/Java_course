package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * The program demostrates drawing fractals using
 * <a href = "http://www.chiark.greenend.org.uk/~sgtatham/newton/">
 * Newton-Raphson iteration</a>.<br>
 * The user is asked to input roots of the complex polynomial used in
 * calculation. <br>
 * At least two roots must be inputed in format : a+ib, where a and b are real
 * numbers. <br>
 * Once the user wants to finish the input he can do so by inputting "done"
 * after which the fractal image will be displayed.
 * 
 * @author Dorian Ivankovic
 *
 */
public class Newton {

	/**
	 * This method is called once the program is run
	 * 
	 * @param args
	 *            - command line arguments
	 */
	public static void main(String[] args) {

		ComplexRootedPolynomial polynom;
		List<Complex> roots = new ArrayList<>();

		try (Scanner sc = new Scanner(System.in)) {
			System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
			System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");

			int count = 1;

			while (true) {
				System.out.print("Root " + count + "> ");
				if (sc.hasNextLine()) {
					String line = sc.nextLine();
					if (line.isEmpty())
						continue;
					if (line.equals("done")) {
						if (count > 2) {
							System.out.println("Image of fractal will appear shortly. Thank you.");
							break;
						} else {
							System.out.println("You must input at least 2 roots.");
							continue;
						}
					}
					try {
						Complex root = parseRoot(line);
						System.out.println(root);
						roots.add(root);
						count++;

					} catch (IllegalArgumentException ex) {
						System.out.println(ex.getMessage());
					}

				}
			}
		}

		polynom = new ComplexRootedPolynomial(roots);

		FractalViewer.show(new NewtonFractalProducer(polynom));

	}

	/**
	 * The method uses Newton-Raphson's iteration to calculate values specific for
	 * each point on the screen.
	 * 
	 * @param reMin
	 *            - real component minimum
	 * @param reMax
	 *            - real component maximum
	 * @param imMin
	 *            - imaginary component minimum
	 * @param imMax
	 *            - imaginary component maximum
	 * @param width
	 *            - width of the screen
	 * @param height
	 *            - height of the screen
	 * @param yMin
	 *            - start y coordinate for calculation
	 * @param yMax
	 *            - end y coordinate for calculation
	 * @param polynom
	 *            - used in Newton-Raphson's iteration
	 * @param data
	 *            - data used to draw fractals on the screen
	 */
	public static void calculate(double reMin, double reMax, double imMin, double imMax, int width, int height,
			int yMin, int yMax, ComplexRootedPolynomial polynom, short[] data) {

		ComplexPolynomial derived = polynom.toComplexPolynom().derive();

		int offset = yMin * width;
		System.out.println("Računam "+yMin+" do "+yMax);

		for (int y = yMin; y <= yMax; y++) {
			for (int x = 0; x < width; x++) {
				double re = x / (width - 1.0) * (reMax - reMin) + reMin;
				double im = (height - 1.0 - y) / (height - 1.0) * (imMax - imMin) + imMin;

				int count = 0;
				Complex zn = new Complex(re, im);
				double module;

				do {
					Complex zn1 = zn.sub(polynom.apply(zn).divide(derived.apply(zn)));
					module = zn1.sub(zn).module();
					zn = zn1;
					count++;
				} while (count < NewtonFractalProducer.MAX_ITER && module > NewtonFractalProducer.CONV_THRESHOLD);

				int idx = polynom.indexOfClosestRootFor(zn, NewtonFractalProducer.ROOT_THRESHOLD);
				if (idx == -1) {
					data[offset++] = 0;
				} else {
					data[offset++] = (short) (idx + 1);
				}
			}

		}

	}

	/**
	 * Parses a string a creates a new complex number from it. Valid strings are in
	 * format a+ib where a and b are real numbers, e.g. "3.51", "-3.17", "-i2.71",
	 * "i", "1", "-2.71-i3.15"
	 * 
	 * @param s
	 *            - string to parse to a complex number
	 * @return new complex number parsed from input string s
	 * @throws NullPointerException
	 *             if s is null IllegalArgumentException if s is not in the
	 *             specified format and can't be parsed
	 */
	private static Complex parseRoot(String s) {
		if (s == null) {
			throw new NullPointerException("string s must not be null.");
		}

		s = s.replaceAll("\\s", "");

		Pattern fullComplexPattern = Pattern.compile("([-]?\\d+\\.?\\d*)([+|-][i](\\d+\\.?\\d*)?)");
		Pattern realPattern = Pattern.compile("([-]?\\d+\\.?\\d*)$");
		Pattern imaginaryPattern = Pattern.compile("([-]?[i](\\d+\\.?\\d*)?)");

		Matcher fullComplexMatcher = fullComplexPattern.matcher(s);
		Matcher realMatcher = realPattern.matcher(s);
		Matcher imaginaryMatcher = imaginaryPattern.matcher(s);

		double real = 0, imaginary = 0;

		if (fullComplexMatcher.matches()) {
			real = Double.parseDouble(fullComplexMatcher.group(1));
			imaginary = parseImaginary(fullComplexMatcher.group(2));
		} else if (realMatcher.matches()) {
			real = Double.parseDouble(realMatcher.group(1));
			imaginary = 0;
		} else if (imaginaryMatcher.matches()) {
			real = 0;
			imaginary = parseImaginary(imaginaryMatcher.group(1));
		} else {
			throw new IllegalArgumentException("Root must be in format : a+ib, where a and b are real numbers.");
		}

		return new Complex(real, imaginary);
	}

	/**
	 * The method is used to parse imaginary one "-i" or "+i" or "i" to a double
	 * value.
	 * 
	 * @param value
	 *            - value to parse
	 * @param imaginary
	 *            value
	 */
	private static double parseImaginary(String value) {
		value = value.replace("i", "");
		if (value.equals("+") || value.equals("")) {
			return 1;
		} else if (value.equals("-")) {
			return -1;
		} else {
			return Double.parseDouble(value);
		}
	}

}
