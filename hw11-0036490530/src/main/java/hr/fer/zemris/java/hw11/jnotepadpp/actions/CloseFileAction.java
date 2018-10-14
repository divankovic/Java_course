package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

/**
 * The action is used to close a specific file from the model. If the file is
 * modified a save dialog is displayed urging the user to decide what to do with
 * the modified file.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CloseFileAction extends LocalizableAction {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 8430447231535603968L;

	/**
	 * Multiple document model of the tabbed pane.
	 */
	private MultipleDocumentModel model;

	/**
	 * Constructs a new {@link CloseFileAction} using it's relevant information
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
	public CloseFileAction(String nameKey, String mnemonicKey, String descriptionKey, ILocalizationProvider provider,
			MultipleDocumentModel model) {

		super(nameKey, mnemonicKey, descriptionKey, provider);

		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		closeAndSave(ActionUtils.getTopLevelParent(event), model, model.getCurrentDocument(), provider);
	}

	/**
	 * The method is used to save a modified document and then close it.
	 * 
	 * @param parent
	 *            - parent component used for displaying dialogs
	 * @param model
	 *            - multiple document model of the tabbed pane
	 * @param document
	 *            - document to save and close
	 * @param provider
	 *            - ILocalizationProvider used for multi language support
	 */
	public static void closeAndSave(Container parent, MultipleDocumentModel model, SingleDocumentModel document,
			ILocalizationProvider provider) {
		if (saveClosing(parent, model, document, provider)) {
			return;
		}

		model.closeDocument(document);
	}

	/**
	 * The method is used to save the modified document before closing it by
	 * displaying a save dialog
	 * 
	 * @param parent
	 *            - parent component used for displaying dialogs
	 * @param model
	 *            - multiple document model of the tabbed pane
	 * @param document
	 *            - document to save and close
	 * @param provider
	 *            - ILocalizationProvider used for multi language support
	 * @return true if the process was cancelled, false otherwise
	 */
	public static boolean saveClosing(Container parent, MultipleDocumentModel model, SingleDocumentModel document,
			ILocalizationProvider provider) {
		if (document.isModified()) {
			int dialogResult = JOptionPane.showConfirmDialog(parent,
					provider.getString("save_file") + " " + document.getFilePath().getFileName().toString() + " ?",
					provider.getString("save"), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (dialogResult == JOptionPane.YES_OPTION) {
				SaveFileAction.save(parent, model, document, provider);
				if (document.isModified())
					return true; // canceled saving
			} else if (dialogResult == JOptionPane.CANCEL_OPTION || dialogResult == JOptionPane.CLOSED_OPTION) {
				return true;
			}
		}

		return false;
	}

}
