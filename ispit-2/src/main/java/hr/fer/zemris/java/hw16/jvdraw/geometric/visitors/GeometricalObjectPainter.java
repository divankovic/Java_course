package hr.fer.zemris.java.hw16.jvdraw.geometric.visitors;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.ConvexPolygon;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;

/**
 * A {@link GeometricalObjectVisitor} used for painting
 * {@link GeometricalObject} using {@link Graphics2D}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * {@link Graphics2D} used for drawing objects.
	 */
	private Graphics2D graphics;

	/**
	 * Constructor
	 * 
	 * @param graphics
	 *            - {@link Graphics2D} used for drawing objects
	 */
	public GeometricalObjectPainter(Graphics2D graphics) {
		this.graphics = graphics;
	}

	@Override
	public void visit(Line line) {
		Point startPoint = line.getStartPoint();
		Point endPoint = line.getEndPoint();

		graphics.setColor(line.getLineColor());
		graphics.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
	}

	@Override
	public void visit(Circle circle) {
		drawCircleOutline(circle.getCenter(), circle.getRadius(), circle.getOutlineColor());
	}

	/**
	 * Draws the circle outline.
	 * 
	 * @param center
	 *            - center of the circle
	 * @param radius
	 *            - circle radius
	 * @param outlineColor
	 *            - color of the outline
	 */
	private void drawCircleOutline(Point center, int radius, Color outlineColor) {
		graphics.setColor(outlineColor);
		graphics.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getCenter();
		int radius = filledCircle.getRadius();

		graphics.setColor(filledCircle.getFillColor());
		graphics.fillOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);

		drawCircleOutline(center, radius, filledCircle.getOutlineColor());
	}

	@Override
	public void visit(ConvexPolygon polygon) {
		graphics.setColor(polygon.getFillColor());
		graphics.fillPolygon(ConvexPolygon.getXCoordinates(polygon.getPoints()), ConvexPolygon.getYCoordinates(polygon.getPoints()), polygon.getPoints().size());
		
		graphics.setColor(polygon.getOutlineColor());
		graphics.drawPolygon(ConvexPolygon.getXCoordinates(polygon.getPoints()), ConvexPolygon.getYCoordinates(polygon.getPoints()), polygon.getPoints().size());
	}

}
