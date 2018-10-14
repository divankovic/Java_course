package hr.fer.zemris.java.gui.calc;

import java.util.function.DoubleBinaryOperator;

/**
 * Defines a calculator's model.
 * @author Dorian Ivankovic
 *
 */
public interface CalcModel {
	
	/**
	 * Adds a new listener to the model, if not already subscribed.
	 * @param l - listener
	 */
	void addCalcValueListener(CalcValueListener l);
	
	/**
	 * Removes the listener from the model.
	 * @param l - listener
	 */
	void removeCalcValueListener(CalcValueListener l);
	
	/**
	 * Returns the string representatition of the current value of the calculator.
	 * The default value is 0.
	 * @return string representation of the current value
	 */
	String toString();
	
	/**
	 * Returns the current value of the calculator, the default is 0.0.
	 * @return current value of the calculator
	 */
	double getValue();
	
	/**
	 * Sets the current value of the calculator if <code>value</code> is not NaN or infinity.
	 * @param value - new value of the calculator
	 */
	void setValue(double value);
	
	/**
	 * Clears the current value of the model.
	 */
	void clear();
	
	/**
	 * Clears the current value, active operand and pending binary operation of the model.
	 */
	void clearAll();
	
	/**
	 * Changes the sign of the current value + to - and - to +.
	 */
	void swapSign();
	
	/**
	 * Inserts the decimal point into the current number if not already there.
	 */
	void insertDecimalPoint();
	
	/**
	 * Inserts a digit at the end of the current value.
	 * For example if current value is "1" and digit is "2" then the new value is "12".
	 * @param digit - digit to insert.
	 */
	void insertDigit(int digit);
	
	/**
	 * Returns the if the active operand is set, false otherwise.
	 * @return true if the active operand is set, false otherwise.
	 */
	boolean isActiveOperandSet();
	
	/**
	 * Returns the active operand.
	 * @return active operand
	 * @throws IllegalStateException if the active operand is not set
	 */
	double getActiveOperand();
	
	/**
	 * Sets the active operand if it's not NaN or infinity.
	 * @param activeOperand - active operand
	 */
	void setActiveOperand(double activeOperand);
	
	/**
	 * Clears the active operand.
	 */
	void clearActiveOperand();
	
	/**
	 * Returns the pending binary operation.
	 * @return pending binary operation.
	 * @throws IllegalStateException if pending binary operation is not set
	 */
	DoubleBinaryOperator getPendingBinaryOperation();
	
	/**
	 * Sets the pending binary operation.
	 * @param op - pending binary operation.
	 */
	void setPendingBinaryOperation(DoubleBinaryOperator op);
}