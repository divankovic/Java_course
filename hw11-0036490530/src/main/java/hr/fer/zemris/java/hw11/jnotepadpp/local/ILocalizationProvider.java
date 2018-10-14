package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Provides services of getting localized words.<br>
 * Each word is fetched from a dictionary under a given key, for example "Name"
 * would be under key name... Notifies all listeners when the current language
 * is changed.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface ILocalizationProvider {

	/**
	 * Fetches localized translation of the word under key <code>key</code>.
	 * 
	 * @param key
	 *            - key of the desired translation
	 * @return localized translation
	 */
	String getString(String key);

	/**
	 * Adds a new {@link ILocalizationListener} interested in language change.
	 * 
	 * @param listener
	 *            - new listener
	 */
	void addLocalizationListener(ILocalizationListener listener);

	/**
	 * Removes the listener from the collection of listers notified about language
	 * change.
	 * 
	 * @param listener
	 *            - listener to remove
	 */
	void removeLocalizationListener(ILocalizationListener listener);

	/**
	 * Returns the current language.
	 * 
	 * @return currentLanguage
	 */
	String getCurrentLanguage();
}
