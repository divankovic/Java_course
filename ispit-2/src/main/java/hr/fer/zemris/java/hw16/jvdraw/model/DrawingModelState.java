package hr.fer.zemris.java.hw16.jvdraw.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Represents the state of the {@link DrawingModel}. Tracks if the model is
 * modified and the path to the file the model was saved to.
 * 
 * @author Dorian Ivankovic
 *
 */
public class DrawingModelState implements DrawingModelListener {

	/**
	 * Path to the file the model was saved to (or null if it wasn't saved).
	 */
	private Path path;

	/**
	 * Flag that indicates whether or not the model has been modified.
	 */
	private boolean modified;

	/**
	 * Model state listeners.
	 */
	private Set<DrawingModelStateListener> listeners = new HashSet<>();

	/**
	 * Default constructor.
	 */
	public DrawingModelState() {

	}

	/**
	 * Returns the path to the file where the model was saved.
	 * 
	 * @return path to the file where the model was saved
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Sets the path to the file where the model was saved
	 * 
	 * @param path
	 *            - path to the file where the model was saved
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Returns true if the model is modified, false otherwise.
	 * 
	 * @return true if the model is modified, false otherwise
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * Sets the modification flag of the model.
	 * 
	 * @param modified
	 *            - modification flag
	 */
	public void setModified(boolean modified) {
		if (this.modified == modified)
			return;
		this.modified = modified;

		if (modified) {
			notify(l -> l.drawingModelStateModified());
		} else {
			notify(l -> l.drawingModelStateSaved());
		}

	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		setModified(true);
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		setModified(true);
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		setModified(true);
	}

	/**
	 * Adds new listener.
	 * 
	 * @param l
	 *            - new listener
	 */
	public void addListener(DrawingModelStateListener l) {
		if (l != null) {
			listeners.add(l);
		}
	}

	/**
	 * Removes the listener.
	 * 
	 * @param l
	 *            - listener to remove
	 */
	public void removeListener(DrawingModelStateListener l) {
		listeners.remove(l);
	}

	/**
	 * Notifies all listeners about the change specified by <code>action</code>.
	 * 
	 * @param action
	 *            - specifies the change - state modified or saved
	 */
	private void notify(Consumer<DrawingModelStateListener> action) {
		List<DrawingModelStateListener> listenersCpy = new ArrayList<>(listeners);

		for (DrawingModelStateListener l : listenersCpy) {
			action.accept(l);
		}
	}

}
