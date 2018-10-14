package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.KeyEvent;

import java.util.Objects;

import javax.swing.AbstractAction;

/**
 * An {@link AbstractAction} that holds key for action's name, mnemonic, and
 * description key as well as a {@link ILocalizationProvider} used to get
 * current key translations. Once the current language is changed all action
 * files all adequatly updated.
 * 
 * @author Dorian Ivankovic
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = -5476352738325557645L;

	/**
	 * Action name key.
	 */
	private String nameKey;

	/**
	 * Action mnemonic key.
	 */
	private String mnemonicKey;

	/**
	 * Action description key.
	 */
	private String descriptionKey;

	/**
	 * Provider used for key translation.
	 */
	protected ILocalizationProvider provider;

	/**
	 * Constructs a new <code>LocalizableAction</code>.
	 * 
	 * @param nameKey
	 *            - action name key
	 * @param mnemonicKey
	 *            - action mnemonic key
	 * @param descriptionKey
	 *            - action description key
	 * @param provider
	 *            - provider used for key translation
	 * @throws NullPointerException
	 *             if name key or provider is null
	 */
	public LocalizableAction(String nameKey, String mnemonicKey, String descriptionKey,
			ILocalizationProvider provider) {
		Objects.requireNonNull(nameKey, "name key must not be null.");
		Objects.requireNonNull(provider, "provider must not be null.");

		this.nameKey = nameKey;
		this.mnemonicKey = mnemonicKey;
		this.descriptionKey = descriptionKey;
		this.provider = provider;

		setTranslation();

		provider.addLocalizationListener(() -> {
			setTranslation();
		});
	}

	/**
	 * Sets the new translations of the action.
	 */
	private void setTranslation() {
		this.putValue(NAME, provider.getString(nameKey));
		if (mnemonicKey != null) {
			this.putValue(MNEMONIC_KEY, KeyEvent.getExtendedKeyCodeForChar(provider.getString(mnemonicKey).charAt(0)));
		}

		if (descriptionKey != null) {
			this.putValue(SHORT_DESCRIPTION, provider.getString(descriptionKey));
		}
	}
}
