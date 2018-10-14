package hr.fer.zemris.java.hw16.jvdraw.geometric.editors;

import java.awt.Toolkit;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;

/**
 * Editor of {@link GeometricalObject}'s used for changing their parameters.
 * 
 * @author Dorian Ivankovic
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {
	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The method checks if the editing is valid.
	 * 
	 * @throws GeometricalEditingException
	 *             - if the editing is not valid
	 */
	public abstract void checkEditing();

	/**
	 * The method accepts the editing and changes the associated
	 * {@link GeometricalObject}.
	 */
	public abstract void acceptEditing();

	/**
	 * The method creates the default number spinner, with initial value set to
	 * <code>initialValue</code>.
	 * 
	 * @param initialValue
	 *            - inital value of the field
	 * @return numeric spinner
	 */
	protected JSpinner getSpinner(int initialValue) {
		SpinnerModel model = new SpinnerNumberModel(initialValue, 0, Integer.MAX_VALUE, 1);
		JSpinner spinner = new JSpinner(model);

		JTextComponent txt = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		txt.setDocument(new CustomDocument());

		txt.setText(String.valueOf(initialValue));

		return spinner;
	}

	/**
	 * Custom document that uses the {@link NumericFilter} as input filter.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	private static class CustomDocument extends PlainDocument {

		/**
		 * Serial version uid.
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Document filter.
		 */
		private NumericFilter filter;

		@Override
		public DocumentFilter getDocumentFilter() {
			if (filter == null) {
				filter = new NumericFilter();
			}
			return filter;
		}
	}

	/**
	 * Custom filter that allows input of only positive {@link Integer} numbers.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	private static class NumericFilter extends DocumentFilter {

		@Override
		public void insertString(FilterBypass fb, int offs, String str, AttributeSet a) throws BadLocationException {

			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.insert(offs, str);
			String text = sb.toString();

			if (test(text)) {
				super.insertString(fb, offs, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}

		@Override
		public void remove(FilterBypass fb, int offs, int len) throws BadLocationException {
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.delete(offs, offs + len);
			String text = sb.toString();

			if (test(text)) {
				super.remove(fb, offs, len);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}
		}

		@Override
		public void replace(FilterBypass fb, int offs, int len, String str, AttributeSet a)
				throws BadLocationException {
			Document doc = fb.getDocument();
			StringBuilder sb = new StringBuilder();
			sb.append(doc.getText(0, doc.getLength()));
			sb.insert(offs, str);
			String text = sb.toString();

			if (test(text)) {
				super.replace(fb, offs, len, str, a);
			} else {
				Toolkit.getDefaultToolkit().beep();
			}

		}

		/**
		 * Test if text is a positive integer.
		 * 
		 * @param text
		 *            - text to test
		 * @return true if text is a positive integer, false otherwise
		 */
		private boolean test(String text) {
			try {
				int value = Integer.parseInt(text);
				if (value < 0)
					return false;
				return true;
			} catch (NumberFormatException ex) {
				return false;
			}
		}

	}
}
