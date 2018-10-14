package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * {@link Tool} used for drawing {@link Circle} objects. The circle is drawn
 * after the first click as the mouse moves so the user can see the final
 * result.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CircleTool extends AbstractTool {

	/**
	 * Constructor
	 * 
	 * @param drawingModel
	 *            - model to add new {@link Circle}'s into
	 * @param fgColorProvider
	 *            - foreground color provider
	 */
	public CircleTool(DrawingModel drawingModel, IColorProvider fgColorProvider) {
		super(drawingModel, fgColorProvider);
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (startPoint != null && endPoint != null) {
			g2d.setColor(fgColor);
			int radius = getRadius();
			g2d.drawOval(startPoint.x - radius, startPoint.y - radius, 2 * radius, 2 * radius);
		}
	}

	/**
	 * Calculates the circle's radius from the start and end point.
	 * 
	 * @return circle radius
	 */
	protected int getRadius() {
		return (int) Math.sqrt(Math.pow(startPoint.x - endPoint.x, 2) + Math.pow(startPoint.y - endPoint.y, 2));
	}

	@Override
	public void addGeometricalObject() {
		Circle circle = new Circle(startPoint, getRadius(), fgColor);
		drawingModel.add(circle);
	}
}
