package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelState;

/**
 * Action used to saved current {@link DrawingModel} to a new .jvd file.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SaveAsFileAction extends AbstractAction {

	/**
	 * Serial version uid
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
	public SaveAsFileAction(DrawingModel drawingModel, DrawingModelState drawingModelState) {
		this.drawingModel = drawingModel;
		this.drawingModelState = drawingModelState;

		putValue(Action.NAME, "Save as");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(Action.SHORT_DESCRIPTION, "Saves .jvd file to disk.");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		saveAs(ActionUtils.getTopLevelParent(event), drawingModel, drawingModelState);
	}

	/**
	 * Performst the save action by displaying a save dialog.
	 * 
	 * @param parent
	 *            - parent {@link Container} - used for centering the save dialog
	 * @param drawingModel
	 *            - holds the current objects
	 * @param drawingModelState
	 *            - state of the <code>drawingModel</code>
	 */
	public static void saveAs(Container parent, DrawingModel drawingModel, DrawingModelState drawingModelState) {
		JFileChooser jfc = new JFileChooser() {

			private static final long serialVersionUID = 5756265224795924752L;

			@Override
			public void approveSelection() {
				String file = getSelectedFile().toString();
				if (!file.endsWith(".jvd"))
					file += ".jvd";
				Path filePath = Paths.get(file);

				if (Files.exists(filePath) && !filePath.equals(drawingModelState.getPath())) {
					int dialogResult = JOptionPane.showConfirmDialog(this,
							filePath.getFileName().toString() + " already exists" + ".\n"
									+ "Do you want to replace it?",
							"Save as", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

					if (dialogResult == JOptionPane.YES_OPTION) {

						saveDocument(parent, drawingModel, drawingModelState, filePath);
						super.approveSelection();
						return;
					} else {
						return;
					}

				}
				saveDocument(parent, drawingModel, drawingModelState, filePath);
				super.approveSelection();
			}
		};
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.setFileFilter(new FileNameExtensionFilter("*.jvd", "jvd"));
		jfc.setDialogTitle("Save document");
		jfc.showSaveDialog(parent);

	}

	/**
	 * Saves the <code>drawingModel2</code> into a file given by
	 * <code>filePath</code>.
	 * 
	 * @param parent
	 *            - parent {@link Container} used for centering the error messages
	 * @param drawingModel2
	 *            - model to save
	 * @param drawingModelState2
	 *            - state of the model
	 * @param filePath
	 *            - path to the file to save to
	 */
	public static void saveDocument(Container parent, DrawingModel drawingModel2, DrawingModelState drawingModelState2,
			Path filePath) {

		try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {

			for (int i = 0, n = drawingModel2.getSize(); i < n; i++) {
				writer.write(drawingModel2.getObject(i).toJvd() + "\n");
			}
			writer.flush();

		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Error has ocurred while saving file " + filePath, "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		drawingModelState2.setPath(filePath);
		drawingModelState2.setModified(false);
	}

}
