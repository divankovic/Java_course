package hr.fer.zemris.java.hw16.jvdraw.geometric.visitors;

import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.ConvexPolygon;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;

/**
 * A {@link GeometricalObjectVisitor} used for calculating the bounding box
 * {@link Rectangle} that fits all visited {@link GeometricalObject}'s.
 * 
 * @author Dorian Ivankovic
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Minimal x coordinate.
	 */
	private int xMin;

	/**
	 * Minimal y coordinate.
	 */
	private int yMin;

	/**
	 * Maximal x coordinate.
	 */
	private int xMax;

	/**
	 * Maximal y coordinate.
	 */
	private int yMax;

	/**
	 * Default constructor.
	 */
	public GeometricalObjectBBCalculator() {
		xMin = -1;
		yMin = -1;
		xMax = 0;
		yMax = 0;
	}

	@Override
	public void visit(Line line) {
		Point startPoint = line.getStartPoint();
		Point endPoint = line.getEndPoint();

		int x0 = startPoint.x < endPoint.x ? startPoint.x : endPoint.x;
		int y0 = startPoint.y < endPoint.y ? startPoint.y : endPoint.y;
		int x1 = x0 == startPoint.x ? endPoint.x : startPoint.x;
		int y1 = y0 == startPoint.y ? endPoint.y : startPoint.y;

		update(x0, y0, x1, y1);
	}

	/**
	 * Performs the update of the minimal and maximal x and y coordinate.
	 * 
	 * @param x0
	 *            - possible new x minimum
	 * @param y0
	 *            - possible new y minimum
	 * @param x1
	 *            - possible new x maximum
	 * @param y1
	 *            - possible new x maximum
	 */
	private void update(int x0, int y0, int x1, int y1) {
		if (xMin == -1 || x0 < xMin) {
			xMin = x0;
		}

		if (yMin == -1 || y0 < yMin) {
			yMin = y0;
		}

		if (x1 > xMax) {
			xMax = x1;
		}

		if (y1 > yMax) {
			yMax = y1;
		}
	}

	@Override
	public void visit(Circle circle) {
		int x0 = circle.getCenter().x - circle.getRadius();
		int y0 = circle.getCenter().y - circle.getRadius();
		int x1 = x0 + 2 * circle.getRadius();
		int y1 = y0 + 2 * circle.getRadius();

		update(x0, y0, x1, y1);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		this.visit((Circle) filledCircle);
	}

	/**
	 * Returns the bounding box of all visited objects.
	 * 
	 * @return the bounding box of all visited objects
	 */
	public Rectangle getBoundingBox() {
		return new Rectangle(xMin, yMin, xMax - xMin, yMax - yMin);
	}

	@Override
	public void visit(ConvexPolygon polygon) {
		int x0 = polygon.getPoints().stream().mapToInt(p->(int)p.x).min().getAsInt();
		int x1 = polygon.getPoints().stream().mapToInt(p->(int)p.x).max().getAsInt();
		int y0 = polygon.getPoints().stream().mapToInt(p->(int)p.y).min().getAsInt();
		int y1 = polygon.getPoints().stream().mapToInt(p->(int)p.y).max().getAsInt();
		
		update(x0, y0, x1, y1);
	}

}
