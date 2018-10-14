package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.JComponent;
import javax.swing.JPopupMenu;

/**
 * Utility class providing helper methods used by all actions in this package
 * such as getting the top level Container for appropriate dialog presentation.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ActionUtils {

	/**
	 * Extracts the top level container from given action event.
	 * 
	 * @param event
	 *            - action event of the component
	 * @return top level container
	 */
	public static Container getTopLevelParent(ActionEvent event) {

		JComponent component = (JComponent) event.getSource();
		Container parent = component.getTopLevelAncestor();

		if (parent == null) {
			parent = ((JComponent) ((JPopupMenu) component.getParent()).getInvoker()).getTopLevelAncestor();
		}

		return parent;

	}

}
