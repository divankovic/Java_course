package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implements listener support of {@link ILocalizationProvider}.
 * 
 * @author Dorian Ivankovic
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * Collection of listeners.
	 */
	private Set<ILocalizationListener> listeners = new HashSet<>();

	@Override
	public void addLocalizationListener(ILocalizationListener listener) {
		Objects.requireNonNull(listener, "Listener must not be null.");
		listeners.add(listener);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener listener) {
		if (listener != null) {
			listeners.remove(listener);
		}
	}

	/**
	 * Notifies all listeners about the language change by using
	 * {@link ILocalizationListener#localizationChanged}.
	 */
	public void fire() {
		List<ILocalizationListener> listenersCopy = new ArrayList<>(listeners);
		listenersCopy.forEach(listener -> listener.localizationChanged());
	}

}
