package hr.fer.zemris.java.hw11.jnotepadpp.model;

/**
 * The listener of {@link MultipleDocumentModel} notified about all relevant changes
 * on the model, such as document removal, document addition, change of current document..
 * @author Dorian Ivankovic
 *
 */
public interface MultipleDocumentListener {
	
	/**
	 * This method is called once the currentModel (current document) changes to
	 * a new document in {@link MultipleDocumentModel}.
	 * PreviousModl and currentModel must not both be null.
	 * @param previousModel - previous current document
	 * @param currentModel - current document
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Called once a new document is added inside the model
	 * @param model - new model added to {@link MultipleDocumentModel}
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Caled once the document <code>model</code> is removed from the {@link MultipleDocumentModel}.
	 * @param model - removed document
	 */
	void documentRemoved(SingleDocumentModel model);
}
