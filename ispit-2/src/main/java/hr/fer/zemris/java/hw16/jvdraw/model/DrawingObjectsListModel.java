package hr.fer.zemris.java.hw16.jvdraw.model;

import javax.swing.AbstractListModel;

import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;

/**
 * {@link AbstractListModel} adapter around {@link DrawingModel}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class DrawingObjectsListModel extends AbstractListModel<GeometricalObject> implements DrawingModelListener {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Monitored {@link DrawingModel}.
	 */
	private DrawingModel drawingModel;

	/**
	 * Default constructor
	 * 
	 * @param drawingModel
	 *            - monitored drawing model
	 */
	public DrawingObjectsListModel(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;
		drawingModel.addDrawingModelListener(this);
	}

	@Override
	public GeometricalObject getElementAt(int position) {
		return drawingModel.getObject(position);
	}

	@Override
	public int getSize() {
		return drawingModel.getSize();
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		fireIntervalAdded(this, index0, index1);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		fireIntervalRemoved(this, index0, index1);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		fireContentsChanged(this, index0, index1);
	}

}
