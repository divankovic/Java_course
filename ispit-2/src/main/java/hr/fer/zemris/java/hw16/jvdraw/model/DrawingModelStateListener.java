package hr.fer.zemris.java.hw16.jvdraw.model;

/**
 * Listener of the {@link DrawingModelState}.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface DrawingModelStateListener {

	/**
	 * Called when the {@link DrawingModelState} is modified.
	 */
	void drawingModelStateModified();

	/**
	 * Called when the {@link DrawingModelState} is saved.
	 */
	void drawingModelStateSaved();
}
