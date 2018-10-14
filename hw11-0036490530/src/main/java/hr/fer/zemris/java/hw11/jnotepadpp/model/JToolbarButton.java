package hr.fer.zemris.java.hw11.jnotepadpp.model;

import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 * Custom toolbar non text button, all other behaviours are the
 * same as a regular JButton.
 * @author Dorian Ivankovic
 *
 */
public class JToolbarButton extends JButton{

	/**
	 * Unique identified
	 */
	private static final long serialVersionUID = 6872267770880055L;
	
	/**
	 * Constructs a new {@link JToolbarButton}.
	 * @param action - action performed
	 */
	public JToolbarButton(AbstractAction action) {
		super(action);
	}

	
	@Override
	public void setText(String text) {
		
	}
	
}
