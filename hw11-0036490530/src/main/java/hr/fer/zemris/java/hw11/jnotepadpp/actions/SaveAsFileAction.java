package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

/**
 * The action is used to save opened document to the disk for which the path is
 * not known or is being saved to another path.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SaveAsFileAction extends LocalizableAction {

	/**
	 * Unique identifier
	 */
	private static final long serialVersionUID = -3866659346174322485L;

	/**
	 * Multiple document model of the tabbed pane.
	 */
	private MultipleDocumentModel model;

	/**
	 * Constructs a new {@link SaveAsFileAction} using it's relevant information
	 * 
	 * @param nameKey
	 *            - name key of the action
	 * @param mnemonicKey
	 *            - mnemonic key of the action
	 * @param descriptionKey
	 *            - description of the action
	 * @param provider
	 *            - ILocalizationProvider used for multi language support
	 * @param model
	 *            - multiple document model of the tabbed pane.
	 */
	public SaveAsFileAction(String nameKey, String mnemonicKey, String descriptionKey, ILocalizationProvider provider,
			MultipleDocumentModel model) {

		super(nameKey, mnemonicKey, descriptionKey, provider);

		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		saveAs(ActionUtils.getTopLevelParent(event), model, model.getCurrentDocument(), provider);
	}

	/**
	 * Saves the specified file by using a file chooser to determine new file save
	 * path.
	 * 
	 * @param parent
	 *            - parent container used for showing dialogs
	 * @param model
	 *            - multiple document model
	 * @param document
	 *            - document to save
	 * @param provider
	 *            - language support provider
	 */
	public static void saveAs(Container parent, MultipleDocumentModel model, SingleDocumentModel document,
			ILocalizationProvider provider) {
		JFileChooser jfc = new JFileChooser() {

			private static final long serialVersionUID = 5756265224795924752L;

			@Override
			public void approveSelection() {
				String file = getSelectedFile().toString();
				if (!file.endsWith(".txt"))
					file += ".txt";
				Path filePath = Paths.get(file);

				if (Files.exists(filePath)) {
					int dialogResult = JOptionPane.showConfirmDialog(this,
							filePath.getFileName().toString() + provider.getString("file_exists_error") + ".\n"
									+ provider.getString("file_replace"),
							provider.getString("save_as_title"), JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

					if (dialogResult == JOptionPane.YES_OPTION) {
						if (checkOpenedDocuments(model, filePath)) {
							JOptionPane.showMessageDialog(this, provider.getString("file_opened_error"),
									provider.getString("warning"), JOptionPane.ERROR_MESSAGE);
							return;

						}
						model.saveDocument(document, filePath);
						super.approveSelection();
						return;
					} else {
						return;
					}

				}
				model.saveDocument(document, filePath);
				super.approveSelection();
			}
		};
		jfc.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setDialogTitle("Save document");
		jfc.showSaveDialog(parent);

	}

	/**
	 * The method checks if the file with path is already opened in the model.
	 * 
	 * @param model
	 *            - a multiple document model, holding all the documents
	 * @param path
	 *            - path to check if it is already opened
	 * @return true if the file with path <code>path</code> is already opened in the
	 *         editor, false otherwise
	 */
	private static boolean checkOpenedDocuments(MultipleDocumentModel model, Path path) {

		for (int i = 0, n = model.getNumberOfDocuments(); i < n; i++) {
			SingleDocumentModel document = model.getDocument(i);
			if (document.getFilePath() != null && document.getFilePath().equals(path))
				return true;
		}

		return false;
	}

}
