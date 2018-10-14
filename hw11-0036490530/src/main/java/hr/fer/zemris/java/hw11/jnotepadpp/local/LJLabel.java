package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Objects;

import javax.swing.JLabel;

/**
 * A localizable {@link JLabel}. The label holds a key under the translation of
 * the label can be fetched from {@link ILocalizationProvider} and updates it's
 * text every time the language changes.
 * 
 * @author Dorian Ivankovic
 *
 */
public class LJLabel extends JLabel {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 4589582964745119032L;

	/**
	 * Label text translation key.
	 */
	private String key;

	/**
	 * {@link ILocalizationProvider} object used to fetch translations.
	 */
	private ILocalizationProvider provider;

	/**
	 * Constructs a new {@link LJLabel} using text key, and provider used for
	 * translation.
	 * 
	 * @param key
	 *            - text key
	 * @param provider
	 *            - used for key translation
	 * @throws NullPointerException if key or provider is null
	 */
	public LJLabel(String key, ILocalizationProvider provider) {
		Objects.requireNonNull(key, "key must not be null.");
		Objects.requireNonNull(provider, "provider must not be null.");

		this.key = key;
		this.provider = provider;

		updateLabel();

		provider.addLocalizationListener(() -> {
			updateLabel();
		});
	}

	/**
	 * Updates the current label's text.
	 */
	private void updateLabel() {
		String translation = provider.getString(key);
		this.setText(translation);
	}

}
