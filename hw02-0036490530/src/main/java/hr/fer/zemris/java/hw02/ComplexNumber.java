package hr.fer.zemris.java.hw02;

import static java.lang.Math.PI;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class represents an unmodifiable complex number with real and imaginary part.
 * Class offers methods for complex number magnitude, angle calculation as well
 * as methods for adding, subtracting, multiplying, dividing, powering and rooting complex numbers ,...
 * 
 * @author Dorian Ivankovic
 *
 */
public class ComplexNumber {
	
	/**
	 * Used for comparison of complex numbers, first.real - other.real 
	 * and first.imaginary - other.imaginary must be in
	 * range [EPS,-EPS] for the complex numbers to be equal.
	 */
	public static final double EPS = 1E-4;
	
	private double real;
	private double imaginary;

	/**
	 * Constructor of ComplexNumber using values of real and imaginary part of
	 * the complex number.
	 * @param real - real part of the complex number
	 * @param imaginary - imaginary part of the complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	
	/**
	 * Returns the real part of the complex number.
	 * @return real part of the complex number
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Returns the imaginary part of the complex number.
	 * @return imaginary part of the complex number
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Calculates the magnitude of the complex number using formula sqrt(real^2 + imaginary^2).
	 * @return magnitude of the complex number
	 */
	public double getMagnitude() {
		return Math.sqrt(real * real + imaginary * imaginary);
	}

