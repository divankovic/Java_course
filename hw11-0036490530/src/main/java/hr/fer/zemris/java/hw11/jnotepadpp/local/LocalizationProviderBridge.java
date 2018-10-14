package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Used as a proxy between the {@link LocalizationProvider} and actual
 * components that need it's services to avoid memory leakeage. Components
 * register as listeners to the proxy, and the proxy is registered as a listener
 * of actual {@link LocalizationProvider}. When the parent of registered
 * components is destroyed it is only required for the proxy to disconnect from
 * the parent provider and the memory leakeage problem is solved.
 * 
 * @author Dorian Ivankovic
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Whether or not the proxy is connected to the central
	 * {@link LocalizationProvider}.
	 */
	private boolean connected;

	/**
	 * Parent that provides all needed localization services.
	 */
	private ILocalizationProvider parent;

	/**
	 * Listener that receives notifications from parent.
	 */
	private ILocalizationListener listener;

	/**
	 * Last memorized language. Used to determine whether or not the current
	 * language has changed from last disconnect.
	 */
	private String language;

	/**
	 * Constructs a new {@link LocalizationProviderBridge}.
	 * 
	 * @param parent
	 *            - actual {@link LocalizationProvider}
	 */
	public LocalizationProviderBridge(ILocalizationProvider parent) {
		this.parent = parent;
		listener = () -> this.fire();
	}

	/**
	 * Connects to the parent {@link LocalizationProvider} to receive updates about
	 * language change.
	 */
	public void connect() {
		if (connected)
			return;
		parent.addLocalizationListener(listener);
		connected = true;

		if (!parent.getCurrentLanguage().equals(language)) {
			this.fire();
		}

	}

	/**
	 * Disconnects from the parent {@link LocalizationProvider} to stop receiving
	 * updates about language change.
	 */
	public void disconnect() {
		if (!connected)
			return;
		parent.removeLocalizationListener(listener);
		connected = false;
		language = parent.getCurrentLanguage();
	}

	@Override
	public String getString(String key) {
		return parent.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return parent.getCurrentLanguage();
	}

}
