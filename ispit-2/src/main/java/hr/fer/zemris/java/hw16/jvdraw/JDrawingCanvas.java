package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.tools.Tool;

/**
 * The component responsible for drawing {@link GeometricalObject}'s. Each time
 * the {@link DrawingModel} of the application changes, the display is updated.
 * 
 * @author Dorian Ivankovic
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Current {@link Tool} used for drawing new objects on the canvas.
	 */
	private Tool currentState;

	/**
	 * Monitored {@link DrawingModel}
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructs a new {@link JDrawingCanvas} using the {@link DrawingModel} to
	 * monitor.
	 * 
	 * @param drawingModel
	 *            - monitored {@link DrawingModel}
	 */
	public JDrawingCanvas(DrawingModel drawingModel) {
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent event) {
				currentState.mouseClicked(event);
			}

		});
		this.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent event) {
				currentState.mouseMoved(event);
				repaint();
			}
		});

		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);
	}

	/**
	 * Sets the current state(tool) used for drawing new
	 * {@link GeometricalObject}'s.
	 * 
	 * @param currentState
	 *            - new {@link Tool}
	 */
	public void setCurrentState(Tool currentState) {
		this.currentState = currentState;
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		GeometricalObjectVisitor visitor = new GeometricalObjectPainter(g2d);

		for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
			drawingModel.getObject(i).accept(visitor);
		}

		currentState.paint(g2d);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
