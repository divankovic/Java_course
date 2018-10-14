package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.math.Vector2D;

/**
 * Represents the state of the turtle when drawing lsystem's state
 * using {@link Painter}.
 * @author Dorian Ivankovic
 *
 */
public class TurtleState {
	
	/**
	 * Current position of the turtle - from point (0,0), expressed
	 * as x,y in [0,1] where x=1 is the right end of the screen and y=1 is the top of the screen.
	 */
	private Vector2D position;
	
	/**
	 * Current direction of the turtle - normalised vector.
	 */
	private Vector2D direction;
	
	/**
	 * Current color used for drawing lines.
	 */
	private Color color;
	
	/**
	 * Step size of the turtle's transition to the next point.
	 */
	private double step;
	
	
	/**
	 * Default <code>TurtleState</code> constructor.
	 */
	public TurtleState() {
		
	}
	
	/**
	 * Constructor for <code>TurtleState</code>.
	 * @param position - position of the turtle
	 * @param direction - direction of the turtle - normalised vector
	 * @param color - color used for drawing lines in the current TurtleState
	 * @param step - Step size of the turtle's transition to the next point
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double step) {
		Objects.requireNonNull(position,"Position must not be null");
		Objects.requireNonNull(direction,"Direction must not be null");
		Objects.requireNonNull(color, "Color must not be null");
		
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.step = step;
	}
	
	/**
	 * Returns a copy of the current <code>TurtleState</code>.
	 * @return copy of the current turtle state
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(),direction.copy(),new Color(color.getRGB()),step);
	}

	/**
	 * Returns the current direction of the turtle.
	 * @return current direction of the turtle
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * Sets the direction of the turtle.
	 * @param direction - new direction of the turtle
	 * @throws NullPointerException if direction is null
	 */
	public void setDirection(Vector2D direction) {
		Objects.requireNonNull(direction,"direction must not be null");
		this.direction = direction;
	}

	/**
	 * Returns the current position of the turtle.
	 * @return current position of the turtle
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * Sets the position of the turtle.
	 * @param position - new position of the turtle
	 * @throws NullPointerException if position is null
	 */
	public void setPosition(Vector2D position) {
		Objects.requireNonNull(position, "position must not be null.");
		this.position = position;
	}

	
	/**
	 * Returns the color currently used when drawing in <code>TurtleState</code>.
	 * @return color currently used when drawing
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color used for drawing using turtle.
	 * @param color - new color to use when drawing
	 * @throws NullPointerException if color is null
	 */
	public void setColor(Color color) {
		Objects.requireNonNull(color,"Color must not be null");
		this.color = color;
	}

	/**
	 * Returns the current size of the step used when drawing using turtle.
	 * @return current size of the step
	 */
	public double getStep() {
		return step;
	}

	/**
	 * Sets the step size used when drawing using turtle.
	 * @param step - new step size
	 */
	public void setStep(double step) {
		this.step = step;
	}
	
	
	
	
}
