package hr.fer.zemris.java.custom.scripting.parser;

import static hr.fer.zemris.java.custom.scripting.lexer.LexerState.*;

import static hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer.tagEnd;
import static hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer.tagStart;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.lexer.LexerException;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;

/**
 * A parser used to parser documents in specified format.
 * @author Dorian Ivankovic
 *
 */
public class SmartScriptParser {

	/**
	 * Lexer used for lexical analysis of the input text.
	 */
	private SmartScriptLexer lexer;
	
	/**
	 * The root element of the parser tree.
	 */
	private DocumentNode startNode;
	
	/**
	 * Stack is used to check if all non-empty tags are closed.
	 */
	private Stack<Node> stack;

	/**
	 * Constructor of the SmartScriptParser using input document,
	 * automatically initializes the parsing of the document.
	 * @param document - document to parse
	 * @throws NullPointerException if document is null
	 */
	public SmartScriptParser(String document) {
		Objects.requireNonNull(document);

		lexer = new SmartScriptLexer(document);
		startNode = new DocumentNode();
		stack = new Stack<>();
		stack.push(startNode);

		parse();
	}

	/**
	 * Returns the root element of the parser tree.
	 * @return root element of the parser tree
	 */
	public DocumentNode getDocumentNode() {
		return startNode;
	}

	
	/**
	 * The method that parses the input document using a set of specified rules.
	 * All new nodes all aded to the existing node that is currently on the top of the stack.
	 * @throws SmartScriptParserException if the document cannot be parsed
	 */
	private void parse() {
		
		try {
			while (true) {
				Element element = lexer.nextElement();
				if (element == null) break;

				String elementText = element.asText();
				
				if (elementText.equals(tagStart)) {
					lexer.setState(TAG);
					continue;
				}

				if (elementText.equals(tagEnd)) {
					lexer.setState(TEXT);
					continue;
				}

				if (lexer.getState() == TEXT) {
					textNodeConstruction(element);
				}else {
					switch(elementText.toUpperCase()) {
					
					case "FOR":
						forNodeConstruction();
						break;
					case "END":
						endNodeConstruction();
						break;
					case "=":
						echoNodeConstruction();
						break;
					default:
						throw new SmartScriptParserException("Tag must start with =, END or FOR, " + element.asText() + " is invalid tag start.");
					
					}
				}

			}

		} catch (LexerException ex) {
			throw new SmartScriptParserException(ex.getMessage(), ex.getCause());
		}
		
		if(stack.size()!=1) {
			throw new SmartScriptParserException("closing tags {$END$} missing , need "+(stack.size()-1)+" more.");
		}
		
	}


	/**
	 * Constructs a text node in the parser tree.
	 * @param element - element to set as text
	 */
	private void textNodeConstruction(Element element) {
		TextNode node = new TextNode(element.asText());
		stack.peek().addChildNode(node);
	}
	
	/**
	 * Constructs an end node, checks if there is the right ammount of end nodes ending opened
	 * non empty node.
	 * @throws SmartScriptParserException if the END tag is not appropriately closed or there is
	 * too many END tags
	 */
	private void endNodeConstruction() {
		Element next = lexer.nextElement();
		
		if (!next.asText().equals(tagEnd)) {
			throw new SmartScriptParserException("END tag must be closed by : " + tagEnd + ".");
		} else {
			lexer.setState(TEXT);
		}

		stack.peek().addChildNode(new EndNode(tagStart+"END"+tagEnd));
		stack.pop();
		if (stack.isEmpty()) {
			throw new SmartScriptParserException("Error in document - more END tags than opened non empty tags.");
		}
		
	}

	/**
	 * Constructs a new echo node, echo node is declared by "=" after the opened tag.
	 */
	private void echoNodeConstruction() {
		
		List<Element> elements = new ArrayList<>();
		Element next = lexer.nextElement();

		while (next != null && !next.asText().equals(tagEnd)) {
			elements.add(next);
			next = lexer.nextElement();
		}

		lexer.setState(TEXT);

		Element[] arrayElements = elements.toArray(new Element[] {});
//		new Element[elements.size()];
//		for (int i = 0; i < arrayElements.length; i++) {
//			arrayElements[i] = elements.get(i);
//		}

		EchoNode node = new EchoNode(arrayElements);
		stack.peek().addChildNode(node);
	
	}

	/**
	 * Constructs a new for node declared by FOR after the opened tag.
	 * @throws SmartScriptParserException if FOR constuct is illegal- too many or too few parameters, unappropriate parameters,...
	 */
	private void forNodeConstruction() {

		Element variable = lexer.nextElement();

		if (!(variable instanceof ElementVariable)) {
			throw new SmartScriptParserException("FOR tag must contain variable as the first element, "
					+ variable.asText() + "is not a variable name.");
		}

		List<Element> elements = new ArrayList<>();

		while (true) {
			Element next = lexer.nextElement();

			if (next.asText().equals(tagEnd)) {
				if (elements.size() < 2) {
					throw new SmartScriptParserException(
							"Too few for loop arguments, was : " + (elements.size() + 1) + ", must be 3 or 4.");
				} else {
					lexer.setState(TEXT);
					break;
				}
			}

			if (elements.size() == 3) {
				throw new SmartScriptParserException("Too many for loop arguments");
			}

			checkFORElement(next);

			elements.add(next);

		}

		Element startExpression = elements.get(0);
		Element endExpression = elements.get(1);
		Element stepExpression = elements.size() == 3 ? elements.get(2) : null;

		ForLoopNode node = new ForLoopNode((ElementVariable) variable, (ElementConstant)startExpression, (ElementConstant)endExpression,
				(ElementConstant)stepExpression);

		stack.peek().addChildNode(node);

		stack.push(node);

	}

	/**
	 * Checks if the element is a for loop element - it must be a number, a variable or a String.
	 * @param next - element to check
	 * @throws SmartScriptParserException if next is not a for loop argument
	 */
	private void checkFORElement(Element next) {
		if (!(next instanceof ElementVariable) && !(next instanceof ElementString) && !(next instanceof ElementConstantInteger)
				&& !(next instanceof ElementConstantDouble) && !(next instanceof ElementFunction)) {
			throw new SmartScriptParserException("For loop elements must be a variable, a number, or a string, "
					+ next.asText() + " isn't any of those.");
		}
	}

}
