package hr.fer.zemris.java.hw02;

import static org.junit.Assert.assertEquals;


import org.junit.BeforeClass;
import org.junit.Test;

public class ComplexNumberTest {

	private static ComplexNumber c1;
	private static double EPS = ComplexNumber.EPS;
	
	@BeforeClass
	public static void setUp() {
		c1 = new ComplexNumber(1,2);
	}
	
	
	@Test
	public void magnitudeTest() {
		assertEquals(Math.sqrt(5), c1.getMagnitude(),EPS);
	}
	
	@Test
	public void angleTest() {
		ComplexNumber c2 = new ComplexNumber(-1,1);
		ComplexNumber c3 = new ComplexNumber(-1,-1);
		ComplexNumber c4 = new ComplexNumber(1,-1);
		
		assertEquals(Math.atan(2),c1.getAngle(),EPS);
		assertEquals(3*Math.PI/4,c2.getAngle(),EPS);
		assertEquals(5*Math.PI/4,c3.getAngle(),EPS);
		assertEquals(7*Math.PI/4,c4.getAngle(),EPS);
	}
	
	@Test
	public void addTest() {	
		assertEquals(new ComplexNumber(3,5),c1.add(new ComplexNumber(2,3)));
	}
	
	
	@Test
	public void subTest() {
		assertEquals(new ComplexNumber(-1,-1),c1.sub(new ComplexNumber(2,3)));
		
	}
	
	@Test
	public void mulTest() {
		assertEquals(new ComplexNumber(-4,7),c1.mul(new ComplexNumber(2,3)));
		
	}
	
	@Test
	public void divTest() {
		assertEquals(new ComplexNumber(8./13,1./13),c1.div(new ComplexNumber(2,3)));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void divIllegalTest() {
		c1.div(new ComplexNumber(0,0));
	}
	
	@Test
	public void powerTest() {
		assertEquals(new ComplexNumber(-11,-2),c1.power(3));
	}
	
	@Test
	public void negativePowerTest() {
		assertEquals(new ComplexNumber(-3./25,-4./25),c1.power(-2));
	}
	
	@Test
	public void rootTest() {
		double magnitude = c1.getMagnitude();
		double angle = c1.getAngle();
		assertEquals(ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(magnitude), angle/2),c1.root(2)[0]);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void rootIllegalTest() {
		c1.root(0);
	}
	
	@Test
	public void fromRealTest() {
		assertEquals(5,ComplexNumber.fromReal(5).getReal(),EPS);
	}
	
	@Test
	public void fromImaginaryTest() {
		assertEquals(5,ComplexNumber.fromImaginary(5).getImaginary(),EPS);
	}
	
	@Test
	public void fromMagnitudeAndAngleTest() {
		assertEquals(new ComplexNumber(1,1),ComplexNumber.fromMagnitudeAndAngle(Math.sqrt(2), Math.PI/4));
	}
	
	@Test
	public void parseTest() {
		assertEquals(new ComplexNumber(1,0),ComplexNumber.parse("1"));
		assertEquals(new ComplexNumber(-1,0),ComplexNumber.parse("-1"));
		
		assertEquals(new ComplexNumber(0,1),ComplexNumber.parse("i"));
		assertEquals(new ComplexNumber(0,-1),ComplexNumber.parse("-i"));
		assertEquals(new ComplexNumber(0,2.5),ComplexNumber.parse("2.5i"));
		assertEquals(new ComplexNumber(0,-2.5),ComplexNumber.parse("-2.5i"));
		
		assertEquals(new ComplexNumber(1,-1),ComplexNumber.parse("1-i"));
		assertEquals(new ComplexNumber(1,1),ComplexNumber.parse("1+i"));
		assertEquals(new ComplexNumber(2.5,-2.5),ComplexNumber.parse("2.5-2.5i"));
	}
	
	@Test(expected = NullPointerException.class)
	public void IllegalParseTest1() {
		ComplexNumber.parse(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void IllegalParseTest2() {
		ComplexNumber.parse("abc");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void IllegalParseTest3() {
		ComplexNumber.parse("1+");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void IllegalParseTest4() {
		ComplexNumber.parse("2,5");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void IllegalParseTest5() {
		ComplexNumber.parse("2.5--2i");
	}
	
}
