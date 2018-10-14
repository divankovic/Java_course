package hr.fer.zemris.java.hw16.jvdraw.geometric.editors;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;

/**
 * {@link GeometricalObjectEditor} used for changing {@link Line}'s parameters.
 * @author Dorian Ivankovic
 *
 */
public class LineEditor extends GeometricalObjectEditor {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Line that is being edited.
	 */
	private Line line;
	
	/**
	 * Line start x coordinate editing field.
	 */
	private JSpinner startX;
	
	/**
	 * Line start y coordinate editing field.
	 */
	private JSpinner startY;
	
	/**
	 * Line end x coordinate editing field.
	 */
	private JSpinner endX;
	
	/**
	 * Line end y coordinate editing field.
	 */
	private JSpinner endY;
	
	/**
	 * Line color editing field.
	 */
	private JColorArea lColor;
	
	/**
	 * Constructor
	 * @param line - line that is being edited
	 */
	public LineEditor(Line line) {
		super();
		this.line = line;
		
		setPreferredSize(new Dimension(80,120));
		this.setLayout(new GridLayout(6,2));
		
		this.add(new JLabel("start.x"));
		startX = getSpinner(line.getStartPoint().x);
		this.add(startX);
		
		this.add(new JLabel("start.y"));
		startY = getSpinner(line.getStartPoint().y);
		this.add(startY);
		
		this.add(new JLabel("end.x"));
		endX = getSpinner(line.getEndPoint().x);
		this.add(endX);
		
		this.add(new JLabel("end.y"));
		endY = getSpinner(line.getEndPoint().y);
		this.add(endY);
		
		this.add(new JLabel("Line color"));
		lColor = new JColorArea(line.getLineColor());
		this.add(lColor);
	}
	

	@Override
	public void checkEditing() {
		if(startX.getValue()==endX.getValue()
				&&startY.getValue()==endY.getValue()){
			throw new GeometricalEditingException("Start and end point must not be the same!");
		}
	}

	@Override
	public void acceptEditing() {
		line.setStartPoint(new Point((int)startX.getValue(), (int)startY.getValue()));
		line.setEndPoint(new Point((int)endX.getValue(), (int)endY.getValue()));
		line.setLineColor(lColor.getCurrentColor());
		
		line.notifyListeners(line);
	}

}
