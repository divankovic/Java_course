package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a simple <a href="https://en.wikipedia.org/wiki/Bar_chart">bar chart</a>,
 * that contains {@link XYValue}'s that need to be displayed, x and y
 * axis description and minimal and maximal y value displayed on the chart.
 * @author Dorian Ivankovic
 *
 */
public class BarChart {
	
	/**
	 * Values to be displayed.
	 */
	private List<XYValue> values;
	
	/**
	 * X axis description.
	 */
	private String xDescription;
	
	/**
	 * Y axis description.
	 */
	private String yDescription;
	
	/**
	 * Minimal y value.
	 */
	private int yMin;
	
	/**
	 * Maximal y value.
	 */
	private int yMax;
	
	/**
	 * Step of the y axis from yMin to yMax.
	 */
	private int yStep;

	/**
	 * Constructs a new {@link BarChart} using all relevant information.
	 * yMax is set to the first largest number that is divisible by yStep.
	 * @param values - values of the chart
	 * @param xDescription - description of the x axis
	 * @param yDescription - description of the y axis
	 * @param yMin - minimal y value on the chart
	 * @param yMax - maximal y value on the chart
	 * @param yStep - y step
	 * @throws IllegalArgumentException if yMin &gt; yMax
	 */
	public BarChart(List<XYValue> values, String xDescription, String yDescription,
			int yMin, int yMax, int yStep) {
		this.values = new ArrayList<>(values);
		
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		
		if(yMin>yMax) throw new IllegalArgumentException("Min bound must be less than max bound.");
		if(yStep<1) throw new IllegalArgumentException("Step must be greater than 0.");
		
		this.yMin = yMin;
		this.yStep = yStep;
		
		if((yMax-yMin)%yStep!=0) {
			this.yMax = ((yMax-yMin)/yStep + 1)*yStep;
		}else {
			this.yMax = yMax;
		}

	}

	/**
	 * Returns the values of the chart.
	 * @return values of the chart.
	 */
	public List<XYValue> getValues() {
		return values;
	}

	/**
	 * Returns the x axis description.
	 * @return x axis description
	 */
	public String getxDescription() {
		return xDescription;
	}


	/**
	 * Returns the y axis description.
	 * @return y axis description
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Returns the minimal y value on the chart.
	 * @return minimal y value on the chart
	 */
	public int getyMin() {
		return yMin;
	}


	/**
	 * Returns the maximal y value on the chart.
	 * @return maximal y value on the chart
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Returns the y step used on the chart.
	 * @return y step
	 */
	public int getyStep() {
		return yStep;
	}
	
}
