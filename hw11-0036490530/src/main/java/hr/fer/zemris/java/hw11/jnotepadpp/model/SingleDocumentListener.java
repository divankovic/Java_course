package hr.fer.zemris.java.hw11.jnotepadpp.model;

/**
 * A listener of the {@link SingleDocumentModel}, notified about
 * document modification and the update of the document's path.
 * @author Dorian Ivankovic
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * This method is called once the modify status of the model changes.
	 * @param model - monitored document
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * This method is called once the path of the model changes.
	 * @param model - monitored document
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
