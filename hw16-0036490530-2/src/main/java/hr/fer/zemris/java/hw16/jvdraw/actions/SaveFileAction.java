package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelState;

/**
 * The action is used to save the {@link DrawingModel} into the file given by
 * {@link DrawingModelState#getPath()}. The action redirects saving to
 * {@link SaveAsFileAction} if the file hasn't been already saved.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SaveFileAction extends AbstractAction {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link DrawingModel} that holds the current objects.
	 */
	private DrawingModel drawingModel;

	/**
	 * State of the <code>drawingModel</code>.
	 */
	private DrawingModelState drawingModelState;

	/**
	 * Constructor
	 * 
	 * @param drawingModel
	 *            - {@link DrawingModel} that holds the current objects
	 * @param drawingModelState
	 *            - State of the <code>drawingModel</code>
	 */
	public SaveFileAction(DrawingModel drawingModel, DrawingModelState drawingModelState) {
		this.drawingModel = drawingModel;
		this.drawingModelState = drawingModelState;

		putValue(Action.NAME, "Save");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(Action.SHORT_DESCRIPTION, "Saves .jvd file to disk.");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		save(ActionUtils.getTopLevelParent(event), drawingModel, drawingModelState);
	}

	/**
	 * Performst the save action.
	 * 
	 * @param parent
	 *            - parent {@link Container} - used for centering the save dialog
	 * @param drawingModel
	 *            - holds the current objects
	 * @param drawingModelState
	 *            - state of the <code>drawingModel</code>
	 */
	public static void save(Container parent, DrawingModel drawingModel, DrawingModelState drawingModelState) {
		if (drawingModelState.getPath() != null) {
			SaveAsFileAction.saveDocument(parent, drawingModel, drawingModelState, drawingModelState.getPath());
		} else {
			SaveAsFileAction.saveAs(parent, drawingModel, drawingModelState);
		}
	}

}
