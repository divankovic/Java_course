package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Represents a model of a single document, having information about file path
 * from which the document was loaded (can be null for new document), document
 * modification status and a reference to Swing component which is used for
 * editing (each document has it's own editor component)
 * 
 * @author Dorian Ivankovic
 *
 */
public interface SingleDocumentModel {

	/**
	 * Returns the text component of the document.
	 * 
	 * @return the text component of the document
	 */
	JTextArea getTextComponent();

	/**
	 * Returns the path of the loaded document.
	 * 
	 * @return the path of the document
	 */
	Path getFilePath();

	/**
	 * Sets the path of the document.
	 * 
	 * @param path
	 *            - new path of the document
	 * @throws NullPointerException
	 *             if path is null
	 */
	void setFilePath(Path path);

	/**
	 * Returns true if the document is modified and false otherwise.
	 * 
	 * @return true if the document is modified and false otherwise
	 */
	boolean isModified();

	/**
	 * Sets the modification flag of the document.
	 * 
	 * @param modified
	 *            - new modification document status
	 */
	void setModified(boolean modified);

	/**
	 * Adds a {@link SingleDocumentListener} which is notified about file
	 * modification or about file path change.
	 * 
	 * @param l
	 *            - new listener
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes the listener from the internal collection of listeners.
	 * 
	 * @param l
	 *            - listener to remove from the collection
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
