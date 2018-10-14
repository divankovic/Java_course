package hr.fer.zemris.java.custom.scripting.exec;


/**
 * Represents a wrapper of any objects, null included.<br>
 * Provides numeric operations that can be executed only
 * if the value stored and the operand are null, instances of
 * <code>Integer</code>, <code>Double</code> or <code>String</code> where strings must be 
 * able to be parsed into a numeric value.<br>
 * If one of the operands is of type <code>Double</code> than the 
 * result value is stored as <code>Double</code> and if they are
 * both of type <code>Integer</code> than the result is stored as <code>Integer</code>.
 * @author Dorian Ivankovic
 *
 */
public class ValueWrapper {
	
	/**
	 * Provided numeric operations that store the result into this <code>value</code>.
	 * @author Dorian Ivankovic
	 *
	 */
	private static enum NumericOperation{
		ADD, SUB, MUL, DIV;
	}
	
	
	/**
	 * Current stored value.
	 */
	private Object value;

	
	/**
	 * Constructs a new <code>ValueWrapper</code> using it's inital value.
	 * @param value initial value of the <code>ValueWrapper</code>
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	
	/**
	 * Returns the value stored in this <code>ValueWrapper</code>.
	 * @return current stored value
	 */
	public Object getValue() {
		return value;
	}

	
	/**
	 * Sets the value of this <code>ValueWrapper</code>
	 * @param value - new value of the wrapper
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	
	/**
	 * Adds the current value with <code>incValue</code> only if they can
	 * be appropriately parsed as described in {@link ValueWrapper},
	 * and sets the current value to the new calculated value.
	 * @param incValue - value to add to current value
	 * @throws IllegalArgumentException - if arguments cannot be appropriately parsed
	 * 	 
	 */
	public void add(Object incValue) {
		convertAndPerform(incValue, NumericOperation.ADD);
	}
	

	/**
	 * Subtracts <code>decValue</code> from the current value only if they can
	 * be appropriately parsed as described in {@link ValueWrapper},
	 * and sets the current value to the new calculated value.
	 *
	 * @param decValue - value to subtract from current value
	 * @throws IllegalArgumentException - if arguments cannot be appropriately parsed
	 */
	public void subtract(Object decValue) {
		convertAndPerform(decValue, NumericOperation.SUB);
	}
	
	/**
	 * Multiplies value with <code>mulValue</code> if they can be appropriately parsed
	 * as described in {@link ValueWrapper}, and sets the current value to the new calculated value.
	 * 
	 * @param mulValue - value to multiply current value with
	 * @throws IllegalArgumentException - if arguments cannot be appropriately parsed
	 */
	public void multiply(Object mulValue) {
		convertAndPerform(mulValue, NumericOperation.MUL);
	}
	
	/**
	 * Divides the current value with <code>divValue</code> only if they can
	 * be appropriately parsed as described in {@link ValueWrapper},
	 * and sets the current value to the new calculated value.
	 * 
	 * @param divValue - value to add to current value
	 * @throws IllegalArgumentException - if arguments cannot be appropriately parsed
	 */
	public void divide(Object divValue) {
		convertAndPerform(divValue, NumericOperation.DIV);
	}
	
	/**
	 * Compares the current value with withValue if they can be appropriately parsed
	 * as described in {@link ValueWrapper}.
	 * @param withValue - value to compare with
	 * @return &gt;0 if current value is greater than <code>withValue</code>,<br>
	 * 	       &lt;0 if current value is less than <code>withValue</code> and<br>
	 * 			0 if they are equal
	 * @throws IllegalArgumentException - if arguments cannot be appropriately parsed
	 */
	public int numCompare(Object withValue) {
		Double v1 = Double.parseDouble(String.valueOf(convertToNumber(this.value)));
		Double v2 = Double.parseDouble(String.valueOf(convertToNumber(withValue)));
		return v1.compareTo(v2);
	}
	
	
	/**
	 * Converts the <code>operand</code> and current value to Numbers using method {@link #convertToNumber(Object)}
	 * and performs the operation given by <code>operation</code>.
	 *
	 * @param operand - second operand for the numeric operation
	 * @param operation - operation that needs to be performed
	 * 
	 * @throws IllegalArgumentException if the operand cannot be parsed
	 */
	private void convertAndPerform(Object operand, NumericOperation operation) {
		Number value1 = convertToNumber(this.value);
		Number value2 = convertToNumber(operand);
		
		Double v1 = Double.parseDouble(String.valueOf(value1));
		Double v2 = Double.parseDouble(String.valueOf(value2));
		performOperation(v1, v2, operation);
		
		if(value1 instanceof Integer && value2 instanceof Integer)
			value = Integer.valueOf(((Double)value).intValue());

	}
	
	
	/**
	 * The method tries to convert the given value into a <code>Number</code> object.
	 * If value is null, then integer with value 0 is returned.
	 * If the value is of type <code>Integer</code> or <code>Double</code> the object
	 * already is of type <code>Number</code>.
	 * If the value is of type <code>String</code> the method tries to parse the string
	 * into a double or to an integer, and throws the <code>Exception</code> if it doesn't
	 * succeed.
	 * If the value is of any other type, an <code>Exception</code> is thrown.
	 * @param value - value to convert to number
	 * @return <code>Number</code> representation of the value.
	 * @throws IllegalArgumentException if value cannot be converted to a <code>Number</code>
	 */
	private Number convertToNumber(Object value) {
		if(value == null) return Integer.valueOf(0);
		if(value instanceof String) {
			try {
				String content = (String) value;
				if(content.toUpperCase().contains("E") || content.contains(".")) {
					return Double.parseDouble(content);
				}else {
					return Integer.parseInt(content);
				}
			}catch(NumberFormatException ex) {
				throw new IllegalArgumentException(value + " cannot be cast to number.");
			}
		}else if(value instanceof Double || value instanceof Integer) {
			return (Number)value;
		}else {
			throw new IllegalArgumentException("Value argument must be null, or instance of Integer, Double or String!");
		}
	}
	
	/**
	 * Performs the operation given by <code>operation</code> on <code>value1</code> and <code>value2</code>
	 * and sets the current value to the result.
	 * @param value1 - first operand
	 * @param value2 - secon operand
	 * @param operation - operation to perform
	 * @throws IllegalArgumentException if the operation is division and the second operand is 0
	 */
	private void performOperation(Double value1, Double value2, NumericOperation operation) {
		switch(operation) {
			case ADD:
				value = value1 + value2;
				break;
			case SUB:
				value = value1 - value2;
				break;
			case DIV:
				if(value2==0) throw new IllegalArgumentException("Cannot divide by 0.");
				value = value1/value2;
				break;
			case MUL:
				value = value1*value2;
		}
	}
}
