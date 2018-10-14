package hr.fer.zemris.java.gui.charts;

import java.awt.Color;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * A component used for displaying a {@link BarChart}.
 * @author Dorian Ivankovic
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = -2290688286165650352L;
	
	/**
	 * Chart background color.
	 */
	public static final Color BACKGROUND_COLOR = Color.WHITE;
	
	/**
	 * Chart axes description color.
	 */
	public static final Color AXES_DESCRIPTION_COLOR = Color.BLACK;
	
	/**
	 * Chart axes color.
	 */
	public static final Color AXES_COLOR = Color.BLACK;
	
	/**
	 * Chart grid color.
	 */
	public static final Color GRID_COLOR = Color.decode("0xC0C0C0");
	
	/**
	 * Chart axes label color.
	 */
	public static final Color AXES_LABELS_COLOR = Color.BLACK;
	
	/**
	 * Color of the bar's on the chart.
	 */
	public static final Color BAR_COLOR = Color.decode("0xFF8000");

	/**
	 * Chart model that contains all relevant information about the chart.
	 */
	private BarChart chart;
	
	/**
	 * Current width of the component.
	 */
	private int width;
	
	/**
	 * Current height of the component.
	 */
	private int height;
	
	/**
	 * Chart's origin x coordinate.
	 */
	private int xStart;
	
	/**
	 * Chart's x axis end coordinate.
	 */
	private int xEnd;
	
	/**
	 * Chart's origin y coordinate.
	 */
	private int yStart;
	
	/**
	 * Chart's y axis end coordinate.
	 */
	private int yEnd;
	
	/**
	 * Minimal x value of the chart.
	 */
	private int xMinChart;
	
	/**
	 * Maximal x value of the chart.
	 */
	private int xMaxChart;
	
	/**
	 * Width of the sigle step on the x axis.
	 */
	private int xWidth;
	
	/**
	 * Height of the single step on the y axis.
	 */
	private int yHeight;

	/**
	 * Constructs a new <code>BarChartComponent</code> using 
	 * a chart to display.
	 * @param chart - {@link BarChart} to display
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}

	@Override
	public void paint(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;

		List<XYValue> values = chart.getValues();
		if (values == null || values.isEmpty())
			return;
		FontMetrics fm = g.getFontMetrics();

		width = getWidth();
		height = getHeight();

		g.setColor(BACKGROUND_COLOR);
		g.fillRect(0, 0, width, height);

		int labelGap = 15;
		int yMaxWidth = fm.stringWidth(String.valueOf(chart.getyMax()));

		xStart = labelGap * 2 + fm.getHeight() + yMaxWidth + 5;
		yStart = height - 2 * labelGap - 2 * fm.getHeight() - 5;
		xEnd = width - 5;
		yEnd = 5;

		int[] xBounds = getxBounds();
		xMinChart = xBounds[0];
		xMaxChart = xBounds[1];
		int yMinChart = chart.getyMin();
		int yMaxChart = chart.getyMax();
		int yStepChart = chart.getyStep();
		int ySteps = (yMaxChart - yMinChart) / yStepChart;

		xWidth = (xEnd - xStart - 7) / (xMaxChart - xMinChart + 1);
		yHeight = (yStart - yEnd - 7) / ySteps;

		drawCoordinateAxesAndLabels(g, fm);

		drawGrid(g, fm);

		drawData(g);
	}

	/**
	 * Calculates the minimal and maximal x values from the values in the chart.
	 * @return minimal and maximal x values in the chart
	 */
	private int[] getxBounds() {
		List<XYValue> values = chart.getValues();

		int xMin = values.get(0).getX();
		int xMax = xMin;

		for (XYValue value : values) {
			int x = value.getX();
			if (x < xMin)
				xMin = x;
			if (x > xMax)
				xMax = x;
		}

		return new int[] { xMin, xMax };
	}

	/**
	 * Draws the coordinate axes and axes labels.
	 * @param g - Graphics2D object used for drawing
	 * @param fm - FontMetrics used for String drawing
	 */
	private void drawCoordinateAxesAndLabels(Graphics2D g, FontMetrics fm) {

		g.setColor(AXES_COLOR);
		
		// xAxis
		g.drawLine(xStart, yStart, xEnd, yStart);
		g.drawLine(xEnd - 5, yStart - 2, xEnd, yStart);
		g.drawLine(xEnd - 5, yStart + 2, xEnd, yStart);

		// yAxis
		g.drawLine(xStart, yStart, xStart, yEnd);
		g.drawLine(xStart - 2, yEnd + 5, xStart, yEnd);
		g.drawLine(xStart + 2, yEnd + 5, xStart, yEnd);

		g.setColor(AXES_DESCRIPTION_COLOR);
		
		// xDescription
		String xDescription = chart.getxDescription();
		g.drawString(xDescription, xStart + ((xEnd - xStart) - fm.stringWidth(xDescription)) / 2,
				height - 15 - fm.getDescent());

		// yDescription
		String yDescription = chart.getyDescription();
		AffineTransform defaultAt = g.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);

		g.setTransform(at);
		g.drawString(yDescription, -yStart + ((yStart - yEnd) - fm.stringWidth(yDescription)) / 2, 15 + fm.getAscent());
		g.setTransform(defaultAt);

	}

	/**
	 * Draws the x-y grid of the chart.
	 * @param g - Graphics2D object used for drawing
	 * @param fm - FontMetrics used for String drawing
	 */
	private void drawGrid(Graphics g, FontMetrics fm) {

		int yMinChart = chart.getyMin();
		int yMaxChart = chart.getyMax();
		int yStepChart = chart.getyStep();
		int ySteps = (yMaxChart - yMinChart) / yStepChart;
		
		// yGrid
		int xEndLine = (xMaxChart - xMinChart + 1) * xWidth + xStart + 2;
		for (int i = 0; i <= ySteps; i++) {
			String yLabel = String.valueOf(yMinChart + i * yStepChart);

			g.setColor(AXES_LABELS_COLOR);
			g.drawString(yLabel, xStart - 5 - fm.stringWidth(yLabel), yStart + fm.getAscent() / 2 - i * yHeight);

			if (i == 0)
				continue;

			g.setColor(GRID_COLOR);
			g.drawLine(xStart - 2, yStart - i * yHeight, xEndLine, yStart - i * yHeight);
		}

		// xGrid
		int yEndLine = yStart - yHeight * ySteps - 2;
		for (int i = xMinChart; i <= xMaxChart; i++) {
			String xLabel = String.valueOf(i);

			g.setColor(AXES_LABELS_COLOR);
			g.drawString(xLabel, xStart + (i - xMinChart) * xWidth + (xWidth - fm.stringWidth(xLabel)) / 2,
					yStart + 5 + fm.getAscent());

			g.setColor(GRID_COLOR);
			g.drawLine(xStart + (i - xMinChart + 1) * xWidth, yStart + 2, xStart + (i - xMinChart + 1) * xWidth, yEndLine);
		}
	}

	/**
	 * Draws bars representing values of the chart.
	* @param g - Graphics2D object used for drawing
	 */
	private void drawData(Graphics g) {
		
		int yMinChart = chart.getyMin();
		int yStepChart = chart.getyStep();
		List<XYValue> values = chart.getValues();

		for (int i = 0, n = values.size(); i < n; i++) {
			XYValue value = values.get(i);
			int xBarStart = xStart + (value.getX() - xMinChart) * xWidth;
			int barHeight = (int) ((value.getY() - yMinChart) / (double) yStepChart * yHeight);

			g.setColor(BAR_COLOR);
			g.fillRect(xBarStart, yStart - barHeight, xWidth, barHeight);

			g.setColor(i == 0 ? AXES_COLOR : BACKGROUND_COLOR);
			g.drawLine(xBarStart, yStart, xBarStart, yStart - barHeight);
		}
	}

}
