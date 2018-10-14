package hr.fer.zemris.java.hw11.jnotepadpp.model;

import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.actions.CloseFileAction;

/**
 * A custom tab component that containes document modification icon, document text,
 * and a document close button.
 * @author Dorian Ivankovic
 *
 */
public class ButtonTabComponent extends JPanel{

	/**
	 * Unique identifier
	 */
	private static final long serialVersionUID = 1371684057805196969L;

	@SuppressWarnings("unused")
	/**
	 * JTabbedPane and MultipleDocument model
	 */
	private MultipleDocumentModel model;
	
	/**
	 * Tabbed pane(parent)
	 */
	private final JTabbedPane pane;
	
	/**
	 * Title label of the button, i.e panel
	 */
	private JLabel titleLabel;
	
	/**
	 * Constructs a new ButtonTabComponent using a tabbed pane model
	 * @param model tabbed pane and MultipleDocumentModel
	 */
	public ButtonTabComponent(MultipleDocumentModel model) {
		
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		
		this.model = model;
		pane = (JTabbedPane)model;
		setOpaque(false);
		
		titleLabel = new JLabel() {
			
			private static final long serialVersionUID = -4448295524557779013L;

			public String getText() {
				int i = pane.indexOfTabComponent(ButtonTabComponent.this);
				if(i!=-1) {
					return pane.getTitleAt(i);
				}
				return null;
			}
			
			public Icon getIcon() {
				int i = pane.indexOfTabComponent(ButtonTabComponent.this);
				if(i!=-1) {
					return pane.getIconAt(i);
				}
				return null;
			}
		};
		
		ImageIcon closeInactiveIcon = IconLoader.loadIcon(ButtonTabComponent.class, "close_not_active.png");
		ImageIcon closeActiveIcon = IconLoader.loadIcon(ButtonTabComponent.class, "close_active.png");
		
		JButton closeButton = new JButton(closeInactiveIcon);
		closeButton.setOpaque(false);
		closeButton.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		closeButton.setContentAreaFilled(false);
		closeButton.setRolloverIcon(closeActiveIcon);
		
		closeButton.addActionListener(event->{
			CloseFileAction.closeAndSave(this.getTopLevelAncestor(),
					model, model.getDocument(pane.indexOfTabComponent(ButtonTabComponent.this)),
					((JNotepadPP) SwingUtilities.getWindowAncestor(ButtonTabComponent.this)).getLocalizationProvider());
		});
	
		add(titleLabel);
		add(closeButton);
	}
	
	/**
	 * Returns the title of tht button component.
	 * @return title of the current componenet
	 */
	public String getTitle() {
		return titleLabel.getText();
	}
}
