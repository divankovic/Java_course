package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;

/**
 * Custom status bar used in {@link JNotepadPP} to display current length of the
 * document, caret position (line and column), size of selected text and current
 * date and time.
 * 
 * @author Dorian Ivankovic
 *
 */
public class JNotepadStatusBar extends JPanel {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Length label.
	 */
	private JLabel lengthLabel;

	/**
	 * Position (row, column, selected tex) label.
	 */
	private JLabel positionLabel;

	/**
	 * Date and time label.
	 */
	private JLabel clockLabel;

	/**
	 * Timer used to update time on clock label.
	 */
	private Timer timer;

	/**
	 * simple date format used for displaying date and time on the clock label.
	 */
	private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");// dd/MM/yyyy

	/**
	 * Multi language support provider.
	 */
	private FormLocalizationProvider flp;

	/**
	 * Constructs a new {@link JNotepadStatusBar} using its
	 * {@link FormLocalizationProvider}.
	 * 
	 * @param flp
	 *            - localization provider
	 */
	public JNotepadStatusBar(FormLocalizationProvider flp) {
		super();
		this.flp = flp;
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		setBackground(Color.LIGHT_GRAY);

		lengthLabel = new JLabel();
		flp.addLocalizationListener(() -> updateLength());

		setLength(0);
		lengthLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lengthLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		positionLabel = new JLabel();
		flp.addLocalizationListener(() -> updateLabel());
		setPosition(1, 1, 0);
		positionLabel.setHorizontalAlignment(SwingConstants.CENTER);

		clockLabel = new JLabel();
		clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
		clockLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		timer = new Timer(1000, event -> {
			Date currentDate = new Date();
			clockLabel.setText(sdfDate.format(currentDate));
		});
		timer.start();

		add(lengthLabel, BorderLayout.LINE_START);
		add(positionLabel, BorderLayout.CENTER);
		add(clockLabel, BorderLayout.LINE_END);

	}

	/**
	 * The method updates the length field of the status bar.
	 */
	private void updateLength() {
		String text = lengthLabel.getText();
		setLength(Integer.parseInt(text.split(":")[1].trim()));
	}

	/**
	 * The method updates the status bar position label.
	 */
	private void updateLabel() {
		String text = positionLabel.getText();
		String[] data = text.split("\\s+");
		int line = Integer.parseInt(data[2]);
		int column = Integer.parseInt(data[5]);
		int selected = Integer.parseInt(data[8]);
		setPosition(line, column, selected);
	}

	/**
	 * The method stops the clock.
	 */
	public void stopClock() {
		timer.stop();
	}

	/**
	 * The method sets the displayed position in the status bar.
	 * 
	 * @param line
	 *            - line of the caret
	 * @param column
	 *            - column of the caret
	 * @param selected
	 *            - size of selected text
	 */
	public void setPosition(int line, int column, int selected) {
		String text = flp.getString("line") + " : " + line + "  " + flp.getString("column") + " : " + column + "  "
				+ flp.getString("selected") + " : " + selected;
		positionLabel.setText(text);
	}

	/**
	 * The method sets the length field on the status bar.
	 * 
	 * @param length
	 *            - length of the document.
	 */
	public void setLength(int length) {
		lengthLabel.setText(flp.getString("length") + " : " + length);
	}
}
