package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * Implementation of the {@link Tool} interface providing methods used by all
 * object specific tools.
 * 
 * @author Dorian Ivankovic
 *
 */
public abstract class AbstractTool implements Tool {

	/**
	 * Start point - first click.
	 */
	protected Point startPoint;

	/**
	 * End point - second click.
	 */
	protected Point endPoint;

	/**
	 * Foregroung color.
	 */
	protected Color fgColor;

	/**
	 * Drawing model - used to add drawn objects into.
	 */
	protected DrawingModel drawingModel;

	/**
	 * Default constructor.
	 * 
	 * @param drawingModel
	 *            - used to add drawn objects into
	 * @param fgColorProvider
	 *            - foreground color provider
	 */
	public AbstractTool(DrawingModel drawingModel, IColorProvider fgColorProvider) {
		this.drawingModel = drawingModel;
		fgColorProvider.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				fgColor = newColor;
			}
		});
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (startPoint == null) {
			startPoint = new Point(e.getPoint());
		} else {
			if (endPoint == null || endPoint.equals(startPoint))
				return;
			addGeometricalObject();
			reset();
		}
	}

	/**
	 * The method adds the specific {@link GeometricalObject} into
	 * <code>drawingModel</code>.
	 */
	public abstract void addGeometricalObject();

	@Override
	public void mouseMoved(MouseEvent e) {
		if (startPoint != null) {
			endPoint = new Point(e.getPoint());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	/**
	 * The method resets the start and end point of the tool.
	 */
	public void reset() {
		startPoint = null;
		endPoint = null;
	}
}
