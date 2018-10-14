package hr.fer.zemris.java.hw16.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.hw16.jvdraw.actions.ExitAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.ExportAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.OpenFileAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveAsFileAction;
import hr.fer.zemris.java.hw16.jvdraw.actions.SaveFileAction;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;
import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.GeometricalEditingException;
import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelImpl;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelListener;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelState;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModelStateListener;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingObjectsListModel;
import hr.fer.zemris.java.hw16.jvdraw.tools.ToolProvider;

/**
 * <p>
 * A simple Paint application. User can add {@link GeometricalObject}'s to the
 * {@link JDrawingCanvas} by mouse clicking. Supported object are {@link Line},
 * {@link Circle} and {@link FilledCircle}. Changing the drawing object is done
 * by selecting the appropriate button on the toolbar.
 * </p>
 * <p>
 * The line is drawn by clicking the position of the start and the end point of
 * the line. Circle and filled circles are drawn by clicking on the position of
 * circle center and another point which defines the radius of the circle.
 * Foreground and background colors can be changed by clicking on
 * {@link JColorArea}'s in the toolbar.
 * 
 * On the right end of the application screen, a list of drawn objects is shown.
 * Objects can be edited by double clicking on the item in the list. Objects can
 * also be removed and shifted up or down in the list by using keys DEl, + and
 * -.
 * </p>
 * <p>
 * The application offers open, save and save as actions. The document is saved
 * in .jvd format which is a textual file containing the specifications of all
 * objects. jsv is specified by {@link GeometricalObject#toJvd()}.
 * 
 * The current document can also be exported as an image.
 * </p>
 * 
 * @author Dorian Ivankovic
 *
 */
public class JVDraw extends JFrame {

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The drawing model of the application that contains all current
	 * {@link GeometricalObject}'s.
	 */
	private DrawingModel drawingModel;

	/**
	 * The state of the drawing model that contains the path to the saved file (if
	 * saved).
	 */
	private DrawingModelState drawingModelState;

	/**
	 * The component responsible for drawing {@link GeometricalObject}'s.
	 */
	private JDrawingCanvas drawingCanvas;

	/**
	 * Foreground color changer.
	 */
	private JColorArea fgColorArea;

	/**
	 * Background color changer.
	 */
	private JColorArea bgColorArea;

	/**
	 * Toolbar.
	 */
	private JToolBar toolBar;

	/**
	 * File menu - contains open, save, save as, exit and export actions.
	 */
	private JMenuBar menuBar;

	/**
	 * A label at the bottom of the application that displays the current foreground
	 * and background color.
	 */
	private JColorInfoLabel colorInfoLabel;

	/**
	 * List that displays all objects in the {@link DrawingModel}.
	 */
	private JList<GeometricalObject> objectList;

	/**
	 * Open file action.
	 */
	private AbstractAction openFileAction;

	/**
	 * Save file action.
	 */
	private AbstractAction saveFileAction;

	/**
	 * Save as action.
	 */
	private AbstractAction saveAsFileAction;

	/**
	 * Exit action.
	 */
	private AbstractAction exitAction;

	/**
	 * Export action
	 */
	private AbstractAction exportAction;

	/**
	 * Constructor. Initializes all parameters of the application.
	 */
	public JVDraw() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(100, 100);
		setSize(900, 600);
		setTitle("JVDraw");

		drawingModel = new DrawingModelImpl();
		drawingModel.addDrawingModelListener(new DrawingModelListener() {

			@Override
			public void objectsAdded(DrawingModel source, int index0, int index1) {
				if (source.getSize() == 1) {
					exportAction.setEnabled(true);
				}
			}

			@Override
			public void objectsRemoved(DrawingModel source, int index0, int index1) {
				if (source.getSize() == 0) {
					exportAction.setEnabled(false);
					saveFileAction.setEnabled(false);
					saveAsFileAction.setEnabled(false);
				}
			}

			@Override
			public void objectsChanged(DrawingModel source, int index0, int index1) {
			}

		});

		drawingModelState = new DrawingModelState();
		drawingModel.addDrawingModelListener(drawingModelState);

		drawingModelState.addListener(new DrawingModelStateListener() {

			@Override
			public void drawingModelStateSaved() {
				saveFileAction.setEnabled(false);
				saveAsFileAction.setEnabled(false);
			}

			@Override
			public void drawingModelStateModified() {
				saveFileAction.setEnabled(true);
				saveAsFileAction.setEnabled(true);
			}
		});

