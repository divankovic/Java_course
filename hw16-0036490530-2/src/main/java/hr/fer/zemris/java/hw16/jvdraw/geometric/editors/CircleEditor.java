package hr.fer.zemris.java.hw16.jvdraw.geometric.editors;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;

/**
 * {@link GeometricalObjectEditor} used for editing {@link Circle}'s.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Circle that is being edited.
	 */
	private Circle circle;

	/**
	 * Circle center x coordinate editing field.
	 */
	protected JSpinner centerX;

	/**
	 * Circle center y coordinate editing field.
	 */
	protected JSpinner centerY;

	/**
	 * Circle radius editing field.
	 */
	protected JSpinner rad;

	/**
	 * Circle outline color editing field.
	 */
	protected JColorArea oColor;

	/**
	 * Constructor.
	 * 
	 * @param circle
	 *            - {@link Circle} that is being edited
	 */
	public CircleEditor(Circle circle) {
		super();
		this.circle = circle;

		setPreferredSize(new Dimension(80, 120));
		this.setLayout(new GridLayout(4, 2));

		initGUI();
	}

	/**
	 * Initializes the GUI of the editor.
	 */
	protected void initGUI() {

		this.add(new JLabel("center.x"));
		centerX = getSpinner(circle.getCenter().x);
		this.add(centerX);

		this.add(new JLabel("center.y"));
		centerY = getSpinner(circle.getCenter().y);
		this.add(centerY);

		this.add(new JLabel("radius"));
		rad = getSpinner(circle.getRadius());
		this.add(rad);

		this.add(new JLabel("Outline color"));
		oColor = new JColorArea(circle.getOutlineColor());
		this.add(oColor);

	}

	@Override
	public void checkEditing() {
		if ((int) rad.getValue() == 0) {
			throw new GeometricalEditingException("Radius must not be 0!");
		}
	}

	@Override
	public void acceptEditing() {
		circle.setCenter(new Point((int) centerX.getValue(), (int) centerY.getValue()));
		circle.setRadius((int) rad.getValue());
		circle.setOutlineColor(oColor.getCurrentColor());

		circle.notifyListeners(circle);
	}

}
