package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.awt.GridLayout;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Documents in the model.
	 */
	private List<SingleDocumentModel> documents;

	/**
	 * Current document - displayed to the user.
	 */
	private SingleDocumentModel currentDocument;

	/**
	 * Collection of {@link MultipleDocumentListener}'s.
	 */
	private Set<MultipleDocumentListener> listeners;

	/**
	 * Icon for showing document modified status.
	 */
	private ImageIcon modifiedIcon;

	/**
	 * Icon for showing documents modified status.
	 */
	private ImageIcon unmodifiedIcon;

	/**
	 * Unique identifier
	 */
	private static final long serialVersionUID = 8856736922453415812L;

	/**
	 * A listener of the current documets that tracks it's modification status and
	 * current path and notifies the {@link MultipleDocumentModel} about all
	 * changes.
	 */
	private SingleDocumentListener currentDocumentListener = new SingleDocumentListener() {

		@Override
		public void documentModifyStatusUpdated(SingleDocumentModel model) {

			ImageIcon icon;

			if (model.isModified()) {
				icon = modifiedIcon;
			} else {
				icon = unmodifiedIcon;
			}
			DefaultMultipleDocumentModel.this.setIconAt(documents.indexOf(model), icon);

			notifyListeners(listener -> listener.currentDocumentChanged(model, model));
		}

		@Override
		public void documentFilePathUpdated(SingleDocumentModel model) {
			DefaultMultipleDocumentModel.this.setTitleAt(documents.indexOf(model),
					model.getFilePath().getFileName().toString());
			DefaultMultipleDocumentModel.this.setToolTipTextAt(documents.indexOf(model),
					model.getFilePath().toAbsolutePath().toString());
			((ButtonTabComponent) getTabComponentAt(getSelectedIndex())).updateUI();
		}
	};

	/**
	 * Default constructor for the {@link DefaultMultipleDocumentModel}.
	 */
	public DefaultMultipleDocumentModel() {
		documents = new ArrayList<>();
		listeners = new HashSet<>();

		modifiedIcon = IconLoader.loadIcon(DefaultMultipleDocumentModel.class, "modified.png");
		unmodifiedIcon = IconLoader.loadIcon(DefaultMultipleDocumentModel.class, "unmodified.png");

		this.addChangeListener(event -> {
			int selectedIndex = getSelectedIndex();
			if (selectedIndex == documents.indexOf(currentDocument))
				return;

			if (selectedIndex != -1) {
				documents.get(selectedIndex).getTextComponent().requestFocusInWindow();
			}

			SingleDocumentModel previousDocument = currentDocument;
			currentDocument = getDocument(selectedIndex);
			notifyListeners(listener -> listener.currentDocumentChanged(previousDocument, currentDocument));

		});

		this.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel document = new DefaultSingleDocumentModel(Paths.get("new" + (getNewDocuments() + 1)), "");
		documents.add(document);

		addDocumentTab(document);
		notifyListeners(listener -> listener.documentAdded(currentDocument));

		return document;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "path must not be null.");
		int loadedIndex = getLoadedDocumentIndex(path);
		if (loadedIndex != -1) {
			SingleDocumentModel document = documents.get(loadedIndex);
			this.setSelectedComponent(getComponentAt(loadedIndex));
			return document;
		}

		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this.getParent(), "Error has ocurred when reading " + path.toAbsolutePath(),
					"Error", JOptionPane.ERROR_MESSAGE);
			return null;
		}

		String text = new String(bytes, StandardCharsets.UTF_8);
		SingleDocumentModel document = new DefaultSingleDocumentModel(path, text);
		documents.add(document);
		addDocumentTab(document);

		notifyListeners(listener -> listener.documentAdded(currentDocument));
		return document;
	}

	/**
	 * Returns the index of the loaded document in the model, if it exists and -1
	 * otherwise.
	 * 
	 * @param path
	 *            - path of the document
	 * @return - index of the loaded document, false otherwise
	 */
	private int getLoadedDocumentIndex(Path path) {

		int loadedIndex = -1;

		for (int i = 0, n = documents.size(); i < n; i++) {
			SingleDocumentModel document = documents.get(i);
			if (document.getFilePath() != null && document.getFilePath().equals(path)) {
				loadedIndex = i;
				break;
			}

		}

		return loadedIndex;
	}

	/**
	 * Adds a new document tab with document model inside it.
	 * 
	 * @param document
	 *            - document to add to the model.
	 */
	private void addDocumentTab(SingleDocumentModel document) {

		String title = "";
		String description = "";

		if (document.getFilePath().toString().startsWith("new")) {
			title = document.getFilePath().toString();
			description = title;
		} else {
			title = document.getFilePath().getFileName().toString();
			description = document.getFilePath().toAbsolutePath().toString();
		}

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 1));
		panel.add(new JScrollPane(document.getTextComponent()));
		this.addTab(title, unmodifiedIcon, panel, description);
		this.setTabComponentAt(this.indexOfComponent(panel), new ButtonTabComponent(this));
		this.revalidate();
		this.repaint();
		this.setSelectedComponent(panel);

		document.addSingleDocumentListener(currentDocumentListener);
	}

	/**
	 * Counts the number of documents in the model that do not have a real saved
	 * path.
	 * 
	 * @return number of documents in the model without a path
	 */
	private int getNewDocuments() {
		int newDocuments = 0;

		for (SingleDocumentModel document : documents) {
			if (document.getFilePath().toString().startsWith("new")) {
				newDocuments++;
			}
		}

		return newDocuments;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {

		Path savePath = newPath;

		if (newPath == null) {
			savePath = model.getFilePath();
		}

		byte[] data = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(savePath, data);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this.getParent(), "Error has ocurred while saving file " + savePath, "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		model.setFilePath(savePath);
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documents.indexOf(model);
		this.remove(getComponentAt(index));
		documents.remove(index);

		this.revalidate();
		this.repaint();

		int selectedIndex = index - 1;

		if (selectedIndex == -1 && getNumberOfDocuments() != 0) {
			selectedIndex = 0;
		}

		if (selectedIndex != -1) {
			this.setSelectedComponent(getComponentAt(selectedIndex));
			this.fireStateChanged();
		}

		this.notifyListeners(listener -> listener.documentRemoved(model));

	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		Objects.requireNonNull(l, "listener must not be null.");
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		if (l != null)
			listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index > documents.size() - 1)
			return null;
		return documents.get(index);
	}

	/**
	 * Notifies the listeners about the specified change specified by a nonsumer
	 * 
	 * @param consumer
	 *            - changed property of the document
	 */
	private void notifyListeners(Consumer<MultipleDocumentListener> consumer) {
		List<MultipleDocumentListener> listenersCopy = new ArrayList<>(listeners);
		listenersCopy.forEach(listener -> consumer.accept(listener));
	}
}
