package hr.fer.zemris.java.gui.charts;

/**
 * Represents a (x,y) value pair used in {@link BarChart}.
 * @author Dorian Ivankovic
 *
 */
public class XYValue {
	
	/**
	 * x coordinate
	 */
	private int x;
	
	/**
	 * y coordinate
	 */
	private int y;

	/**
	 * Constructs a new {@link XYValue} using it's x and y coordinate.
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Returns the x coordinate of this <code>XYValue</code>.
	 * @return x coordinate
	 */
	public int getX() {
		return x;
	}


	/**
	 * Returns the y coordinate of this <code>XYValue</code>.
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
	
}