	/**
	 * Calculates the angle of the complex number in radians in range [0, 2π]
	 * using formula inversetangent(Im(complex number)/Re(complex number)).
	 * @return angle of the complex number in radians [0, 2π]
	 */
	public double getAngle() {
		double angle =  Math.atan2(imaginary, real);
		
		if(angle<0) {
			angle = 2*PI + angle;
		}
		
		return angle;
	}

	
	/**
	 * Adds the complex number c to the current complex number 
	 * and as a result returns the new complex number.
	 * @param c - complex number to add to the current complex number
	 * @return new complex number - result of operation add (current + c)
	 * @throws NullPointerException - if c is null
	 */
	public ComplexNumber add(ComplexNumber c) {
		c = Objects.requireNonNull(c);
		
		double real = this.real + c.getReal();
		double imaginary = this.imaginary + c.getImaginary();

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Subtracts the complex number c from the current complex number 
	 * and as a result returns the new complex number.
	 * @param c - complex number to subtract from the current complex number
	 * @return new complex number - result of operation subtract (current - c)
	 * @throws NullPointerException - if c is null
	 */
	public ComplexNumber sub(ComplexNumber c) {
		c = Objects.requireNonNull(c);
		
		double real = this.real - c.getReal();
		double imaginary = this.imaginary - c.getImaginary();

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Multiplies the complex number c with current complex number.
	 * @param c - complex number to multiply with
	 * @return new complex number - the result of multiplication operation (current*c)
	 * @throws NullPointerException - if c is null
	 */
	public ComplexNumber mul(ComplexNumber c) {
		c = Objects.requireNonNull(c);
		
		double real = this.real * c.real - this.imaginary * c.imaginary;
		double imaginary = this.real * c.imaginary + this.imaginary * c.real;

		return new ComplexNumber(real, imaginary);
	}

	/**
	 * Divides the current complex number with complex number c.
	 * @param c - complex number to divide with
	 * @return new complex number - result of operation (current/c)
	 * @throws IllegalArgumentException - if c is 0, division with 0 occurs
	 * 		   NullPointerException - if c is null
	 */
	public ComplexNumber div(ComplexNumber c) {
		c = Objects.requireNonNull(c);
		
		if(c.getMagnitude() == 0) {
			throw new IllegalArgumentException("ComplexNumber c must not be 0, can't divide with 0.");
		}
		
		ComplexNumber conjugatedC = new ComplexNumber(c.getReal(),-1*c.getImaginary());
		
		ComplexNumber nominatorComplex = mul(conjugatedC);
		double denominator = Math.pow(conjugatedC.getMagnitude(),2);
		
		return new ComplexNumber(nominatorComplex.getReal()/denominator,nominatorComplex.getImaginary()/denominator);
	
	}

	/**
	 * Calculates the nth power of current complex number
	 * @param n - power
	 * @return new complex number, result of operation current^n
	 */
	public ComplexNumber power(int n) {
		if (n == 0) return new ComplexNumber(1, 0);

		
		ComplexNumber result = ComplexNumber.fromMagnitudeAndAngle(Math.pow(getMagnitude(), Math.abs(n)), getAngle()*Math.abs(n));
		
		if(n<0) {
			result = new ComplexNumber(1,0).div(result);
		}

		return result;
	}

	/**
	 * Calculates the roots of the complex number using de Moivre's formula : 
	 * <a href="https://en.wikipedia.org/wiki/De_Moivre%27s_formula">https://en.wikipedia.org/wiki/De_Moivre%27s_formula</a>
	 * @param n - desired root of the current complex number
	 * @return arrays of roots of the current complex number, size of n
	 * @throws IllegalArgumentException if n less or equal to 0
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException("n must not be less or equal to 0.");
		}

		double rootMagnitude = Math.pow(this.getMagnitude(),1./n);
		double angle = this.getAngle();

		ComplexNumber[] roots = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			roots[i] = ComplexNumber.fromMagnitudeAndAngle(rootMagnitude, (angle + 2 * i * PI) / n);
		}

		return roots;

	}

	/**
	 * Returns a string representation of the current complex number.
	 * @return string representation of the current complex number
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		boolean appendPlus = false;
		
		if (real != 0) {
			result.append(real);
			appendPlus = true;
		}
		
		if(imaginary!=0) {
			if(imaginary>0 && appendPlus) {
				result.append("+");
			}
			result.append(imaginary);
			result.append("i");
		}
		
		return result.toString();
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (!(obj instanceof ComplexNumber))
			return false;
		
		ComplexNumber other = (ComplexNumber) obj;
		
		if (Math.abs(this.real-other.real)>EPS) {
			return false;
		}

		if (Math.abs(this.imaginary-other.imaginary)>EPS) {
			return false;
		}
		
		return true;
	}


	/**
	 * Creates a new complex number from its real part.
	 * @param real - real part of the complex number
	 * @return new complex number with real part set to specified value
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Creates a new complex number from its imaginary part.
	 * @param imaginary - imaginary part of the complex number
	 * @return new complex number with imaginary part set to specified value
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Creates a new complex number using it's magnitude and angle using
	 * formula real = magnitude*cos(angle), imaginary = magnitude*sin(angle)
	 * @param magnitude - magnitude of the new complex number
	 * @param angle - angle of the new complex number
	 * @return a new complex number with magnitude and value set to specified values
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	/**
	 * Parses a string a creates a new complex number from it.
	 * Valid strings are in format a+bi where a and b are real numbers, e.g.
	 * "3.51", "-3.17", "-2.71i", "i", "1", "-2.71-3.15i"
	 * @param s - string to parse to a complex number
	 * @return new complex number parsed from input string s
	 * @throws NullPointerException if s is null
	 * 		   IllegalArgumentException if s is not in the specified format and can't be parsed
	 */
	public static ComplexNumber parse(String s) {
		if (s == null) {
			throw new NullPointerException("string s must not be null.");
		}

		s = s.replaceAll("\\s","");
		
		//Parsing is achieved using regular expressions http://www.vogella.com/tutorials/JavaRegularExpressions/article.html
		
		Pattern fullComplexPattern = Pattern.compile("([-]?\\d+\\.?\\d*)([+|-](\\d+\\.?\\d*)?)[i$]");
		Pattern realPattern = Pattern.compile("([-]?\\d+\\.?\\d*)$");
		Pattern imaginaryPattern = Pattern.compile("([-]?(\\d+\\.?\\d*)?)(i$)");
		
		Matcher fullComplexMatcher = fullComplexPattern.matcher(s);
		Matcher realMatcher = realPattern.matcher(s);
		Matcher imaginaryMatcher = imaginaryPattern.matcher(s);
		
		double real=0, imaginary = 0;
		
		if(fullComplexMatcher.matches()) {
			real = Double.parseDouble(fullComplexMatcher.group(1));
			imaginary = parseImaginary(fullComplexMatcher.group(2));
		}else if(realMatcher.matches()) {
			real = Double.parseDouble(realMatcher.group(1));
			imaginary = 0;
		}else if(imaginaryMatcher.matches()) {
			real = 0;
			imaginary = parseImaginary(imaginaryMatcher.group(1));
		}else {
			throw new IllegalArgumentException("String must be in format : a+bi, where a and b are real numbers"
												+"with \".\" as decimal separator.");
		}
		
		return new ComplexNumber(real, imaginary);
	}

	/**
	 * The method is used to parse imaginary one "-i" or "+i" or "i" to a double value.
	 * @param value value to parse
	 * @return double representation of the imaginary value
	 */
	private static double parseImaginary(String value) {
		if(value.equals("+") || value.equals("")) {
			return 1;
		}else if(value.equals("-")) {
			return -1;
		}else {
			return Double.parseDouble(value);
		}
	}

}
