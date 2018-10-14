package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JMenu;

/**
 * A localizable {@link JMenu}. The menu holds a key under the translation of
 * the menu can be fetched from {@link ILocalizationProvider} and updates it's
 * text every time the language changes.
 * 
 * @author Dorian Ivankovic
 *
 */
public class LJMenu extends JMenu {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 6245717331704934952L;

	/**
	 * Menu text translation key.
	 */
	private String key;

	/**
	 * {@link ILocalizationProvider} object used to fetch translations.
	 */
	private ILocalizationProvider provider;

	/**
	 * Constructs a new {@link LJMenu} using text key, and provider used for
	 * translation.
	 * 
	 * @param key
	 *            - text key
	 * @param provider
	 *            - used for key translation
	 * @throws NullPointerException
	 *             if key or provider is null
	 */
	public LJMenu(String key, ILocalizationProvider provider) {
		Objects.requireNonNull(key, "key must not be null.");
		Objects.requireNonNull(provider, "provider must not be null.");

		this.key = key;
		this.provider = provider;

		updateMenu();

		provider.addLocalizationListener(() -> {
			updateMenu();
		});
	}

	/**
	 * Updates the current menu's text.
	 */
	private void updateMenu() {
		String translation = provider.getString(key);
		this.setText(translation);
	}
}
