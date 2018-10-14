package hr.fer.zemris.lsystems.impl;

import java.util.Objects;

import hr.fer.zemris.java.hw04.custom.collections.EmptyStackException;
import hr.fer.zemris.java.hw04.custom.collections.ObjectStack;

/**
 * Context is used to memorise the previous {@link TurtleState} when drawing
 * structures using an {@link ObjectStack}.
 * @author Dorian Ivankovic
 *
 */
public class Context {
	
	/**
	 * Stack used to store previous {@link TurtleState}'s.
	 */
	private ObjectStack stack;
	
	
	/**
	 * Default constructor for the <code>Context</code>.
	 */
	public Context() {
		stack = new ObjectStack();
	}
	
	/**
	 * Returns the current state of the <code>Context</code> which is the
	 * one that is currently at the top of the stack.
	 * @return current state of the <code>Context</code>
	 * @throws EmptyStackException if the stack is empty
	 */
	public TurtleState getCurrentState() {
		return (TurtleState) stack.peek();
	}
	
	
	/**
	 * Adds a new {@link TurtleState} by pushing it to the stack.
	 * @param state - newest state of the <code>Context</code>.
	 * @throws NullPointerException if state is null
	 */
	public void pushState(TurtleState state) {
		Objects.requireNonNull(state,"state must not be null.");
		stack.push(state);
	}
	
	/**
	 * Removes the last {@link TurtleState} from the stack.
	 * @throws EmptyStackException if the stack is already empty
	 */
	public void popState() {
		stack.pop();
	}

}