		initGUI();

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent event) {
				exitAction.actionPerformed(null);
			}
		});
	}

	/**
	 * Initializes the GUI of the application.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		fgColorArea = new JColorArea(Color.BLUE);
		bgColorArea = new JColorArea(Color.RED);

		colorInfoLabel = new JColorInfoLabel(fgColorArea, bgColorArea);
		colorInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cp.add(colorInfoLabel, BorderLayout.PAGE_END);

		ToolProvider.init(this, drawingModel, fgColorArea, bgColorArea);
		drawingCanvas = new JDrawingCanvas(drawingModel);
		cp.add(drawingCanvas, BorderLayout.CENTER);

		createToolbar();
		createActions();
		createMenu();

		objectList = new JList<>(new DrawingObjectsListModel(drawingModel));
		objectList.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		cp.add(objectList, BorderLayout.LINE_END);

		objectList.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent event) {
				int[] selected = objectList.getSelectedIndices();

				if (selected.length == 0) {
					return;
				}

				int keyCode = event.getKeyCode();

				if (keyCode == KeyEvent.VK_DELETE) {
					while (selected.length != 0) {
						drawingModel.remove(objectList.getModel().getElementAt(selected[0]));
						selected = objectList.getSelectedIndices();
					}
				} else if (keyCode == KeyEvent.VK_PLUS) {
					int index = selected[0];
					if (index == 0) {
						return;
					}

					drawingModel.changeOrder(objectList.getModel().getElementAt(index), -1);

					objectList.setSelectedIndex(index - 1);
				} else if (keyCode == KeyEvent.VK_MINUS) {
					int index = selected[0];
					if (index == objectList.getModel().getSize() - 1) {
						return;
					}

					drawingModel.changeOrder(objectList.getModel().getElementAt(index), 1);

					objectList.setSelectedIndex(index + 1);
				}

			}

		});

		objectList.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {

				if (evt.getClickCount() == 2) {

					int index = objectList.locationToIndex(evt.getPoint());
					if (index == -1)
						return;

					Rectangle r = objectList.getCellBounds(0, objectList.getLastVisibleIndex());
					if (r == null || !r.contains(evt.getPoint()))
						return;

					GeometricalObject object = objectList.getModel().getElementAt(index);

					GeometricalObjectEditor editor = object.createGeometricalObjectEditor();
					if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "Modify geometrical object",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {

						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (GeometricalEditingException ex) {
							JOptionPane.showMessageDialog(JVDraw.this, ex.getMessage(), "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}
				}

			}
		});
	}

	/**
	 * Creates all actions offered by the application.
	 */
	private void createActions() {
		openFileAction = new OpenFileAction(drawingModel, drawingModelState);

		saveFileAction = new SaveFileAction(drawingModel, drawingModelState);
		saveFileAction.setEnabled(false);

		saveAsFileAction = new SaveAsFileAction(drawingModel, drawingModelState);
		saveAsFileAction.setEnabled(false);

		exitAction = new ExitAction(this, drawingModel, drawingModelState);

		exportAction = new ExportAction(drawingModel);
		exportAction.setEnabled(false);
	}

	/**
	 * Creates the application menu.
	 */
	private void createMenu() {
		menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(openFileAction));
		fileMenu.add(new JMenuItem(saveFileAction));
		fileMenu.add(new JMenuItem(saveAsFileAction));
		fileMenu.add(new JMenuItem(exportAction));

		fileMenu.addSeparator();

		fileMenu.add(new JMenuItem(exitAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Createas the toolbar of the application.
	 */
	private void createToolbar() {
		toolBar = new JToolBar("toolbar");
		toolBar.setFloatable(true);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		toolBar.add(fgColorArea);
		toolBar.add(bgColorArea);

		JToggleButton lineButton = new JToggleButton("Line");
		JToggleButton circleButton = new JToggleButton("Circle");
		JToggleButton fcircleButton = new JToggleButton("Filled circle");
		JToggleButton polygonButton = new JToggleButton("Convex polygon");

		lineButton.addActionListener(e -> drawingCanvas.setCurrentState(ToolProvider.getLineTool()));
		lineButton.setMnemonic(KeyEvent.VK_L);

		circleButton.addActionListener(e -> drawingCanvas.setCurrentState(ToolProvider.getCircleTool()));
		circleButton.setMnemonic(KeyEvent.VK_C);

		fcircleButton.addActionListener(e -> drawingCanvas.setCurrentState(ToolProvider.getfCircleTool()));
		fcircleButton.setMnemonic(KeyEvent.VK_D);
		
		polygonButton.addActionListener(e-> drawingCanvas.setCurrentState(ToolProvider.getConvexPolygonTool()));
		polygonButton.setMnemonic(KeyEvent.VK_P);

		ButtonGroup group = new ButtonGroup();
		group.add(lineButton);
		group.add(circleButton);
		group.add(fcircleButton);
		group.add(polygonButton);

		lineButton.doClick();

		toolBar.add(lineButton);
		toolBar.add(circleButton);
		toolBar.add(fcircleButton);
		toolBar.add(polygonButton);

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            - command line arguments - not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}
}
