package hr.fer.zemris.java.custom.scripting.lexer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static hr.fer.zemris.java.custom.scripting.lexer.LexerState.*;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.hw03.prob1.LexerException;

public class SmartScriptLexerTest {

	SmartScriptLexer lexer;
	
	@Test
	public void testTextExtraction() {
		String document = "Ovo je probni tekst.\r\n"+"Još malo \\\\\\\\ \\{\\{ teksta \\{$.";
		lexer = new SmartScriptLexer(document);
		assertEquals(lexer.nextElement(),new ElementString("Ovo je probni tekst.\r\n"
				+ "Još malo \\\\ {{ teksta {$."));
		assertEquals(lexer.nextElement(),null);
	}
	
	@Test(expected = LexerException.class)
	public void testIllegalTextExtraction() {
		String document = "Ovo je probni tekst \\a.";
		lexer = new SmartScriptLexer(document);
		lexer.nextElement();
	}
	
	
	@Test
	public void testVariableExtraction() {
		String document = "A7_bb counter tmp_34 b44 a___";
		lexer = new SmartScriptLexer(document);
		lexer.setState(TAG);
		Element[] elements = {
			new ElementVariable("A7_bb"),
			new ElementVariable("counter"),
			new ElementVariable("tmp_34"),
			new ElementVariable("b44"),
			new ElementVariable("a___"),
			null
		};
		checkElementStream(lexer,elements);
		    
	}
	
	
	@Test(expected = LexerException.class)
	public void testIllegalVariableExtraction() {
		String document = "__a";
		lexer = new SmartScriptLexer(document);
		lexer.setState(TAG);
		lexer.nextElement();
	}
	
	
	@Test
	public void testFunctionExtraction() {

		String document = "@ss22__ @c @m__2s";
		lexer = new SmartScriptLexer(document);
		lexer.setState(TAG);
		Element[] elements = {
			new ElementFunction("ss22__"),
			new ElementFunction("c"),
			new ElementFunction("m__2s"),
			null
		};
		checkElementStream(lexer,elements);
	}
	
	@Test(expected = LexerException.class)
	public void testIllegalFunctionExtraction() {
		String document = "@22";
		lexer = new SmartScriptLexer(document);
		lexer.setState(TAG);
		lexer.nextElement();
	}
	
	@Test
	public void testNumberExtraction() {
		String document = "1234 1.34 -2.4 -2 4.";
		lexer = new SmartScriptLexer(document);
		lexer.setState(TAG);
		Element[] elements = {
			new ElementConstantInteger(1234),
			new ElementConstantDouble(1.34),
			new ElementConstantDouble(-2.4),
			new ElementConstantInteger(-2),
			new ElementConstantDouble(4.),
			null
		};
		checkElementStream(lexer,elements);
	}
	
	
	@Test(expected = LexerException.class)
	public void testLargeNumberExtraction() {
		String document = "12345678910";
		lexer = new SmartScriptLexer(document);
		lexer.setState(TAG);
		lexer.nextElement();
	}
	
	@Test
	public void testStringExtraction() {
		String document = " \"Some \\\\ text \n\" \"Joe \\\"Long\\\" \\\\\\\\ Smith\"";
		lexer = new SmartScriptLexer(document);
		lexer.setState(TAG);
		Element[] elements = {
				new ElementString("Some \\ text \n"),
				new ElementString("Joe \"Long\" \\\\ Smith"),
				null
			};
		checkElementStream(lexer,elements);
		
	}
		
	private void checkElementStream(SmartScriptLexer lexer, Element[] correctElements) {
		int counter = 0;
		for(Element expected : correctElements) {
			Element actual = lexer.nextElement();
			String msg = "Checking element "+counter + ":";
			assertEquals(msg,actual, expected);
			counter++;
		}
	}
}
