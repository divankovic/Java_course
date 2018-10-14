package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Container;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

/**
 * The action is used to save opened document to the disk. If the file path is
 * known, the file is saved immediately after the request, and otherwise a save
 * file dialog is displayes.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SaveFileAction extends LocalizableAction {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = -1070476658022711396L;

	/**
	 * Multiple document model of the tabbed pane.
	 */
	private MultipleDocumentModel model;

	/**
	 * Constructs a new {@link SaveFileAction} using it's relevant information
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
	public SaveFileAction(String nameKey, String mnemonicKey, String descriptionKey, ILocalizationProvider provider,
			MultipleDocumentModel model) {

		super(nameKey, mnemonicKey, descriptionKey, provider);

		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		save(ActionUtils.getTopLevelParent(event), model, model.getCurrentDocument(), provider);
	}

	/**
	 * Saves the specified document.
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
	public static void save(Container parent, MultipleDocumentModel model, SingleDocumentModel document,
			ILocalizationProvider provider) {
		if (!document.getFilePath().toString().startsWith("new")) {
			model.saveDocument(document, null);
		} else {
			SaveAsFileAction.saveAs(parent, model, document, provider);
		}
	}

}
