package hr.fer.zemris.java.gui.calc;

/**
 * A listener of the current value of {@link CalcModel}.
 * @author Dorian Ivankovic
 *
 */
public interface CalcValueListener {
	
	/**
	 * This method is called once the {@link CalcModel}'s current value is changed.
	 * @param model - observed model
	 */
	void valueChanged(CalcModel model);
}