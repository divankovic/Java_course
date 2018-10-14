package hr.fer.zemris.lsystems.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Used for testing the {@link LSystem}.
 * @author Dorian Ivankovic
 *
 */
public class Glavni2 {
	
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
		
	}
	
	
	/**
	 * Creates a custom Koch curve by setting up a new {@link LSystemBuilderProvider}.
	 * @param provider - used to create a <code>LSystemBuilderProvider</code>
	 * @return new LSystem
	 * @see <a href=Koch>https://en.wikipedia.org/wiki/Koch_snowflake</a>
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] { 
				"origin 0.05 0.4",
				"angle 0",
				"unitLength 0.9",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
}
