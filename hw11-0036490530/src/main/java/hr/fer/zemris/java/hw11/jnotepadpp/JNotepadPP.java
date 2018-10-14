package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;

import hr.fer.zemris.java.hw11.jnotepadpp.actions.ChangeCaseAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CopyAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CutAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.ExitAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.NewFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.OpenFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.PasteAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveAsFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SaveFileAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.SortAction;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.StatInfoAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJToolBar;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.model.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.IconLoader;
import hr.fer.zemris.java.hw11.jnotepadpp.model.JToolbarButton;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.model.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.model.SingleDocumentModel;

/**
 * A simple Notepad++ like application for text editing. Has options like
 * opening files from data system, editing and saving files, editing mulitple
 * files at once , etc.
 * 
 * @author Dorian Ivankovic
 *
 */
public class JNotepadPP extends JFrame {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 5729111831966518041L;

	/**
	 * Default title of the application.
	 */
	public static final String title = "JNotepad++";

	/**
	 * Multiple document model used in the application.
	 */
	private MultipleDocumentModel model;

	/**
	 * Application's menu bar.
	 */
	private JMenuBar menuBar;

	/**
	 * Application's tool bar, contains shortcuts for all basic operations.
	 */
	private JToolBar toolBar;

	/**
	 * Application's status bar. Described in {@link JNotepadStatusBar}.
	 */
	private JNotepadStatusBar statusBar;

	/**
	 * New file action.
	 */
	private AbstractAction newFileAction;

	/**
	 * Open file action.
	 */
	private AbstractAction openFileAction;

	/**
	 * Save file action.
	 */
	private AbstractAction saveFileAction;

	/**
	 * Save as file action.
	 */
	private AbstractAction saveAsFileAction;

	/**
	 * Close file action.
	 */
	private AbstractAction closeFileAction;

	/**
	 * Exit application action.
	 */
	private AbstractAction exitAction;

	/**
	 * Cut action.
	 */
	private AbstractAction cutAction;

	/**
	 * Copy action.
	 */
	private AbstractAction copyAction;

	/**
	 * Paste action.
	 */
	private AbstractAction pasteAction;

	/**
	 * Statistical information action.
	 */
	private AbstractAction statInfoAction;

	/**
	 * Upper case action.
	 */
	private AbstractAction upperCaseAction;

	/**
	 * Lower case action.
	 */
	private AbstractAction lowerCaseAction;

	/**
	 * Invert case action.
	 */
	private AbstractAction invertCaseAction;

	/**
	 * Sort ascending action.
	 */
	private AbstractAction sortAscendingAction;

	/**
	 * Sort descending action.
	 */
	private AbstractAction sortDescendingAction;

	/**
	 * Remove duplicates action.
	 */
	private AbstractAction removeDuplicatesAction;

	/**
	 * Localization provider.
	 */
	private FormLocalizationProvider flp;

