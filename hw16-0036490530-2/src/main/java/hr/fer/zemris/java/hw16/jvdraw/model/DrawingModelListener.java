package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;

/**
 * Listener of the {@link DrawingModel}.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface DrawingModelListener {

	/**
	 * Called when {@link GeometricalObject} are added into the model.
	 * 
	 * @param source
	 *            - monitored model
	 * @param index0
	 *            - begin index of the addition
	 * @param index1
	 *            - end index of the addition
	 */
	void objectsAdded(DrawingModel source, int index0, int index1);

	/**
	 * Called when {@link GeometricalObject}'s are removed from the model.
	 * 
	 * @param source
	 *            - monitored model
	 * @param index0
	 *            - begin index of the removal
	 * @param index1
	 *            - end index of the removal
	 */
	void objectsRemoved(DrawingModel source, int index0, int index1);

	/**
	 * Called when {@link GeometricalObject}'s in the model are changed.
	 * 
	 * @param source
	 *            - monitored model
	 * @param index0
	 *            - begin index of the change
	 * @param index1
	 *            - end index of the change
	 */
	void objectsChanged(DrawingModel source, int index0, int index1);
}
