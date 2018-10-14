package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * A {@link SingleDocumentModel} implementation.
 * @author Dorian Ivankovic
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Path of the document.
	 */
	private Path filePath;
	
	/**
	 * Text area component of the document.
	 */
	private JTextArea textArea;
	
	/**
	 * Used to determine whether or not the document has been changed
	 */
	private boolean modified;
	
	/**
	 * Listeners of the document.
	 */
	private Set<SingleDocumentListener> listeners;

	/**
	 * Constructs a new document using its filePath and text content.
	 * @param filePath - path of the document
	 * @param textContent - text content of the document
	 */
	public DefaultSingleDocumentModel(Path filePath, String textContent) {
		this.filePath = filePath;
		textArea = new JTextArea(textContent);
		listeners = new HashSet<>();

		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent event) {
				setModified(true);
			}

			@Override
			public void insertUpdate(DocumentEvent event) {
				setModified(true);
			}

			@Override
			public void removeUpdate(DocumentEvent event) {
				setModified(true);
			}
		});

	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path, "path must not be null.");
		if (filePath != null && filePath.equals(path))
			return;
		filePath = path;
		notifyListeners(listener -> listener.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		if (this.modified == modified)
			return;
		this.modified = modified;
		notifyListeners(listener -> listener.documentModifyStatusUpdated(this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		Objects.requireNonNull(l, "listener must not be null");
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		if (l != null) {
			listeners.remove(l);
		}
	}

	/**
	 * Notifies all {@link SingleDocumentListener}'s about the change specified by the consumer.
	 * @param consumer - specified changed property of the document.
	 */
	private void notifyListeners(Consumer<SingleDocumentListener> consumer) {
		List<SingleDocumentListener> listenersCopy = new ArrayList<>(listeners);
		listenersCopy.forEach(listener -> consumer.accept(listener));
	}

}
