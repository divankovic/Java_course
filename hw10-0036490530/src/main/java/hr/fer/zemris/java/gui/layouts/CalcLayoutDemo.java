package hr.fer.zemris.java.gui.layouts;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demonstrates the use of {@link CalcLayout}.
 * @author Dorian Ivankovic
 *
 */
public class CalcLayoutDemo extends JFrame{
	
	
	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = -7443857046527196251L;
	
	/**
	 * Constructs a new <code>CalcLayoutDemo</code> at location (100,100) with 
	 * size (490,350).
	 */
	public CalcLayoutDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100,100);
		setTitle("Calc layout demo");
		setSize(490,350);
		initGUI();
	}

	/**
	 * Method initializes the GUI of the <code>CalcLayotDemo</code>.
	 */
	private void initGUI() {
		getContentPane().setLayout(new BorderLayout());
		JPanel panel = new JPanel(new CalcLayout(3));
		panel.add(getLabel("1,1"), "1,1");
		panel.add(getLabel("2,3"), "2,3");
		panel.add(getLabel("2,7"), "2,7");
		panel.add(getLabel("4,2"), "4,2");
		panel.add(getLabel("4,5"), new RCPosition(4,5));
		panel.add(getLabel("4,7"), new RCPosition(4,7));
		
		getContentPane().add(panel, BorderLayout.CENTER);
	}

	/**
	 * Gets the default label for this demo.
	 * @param name - text in the label
	 * @return default label
	 */
	private JLabel getLabel(String name) {
		JLabel label = new JLabel(name);
		label.setOpaque(true);
		label.setBackground(Color.BLUE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		return label;
	}
	
	/**
	 * This method is called once the program is run
	 * @param args - command line arguments, not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new CalcLayoutDemo().setVisible(true);
		});
	}
}
