package hr.fer.zemris.java.hw16.jvdraw.geometric;

/**
 * Listener of the {@link GeometricalObject}'s state.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface GeometricalObjectListener {

	/**
	 * Called when the {@link GeometricalObject} <code>o</code> has been changed so
	 * that the canvas can reapaint the whole picture.
	 * 
	 * @param o
	 *            - changed {@link GeometricalObject}
	 */
	void geometricalObjectChanged(GeometricalObject o);
}
