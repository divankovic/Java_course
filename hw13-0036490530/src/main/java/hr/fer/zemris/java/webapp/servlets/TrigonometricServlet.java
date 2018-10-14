package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The servlet takes two parameters: a and b, and displays a table of sin and
 * cos values of integers between a and b. a default value is 0, and b default
 * value is 360.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int a = parse(req.getParameter("a"), 0);
		int b = parse(req.getParameter("b"), 360);

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		} else if (b > a + 720) {
			b = a + 720;
		}

		List<NumberTrigonometric> values = new ArrayList<>();

		for (int i = a; i <= b; i++) {
			double rad = Math.toRadians(i);
			values.add(new NumberTrigonometric(i, Math.sin(rad), Math.cos(rad)));
		}

		req.setAttribute("values", values);

		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}

	/**
	 * The method parses the given parameter to integer or returns the default value
	 * if parameter cannot be converted into integer.
	 * 
	 * @param parameter
	 *            - value to parse into integer value
	 * @param defValue
	 *            - default value
	 * @return parsed integer value
	 */
	private int parse(String parameter, int defValue) {
		int result = defValue;
		try {
			result = Integer.parseInt(parameter);
		} catch (NumberFormatException ex) {
		}

		return result;
	}

	/**
	 * The class represents a data unit holding an integer number and its
	 * trigonometric sinus and cosinus function values.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	public static class NumberTrigonometric {

		/**
		 * Value(in degrees).
		 */
		private int number;

		/**
		 * Sinus function of the value.
		 */
		private double sinValue;

		/**
		 * Cosinus function of the value.
		 */
		private double cosValue;

		/**
		 * Default no argument contstructor.
		 */
		public NumberTrigonometric() {

		}

		/**
		 * Constructs a new <code>NumberTrigonometric</code> using it's value in degrees
		 * and its sinus and cosinus value.
		 * 
		 * @param number
		 *            - degrees
		 * @param sinValue
		 *            - sinus function value
		 * @param cosValue
		 *            - cosinus function value
		 */
		public NumberTrigonometric(int number, double sinValue, double cosValue) {
			this.number = number;
			this.sinValue = sinValue;
			this.cosValue = cosValue;
		}

		/**
		 * Returns the stored number(degrees).
		 * 
		 * @return stored number
		 */
		public int getNumber() {
			return number;
		}

		/**
		 * Sets the stored number.
		 * 
		 * @param number - stored number
		 */
		public void setNumber(int number) {
			this.number = number;
		}

		/**
		 * Returns the sinus function value of the stored number in degrees.
		 * 
		 * @return sinus function value
		 */
		public double getSinValue() {
			return sinValue;
		}

		/**
		 * Returns the cosinus function value of the stored number in degrees.
		 * 
		 * @return cosinus function value
		 */
		public double getCosValue() {
			return cosValue;
		}

	}

}
