package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.List;
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
 * The action is used to sort selected lines of text by criteria determined by
 * function. The action is perform in one of 3 ways : sorting ascening, sorting
 * descending and removing duplicate lines.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SortAction extends LocalizableAction {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = -6866333668746025833L;

	/**
	 * Multiple document model of the tabbed pane.
	 */
	private MultipleDocumentModel model;

	/**
	 * Function that determines which way of sorting is done.
	 */
	private Function<List<String>, List<String>> function;

	/**
	 * Constructs a new {@link SortAction} using it's relevant information.
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
	 * @param model
	 *            - multiple document model of the tabbed pane
	 * @param function
	 *            - function that determines which way of sorting is done
	 */
	public SortAction(String nameKey, String mnemonicKey, String descriptionKey, KeyStroke accelerator,
			ILocalizationProvider provider, MultipleDocumentModel model,
			Function<List<String>, List<String>> function) {

		super(nameKey, mnemonicKey, descriptionKey, provider);

		this.model = model;
		this.function = function;

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
			int startLine = textArea.getLineOfOffset(offset);
			int start = textArea.getLineStartOffset(startLine);
			int endLine = textArea.getLineOfOffset(offset + len);
			int end = textArea.getLineEndOffset(endLine)-1;

			String selectedText = doc.getText(start, end - start);

			List<String> lines = Arrays.asList(selectedText.split("\n"));

			lines = function.apply(lines);

			String text = buildText(lines);
			
			doc.remove(start, end - start);
			
			doc.insertString(start, text, null);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Builds a text from a list of lines.
	 * 
	 * @param lines
	 *            - list of lines to build text from
	 * @return built text
	 */
	private String buildText(List<String> lines) {
		StringBuilder text = new StringBuilder();
		lines.forEach(line -> text.append(line).append("\n"));
		return text.toString().substring(0, text.length() - 1);
	}
}
