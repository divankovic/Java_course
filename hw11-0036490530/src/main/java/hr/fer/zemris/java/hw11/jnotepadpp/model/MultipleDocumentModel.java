package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;

/**
 * A model capable of holding zero, one or more documents, and also
 * having a concept of current document - the one which is shown to the user
 * and on which the user works.
 * @author Dorian Ivankovic
 *
 */
public interface MultipleDocumentModel {
	
	/**
	 * Creates a new empty document.
	 * @return a new empty document.
	 */
	SingleDocumentModel createNewDocument();
	
	/**
	 * Returns the current document.
	 * @return current document of the {@link MultipleDocumentModel}
	 */
	SingleDocumentModel getCurrentDocument();
	
	/**
	 * Loads the document from <code>path</code> if the file is not already
	 * opened in the editor, in which cased the tab with the file is focused.
	 * @param path - path of the document
	 * @return loaded document
	 * @throws NullPointerException if path is null
	 */
	SingleDocumentModel  loadDocument(Path path);
	
	/**
	 * Saves the model document to newPath.
	 * If newPath is null, the document is saved using the path file associated with
	 * the document.
	 * @param model - document to save
	 * @param newPath - path to save to
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);
	
	/**
	 * Removes the specified document from the model.
	 * @param model - document to remove from the model
	 */
	void closeDocument(SingleDocumentModel model);
	
	/**
	 * Adds a new {@link MultipleDocumentListener} that monitors the changes of the model.
	 * @param l - new listener
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Removes the specified listener from the internal collection of listeners.
	 * @param l - listener to remove from the listeners collection
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);
	
	/**
	 * Returns the current number of documents in the model.
	 * @return current number  of doucuments in the model.
	 */
	int getNumberOfDocuments();
	
	/**
	 * Gets the document at the specified index.
	 * @param index - index of the wanted document
	 * @return document at the specified index
	 * @throws IndexOutOfBoundsException if index >0 or indes>getNumberOfdocuments()-1
	 */
	SingleDocumentModel getDocument(int index);
}
