package hr.fer.zemris.java.hw16.jvdraw.geometric;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectVisitor;

/**
 * Represents the geometrical object.
 * 
 * @author Dorian Ivankovic
 *
 */
public abstract class GeometricalObject {

	/**
	 * Listeners of the object's state.
	 */
	protected Set<GeometricalObjectListener> listeners;

	/**
	 * Default constructor.
	 */
	public GeometricalObject() {
		this.listeners = new HashSet<>();
	}

	/**
	 * Used for visiting the object using the Visitor design pattern and double
	 * dispatch method.
	 * 
	 * @param v
	 *            - {@link GeometricalObjectVisitor}
	 */
	public abstract void accept(GeometricalObjectVisitor v);

	/**
	 * Returns the {@link GeometricalObjectEditor} panel used to alter this
	 * {@link GeometricalObject}.
	 * 
	 * @return panel used to alter this object
	 */
	public abstract GeometricalObjectEditor createGeometricalObjectEditor();

	/**
	 * Add new {@link GeometricalObjectListener}.
	 * 
	 * @param l
	 *            - new listener
	 */
	public void addGeometricalObjectListener(GeometricalObjectListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	/**
	 * Removes the {@link GeometricalObjectListener} <code>l</code>.
	 * 
	 * @param l
	 *            - listener to remove
	 */
	public void removeGeometricalObjectListener(GeometricalObjectListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies the listeners about the change of the object.
	 * 
	 * @param object
	 *            - changed {@link GeometricalObject}.
	 */
	public void notifyListeners(GeometricalObject object) {
		List<GeometricalObjectListener> listenersCpy = new ArrayList<>(listeners);
		for (GeometricalObjectListener listener : listenersCpy) {
			listener.geometricalObjectChanged(object);
		}
	}

	/**
	 * Turns the object into .jvd format.
	 * 
	 * @return string representation of object's .jvd format.
	 */
	public abstract String toJvd();
}
