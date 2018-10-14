package hr.fer.zemris.java.hw16.jvdraw.geometric;

import java.awt.Color;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.LineEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectVisitor;

/**
 * Represents a simple line geometrical object.
 * @author Dorian Ivankovic
 *
 */
public class Line extends GeometricalObject {
	
	/**
	 * Start point of the line.
	 */
	private Point startPoint;
	
	/**
	 * End point of the line.
	 */
	private Point endPoint;
	
	/**
	 * Line color.
	 */
	private Color lineColor;
	
	/**
	 * Constructor
	 * @param startPoint - start point of the line
	 * @param endPoint - end point of the line
	 * @param lineColor - line color
	 */
	public Line(Point startPoint, Point endPoint, Color lineColor) {
		this.startPoint = startPoint;
		this.endPoint = endPoint;
		this.lineColor = lineColor;
	}
	
	/**
	 * Returns the start point of the line
	 * @return start point of the line
	 */
	public Point getStartPoint() {
		return startPoint;
	}

	/**
	 * Returns the end point of the line
	 * @return end point of the line
	 */
	public Point getEndPoint() {
		return endPoint;
	}

	/**
	 * Returns the line color.
	 * @return line color
	 */
	public Color getLineColor() {
		return lineColor;
	}

	/**
	 * Sets the start point
	 * @param startPoint - line start point
	 */
	public void setStartPoint(Point startPoint) {
		this.startPoint = startPoint;
	}

	/**
	 * Sets the line end point
	 * @param endPoint - end point
	 */
	public void setEndPoint(Point endPoint) {
		this.endPoint = endPoint;
	}

	/**
	 * Sets the line color
	 * @param lineColor - line color
	 */
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endPoint == null) ? 0 : endPoint.hashCode());
		result = prime * result + ((lineColor == null) ? 0 : lineColor.hashCode());
		result = prime * result + ((startPoint == null) ? 0 : startPoint.hashCode());
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
		Line other = (Line) obj;
		if (endPoint == null) {
			if (other.endPoint != null)
				return false;
		} else if (!endPoint.equals(other.endPoint))
			return false;
		if (lineColor == null) {
			if (other.lineColor != null)
				return false;
		} else if (!lineColor.equals(other.lineColor))
			return false;
		if (startPoint == null) {
			if (other.startPoint != null)
				return false;
		} else if (!startPoint.equals(other.startPoint))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Line ("+startPoint.x+","+startPoint.y+")-("+endPoint.x+","+endPoint.y+")";
	}

	@Override
	public String toJvd() {
		return String.format("LINE %d %d %d %d %d %d %d", startPoint.x, startPoint.y, endPoint.x,endPoint.y,
							lineColor.getRed(), lineColor.getGreen(), lineColor.getBlue());
	}
	
	

}
