package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * The program demonstrates the use of {@link PrimListModel}, displays two
 * lists which are listeners of the model.
 * @author Dorian Ivankovic
 *
 */
public class PrimDemo extends JFrame{

	/**
	 * Unique indetifier
	 */
	private static final long serialVersionUID = 2552614965736703542L;

	/**
	 * Constructs a new <code>PrimListDemo</code> that displays two list's 
	 * of prime numbers and a button to add the next prime number.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(100,100);
		setSize(500,500);
		setTitle("PrimDemo");
		initGUI();
	}
	
	/**
	 * Initializes the GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JButton nextButton = new JButton("next");
		nextButton.addActionListener(event->model.next());
		
		JPanel centralPanel = new JPanel(new GridLayout(1,0));
		centralPanel.add(new JScrollPane(list1));
		centralPanel.add(new JScrollPane(list2));
		
		cp.add(centralPanel, BorderLayout.CENTER);
		cp.add(nextButton, BorderLayout.PAGE_END);
		
	}

	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments, not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new PrimDemo().setVisible(true);
		});
	}
}
