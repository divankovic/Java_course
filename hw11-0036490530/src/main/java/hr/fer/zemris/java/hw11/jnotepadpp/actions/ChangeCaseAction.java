package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.function.Function;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;

/**
 * The action is used to change case of the selected text in one of 3 following
 * ways - to upper case, to lower case or invert case.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ChangeCaseAction extends LocalizableAction {

	/**
	 * Unique identifier
	 */
	private static final long serialVersionUID = -6866333668746025833L;

	/**
	 * Multiple document model of the tabbed pane.
	 */
	private MultipleDocumentModel model;

	/**
	 * Function used to determine the way of case changing.
	 */
	private Function<String, String> converter;

	/**
	 * Constructs a new {@link ChangeCaseAction} using it's relevant information
	 *
	 * @param nameKey
	 *            - name key of the action
	 * @param mnemonicKey
	 *            - mnemonic key of the action
	 * @param descriptionKey
	 *            - description key of the action
	 * @param accelerator
	 *            - accelerator key of the action
	 * @param provider
	 *            - ILocalizationProvider used for multi language support
	 * @param converter
	 *            - used for case changing
	 * @param model
	 *            - multiple document model of the tabbed pane
	 */
	public ChangeCaseAction(String nameKey, String mnemonicKey, String descriptionKey, KeyStroke accelerator,
			ILocalizationProvider provider, Function<String, String> converter, MultipleDocumentModel model) {

		super(nameKey, mnemonicKey, descriptionKey, provider);

		this.model = model;
		this.converter = converter;

		putValue(Action.ACCELERATOR_KEY, accelerator);

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();

		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

		if (len == 0)
			return;

		int offset = 0;

		if (len != 0) {
			offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		} else {
			len = doc.getLength();
		}

		try {
			String text = doc.getText(offset, len);
			text = converter.apply(text);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);

		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * The method inverts the case of the input text.
	 * 
	 * @param text
	 *            - input text to invert case of.
	 * @return text with inverted case
	 */
	public static String invertCase(String text) {
		char[] symbols = text.toCharArray();
		for (int i = 0; i < symbols.length; i++) {
			char c = symbols[i];
			if (Character.isLowerCase(c)) {
				symbols[i] = Character.toUpperCase(c);
			} else if (Character.isUpperCase(c)) {
				symbols[i] = Character.toLowerCase(c);
			}
		}

		return new String(symbols);
	}
}
