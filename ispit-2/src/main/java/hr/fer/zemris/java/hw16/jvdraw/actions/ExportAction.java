package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectPainter;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

/**
 * The action used to export current {@link DrawingModel} into .jpg, .png, or
 * .gif image.
 * 
 * @author Dorian Ivankovic
 *
 */
public class ExportAction extends AbstractAction {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link DrawingModel} containg objects to export into image.
	 */
	private DrawingModel drawingModel;

	/**
	 * Constructor
	 * 
	 * @param drawingModel
	 *            - {@link DrawingModel} containg objects to export into image
	 */
	public ExportAction(DrawingModel drawingModel) {
		this.drawingModel = drawingModel;

		putValue(Action.NAME, "Export image");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(Action.SHORT_DESCRIPTION, "Exports picture.");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		Container parent = ActionUtils.getTopLevelParent(event);
		JFileChooser jfc = new JFileChooser() {

			private static final long serialVersionUID = 5756265224795924752L;

			@Override
			public void approveSelection() {
				String file = getSelectedFile().toString();
				String type = getFileFilter().getDescription().replace(".", "");
				Path filePath = Paths.get(file + "." + type);

				if (Files.exists(filePath)) {
					int dialogResult = JOptionPane.showConfirmDialog(this,
							filePath.getFileName().toString() + " already exists" + ".\n"
									+ "Do you want to replace it?",
							"Export", JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);

					if (dialogResult == JOptionPane.YES_OPTION) {

						exportImage(parent, filePath, type);
						super.approveSelection();
						return;
					} else {
						return;
					}

				}
				exportImage(parent, filePath, type);
				super.approveSelection();
			}

		};
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(".jpg", "jpg"));
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(".png", "jpg"));
		jfc.addChoosableFileFilter(new FileNameExtensionFilter(".gif", "jpg"));
		jfc.setDialogTitle("Export image");
		jfc.showSaveDialog(parent);
	}

	/**
	 * Export the image of the type <code>type</code> into file given by
	 * <code>filePath</code>.
	 * 
	 * @param parent
	 *            - used for centering the save dialog
	 * @param filePath
	 *            - path to image
	 * @param type
	 *            - type of the image : .jgp, .png or .gif
	 */
	private void exportImage(Container parent, Path filePath, String type) {
		GeometricalObjectBBCalculator bbCalc = new GeometricalObjectBBCalculator();

		for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
			drawingModel.getObject(i).accept(bbCalc);
		}

		Rectangle box = bbCalc.getBoundingBox();
		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g = image.createGraphics();
		g.translate(-box.x, -box.y);

		g.setColor(Color.WHITE);
		g.fill(box);
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g);

		for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
			drawingModel.getObject(i).accept(painter);
		}

		g.dispose();

		try {
			ImageIO.write(image, type, filePath.toFile());
			JOptionPane.showMessageDialog(parent, "Image " + filePath.getFileName() + " successfuly exported.",
					"Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(parent, "Couldn't export image " + filePath, "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}
}
