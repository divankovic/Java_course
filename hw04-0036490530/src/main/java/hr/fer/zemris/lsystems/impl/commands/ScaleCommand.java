package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Updates the effective step size by multiplying by factor scale.
 * @author Dorian Ivankovic
 *
 */

public class ScaleCommand implements Command {
	
	/**
	 * Used for step scaling.
	 */
	private double scale;
	
	/**
	 * Default constructor for <code>ScaleCommand</code>.
	 * @param scale - new scale size
	 */
	public ScaleCommand(double scale) {
		this.scale = scale;
	}
	
	/**
	 * Updates the effective step size of the current {@link TurtleState} by multiplying by factor scale.
	 * @param ctx - context of the <code>LSystem</code>
	 * @param painter - used for drawing
	 */
	public void execute(Context ctx, Painter painter) {
		TurtleState state = ctx.getCurrentState();
		state.setStep(state.getStep()*scale);
	}

}
