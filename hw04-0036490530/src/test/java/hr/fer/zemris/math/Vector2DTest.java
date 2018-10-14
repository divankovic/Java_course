package hr.fer.zemris.math;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class Vector2DTest {
	public static final double EPS = 1E-4;
	
	@Test
	public void translateTest() {
		Vector2D vector = new Vector2D(2, 2);
		Vector2D offset = new Vector2D(3, -1);
		
		vector.translate(offset);
		assertEquals(5,vector.getX(),EPS);
		assertEquals(1,vector.getY(),EPS);
		
		Vector2D offset2 = new Vector2D(-2, -1);
		Vector2D translated = vector.translated(offset2);
		
		assertEquals(3,translated.getX(),EPS);
		assertEquals(0,translated.getY(),EPS);
		
	}
	
	@Test(expected = NullPointerException.class)
	public void illegalTranslateTest() {
		Vector2D vector = new Vector2D(2, 2);
		vector.translate(null);
	}
	
	
	@Test
	public void rotateTest() {
		Vector2D vector = new Vector2D(1,1);
		vector.rotate(90);
		
		assertEquals(-1,vector.getX(),EPS);
		assertEquals(1,vector.getY(),EPS);
		
		Vector2D rotated = vector.rotated(60);
		
		assertEquals(-1.366025,rotated.getX(),EPS);
		assertEquals(-0.366025,rotated.getY(),EPS);
		
	}
	
	
	@Test
	public void scaleTest() {
		Vector2D vector = new Vector2D(1,1);
		vector.scale(2.5);
	
		assertEquals(2.5,vector.getX(),EPS);
		assertEquals(2.5,vector.getY(),EPS);
		
		Vector2D scaled = vector.scaled(-0.5);
		
		assertEquals(-1.25,scaled.getX(),EPS);
		assertEquals(-1.25,scaled.getY(),EPS);
	}
}
