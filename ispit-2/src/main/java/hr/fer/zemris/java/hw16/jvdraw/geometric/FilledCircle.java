package hr.fer.zemris.java.hw16.jvdraw.geometric;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.FilledCircleEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectVisitor;

/**
 * Circle filled by a different color.
 * 
 * @author Dorian Ivankovic
 *
 */
public class FilledCircle extends Circle {

	/**
	 * Fill color of the circle.
	 */
	private Color fillColor;

	/**
	 * Constructor
	 * 
	 * @param center
	 *            - circle center
	 * @param radius
	 *            - circle radius
	 * @param outlineColor
	 *            - circle outline color
	 * @param fillColor
	 *            - circle fill color
	 */
	public FilledCircle(Point center, int radius, Color outlineColor, Color fillColor) {
		super(center, radius, outlineColor);
		this.fillColor = fillColor;
	}

	/**
	 * Returns the fill color of the circle.
	 * 
	 * @return circle fill color
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * Sets the circle's fill color.
	 * 
	 * @param fillColor
	 *            - circle fill color
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit((FilledCircle) this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((fillColor == null) ? 0 : fillColor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FilledCircle other = (FilledCircle) obj;
		if (fillColor == null) {
			if (other.fillColor != null)
				return false;
		} else if (!fillColor.equals(other.fillColor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Filled circle (%d,%d), %d, #%02X%02X%02X", center.x, center.y, radius, fillColor.getRed(),
				fillColor.getGreen(), fillColor.getBlue());
	}

	@Override
	public String toJvd() {
		return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d", center.x, center.y, radius, outlineColor.getRed(),
				outlineColor.getGreen(), outlineColor.getBlue(), fillColor.getRed(), fillColor.getGreen(),
				fillColor.getBlue());

	}
}
