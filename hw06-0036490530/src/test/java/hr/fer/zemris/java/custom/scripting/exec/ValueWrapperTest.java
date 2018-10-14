package hr.fer.zemris.java.custom.scripting.exec;

import java.util.LinkedList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ValueWrapperTest {

	private ValueWrapper wrapper;
	public static final double EPS = 1E-6;
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalTypeInOperationTest() {
		wrapper = new ValueWrapper(new LinkedList<String>());
		wrapper.add(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void illegalStringValueTest() {
		wrapper = new ValueWrapper("2r");
		wrapper.add(2);
	}
	
	@Test
	public void addTest() {
		
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue());
		
		assertEquals(null, v2.getValue());
		assertEquals(0, v1.getValue());
		
		wrapper = new ValueWrapper(1);
		wrapper.add(2);
		assertEquals(3,wrapper.getValue());
		
		wrapper.setValue(1.5);
		wrapper.add(3);
		assertEquals(4.5, (Double)wrapper.getValue(), EPS);
		
		wrapper.setValue(null);
		wrapper.add(2);
		assertEquals(2, wrapper.getValue());
		
		wrapper.setValue(1.5);
		wrapper.add(1.3);
		assertEquals(2.8, (Double)wrapper.getValue(), EPS);
		
		wrapper.setValue("1");
		wrapper.add("4");
		assertEquals(5, wrapper.getValue());
		
		wrapper.setValue("2.5");
		wrapper.add("1E-2");
		assertEquals(2.51, (Double)wrapper.getValue(), EPS);
		
	}
	
	@Test
	public void subtractTest() {
		
		wrapper = new ValueWrapper(1);
		wrapper.subtract(2);
		assertEquals(-1,wrapper.getValue());
		
		wrapper.setValue(1.5);
		wrapper.subtract(3);
		assertEquals(-1.5, (Double)wrapper.getValue(), EPS);
		
		wrapper.setValue(null);
		wrapper.subtract(2);
		assertEquals(-2, wrapper.getValue());
		
		wrapper.setValue(1.5);
		wrapper.subtract(1.3);
		assertEquals(0.2, (Double)wrapper.getValue(), EPS);
		
		wrapper.setValue("1");
		wrapper.subtract("4");
		assertEquals(-3, wrapper.getValue());
		
		wrapper.setValue("2.5");
		wrapper.subtract("1E-2");
		assertEquals(2.49, (Double)wrapper.getValue(), EPS);
		
	}
	
	@Test
	public void divideTest() {
		
		wrapper = new ValueWrapper(1);
		wrapper.divide(2);
		assertEquals(0,wrapper.getValue());
		
		wrapper.setValue(1.5);
		wrapper.divide(3);
		assertEquals(0.5, (Double)wrapper.getValue(), EPS);
		
		wrapper.setValue(null);
		wrapper.divide(2);
		assertEquals(0, wrapper.getValue());
		
		wrapper.setValue(3.0);
		wrapper.divide(1.5);
		assertEquals(2.0, (Double)wrapper.getValue(), EPS);
		
		wrapper.setValue("1");
		wrapper.divide("4");
		assertEquals(0, wrapper.getValue());
		
		wrapper.setValue("2.5");
		wrapper.divide("1E-2");
		assertEquals(250, (Double)wrapper.getValue(), EPS);
		
	}
	
	@Test
	public void numCompareTest() {

		wrapper = new ValueWrapper(null);
		assertEquals(0, wrapper.numCompare(null));
		
		wrapper.setValue(2);
		assertEquals(1, wrapper.numCompare(-1));
	
		wrapper.setValue(2);
		assertEquals(-1, wrapper.numCompare(5));
		
		wrapper.setValue(2.5);
		assertEquals(1, wrapper.numCompare(-1.3));
		
		wrapper.setValue(2.5);
		assertEquals(-1, wrapper.numCompare(5.4));
		
		wrapper.setValue("2.5");
		assertEquals(0, wrapper.numCompare("2.5"));
	}
	
}
