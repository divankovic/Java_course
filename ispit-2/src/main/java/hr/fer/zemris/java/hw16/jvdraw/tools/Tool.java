package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw16.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;

/**
 * State used for monitoring the interaction with the user through
 * {@link JDrawingCanvas} and drawing {@link GeometricalObject}'s using mouse
 * clicks.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface Tool {

	/**
	 * Called when the mouse is pressed.
	 * 
	 * @param e
	 *            - event
	 */
	void mousePressed(MouseEvent e);

	/**
	 * Called when the mouse is released.
	 * 
	 * @param e
	 *            - event
	 */
	void mouseReleased(MouseEvent e);

	/**
	 * Called when the mouse is clicked.
	 * 
	 * @param e
	 *            -event
	 */
	void mouseClicked(MouseEvent e);

	/**
	 * Called when the mouse is moved.
	 * 
	 * @param e
	 *            - event
	 */
	void mouseMoved(MouseEvent e);

	/**
	 * Called when the mouse is dragged.
	 * 
	 * @param e
	 *            - event
	 */
	void mouseDragged(MouseEvent e);

	/**
	 * Called when the {@link GeometricalObject} object needs to be painted using
	 * {@link Graphics2D}.
	 * 
	 * @param g2d
	 *            - used to draw {@link GeometricalObject}'s
	 */
	void paint(Graphics2D g2d);
}
