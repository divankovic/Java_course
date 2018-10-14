package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;

/**
 * Models an object containing a color property. The object is a subject and
 * notifies all {@link ColorChangeListener}'s about the color change.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface IColorProvider {

	/**
	 * Returns the current color property.
	 * 
	 * @return current color
	 */
	Color getCurrentColor();

	/**
	 * Adds new {@link ColorChangeListener}.
	 * 
	 * @param l
	 *            - new {@link ColorChangeListener}
	 */
	void addColorChangeListener(ColorChangeListener l);

	/**
	 * Removes the {@link ColorChangeListener} <code>l</code>.
	 * 
	 * @param l
	 *            - {@link ColorChangeListener} to remove
	 */
	void removeColorChangeListener(ColorChangeListener l);
}
