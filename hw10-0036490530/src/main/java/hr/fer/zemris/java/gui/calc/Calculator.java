package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * A simple calculator GUI.
 * @author Dorian Ivankovic
 *
 */
public class Calculator extends JFrame implements CalcValueListener{
	
	/**
	 * Unique identifier.
	 */
	private static final long serialVersionUID = 5016122500695877672L;
	
	/**
	 * Calculator button color.
	 */
	public static final Color BUTTON_COLOR = Color.decode("0x0080FF");
	
	/**
	 * Calculator screen color.
	 */
	public static final Color SCREEN_COLOR = Color.decode("0xDDDD00");
	
	/**
	 * Calculator model.
	 */
	private CalcModel calcModel;

	/**
	 * Calculator's container.
	 */
	private Container cp;
	
	/**
	 * Display of the calculator.
	 */
	private JLabel calcDisplay;
	
	/**
	 * Inverse operation checkbox, inverseable operations perform the inverse when this checkbox is checked.
	 */
	private JCheckBox inverseCheckBox;
	
	/**
	 * Stack of saved operands.
	 */
	private Stack<Double> operandStack;
	
	
	/**
	 * Constructs a new calculator at position (100,100) of size (420,300).
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(100,100);
		setSize(420, 300);
	
		calcModel = new CalcModelImpl();
		calcModel.addCalcValueListener(this);
		
		operandStack = new Stack<>();
		
		initGUI();
	}
	
	/**
	 * Initializes the GUI of the calculator, the calculator uses {@link CalcLayout}.
	 */
	private void initGUI() {
		
		cp = getContentPane();
		cp.setLayout(new CalcLayout(5));
		
		setUpCalculatorDisplay();

		addDigitsAndDecimal();
		
		addUtilityFunctions();
		
		addUnaryOperations();
		
		addBinaryOperationButtons();
	}
	
	/**
	 * Sets the default calculator element look.
	 * @param element - calculator element.
	 */
	public static void setDefaultCalcElement(JComponent element) {
		element.setOpaque(true);
		element.setBackground(BUTTON_COLOR);
		element.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}
	
	/**
	 * Sets up the display of the calculator.
	 */
	private void setUpCalculatorDisplay() {
		
		calcDisplay = new JLabel();
		calcDisplay.setOpaque(true);
		calcDisplay.setBackground(SCREEN_COLOR);
		calcDisplay.setHorizontalAlignment(SwingConstants.RIGHT);
		
		Border margins = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		Border custom = BorderFactory.createMatteBorder(2, 2, 2, 2, BUTTON_COLOR);
		calcDisplay.setBorder(new CompoundBorder(custom, margins));
		
		Font currentFont = calcDisplay.getFont();
		calcDisplay.setFont(new Font(currentFont.getName(), Font.BOLD, (int)(currentFont.getSize()*1.5)));
		valueChanged(calcModel);
		
		cp.add(calcDisplay, "1,1");
	}
	
	/**
	 * Adds digits (0-9) and a decimal separator(.) buttons to the calculator.
	 */
	private void addDigitsAndDecimal() {
		
		for(int i=0;i<=9;i++) {
			JButton digitButton = new JButton(String.valueOf(i));
			setDefaultCalcElement(digitButton);
			digitButton.addActionListener(event->calcModel.insertDigit(Integer.parseInt(digitButton.getText())));
			
			if(i==0) {
				cp.add(digitButton, "5,3");
				continue;
			}
			
			RCPosition buttonPosition = new RCPosition(5-(i-1)/3-1,3+(i-1)%3);
			cp.add(digitButton, buttonPosition);
		}
		
		JButton decimalPointButton = new JButton(".");
		setDefaultCalcElement(decimalPointButton);
		decimalPointButton.addActionListener(event->calcModel.insertDecimalPoint());
		cp.add(decimalPointButton, "5,5");
		
	}
	
	/**
	 * Adds the utility functions to the calculator - clr(clear), res(restart), push(pushes the current operand to the stack), 
	 * pop(pops the operand from the stack if the stack is not empty), inv - inverse check box.
	 */
	private void addUtilityFunctions() {
		
		JButton clearButton = new JButton("clr");
		setDefaultCalcElement(clearButton);
		clearButton.addActionListener(event->calcModel.clear());
		cp.add(clearButton, "1,7");
		
		JButton resetButton = new JButton("res");
		setDefaultCalcElement(resetButton);
		resetButton.addActionListener(event->{
			calcModel.clearAll();
			operandStack.clear();
		});
		cp.add(resetButton, "2,7");
		
		JButton pushButton = new JButton("push");
		setDefaultCalcElement(pushButton);
		pushButton.addActionListener(event->operandStack.push(calcModel.getValue()));
		cp.add(pushButton, "3,7");
		
		JButton popButton = new JButton("pop");
		setDefaultCalcElement(popButton);
		popButton.addActionListener(event->{
			if(!operandStack.isEmpty()) {
				calcModel.setValue(operandStack.pop());
			}else {
				displayError("Empty stack","Operand stack is empty.");
			}
		});
		cp.add(popButton, "4,7");
		
		inverseCheckBox = new JCheckBox("inv");
		setDefaultCalcElement(inverseCheckBox);
		cp.add(inverseCheckBox, "5,7");
	}
	
