package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.DoubleBinaryOperator;

/**
 * An implementation of {@link CalcModel}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Current value of the model.
	 */
	private String currentValue;

	/**
	 * Active operand.
	 */
	private Double activeOperand;

	/**
	 * Pending binary operation.
	 */
	private DoubleBinaryOperator pendingOperation;

	/**
	 * Listeners of the model.
	 */
	private Set<CalcValueListener> listeners;

	/**
	 * Constructs a new {@link CalcModelImpl}.
	 */
	public CalcModelImpl() {
		listeners = new HashSet<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		Objects.requireNonNull(l);
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		if (l == null)
			return;
		listeners.remove(l);
	}

	private void notifyListeners() {
		List<CalcValueListener> listenersCpy = new ArrayList<>(listeners);
		for (CalcValueListener listener : listenersCpy) {
			listener.valueChanged(this);
		}
	}

	@Override
	public String toString() {
		if (currentValue == null)
			return "0";
		else
			return currentValue;
	}

	@Override
	public double getValue() {
		if (currentValue == null)
			return 0.0;
		else
			return Double.parseDouble(currentValue);
	}

	@Override
	public void setValue(double value) {
		if (Double.isFinite(value)) {

			currentValue = String.valueOf(value);

			if ((value - Math.floor(value)) < 1E-20) {
				currentValue = currentValue.substring(0, currentValue.indexOf('.'));
			} else if ((Math.ceil(value) - value) < 1E-20) {
				currentValue = String.valueOf(Math.ceil(value)).substring(0, currentValue.indexOf('.'));
			}

			notifyListeners();
		}
	}

	@Override
	public void clear() {
		currentValue = null;
		notifyListeners();
	}

	@Override
	public void clearAll() {
		currentValue = null;
		activeOperand = null;
		pendingOperation = null;
		notifyListeners();
	}

	@Override
	public void swapSign() {
		if (currentValue != null) {
			if (!currentValue.startsWith("-")) {
				currentValue = "-" + currentValue;
			} else {
				currentValue = currentValue.replace("-", "");
			}
			notifyListeners();
		}
	}

	@Override
	public void insertDecimalPoint() {
		if (currentValue != null) {
			if (!currentValue.contains(".")) {
				currentValue += ".";
				notifyListeners();
			}
		} else {
			currentValue = "0.";
			notifyListeners();
		}
	}

	@Override
	public void insertDigit(int digit) {

		if (currentValue == null) {
			currentValue = String.valueOf(digit);
		} else {
			if (currentValue.equals("0") && digit == 0)
				return;
			String newValue = (currentValue.equals("0") ? "" : currentValue) + digit;
			if (Double.isFinite(Double.parseDouble(newValue))) {
				currentValue = newValue;
			}
		}
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() {
		if (activeOperand == null)
			throw new IllegalStateException();
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		if (Double.isFinite(activeOperand)) {
			this.activeOperand = activeOperand;
		}
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		if (pendingOperation == null)
			throw new IllegalStateException();
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

}
