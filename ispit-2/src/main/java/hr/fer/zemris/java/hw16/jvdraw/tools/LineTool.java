package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * 
 * {@link Tool} used for drawing {@link Line} objects. The line is
 * drawn after the first click as the mouse moves so the user can see the final
 * result.
 * 
 * @author Dorian Ivankovic
 *
 */
public class LineTool extends AbstractTool{

	/**
	 * Constructor
	 * 
	 * @param drawingModel
	 *            - model to add new {@link Line}'s into
	 * @param fgColorProvider
	 *            - foreground color provider
	 */
	public LineTool(DrawingModel drawingModel, IColorProvider fgColorProvider) {
		super(drawingModel, fgColorProvider);
	}

	@Override
	public void paint(Graphics2D g2d) {
		if(startPoint!=null && endPoint!=null) {
			g2d.setColor(fgColor);
			g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
		}
	}

	@Override
	public void addGeometricalObject() {
		Line line = new Line(startPoint, endPoint, fgColor);
		drawingModel.add(line);
	}

}
