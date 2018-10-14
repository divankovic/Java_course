package hr.fer.zemris.java.hw16.jvdraw.model;

import hr.fer.zemris.java.hw16.jvdraw.JVDraw;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;

/**
 * Representst the model of the application {@link JVDraw} and holds all
 * {@link GeometricalObject}'s currently active in the application.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface DrawingModel {

	/**
	 * Returns the size of stored {@link GeometricalObject}'s.
	 * 
	 * @return size of stored objects
	 */
	int getSize();

	/**
	 * Returns the {@link GeometricalObject} at the specified <code>index</code>.
	 * 
	 * @param index
	 *            - index of the searched object
	 * @return {@link GeometricalObject} at the specified index
	 */
	GeometricalObject getObject(int index);

	/**
	 * Adds the {@link GeometricalObject} <code>object</code>.
	 * 
	 * @param object
	 *            - object to add into model
	 */
	void add(GeometricalObject object);

	/**
	 * Removes the specified object from the model.
	 * 
	 * @param object
	 *            - {@link GeometricalObject} to remove from the model
	 */
	void remove(GeometricalObject object);

	/**
	 * Changes the order of objects in the model by shifting the <code>object</code>
	 * by <code>offset</code> positions.
	 * 
	 * @param object
	 *            - object to change position of
	 * @param offset
	 *            - number of places to move the object
	 */
	void changeOrder(GeometricalObject object, int offset);

	/**
	 * Adds new {@link DrawingModelListener}.
	 * 
	 * @param l
	 *            - listener to add
	 */
	void addDrawingModelListener(DrawingModelListener l);

	/**
	 * Removes the listener from the object.
	 * 
	 * @param l
	 *            - listener to remove
	 */
	void removeDrawingModelListener(DrawingModelListener l);
}
