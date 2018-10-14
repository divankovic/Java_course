package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * A command that can be executedin lsystems context.
 * @author Dorian Ivankovic
 *
 */
public interface Command {
	
	/**
	 * Executes the specified action using lsystem's context and painter used for drawing.
	 * @param ctx  - context of the lsystem
	 * @param painter - painter used to draw lsystem's development
	 */
	void execute(Context ctx, Painter painter);
}
