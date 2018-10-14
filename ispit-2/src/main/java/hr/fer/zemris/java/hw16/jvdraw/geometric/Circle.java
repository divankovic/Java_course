package hr.fer.zemris.java.hw16.jvdraw.geometric;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.CircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectVisitor;

/**
 * Represents a simple circle geometrical object.
 * 
 * @author Dorian Ivankovic
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Center of the circle.
	 */
	protected Point center;

	/**
	 * Circle radius.
	 */
	protected int radius;

	/**
	 * Outline color of the circle.
	 */
	protected Color outlineColor;

	/**
	 * Constructor.
	 * 
	 * @param center
	 *            - circle center
	 * @param radius
	 *            - circle radius
	 * @param outlineColor
	 *            - circle outline color
	 */
	public Circle(Point center, int radius, Color outlineColor) {
		super();
		this.center = center;
		this.radius = radius;
		this.outlineColor = outlineColor;
	}

	/**
	 * Returns the center of the circle
	 * 
	 * @return center of the circle
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Returns the radius of the circle
	 * 
	 * @return radius of the circle
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Returns the outline color of the circle.
	 * 
	 * @return outline color of the circle
	 */
	public Color getOutlineColor() {
		return outlineColor;
	}

	/**
	 * Sets the center of the circle
	 * 
	 * @param center
	 *            - new circle center
	 */
	public void setCenter(Point center) {
		this.center = center;
	}

	/**
	 * Sets the radius of the circle
	 * 
	 * @param radius
	 *            - new radius of the circle
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	/**
	 * Sets the outline color of the circle
	 * 
	 * @param outlineColor
	 *            - outline color of the circle
	 */
	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((center == null) ? 0 : center.hashCode());
		result = prime * result + ((outlineColor == null) ? 0 : outlineColor.hashCode());
		result = prime * result + radius;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Circle other = (Circle) obj;
		if (center == null) {
			if (other.center != null)
				return false;
		} else if (!center.equals(other.center))
			return false;
		if (outlineColor == null) {
			if (other.outlineColor != null)
				return false;
		} else if (!outlineColor.equals(other.outlineColor))
			return false;
		if (radius != other.radius)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Circle (" + center.x + "," + center.y + "), " + radius;
	}

	@Override
	public String toJvd() {
		return String.format("CIRCLE %d %d %d %d %d %d", center.x, center.y, radius, outlineColor.getRed(),
				outlineColor.getGreen(), outlineColor.getBlue());
	}
}
