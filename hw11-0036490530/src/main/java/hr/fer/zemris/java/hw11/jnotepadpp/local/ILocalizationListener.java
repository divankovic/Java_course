package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Listener of the current language used, provided by
 * {@link ILocalizationProvider}. When the current language changes,
 * {@link #localizationChanged} is called.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface ILocalizationListener {

	/**
	 * This method is called when {@link ILocalizationProvider}'s current language
	 * is changed.
	 */
	void localizationChanged();
}
