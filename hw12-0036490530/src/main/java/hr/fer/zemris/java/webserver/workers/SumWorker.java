package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Accepts two parameters a, b (if there are no parameters and the values cannot be parsed,
 * then the parameters are set to default values, calculates their sum, and
 * displays a HTML page with a table, showing a,b and the result of the operation.
 * @author Dorian Ivankovic
 *
 */
public class SumWorker implements IWebWorker{
	
	/**
	 * First operand default value.
	 */
	public static final int DEF_A = 1;
	
	/**
	 * Second operand default value.
	 */
	public static final int DEF_B = 2;
	
	@Override
	public void processRequest(RequestContext context) throws Exception {
		
		int a = getValue(DEF_A, context, "a");
		int b = getValue(DEF_B, context, "b");
		
		context.setTemporaryParameter("a", String.valueOf(a));
		context.setTemporaryParameter("b", String.valueOf(b));
		
		String result = String.valueOf(a+b);
		context.setTemporaryParameter("sum", result);
		
		context.getDispatcher().dispatchRequest("private/calc.smscr");
	}
	
	/**
	 * The method gets the parameter under name <code>name</code> from
	 * the context's parameters.
	 * @param defaultValue - default value of the parameter
	 * @param context - {@link RequestContext} used for retrieving the parameter
	 * @param name - name of the parameter
	 * @return value of the parameter
	 */
	private int getValue(int defaultValue, RequestContext context, String name) {
		String value = context.getParameter(name);
		if(value==null) {
			return defaultValue;
		}else {
			try{
				return Integer.parseInt(value);
			}catch(NumberFormatException ex) {
				return defaultValue;
			}
		}
	}

}
