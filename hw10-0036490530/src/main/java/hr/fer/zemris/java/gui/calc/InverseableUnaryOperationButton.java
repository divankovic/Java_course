package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;

/**
 * A button that stores an inverseable unary operation and provides method {@link InverseableUnaryOperationButton#perform}
 * that performs the given operation on given argument.
 * @author Dorian Ivankovic
 *
 */
public class InverseableUnaryOperationButton extends JButton{

	/**
	 * Unique identifier
	 */
	private static final long serialVersionUID = -7603030406831526626L;
	
	/**
	 * Unary operation.
	 */
	private DoubleUnaryOperator operation;
	
	/**
	 * Inverse unary operation.
	 */
	private DoubleUnaryOperator inverseOperation;
	
	/**
	 * Constructs a new {@link InverseableUnaryOperationButton} using button's text,
	 * unary operation and the unary operation's inverse.
	 * @param text - text of the button
	 * @param operation - unary operation
	 * @param inverseOperation - inverse of the unary operation
	 */
	public InverseableUnaryOperationButton(String text, DoubleUnaryOperator operation, DoubleUnaryOperator inverseOperation) {
		super(text);
		this.operation = operation;
		this.inverseOperation = inverseOperation;
		Calculator.setDefaultCalcElement(this);
	}
	
	/**
	 * Performs the unary operation if <code>inverse</code> flag is false,
	 * and performs the inverse unary operation on the given argument <code>value</code> otherwise.
	 * @param inverse - to determine whether to inverse the operation or not
	 * @param value - argument for the operations
	 * @return result of the operation
	 */
	public double perform(boolean inverse, double value) {
		if(inverse) {
			return inverseOperation.applyAsDouble(value);
		}
		return operation.applyAsDouble(value);
	}
}