	/**
	 * Adds unary and inverseable unary operations - {@link InverseableUnaryOperationButton} to the calculator.<br>
	 * - 1/x, +/- <br>
	 * Inverseable - sin, cos, tan, ctg, log, ln.
	 * 
	 */
	private void addUnaryOperations() {
		ActionListener unaryOperationListener = event->{
			double value = ((InverseableUnaryOperationButton)event.getSource()).perform(inverseCheckBox.isSelected(), calcModel.getValue());
			if(!Double.isFinite(value)) displayError("Math Error", "Result is "+value+".");
			calcModel.setValue(value);
		};
		
		InverseableUnaryOperationButton sinOperationButton = new InverseableUnaryOperationButton("sin",
				value->Math.sin(value),
				value->Math.asin(value));
		sinOperationButton.addActionListener(unaryOperationListener);
		cp.add(sinOperationButton, "2,2");
		
		InverseableUnaryOperationButton cosOperationButton = new InverseableUnaryOperationButton("cos",
				value->Math.cos(value),
				value->Math.acos(value));
		cosOperationButton.addActionListener(unaryOperationListener);
		cp.add(cosOperationButton, "3,2");
		
		InverseableUnaryOperationButton tanOperationButton = new InverseableUnaryOperationButton("tan",
				value->Math.tan(value),
				value->Math.atan(value));
		tanOperationButton.addActionListener(unaryOperationListener);
		cp.add(tanOperationButton, "4,2");
		
		
		InverseableUnaryOperationButton ctgOperationButton = new InverseableUnaryOperationButton("ctg",
				value->1/Math.tan(value),
				value->Math.atan(1/value));
		ctgOperationButton.addActionListener(unaryOperationListener);
		cp.add(ctgOperationButton, "5,2");
	
		InverseableUnaryOperationButton logOperationButton = new InverseableUnaryOperationButton("log",
				value->Math.log10(value),
				value->Math.pow(10,value));
		logOperationButton.addActionListener(unaryOperationListener);
		cp.add(logOperationButton, "3,1");
		
		InverseableUnaryOperationButton lnOperationButton = new InverseableUnaryOperationButton("ln",
				value->Math.log(value),
				value->Math.pow(Math.E, value));
		lnOperationButton.addActionListener(unaryOperationListener);
		cp.add(lnOperationButton, "4,1");
		
		JButton reciprocalOperationButton = new JButton("1/x");
		setDefaultCalcElement(reciprocalOperationButton);
		reciprocalOperationButton.addActionListener(event->{
			double result = 1/calcModel.getValue();
			if(!Double.isFinite(result)) displayError("Math Error", "Result is "+result+".");
			calcModel.setValue(result);
		});
		cp.add(reciprocalOperationButton, "2,1");
		
		JButton signSwapButton = new JButton("+/-");
		setDefaultCalcElement(signSwapButton);
		signSwapButton.addActionListener(event->calcModel.swapSign());
		cp.add(signSwapButton, "5,4");
		
	}
	

	/**
	 * Adds binary operations - {@link BinaryOperationButton} 
	 * and equals (=) operation to the calculator.<br>
	 * x^n, /, *, - ,+
	 */
	private void addBinaryOperationButtons() {
		
		ActionListener binaryOperationListener = event->{
			BinaryOperationButton source = (BinaryOperationButton) event.getSource();
			if(calcModel.isActiveOperandSet()) {
				performBinaryOperation();
			}
			calcModel.setActiveOperand(calcModel.getValue());
			calcModel.setPendingBinaryOperation(source.getOperation());
			calcModel.clear();
		};
		
		BinaryOperationButton addButton = new BinaryOperationButton((x,y)->x+y, "+");
		addButton.addActionListener(binaryOperationListener);
		cp.add(addButton, "5,6");
		
		BinaryOperationButton subButton = new BinaryOperationButton((x,y)->x-y, "-");
		subButton.addActionListener(binaryOperationListener);
		cp.add(subButton, "4,6");
		
		BinaryOperationButton mulButton = new BinaryOperationButton((x,y)->x*y, "*");
		mulButton.addActionListener(binaryOperationListener);
		cp.add(mulButton, "3,6");
		
		BinaryOperationButton divButton = new BinaryOperationButton((x,y)->x/y, "/");
		divButton.addActionListener(binaryOperationListener);
		cp.add(divButton, "2,6");
		
		BinaryOperationButton powerButton = new BinaryOperationButton((x,y)->Math.pow(x,y), "x^n") {

			private static final long serialVersionUID = -6862753950651271382L;
			
			@Override
			public DoubleBinaryOperator getOperation() {
				if(inverseCheckBox.isSelected()) return (x,y)->Math.pow(x,1/y);
				return super.getOperation();
			}
		};
		powerButton.addActionListener(binaryOperationListener);
		cp.add(powerButton, "5,1");
		
		JButton equalsButton = new JButton("=");
		setDefaultCalcElement(equalsButton);
		equalsButton.addActionListener(event->{
			performBinaryOperation();
		});
		cp.add(equalsButton, "1,6");
	}
	
	/**
	 * Performs the pending binary operation on current operand
	 * and active operand in the {@link CalcModel}.
	 */
	private void performBinaryOperation() {
		try {
			double currentValue = calcModel.getValue();
			if(!calcModel.isActiveOperandSet()) return;
			double operand = calcModel.getActiveOperand();
			DoubleBinaryOperator operator = calcModel.getPendingBinaryOperation();
			
			
			double result = operator.applyAsDouble(operand, currentValue);
			if(!Double.isFinite(result)) displayError("Math Error", "Result is "+result+".");
			
			calcModel.setValue(result);
			calcModel.clearActiveOperand();
			calcModel.setPendingBinaryOperation(null);
		}catch(IllegalStateException ex) {
		}
	}

	/**
	 * Displays an error message with the specified title and content.
	 * @param title - title of the error message
	 * @param message - error message
	 */
	private void displayError(String title, String message) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.ERROR_MESSAGE);
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		calcDisplay.setText(model.toString());
	}
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()->{
			new Calculator().setVisible(true);
		});
	}
	
}
