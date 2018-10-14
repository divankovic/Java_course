package hr.fer.zemris.java.hw03.prob2;

import java.nio.file.Files;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.collections.hw03.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * Program for demonstration of the <code>SmartScriptParser</code>, 
 * takes the path to the document to parse as the input command line argument.
 * @author Dorian Ivankovic
 *
 */
public class SmartScriptTester {

	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println("Program must have one input - path to document to parse.");
			return;
		}
		
		String docBody;
		try {
			docBody = new String(
					 Files.readAllBytes(Paths.get(args[0])),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			System.out.println("File not found");
			return;
		}
		
		SmartScriptParser parser = null;
		
		try {
			parser = new SmartScriptParser(docBody);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		} catch(Exception e) {
			System.out.println("If this line ever executes, you have failed this class!");
			System.exit(-1);
		}
		
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(document);
		System.out.println(originalDocumentBody); // should write something like original
		 // content of docBody

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		
		System.out.println(document.equals(document2));
		
	}
	
	/**
	 * Creates the original method body from the parsed tree.
	 * Slight differences may occur - like the number or whitespaces between words,...
	 * @param node - the root node of the parser tree
	 * @return text of the original document
	 */
	public static String createOriginalDocumentBody(Node node) {
		StringBuilder text = new StringBuilder();
		text.append(node.toString());
		
		ArrayIndexedCollection children = node.getChildren();
		
		if(children!=null && !children.isEmpty()) {
			for(Object child : children) {
				text.append(createOriginalDocumentBody((Node)child));
			}
			
			if(node instanceof ForLoopNode) {
				text.append("{$END$}");
			}
		}
		
		
		return text.toString();
		
		
		
	}

}
