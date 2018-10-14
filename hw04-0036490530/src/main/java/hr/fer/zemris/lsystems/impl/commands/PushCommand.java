package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * The command is used to add a new {@link TurtleState} to the {@link Context}.
 * @author Dorian Ivankovic
 *
 */
public class PushCommand implements Command {

	/**
	 * Adds a new {@link TurtleState} to the {@link Context}.
	 * @param ctx - context of the <code>LSystem</code>
	 * @param painter - used for drawing
	 */
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		TurtleState copy = currentState.copy();
		ctx.pushState(copy);
	}

}
