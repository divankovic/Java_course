package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Color;
import java.awt.Graphics2D;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * 
 * {@link Tool} used for drawing {@link FilledCircle} objects. The circle is
 * drawn after the first click as the mouse moves so the user can see the final
 * result.
 * 
 * @author Dorian Ivankovic
 *
 */
public class FilledCircleTool extends CircleTool {

	/**
	 * Background color.
	 */
	private Color bgColor;

	/**
	 * Constructor
	 * 
	 * @param drawingModel
	 *            - model to add new {@link FilledCircle}'s into
	 * @param fgColorProvider
	 *            - foreground color provider
	 * @param bgColorProvider
	 *            - backgroun color provider
	 */
	public FilledCircleTool(DrawingModel drawingModel, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		super(drawingModel, fgColorProvider);
		bgColorProvider.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				bgColor = newColor;
			}
		});
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (startPoint != null && endPoint != null) {
			g2d.setColor(bgColor);
			int radius = getRadius();
			g2d.fillOval(startPoint.x - radius, startPoint.y - radius, 2 * radius, 2 * radius);

			super.paint(g2d);
		}
	}

	@Override
	public void addGeometricalObject() {
		FilledCircle filledCircle = new FilledCircle(startPoint, getRadius(), fgColor, bgColor);
		drawingModel.add(filledCircle);
	}

}
