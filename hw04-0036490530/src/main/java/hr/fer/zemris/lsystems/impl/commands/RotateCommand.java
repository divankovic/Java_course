package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Command used for changing the direction of the current {@link TurtleState}.
 * @author Dorian Ivankovic
 *
 */
public class RotateCommand implements Command {
	
	/**
	 * Angle of rotation.
	 */
	private double angle;
	
	/**
	 * Default constructor.
	 * @param angle - angle of the <code>RotateCommand</code>.
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}
	
	/**
	 * Changers the direction of the current {@link TurtleState} by rotating by angle in degrees.
	 * @param ctx - context of the <code>LSystem</code>
	 * @param painter - used for drawing
	 */
	public void execute(Context ctx, Painter painter) {

		ctx.getCurrentState().getDirection().rotate(angle);
	}

}
