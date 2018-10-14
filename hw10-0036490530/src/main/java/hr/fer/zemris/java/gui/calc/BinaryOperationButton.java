package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;

/**
 * A button that contains a {@link DoubleBinaryOperator} operation.
 * @author Dorian Ivankovic
 *
 */
public class BinaryOperationButton extends JButton{

	/**
	 * Unique identifier
	 */
	private static final long serialVersionUID = 2491977077625981544L;

	/**
	 * Binary operation.
	 */
	private DoubleBinaryOperator operation;

	/**
	 * Constructs a new {@link BinaryOperationButton} using it's operation and button's name.
	 * @param operation - binary operation
	 * @param text - button displayed name
	 */
	public BinaryOperationButton(DoubleBinaryOperator operation, String text) {
		super(text);
		this.operation = operation;
		Calculator.setDefaultCalcElement(this);
	}

	/**
	 * Returns the button's binary operation.
	 * @return button's binary operation
	 */
	public DoubleBinaryOperator getOperation() {
		return operation;
	}
	
	
	
	
}
