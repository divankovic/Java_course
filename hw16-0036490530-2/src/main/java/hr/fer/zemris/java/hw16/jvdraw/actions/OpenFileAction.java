package hr.fer.zemris.java.hw16.jvdraw.actions;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.ConvexPolygon;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelState;

/**
 * Action that is used for opening .jvd files from disk.
 * 
 * @author Dorian Ivankovic
 *
 */
public class OpenFileAction extends AbstractAction {

	/**
	 * Serial version uid
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * {@link DrawingModel} to change - upload all object from .jvd file.
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
	 *            - {@link DrawingModel} to change - upload all object from .jvd file
	 * @param drawingModelState
	 *            - State of the <code>drawingModel</code>
	 */
	public OpenFileAction(DrawingModel drawingModel, DrawingModelState drawingModelState) {
		this.drawingModel = drawingModel;
		this.drawingModelState = drawingModelState;

		putValue(Action.NAME, "Open");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.SHORT_DESCRIPTION, "Opens .jvd file from disk.");
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));

	}

	@Override
	public void actionPerformed(ActionEvent event) {
		JFileChooser fc = new JFileChooser();
		fc.setAcceptAllFileFilterUsed(false);
		fc.setFileFilter(new FileNameExtensionFilter("*.jvd", "jvd"));
		fc.setDialogTitle("Open file");

		if (fc.showOpenDialog(ActionUtils.getTopLevelParent(event)) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(((JComponent) event.getSource()).getParent(),
					"File " + fileName.getAbsolutePath() + " doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (filePath.equals(drawingModelState.getPath())) {
			return;
		}

		try {
			List<GeometricalObject> objects = loadJvdDocument(filePath);
			drawingModel.removeDrawingModelListener(drawingModelState);

			// clear drawing model
			for (int i = 0, n = drawingModel.getSize(); i < n; i++) {
				drawingModel.remove(drawingModel.getObject(0));
			}

			// insert new objects into model
			for (GeometricalObject object : objects) {
				drawingModel.add(object);
			}

			drawingModel.addDrawingModelListener(drawingModelState);
			drawingModelState.setModified(false);
			drawingModelState.setPath(filePath);

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(((JComponent) event.getSource()).getParent(),
					"File " + fileName.getAbsolutePath() + " is in illegal format.\n"
							+ "Object name followed by its relevant information separated by spaces is expected.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * The method loads the list of {@link GeometricalObject} from the .jvd file
	 * given by <code>filePath</code>.
	 * 
	 * @param filePath
	 *            - path to the .jvd file
	 * @return list of {@link GeometricalObject}
	 * @throws IOException
	 *             - if the file <code>filePath</code> cannot be opened
	 * @throws IllegalArgumentException
	 *             - if the file <code>filePath</code> is in illegal format
	 */
	private List<GeometricalObject> loadJvdDocument(Path filePath) throws IOException {
		List<String> lines = Files.readAllLines(filePath);
		List<GeometricalObject> objects = new ArrayList<>();

		int startX, startY, radius, red, green, blue;
		for (String line : lines) {
			if (line.isEmpty())
				continue;
			String[] elements = line.split(" ");
			String object = elements[0].toUpperCase();
			switch (object) {
			case "LINE":
				startX = Integer.parseInt(elements[1]);
				startY = Integer.parseInt(elements[2]);
				int endX = Integer.parseInt(elements[3]);
				int endY = Integer.parseInt(elements[4]);
				red = Integer.parseInt(elements[5]);
				green = Integer.parseInt(elements[6]);
				blue = Integer.parseInt(elements[7]);

				if (startX < 0 || startY < 0 || endX < 0 || endY < 0) {
					throw new IllegalArgumentException();
				}

				checkColor(red, green, blue);
				Line l = new Line(new Point(startX, startY), new Point(endX, endY), new Color(red, green, blue));
				if (!objects.contains(l)) {
					objects.add(l);
				}

				break;
			case "CIRCLE":

				startX = Integer.parseInt(elements[1]);
				startY = Integer.parseInt(elements[2]);
				radius = Integer.parseInt(elements[3]);
				red = Integer.parseInt(elements[4]);
				green = Integer.parseInt(elements[5]);
				blue = Integer.parseInt(elements[6]);

				if (startX < 0 || startY < 0 || radius < 0) {
					throw new IllegalArgumentException();
				}

				checkColor(red, green, blue);
				Circle circle = new Circle(new Point(startX, startY), radius, new Color(red, green, blue));
				if (!objects.contains(circle)) {
					objects.add(circle);
				}

				break;
			case "FCIRCLE":
				startX = Integer.parseInt(elements[1]);
				startY = Integer.parseInt(elements[2]);
				radius = Integer.parseInt(elements[3]);
				red = Integer.parseInt(elements[4]);
				green = Integer.parseInt(elements[5]);
				blue = Integer.parseInt(elements[6]);
				int rf = Integer.parseInt(elements[7]);
				int gf = Integer.parseInt(elements[8]);
				int bf = Integer.parseInt(elements[9]);

				if (startX < 0 || startY < 0 || radius < 0) {
					throw new IllegalArgumentException();
				}

				checkColor(red, green, blue);
				checkColor(rf, gf, bf);
				FilledCircle fcircle = new FilledCircle(new Point(startX, startY), radius, new Color(red, green, blue),
						new Color(rf, gf, bf));
				if (!objects.contains(fcircle)) {
					objects.add(fcircle);
				}

				break;
			case "FPOLY":
				int n = Integer.parseInt(elements[1]);

				List<Point> points = new ArrayList<>();
				
				for(int i = 0;i<n;i++) {
					points.add(new Point(Integer.parseInt(elements[2*i+2]), Integer.parseInt(elements[2*i+3])));
				}
				
				red = Integer.parseInt(elements[2*n+2]);
				green = Integer.parseInt(elements[2*n+3]);
				blue = Integer.parseInt(elements[2*n+4]);
				rf = Integer.parseInt(elements[2*n+5]);
				gf = Integer.parseInt(elements[2*n+6]);
				bf = Integer.parseInt(elements[2*n+7]);

				checkColor(red, green, blue);
				checkColor(rf, gf, bf);
				
				ConvexPolygon polygon = new ConvexPolygon(points, new Color(red, green, blue), new Color(rf, gf, bf));
				if (!objects.contains(polygon)) {
					objects.add(polygon);
				}

				break;
			default:
				throw new IllegalArgumentException();
			}
		}

		return objects;
	}

	/**
	 * Checks the range of color components - must be 0-255.
	 * 
	 * @param red
	 *            - red component
	 * @param green
	 *            - green component
	 * @param blue
	 *            - blue component
	 * @throws IllegalArgumentException
	 *             - if either component is not in rang 0-255
	 */
	private void checkColor(int red, int green, int blue) {
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
			throw new IllegalArgumentException();
		}
	}

}
