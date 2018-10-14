package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Concrete singleton implementation of {@link ILocalizationProvider} using the
 * support of {@link AbstractLocalizationProvider} for listeners notification.
 * 
 * @author Dorian Ivankovic
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Default language used.
	 */
	public static String DEFAULT_LANGUAGE = "en";

	/**
	 * Dictionary files base name.
	 */
	private static String BASE_NAME = "hr.fer.zemris.java.hw11.jnotepadpp.dict";

	/**
	 * Single instance of this class.
	 */
	private static LocalizationProvider instance = new LocalizationProvider();

	/**
	 * Currently used language.
	 */
	private String language;

	/**
	 * A bundle holding all dictionaries needed for translation.
	 */
	private ResourceBundle bundle;

	/**
	 * Constructs a new {@link LocalizationProvider}.
	 */
	private LocalizationProvider() {
		language = DEFAULT_LANGUAGE;
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BASE_NAME, locale);
	}

	/**
	 * Returns an instance of {@link LocalizationProvider}.
	 * 
	 * @return instance of <code>LocalizationProvider</code>
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}

	/**
	 * Sets the current language. This method notifies all listeners about the
	 * language change if the langage actually has changed.
	 * 
	 * @param language
	 *            - new language
	 */
	public void setLanguage(String language) {
		if (language.equals(this.language))
			return;
		this.language = language;
		bundle = ResourceBundle.getBundle(BASE_NAME, Locale.forLanguageTag(language));
		fire();
	}

	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	@Override
	public String getCurrentLanguage() {
		return language;
	}
}
