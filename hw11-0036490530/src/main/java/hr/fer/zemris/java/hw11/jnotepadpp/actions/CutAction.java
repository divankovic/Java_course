package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;

/**
 * The action is used to cut selected text form the text component of the
 * current document into the clipboard.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CutAction extends LocalizableAction {

	/**
	 * Unique Identifier.
	 */
	private static final long serialVersionUID = -6866333668746025833L;

	/**
	 * Multiple document model of the tabbed pane.
	 */
	private MultipleDocumentModel model;

	/**
	 * Constructs a new {@link CutAction} using it's relevant information
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
	public CutAction(String nameKey, String mnemonicKey, String descriptionKey, ILocalizationProvider provider,
			MultipleDocumentModel model) {

		super(nameKey, mnemonicKey, descriptionKey, provider);

		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		model.getCurrentDocument().getTextComponent().cut();
	}

}
