package hr.fer.zemris.java.hw16.jvdraw.tools;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * The class provides {@link Tool}'s for specific {@link GeometricalObject}'s.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ToolProvider {

	/**
	 * {@link Line} tool.
	 */
	private static Tool lineTool;

	/**
	 * {@link Circle} tool.
	 */
	private static Tool circleTool;

	/**
	 * {@link FilledCircle} tool.
	 */
	private static Tool fCircleTool;
	
	private static Tool convexPolygonTool;

	/**
	 * Initializes the tool provider.
	 * 
	 * @param drawingModel
	 *            - model to add new objects into
	 * @param fgColorProvider
	 *            - foreground color provider
	 * @param bgColorProvider
	 *            - background color provider
	 */
	public static void init(JFrame frame, DrawingModel drawingModel, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		lineTool = new LineTool(drawingModel, fgColorProvider);
		circleTool = new CircleTool(drawingModel, fgColorProvider);
		fCircleTool = new FilledCircleTool(drawingModel, fgColorProvider, bgColorProvider);
		convexPolygonTool = new ConvexPolygonTool(frame, drawingModel, fgColorProvider, bgColorProvider);
	}

	/**
	 * Returns the line tool.
	 * 
	 * @return the line tool.
	 */
	public static Tool getLineTool() {
		return lineTool;
	}

	/**
	 * Returns the circle tool.
	 * 
	 * @return the circle tool
	 */
	public static Tool getCircleTool() {
		return circleTool;
	}

	/**
	 * Returnst the filled circle tool.
	 * 
	 * @return - filled circle tool
	 */
	public static Tool getfCircleTool() {
		return fCircleTool;
	}
	
	public static Tool getConvexPolygonTool() {
		return convexPolygonTool;
	}
}
