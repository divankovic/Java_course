package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * The command is used to draw the next line - length of step in the current direction of the turtle.
 * @author Dorian Ivankovic
 *
 */
public class DrawCommand implements Command {

	/**
	 * Step size of the next line.
	 */
	private double step;
	
	/**
	 * Default constructor for <code>DrawCommand</code>.
	 * @param step - step size
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	/**
	 * Draws the next line - length of step in the current direction of the turtle.
	 * The effective step size is step * step of the current {@link TurtleState} which is determined by
	 * the productions level.
	 * For more information see {@link LSystemBuilderImpl}
	 * @param ctx - context of the <code>LSystem</code>
	 * @param painter - used for drawing
	 */
	public void execute(Context ctx, Painter painter) {
		TurtleState currentState = ctx.getCurrentState();
		Vector2D currentPosition = currentState.getPosition();
		Vector2D direction = currentState.getDirection();
		
		double stepScaled = step* currentState.getStep();
		Vector2D offset = new Vector2D(direction.getX()*stepScaled, direction.getY()*stepScaled);
		Vector2D nextPosition = currentPosition.translated(offset);
		
		painter.drawLine(currentPosition.getX(),currentPosition.getY(), nextPosition.getX(), nextPosition.getY(),
				ctx.getCurrentState().getColor(), 1);
		
		currentState.setPosition(nextPosition);
		
	}

}
