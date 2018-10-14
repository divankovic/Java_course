package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A visitor used to implement <a href="https://en.wikipedia.org/wiki/Visitor_pattern">visitor pattern</a>
 * using the <a href="https://en.wikipedia.org/wiki/Double_dispatch">doble dispatch</a> method.
 * @author Dorian Ivankovic
 *
 */
public interface INodeVisitor {
	
	/**
	 * {@link TextNode} processing
	 * @param node - node to process
	 */
	void visitTextNode(TextNode node);
	
	/**
	 * {@link ForLoopNode} processing
	 * @param node - node to process
	 */
	void visitForLoopNode(ForLoopNode node);
	
	/**
	 * {@link EchoNode} processing
	 * @param node - node to process
	 */
	void visitEchoNode(EchoNode node);
	
	/**
	 * {@link DocumentNode} processing
	 * @param node - node to process
	 */
	void visitDocumentNode(DocumentNode node);
}
