package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelState;

/**
 * An action used to exit the application. If the {@link DrawingModel} is
 * modified, a save dialog is shown before the exit.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ExitAction extends AbstractAction {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Frame to dispose when exiting the application.
	 */
	private JFrame frame;

	/**
	 * {@link DrawingModel} of the application.
	 */
	private DrawingModel drawingModel;

	/**
	 * State of the application's {@link DrawingModel}.
	 */
	private DrawingModelState drawingModelState;

	/**
	 * Constructs the <code>ExitAction</code> using all required attributes.
	 * 
	 * @param frame
	 *            - fram to dispose when exiting the application
	 * @param drawingModel
	 *            - {@link DrawingModel} of the application
	 * @param drawingModelState
	 *            - DrawingModelState drawingModelState
	 */
	public ExitAction(JFrame frame, DrawingModel drawingModel, DrawingModelState drawingModelState) {
		this.frame = frame;
		this.drawingModel = drawingModel;
		this.drawingModelState = drawingModelState;

		putValue(Action.NAME, "Exit");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(Action.SHORT_DESCRIPTION, "Exit application");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Path path = drawingModelState.getPath();
		if (drawingModelState.isModified()) {
			int dialogResult = JOptionPane.showConfirmDialog(frame,
					"Save file " + (path == null ? "" : path.getFileName().toString()) + "?", "Save",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (dialogResult == JOptionPane.YES_OPTION) {
				SaveFileAction.save(frame, drawingModel, drawingModelState);
				if (drawingModelState.isModified())
					return; // canceled saving
			} else if (dialogResult == JOptionPane.CANCEL_OPTION || dialogResult == JOptionPane.CLOSED_OPTION) {
				return;
			}
		}

		frame.dispose();
	}

}
