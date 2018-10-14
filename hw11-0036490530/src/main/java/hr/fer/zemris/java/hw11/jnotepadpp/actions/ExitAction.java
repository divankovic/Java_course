package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

/**
 * The action is used to exit the application. Before exiting the user is asked
 * to save or discard all unsaved documents. The action can also be cancelled by
 * clicking cancel any time a dialog appears.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ExitAction extends LocalizableAction {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = -5050548213592449042L;

	/**
	 * Frame to close once all documents have been closed.
	 */
	private JFrame frame;

	/**
	 * Multiple document model of the tabbed pane.
	 */
	private MultipleDocumentModel model;

	/**
	 * Constructs a new {@link ExitAction} using it's relevant information
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
	 * @param frame
	 *            - frame to dipose once finished with document saving
	 */
	public ExitAction(String nameKey, String mnemonicKey, String descriptionKey, ILocalizationProvider provider,
			JFrame frame, MultipleDocumentModel model) {

		super(nameKey, mnemonicKey, descriptionKey, provider);

		this.frame = frame;
		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift W"));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		closeAll(frame, model, provider);
	}

	public static void closeAll(JFrame frame, MultipleDocumentModel model, ILocalizationProvider provider) {

		int n = model.getNumberOfDocuments();
		for (int i = 0; i < n; i++) {
			SingleDocumentModel document = model.getDocument(i);
			((JTabbedPane) model).setSelectedIndex(i);
			boolean canceled = CloseFileAction.saveClosing(frame, model, document, provider);

			if (canceled)
				return;
		}

		for (int i = 0; i < n; i++) {
			model.closeDocument(model.getCurrentDocument());
		}

		frame.dispose();
	}

}
