package hr.fer.zemris.java.hw16.jvdraw.colors;

import java.awt.Color;

/**
 * Listener of the {@link IColorProvider}'s color property.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface ColorChangeListener {

	/**
	 * The {@link IColorProvider} calls this method once it's color property
	 * changes.
	 * 
	 * @param source
	 *            - monitored {@link IColorProvider}
	 * @param oldColor
	 *            - old color property of the source
	 * @param newColor
	 *            - new color property of the source
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
