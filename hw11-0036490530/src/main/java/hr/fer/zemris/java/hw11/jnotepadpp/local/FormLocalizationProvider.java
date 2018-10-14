package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

/**
 * {@link LocalizationProviderBridge} i.e. proxy implementation. Adds a listener
 * to the frame that automatically calls
 * {@link LocalizationProviderBridge#disconnect} when the window is closing. All
 * frames that need this service simply need to create a new
 * {@link FormLocalizationProvider}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructs a new {@link FormLocalizationProvider} using parent
	 * {@link ILocalizationProvider} and a frame that needs to disonnent this proxy
	 * from the provider when closing.
	 * 
	 * @param parent
	 *            - parent {@link ILocalizationProvider}
	 * @param frame
	 *            - monitored frame
	 */
	public FormLocalizationProvider(ILocalizationProvider parent, JFrame frame) {
		super(parent);

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent event) {
				FormLocalizationProvider.this.disconnect();
			}

			@Override
			public void windowOpened(WindowEvent event) {
				FormLocalizationProvider.this.connect();
			}

		});
	}

}
