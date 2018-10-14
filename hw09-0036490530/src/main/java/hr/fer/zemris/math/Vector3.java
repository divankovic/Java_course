package hr.fer.zemris.math;

import java.util.Locale;
import java.util.Objects;

/**
 * Represents an unmodifiable 3D vector meaning that each operation on it return
 * a new vector.
 * 
 * @author Dorian Ivankovic
 *
 */
public class Vector3 {

	/**
	 * x component of the vector
	 */
	private double x;

	/**
	 * y component of the vector
	 */
	private double y;

	/**
	 * z component of the vector
	 */
	private double z;

	/**
	 * Constructs a new <code>Vector3</code> using it's x,y and z coordinates.
	 * 
	 * @param x
	 *            - x coordinate
	 * @param y
	 *            - y coordinate
	 * @param z
	 *            - z coordinate
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	/**
	 * Returns the norm of the vector using formula sqrt(x^2+y^2+z^2).
	 * 
	 * @return norm of the vector
	 */
	public double norm() {
		return Math.sqrt(x * x + y * y + z * z);
	}

	/**
	 * Return the normalized vector - vector of the same direction like the original
	 * one, but with norm = 1.
	 * 
	 * @return normalized vector
	 */
	public Vector3 normalized() {
		double norm = norm();
		if (norm == 0)
			return new Vector3(0, 0, 0);
		return new Vector3(x / norm, y / norm, z / norm);
	}

	/**
	 * Adds vector to current vector and returns a new vector as a result.
	 * 
	 * @param other
	 *            - vector to add to current vector
	 * @return - vector that is the result of the sum of current vector and vector
	 *         <code>other</code>
	 * @throws NullPointerException
	 *             if <code>other</code> is null
	 */
	public Vector3 add(Vector3 other) {
		Objects.requireNonNull(other, "Vector3 must not be null.");
		return new Vector3(x + other.x, y + other.y, z + other.z);
	}

	/**
	 * Subtracts vector from current vector and returns a new vector as a result.
	 * 
	 * @param other
	 *            - vector to subtract from current vector
	 * @return - vector that is the result of the subraction of vector
	 *         <code>other</code> from current vector
	 * @throws NullPointerException
	 *             if <code>other</code> is null
	 */
	public Vector3 sub(Vector3 other) {
		Objects.requireNonNull(other, "Vector3 must not be null.");
		return new Vector3(x - other.x, y - other.y, z - other.z);
	}

	/**
	 * Calculates the <a href="https://en.wikipedia.org/wiki/Dot_product">dot
	 * product</a> of the current vector and vector <code>other</code>.
	 * 
	 * @param other
	 *            - vector to perform dot product with
	 * @return dot product of current vector and vector <code>other</code>
	 * @throws NullPointerException
	 *             if other is null
	 */
	public double dot(Vector3 other) {
		Objects.requireNonNull(other, "Vector3 must not be null");
		return x * other.x + y * other.y + z * other.z;
	}

	/**
	 * Calculates the <a href="https://en.wikipedia.org/wiki/Cross_product">cross
	 * product</a> of the current vector and vector <code>other</code>.
	 * 
	 * @param other
	 *            - vector to perform cross product with
	 * @return cross product of current vector and vector <code>other</code>
	 * @throws NullPointerException
	 *             if other is null
	 */
	public Vector3 cross(Vector3 other) {
		Objects.requireNonNull(other, "Vector3 must not be null.");
		return new Vector3(y * other.z - z * other.y, 
				   z * other.x - x * other.z, 
				   x * other.y - y * other.x);
	}

	/**
	 * Scaler the current vector with the given factor and returns a new vector as a
	 * result.
	 * 
	 * @param s
	 *            - scale factor
	 * @return scaled vector
	 */
	public Vector3 scale(double s) {
		return new Vector3(x * s, y * s, z * s);
	}

	/**
	 * Calculates the cosinus of the angle between this vector and vector
	 * <code>other</code>
	 * 
	 * @param other
	 *            - angle corresponds to angle between current vector and this
	 *            vector
	 * @return cosinus of the angle between this vector and vector
	 *         <code>other</code>
	 * @throws NullPointerException
	 *             if other is null
	 * @throws IllegalArgumentException
	 *             if this norm or other norm is 0
	 */
	public double cosAngle(Vector3 other) {
		Objects.requireNonNull(other, "Vector3 must not be null.");
		double otherNorm = other.norm();
		double norm = norm();

		if (otherNorm == 0 || norm == 0)
			throw new IllegalArgumentException("Null vector not allowed.");

		return dot(other) / (norm * otherNorm);
	}

	/**
	 * Returns the x component of the vector.
	 * 
	 * @return x component of the vector
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y component of the vector.
	 * 
	 * @return y component of the vector
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the z component of the vector.
	 * 
	 * @return z component of the vector
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns an array of vector coordinated (x,y,z).
	 * 
	 * @return vector coordinates
	 */
	public double[] toArray() {
		return new double[] { x, y, z };
	}

	public String toString() {
		return String.format(Locale.US, "(%.6f, %.6f, %.6f)", x, y, z);
	}

}
