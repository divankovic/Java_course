package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstrates the use of {@link BarChartComponent}.
 * Draws the bar chart specified in a .txt file, in format:<br>
 * <ul>
 * 	<li>xLabel</li>
 * 	<li>yLabel</li>
 * 	<li>x1,y1 x2,y2 x3,y3 ... - value pairs : x,y of the value separated by ",", values separated by spaces</li>
 *	<li>yMin</li>
 *	<li>yMax</li>
 *	<li>yStep</li>
 * </ul>
 *  The program takes a path to this file as input command line argument.
 * @author Dorian Ivankovic
 *
 */
public class BarChartDemo extends JFrame{

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 8001783544740936988L;
	
	/**
	 * Chart to be displayed.
	 */
	private static BarChart chart;
	
	/**
	 * Path to the .txt file specifying chart content.
	 */
	private static String path;
	
	/**
	 * Constructs a new <code>BarChartDemo</code> used to draw the chart.
	 */
	public BarChartDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100,100);
		setSize(600,400);
		setTitle("Bar Chart");
		initGUI();
	}
	
	/**
	 * Initializes the GUI of the <code>BarChartDemo</code>.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		JLabel chartDataPath = new JLabel(path);
		chartDataPath.setHorizontalAlignment(SwingConstants.CENTER);
		
		cp.add(chartDataPath, BorderLayout.PAGE_START);
		cp.add(new BarChartComponent(chart), BorderLayout.CENTER);
	}

	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments.
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println("One argument expected: path to chart data file.");
			return;
		}
		
		try {
			Path p = Paths.get(args[0]);
			path = p.toAbsolutePath().normalize().toString();
			List<String> lines = Files.readAllLines(p);
			String xLabel = lines.get(0);
			String yLabel = lines.get(1);
			
			List<XYValue> values = new ArrayList<>();
			
			String[] vals = lines.get(2).split("\\s+");
			for(String val : vals) {
				String[] coordinates = val.split(",");
				values.add(new XYValue(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
			}
			
			
			int yMin = Integer.parseInt(lines.get(3));
			int yMax = Integer.parseInt(lines.get(4));
			int yStep = Integer.parseInt(lines.get(5));
			
			chart = new BarChart(values, xLabel, yLabel, yMin, yMax, yStep);
		}catch(IOException ex) {
			System.out.println("Couldn't open file");
			return;
		}catch(NumberFormatException | IndexOutOfBoundsException ex) {
			System.out.println("Illegal data format, expected : ");
			System.out.println("lines 1 and 2 = x and y chart label.");
			System.out.println("line 3 chart elements in format: x1,y1 x2,y2 x3,y3 ..., where all x's and y's are whole numbers");
			System.out.println("line 4, 5, 6 yMin, yMax and yStep, where all numbers all whole and yMin<yMax and yStep<(yMax-yMin)");
			return;
		}catch(IllegalArgumentException ex) {
			System.out.println(ex.getMessage());
			return;
		}
		
		SwingUtilities.invokeLater(()->new BarChartDemo().setVisible(true));
	}
}
