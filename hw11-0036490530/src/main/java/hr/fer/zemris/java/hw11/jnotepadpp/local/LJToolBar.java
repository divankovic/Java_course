package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JToolBar;

/**
 * A localizable {@link JToolBar}. The toolbar holds a key under the translation
 * of the toolbar can be fetched from {@link ILocalizationProvider} and updates
 * it's text every time the language changes.
 * 
 * @author Dorian Ivankovic
 *
 */
public class LJToolBar extends JToolBar {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 3506655799755833092L;

	/**
	 * Toolbar text translation key.
	 */
	private String key;

	/**
	 * {@link ILocalizationProvider} object used to fetch translations.
	 */
	private ILocalizationProvider provider;

	/**
	 * Constructs a new {@link LJToolBar} using text key, and provider used for
	 * translation.
	 * 
	 * @param key
	 *            - text key
	 * @param provider
	 *            - used for key translation
	 * @throws NullPointerException
	 *             if key or provider is null
	 */
	public LJToolBar(String key, ILocalizationProvider provider) {
		Objects.requireNonNull(key, "key must not be null.");
		Objects.requireNonNull(provider, "provider must not be null.");

		this.key = key;
		this.provider = provider;

		updateToolbar();

		provider.addLocalizationListener(() -> {
			updateToolbar();
		});
	}

	/**
	 * Updates the current toolbar's text.
	 */
	private void updateToolbar() {
		String translation = provider.getString(key);
		this.setName(translation);
	}
}
