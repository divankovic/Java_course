package hr.fer.zemris.lsystems.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Used for testing the {@link LSystem}.
 * @author Dorian Ivankovic
 *
 */
public class Glavni3 {
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(LSystemBuilderImpl::new);
	}
}