	/**
	 * Constructs a new {@link JNotepadPP} and initializes it's components.
	 */
	public JNotepadPP() {
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLocation(0, 0);
		setSize(600, 600);
		setTitle(title);
		initGUI();

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent event) {
				ExitAction.closeAll(JNotepadPP.this, model, flp);

				if (!JNotepadPP.this.isVisible()) {
					statusBar.stopClock();
				}
			}

		});
	}

	/**
	 * Initializes the GUI of the application.
	 */
	private void initGUI() {
		model = new DefaultMultipleDocumentModel();
		model.addMultipleDocumentListener(new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {

				if (currentModel != null) {
					updateStatusBarAndSelectionActions(currentModel.getTextComponent());
					if (currentModel.isModified()) {
						saveFileAction.setEnabled(true);
					} else {
						saveFileAction.setEnabled(false);
					}
				} else {
					saveFileAction.setEnabled(false);
				}

				JNotepadPP.this
						.setTitle((currentModel == null ? "" : currentModel.getFilePath().toString() + " - ") + title);
			}

			@Override
			public void documentAdded(SingleDocumentModel documentModel) {
				JTextArea textArea = documentModel.getTextComponent();
				textArea.addCaretListener(event -> {

					updateStatusBarAndSelectionActions(textArea);

				});

				if (model.getNumberOfDocuments() == 1) {
					saveAsFileAction.setEnabled(true);
					closeFileAction.setEnabled(true);
					pasteAction.setEnabled(true);
					statInfoAction.setEnabled(true);
					statusBar.setVisible(true);
				}
			}

			private void updateStatusBarAndSelectionActions(JTextArea textArea) {
				int length = textArea.getText().length();
				statusBar.setLength(length);

				int caretPosition = textArea.getCaretPosition();
			
				if(caretPosition == length+1) caretPosition--;
				
				int line = 1;
				int column = 1;
				try {
					line = textArea.getLineOfOffset(caretPosition);
					column = caretPosition - textArea.getLineStartOffset(line);
				} catch (BadLocationException e) {
					System.out.println(caretPosition);
					e.printStackTrace();
				}

				int selected = textArea.getSelectionEnd() - textArea.getSelectionStart();

				statusBar.setPosition(line + 1, column + 1, selected);

				if (selected != 0) {
					cutAction.setEnabled(true);
					copyAction.setEnabled(true);

					upperCaseAction.setEnabled(true);
					lowerCaseAction.setEnabled(true);
					invertCaseAction.setEnabled(true);

					sortAscendingAction.setEnabled(true);
					sortDescendingAction.setEnabled(true);
					removeDuplicatesAction.setEnabled(true);

				} else {
					cutAction.setEnabled(false);
					copyAction.setEnabled(false);

					upperCaseAction.setEnabled(false);
					lowerCaseAction.setEnabled(false);
					invertCaseAction.setEnabled(false);

					sortAscendingAction.setEnabled(false);
					sortDescendingAction.setEnabled(false);
					removeDuplicatesAction.setEnabled(false);

				}
			}

			@Override
			public void documentRemoved(SingleDocumentModel documentModel) {
				if (model.getNumberOfDocuments() == 0) {
					saveFileAction.setEnabled(false);
					saveAsFileAction.setEnabled(false);
					closeFileAction.setEnabled(false);
					pasteAction.setEnabled(false);
					statInfoAction.setEnabled(false);
					statusBar.setVisible(false);
				}
			}
		});

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add((JTabbedPane) model, BorderLayout.CENTER);

		statusBar = new JNotepadStatusBar(flp);
		statusBar.setVisible(false);
		cp.add(statusBar, BorderLayout.SOUTH);

		createActions();
		createMenus();
		createToolbar();
	}

	/**
	 * Creates all actions of the application.
	 */
	private void createActions() {
		newFileAction = new NewFileAction("new", "new_mnemonic", "new_description", flp, model);
		openFileAction = new OpenFileAction("open", "open_mnemonic", "open_description", flp, model);

		saveFileAction = new SaveFileAction("save", "save_mnemonic", "save_description", flp, model);
		saveFileAction.setEnabled(false);
		saveAsFileAction = new SaveAsFileAction("save_as", "save_as_mnemonic", "save_as_description", flp, model);
		saveAsFileAction.setEnabled(false);

		closeFileAction = new CloseFileAction("close", "close_mnemonic", "close_description", flp, model);
		closeFileAction.setEnabled(false);
		exitAction = new ExitAction("exit", "exit_mnemonic", "exit_description", flp, JNotepadPP.this, model);

		statInfoAction = new StatInfoAction("stat_info", "stat_info_mnemonic", "stat_info_description", flp, model);
		statInfoAction.setEnabled(false);

		copyAction = new CopyAction("copy", "copy_mnemonic", "copy_description", flp, model);
		copyAction.setEnabled(false);
		cutAction = new CutAction("cut", "cut_mnemonic", "cut_description", flp, model);
		cutAction.setEnabled(false);
		pasteAction = new PasteAction("paste", "paste_mnemonic", "paste_description", flp, model);
		pasteAction.setEnabled(false);

		upperCaseAction = new ChangeCaseAction("upper_case", "upper_case_mnemonic", "upper_case_description",
				KeyStroke.getKeyStroke("control U"), flp, text -> text.toUpperCase(), model);
		upperCaseAction.setEnabled(false);

		lowerCaseAction = new ChangeCaseAction("lower_case", "lower_case_mnemonic", "lower_case_description",
				KeyStroke.getKeyStroke("control L"), flp, text -> text.toLowerCase(), model);
		lowerCaseAction.setEnabled(false);

		invertCaseAction = new ChangeCaseAction("invert_case", "invert_case_mnemonic", "invert_case_description",
				KeyStroke.getKeyStroke("control shift I"), flp, text -> ChangeCaseAction.invertCase(text), model);
		invertCaseAction.setEnabled(false);

		sortAscendingAction = new SortAction("sort_a", "sort_a_mnemonic", "sort_a_description",
				KeyStroke.getKeyStroke("control shift A"), flp, model, list -> {
					Locale locale = new Locale(flp.getCurrentLanguage());
					Collator collator = Collator.getInstance(locale);
					Collections.sort(list, collator);
					return list;
				});

		sortAscendingAction.setEnabled(false);

		sortDescendingAction = new SortAction("sort_d", "sort_d_mnemonic", "sort_d_description",
				KeyStroke.getKeyStroke("control shift D"), flp, model, list -> {
					Locale locale = new Locale(flp.getCurrentLanguage());
					Collator collator = Collator.getInstance(locale);
					Collections.sort(list, collator.reversed());
					return list;
				});
		sortDescendingAction.setEnabled(false);

		removeDuplicatesAction = new SortAction("remove_duplicates", "remove_duplicates_mnemonic",
				"remove_duplicates_description", KeyStroke.getKeyStroke("control shift R"), flp, model, list -> {
					Set<String> uniques = new LinkedHashSet<>(list);
					return new ArrayList<>(uniques);
				});
		removeDuplicatesAction.setEnabled(false);
	}

	/**
	 * Creates all the menus of the application.
	 */
	private void createMenus() {
		menuBar = new JMenuBar();

		JMenu fileMenu = new LJMenu("file", flp);
		menuBar.add(fileMenu);

		fileMenu.add(new JMenuItem(newFileAction));
		fileMenu.add(new JMenuItem(openFileAction));

		fileMenu.add(new JMenuItem(saveFileAction));
		fileMenu.add(new JMenuItem(saveAsFileAction));

		fileMenu.add(new JMenuItem(closeFileAction));
		fileMenu.add(new JMenuItem(exitAction));

		fileMenu.add(new JMenuItem(statInfoAction));

		JMenu editMenu = new LJMenu("edit", flp);
		menuBar.add(editMenu);

		editMenu.add(new JMenuItem(cutAction));
		editMenu.add(new JMenuItem(copyAction));
		editMenu.add(new JMenuItem(pasteAction));

		JMenu languagesMenu = new LJMenu("languages", flp);
		menuBar.add(languagesMenu);

		JMenuItem hrLanguageItem = new JMenuItem(new LocalizableAction("hr", "hr_mnemonic", null, flp) {

			private static final long serialVersionUID = 7581026139983053908L;

			@Override
			public void actionPerformed(ActionEvent event) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}

		});
		languagesMenu.add(hrLanguageItem);

		JMenuItem enLanguageItem = new JMenuItem(new LocalizableAction("en", "en_mnemonic", null, flp) {

			private static final long serialVersionUID = 7581026139983053908L;

			@Override
			public void actionPerformed(ActionEvent event) {
				LocalizationProvider.getInstance().setLanguage("en");
			}

		});
		languagesMenu.add(enLanguageItem);

		JMenuItem deLanguageItem = new JMenuItem(new LocalizableAction("de", "de_mnemonic", null, flp) {

			private static final long serialVersionUID = 7581026139983053908L;

			@Override
			public void actionPerformed(ActionEvent event) {
				LocalizationProvider.getInstance().setLanguage("de");
			}

		});
		languagesMenu.add(deLanguageItem);

		JMenu tools = new LJMenu("tools", flp);
		menuBar.add(tools);

		JMenu changeCase = new LJMenu("change_case", flp);
		tools.add(changeCase);

		changeCase.add(new JMenuItem(upperCaseAction));
		changeCase.add(new JMenuItem(lowerCaseAction));
		changeCase.add(new JMenuItem(invertCaseAction));

		JMenu sortMenu = new LJMenu("sort", flp);
		tools.add(sortMenu);

		sortMenu.add(new JMenuItem(sortAscendingAction));
		sortMenu.add(new JMenuItem(sortDescendingAction));
		sortMenu.add(new JMenuItem(removeDuplicatesAction));

		this.setJMenuBar(menuBar);
	}

	/**
	 * Creates the toolbar of the application.
	 */
	private void createToolbar() {
		toolBar = new LJToolBar("toolbar", flp);
		toolBar.setFloatable(true);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		toolBar.add(getToolbarButton(newFileAction, "new_file.png"));
		toolBar.add(getToolbarButton(openFileAction, "open.png"));

		toolBar.add(getToolbarButton(saveFileAction, "save.png"));
		toolBar.add(getToolbarButton(saveAsFileAction, "save_as.png"));

		toolBar.add(getToolbarButton(closeFileAction, "close_file.png"));
		toolBar.add(getToolbarButton(exitAction, "exit.png"));

		toolBar.add(getToolbarButton(statInfoAction, "stat_info.png"));

		toolBar.addSeparator();

		toolBar.add(getToolbarButton(cutAction, "cut.png"));
		toolBar.add(getToolbarButton(copyAction, "copy.png"));
		toolBar.add(getToolbarButton(pasteAction, "paste.png"));

		this.getContentPane().add(toolBar, BorderLayout.PAGE_START);
	}

	/**
	 * Creates a default toolbar button with action and icon loaded based on
	 * iconName.
	 * 
	 * @param action
	 *            - action of the toolbar button
	 * @param iconName
	 *            - name of the toolbar button icon
	 * @return toolbar button
	 */
	private JButton getToolbarButton(AbstractAction action, String iconName) {
		JButton button = new JToolbarButton(action);

		Icon icon = IconLoader.loadIcon(this.getClass(), iconName);
		button.setIcon(icon);
		button.setPreferredSize(new Dimension(icon.getIconWidth() + 5, icon.getIconHeight() + 5));

		return button;
	}

	/**
	 * Returns the application's localization provider.
	 * 
	 * @return localization provider
	 */
	public ILocalizationProvider getLocalizationProvider() {
		return flp;
	}

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            - command line arguments, not used
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
