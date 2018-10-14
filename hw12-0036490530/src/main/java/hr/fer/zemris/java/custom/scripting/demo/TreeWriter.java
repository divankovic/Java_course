package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

/**
 * The program accepts a file name as a command line argument, opens the 
 * smart script file, parses it into a tree using {@link SmartScriptParser}
 * and then reproduces it into original form using {@link INodeVisitor} and
 * writes it out to the standard output.
 * @author Dorian Ivankovic
 *
 */
public class TreeWriter {
	
	/**
	 * Visitor that processes the nodes by writing their original content
	 * to the standard output.
	 * @author Dorian Ivankovic
	 *
	 */
	private static class WriterVisitor implements INodeVisitor{

		@Override
		public void visitTextNode(TextNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {
			System.out.print(node.toString());
			for(Node n:node.getChildren()) {
				n.accept(this);
			}
		}

		@Override
		public void visitEchoNode(EchoNode node) {
			System.out.print(node.toString());
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			for(Node n:node.getChildren()) {
				n.accept(this);
			}
		}
		
	}
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments - path to smart script file
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
			WriterVisitor visitor = new WriterVisitor();
			parser.getDocumentNode().accept(visitor);
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		}
	}
}
