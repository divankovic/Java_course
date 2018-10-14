package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Skip command - moves the turtle in the current direction of the turtle and updates the positon of the turtle
 * but doesn't draw a line.
 * @author Dorian Ivankovic
 *
 */
public class SkipCommand implements Command {

	/**
	 * Size of the step of the turtle.
	 */
	private double step;
	
	/**
	 * Default constructor for <code>SkipCommand</code>.
	 * @param step - new step size
	 */
	public SkipCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Moves the turtle in the current direction of the turtle and updates the positon of the turtle
	 * but doesn't draw a line
	 * @param ctx - context of the <code>LSystem</code>
	 * @param painter - used for drawing
	 */
	public void execute(Context ctx, Painter painter) {
		Vector2D currentPosition = ctx.getCurrentState().getPosition();
		Vector2D direction = ctx.getCurrentState().getDirection();
		
		Vector2D offset = new Vector2D(direction.getX()*step, direction.getY()*step);
		Vector2D nextPosition = currentPosition.translated(offset);
		
		currentPosition = nextPosition;
		
	}

}
