package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;

/**
 * A component that displays current selected foreground and background color in
 * {@link JVDraw} using {@link JColorArea}'s.
 * 
 * @author Dorian Ivankovic
 *
 */
public class JColorInfoLabel extends JLabel implements ColorChangeListener {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Foreground color provider.
	 */
	private IColorProvider fgColorProvider;

	/**
	 * Backgroung color provider.
	 */
	private IColorProvider bgColorProvider;

	/**
	 * Constructs a new {@link JColorInfoLabel} using color providers.
	 * 
	 * @param fgColorProvider
	 *            - foreground color provider
	 * @param bgColorProvider
	 *            - background color provider
	 */
	public JColorInfoLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		this.fgColorProvider = fgColorProvider;
		this.bgColorProvider = bgColorProvider;

		fgColorProvider.addColorChangeListener(this);
		bgColorProvider.addColorChangeListener(this);

	}

	@Override
	public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
		this.setText("Foreground color: (" + getRGB(fgColorProvider.getCurrentColor()) + ")  Background color: ("
				+ getRGB(bgColorProvider.getCurrentColor()) + ").");
	}

	/**
	 * Creates the <code>String</code> representation of the {@link Color}.
	 * 
	 * @param color
	 *            - color to convert to <code>String</code>
	 * @return <code>String</code> representation of the <code>color</code>
	 */
	private String getRGB(Color color) {
		return color.getRed() + ", " + color.getGreen() + ", " + color.getBlue();
	}

}
