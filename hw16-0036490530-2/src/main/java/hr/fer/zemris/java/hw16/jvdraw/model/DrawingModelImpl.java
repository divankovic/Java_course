package hr.fer.zemris.java.hw16.jvdraw.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObjectListener;

/**
 * Implementation of the {@link DrawingModel}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class DrawingModelImpl implements DrawingModel, GeometricalObjectListener {

	/**
	 * List of currently active {@link GeometricalObject}'s.
	 */
	private List<GeometricalObject> objects;

	/**
	 * Model listeners.
	 */
	private Set<DrawingModelListener> listeners;

	/**
	 * Used to notify {@link DrawingModelListener}.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	private static interface DrawingModelStrategy {

		/**
		 * Notify the listener <code>l</code> about the change in <code>source</code>
		 * beetween positions <code>index0</code> and <code>index1</code>.
		 * 
		 * @param l
		 *            - listener
		 * @param source
		 *            - monitored model
		 * @param index0
		 *            - begin index
		 * @param index1
		 *            - end index
		 */
		void process(DrawingModelListener l, DrawingModel source, int index0, int index1);
	}

	/**
	 * Default constructor.
	 */
	public DrawingModelImpl() {
		objects = new ArrayList<>();
		listeners = new HashSet<>();
	}

	@Override
	public int getSize() {
		return objects.size();
	}

	@Override
	public GeometricalObject getObject(int index) {
		return objects.get(index);
	}

	@Override
	public void add(GeometricalObject object) {
		objects.add(object);
		object.addGeometricalObjectListener(this);

		int index0 = objects.indexOf(object);
		int index1 = index0;
		fire((l, s, i0, i1) -> l.objectsAdded(s, i0, i1), index0, index1);
	}

	@Override
	public void remove(GeometricalObject object) {
		int index0 = objects.indexOf(object);
		int index1 = index0;

		objects.remove(object);

		fire((l, s, i0, i1) -> l.objectsRemoved(s, i0, i1), index0, index1);
	}

	@Override
	public void changeOrder(GeometricalObject object, int offset) {
		int index = objects.indexOf(object);
		objects.remove(object);
		objects.add(index + offset, object);

		int index0, index1;
		if (index < index + offset) {
			index0 = index;
			index1 = index + offset;
		} else {
			index0 = index + offset;
			index1 = index;
		}

		fire((l, s, i0, i1) -> l.objectsChanged(s, i0, i1), index0, index1);
	}

	@Override
	public void addDrawingModelListener(DrawingModelListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	@Override
	public void removeDrawingModelListener(DrawingModelListener l) {
		listeners.remove(l);
	}

	@Override
	public void geometricalObjectChanged(GeometricalObject o) {
		int index0 = objects.indexOf(o);
		int index1 = index0;
		fire((l, s, i0, i1) -> l.objectsChanged(s, i0, i1), index0, index1);
	}

	/**
	 * Notifies all listeners about the specific change beetween indexes
	 * <code>index0</code> and <code>index1</code> in the model.
	 * 
	 * @param strategy
	 *            - notifies the listeners about the specific change - addition,
	 *            removal, change
	 * @param index0
	 *            - begin index
	 * @param index1
	 *            - end index
	 */
	private void fire(DrawingModelStrategy strategy, int index0, int index1) {
		List<DrawingModelListener> listenersCpy = new ArrayList<>(listeners);

		for (DrawingModelListener listener : listenersCpy) {
			strategy.process(listener, this, index0, index1);
		}
	}
}
