package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * The command is used to remove the current {@link TurtleState} from the {@link Context}.
 * @author Dorian Ivankovic
 *
 */
public class PopCommand implements Command {

	/**
	 * Removes the current {@link TurtleState} from the {@link Context}.
	 * @param ctx - context of the <code>LSystem</code>
	 * @param painter - used for drawing
	 */
	public void execute(Context ctx, Painter painter) {
		ctx.popState();
	}

}
