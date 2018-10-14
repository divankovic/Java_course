package hr.fer.zemris.java.hw16.jvdraw.geometric.visitors;

import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.ConvexPolygon;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;

/**
 * Visitor of {@link GeometricalObject}'s
 * 
 * @author Dorian Ivankovic
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Performs the operation on the <code>line</code>.
	 * 
	 * @param line
	 *            - line to perform operation on
	 */
	void visit(Line line);

	/**
	 * Performs the operation on the <code>circle</code>.
	 * 
	 * @param circle
	 *            - circle to perform operation on
	 */
	void visit(Circle circle);

	/**
	 * Performs the operation on the <code>filledCircle</code>.
	 * 
	 * @param filledCircle
	 *            - filled circle to perform operation on
	 */
	void visit(FilledCircle filledCircle);
	
	void visit(ConvexPolygon polygon);
}
