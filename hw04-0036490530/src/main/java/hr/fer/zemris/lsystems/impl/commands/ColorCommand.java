package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * The command is used to set the color of the current {@link TurtleState} of the {@link Context}.
 * @author Dorian Ivankovic
 *
 */
public class ColorCommand implements Command {
	
	/**
	 * Color used for drawing.
	 */
	private Color color;
	
	/**
	 * Default constuctor for <code>ColorCommand</code>.
	 * @param color - color used for drawing
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	/**
	 * Sets the color of the current {@link TurtleState} of the {@link Context}.
	 * @param ctx - context of the <code>LSystem</code>
	 * @param painter - used for drawing
	 */
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

}
