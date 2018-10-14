package hr.fer.zemris.java.hw16.jvdraw.geometric.editors;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;

/**
 * {@link GeometricalObjectEditor} used for editing {@link FilledCircle}'s.
 * 
 * @author Dorian Ivankovic
 *
 */
public class FilledCircleEditor extends CircleEditor {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Filled circle that is being edited.
	 */
	private FilledCircle filledCircle;

	/**
	 * Circle fill color editing field.
	 */
	private JColorArea fColor;

	/**
	 * Constructor
	 * 
	 * @param filledCircle
	 *            - {@link FilledCircle} that is being edited
	 */
	public FilledCircleEditor(FilledCircle filledCircle) {
		super(filledCircle);
		this.filledCircle = filledCircle;

		setPreferredSize(new Dimension(80, 120));
		this.setLayout(new GridLayout(5, 2));

		this.add(new JLabel("Fill color"));
		fColor = new JColorArea(filledCircle.getFillColor());
		this.add(fColor);
	}

	@Override
	public void acceptEditing() {
		filledCircle.setCenter(new Point((int) centerX.getValue(), (int) centerY.getValue()));
		filledCircle.setRadius((int) rad.getValue());
		filledCircle.setOutlineColor(oColor.getCurrentColor());
		filledCircle.setFillColor(fColor.getCurrentColor());

		filledCircle.notifyListeners(filledCircle);

	}

}
