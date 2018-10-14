package hr.fer.zemris.math;

import java.util.Objects;

/**
 * A simple 2D vector.
 * @author Dorian Ivankovic
 *
 */
public class Vector2D {
	
	/**
	 * x component of the vector.
	 */
	private double x;
	
	/**
	 * y component of the vector.
	 */
	private double y;
	
	/**
	 * Used for vector comparison.
	 */
	public static final double EPS = 1e-5;
	
	
	/**
	 * Default constructor for 2D vector.
	 * @param x - x component of the vector
	 * @param y - y component of the vector
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}


	/**
	 * Returns the x component of the vector.
	 * @return x component of the vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y component of the vector.
	 * @return y component of the vector
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Translates the current vector using the vector offset.
	 * The vector offset is added to the current vector.
	 * @param offset - vector used to translate the current vector
	 * @throws NullPointerException if offset is null
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset);
		x = x + offset.getX();
		y = y + offset.getY();
	}
	
	/**
	 * Uses {@link #translate(Vector2D)} to translate the current vector
	 * and return a new translated vector. 
	 * @param offset - vector used to translate the current vector
	 * @return translated vector
	 */
	public Vector2D translated(Vector2D offset) {
		Vector2D copy = copy();
		copy.translate(offset);
		return copy;
	}
	
	
	/**
	 * Rotates the current vector using angle in degrees.
	 * The rotation is performed using formula <a href=Vector_rotation>https://matthew-brett.github.io/teaching/rotation_2d.html</a>
	 * @param angle - angle of rotation (in degrees)
	 */
	public void rotate(double angle) {
		double angleRad = Math.toRadians(angle);
		
		double x1 = Math.cos(angleRad)*x - Math.sin(angleRad)*y; 
		double y1 = Math.sin(angleRad)*x + Math.cos(angleRad)*y;
		
		this.x = x1;
		this.y = y1;
	
	}
	
	/**
	 * Uses {@link #rotate(double)} to rotate the current vector and return a new rotated vector.
	 * @param angle - angle of rotation
	 * @return rotated vector
	 */
	public Vector2D rotated(double angle) {
		Vector2D copy = copy();
		copy.rotate(angle);
		return copy;
	}
	
	
	/**
	 * Scales the current vector by multiplying it's component by scaler.
	 * @param scaler - scale factor of the vector scaling
	 */
	public void scale(double scaler) {
		x = x*scaler;
		y = y*scaler;
	}
	
	/**
	 * Uses {@link #scale(double)} to scale the current vector and return a new scaled vector.
	 * @param scaler - scale factor of the vector scaling
	 * @return scaled vector
	 */
	public Vector2D scaled(double scaler) {
		Vector2D copy = copy();
		copy.scale(scaler);
		return copy;
	}
	
	/**
	 * Copies the current vector.
	 * @return copy of the current vector
	 */
	public Vector2D copy() {
		return  new Vector2D(x, y);
	}
	
	/**
	 * Creates a new normalised vector given the value of angle in degrees.
	 * @param angle - angle of the vector
	 * @return normalised direction vector of the given angle
	 */
	public static Vector2D normalisedVectorFromAngle(double angle) {
		double angleRad = Math.toRadians(angle);
		return new Vector2D(Math.cos(angleRad),Math.sin(angleRad));
	}
	
	
	@Override
	public boolean equals(Object other) {
		if(other==this) return true;
		if(!(other instanceof Vector2D)) return false;
		
		Vector2D vector = (Vector2D)other;
		
		if(Math.abs(this.x-vector.x)>EPS) return false;
		if(Math.abs(this.y - vector.y)>EPS) return false;
		
		return true;
	
	}
}
