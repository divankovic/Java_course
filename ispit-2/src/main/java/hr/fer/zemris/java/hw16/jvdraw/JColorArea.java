package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;

/**
 * Color component used for choosing color. The current color can be changed by
 * clicking on the component when the new color can be selected on
 * {@link JColorChooser}. The component is painted in currently selected color
 * and holds the selected color as a private property.
 * 
 * @author Dorian Ivankovic
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Current color.
	 */
	private Color selectedColor;

	/**
	 * Listeners intereste in current color change.
	 */
	private Set<ColorChangeListener> listeners;

	/**
	 * Constuctor
	 * 
	 * @param selectedColor
	 *            - inital <code>selectedColor</code>
	 */
	public JColorArea(Color selectedColor) {
		this.selectedColor = selectedColor;
		listeners = new HashSet<>();

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				Color newColor = JColorChooser.showDialog(JColorArea.this.getTopLevelAncestor(), "Choose color",
						selectedColor);
				if (newColor != null && !newColor.equals(selectedColor)) {
					setCurrentColor(newColor);
				}
			}

		});

	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		if (l != null) {
			listeners.add(l);
			l.newColorSelected(this, selectedColor, selectedColor);
		}
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(15, 15);
	}

	/**
	 * Sets the <code>selectedColor</code>.
	 * 
	 * @param newColor
	 *            - new color
	 */
	public void setCurrentColor(Color newColor) {
		Color oldColor = selectedColor;
		selectedColor = newColor;
		notifyListeners(oldColor);

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(selectedColor);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}

	/**
	 * Notifies all {@link ColorChangeListener}'s about the color change.
	 * 
	 * @param oldColor
	 *            - old color
	 */
	private void notifyListeners(Color oldColor) {

		List<ColorChangeListener> listenersCpy = new ArrayList<>(listeners);
		for (ColorChangeListener listener : listenersCpy) {
			listener.newColorSelected(this, oldColor, selectedColor);
		}

	}

}
