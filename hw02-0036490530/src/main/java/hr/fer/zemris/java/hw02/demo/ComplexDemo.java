package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

public class ComplexDemo {
	public static void main(String[] args) {
		ComplexNumber c = new ComplexNumber(1,2);
		ComplexNumber res = c.div(new ComplexNumber(2,3));
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57))
		.div(c2).power(3).root(2)[1];
		System.out.println(c3);
		ComplexNumber c4 = new ComplexNumber(8/13,1/13);
		System.out.println(c4.equals(res));
	}
}
