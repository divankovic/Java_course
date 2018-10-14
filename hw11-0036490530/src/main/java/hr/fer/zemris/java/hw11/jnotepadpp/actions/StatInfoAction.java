package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;

/**
 * The action displays basic statistical info about the opened document in
 * current {@link MultipleDocumentModel}- number of characters, number of
 * non-blank characters and the number of lines.
 * 
 * @author Dorian Ivankovic
 *
 */
public class StatInfoAction extends LocalizableAction {

	/**
	 * Unique identifier
	 */
	private static final long serialVersionUID = -6866333668746025833L;

	/**
	 * Multiple document model of the tabbed pane.
	 */
	private MultipleDocumentModel model;

	/**
	 * Constructs a new {@link StatInfoAction} using it's relevant information
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
	public StatInfoAction(String nameKey, String mnemonicKey, String descriptionKey, ILocalizationProvider provider,
			MultipleDocumentModel model) {

		super(nameKey, mnemonicKey, descriptionKey, provider);

		this.model = model;

		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JTextArea textArea = model.getCurrentDocument().getTextComponent();
		String text = textArea.getText();
		int totalCharacters = text.length();
		int nonBlankCharacters = countNonBlankCharacters(text);
		int totalLines = textArea.getLineCount();

		String message = String.format(provider.getString("stat_info_format"), totalCharacters, nonBlankCharacters,
				totalLines);

		JOptionPane.showMessageDialog(ActionUtils.getTopLevelParent(event), message, (String) getValue(NAME),
				JOptionPane.INFORMATION_MESSAGE);
		textArea.requestFocusInWindow();
	}

	/**
	 * The method counts non blank characters( blank = ' ', '\t' or '\n') in text.
	 * 
	 * @param text
	 *            - text to count characters from
	 * @return number of non blank characters in the test
	 */
	private int countNonBlankCharacters(String text) {
		char[] symbols = text.toCharArray();
		int nonBlank = 0;

		for (char c : symbols) {
			if (c == ' ' || c == '\t' || c == '\n')
				continue;
			nonBlank++;
		}

		return nonBlank;
	}
}
