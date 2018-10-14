package hr.fer.zemris.java.hw16.jvdraw.geometric.editors;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JSpinner;

import hr.fer.zemris.java.hw16.jvdraw.JColorArea;
import hr.fer.zemris.java.hw16.jvdraw.geometric.ConvexPolygon;
import hr.fer.zemris.java.hw16.jvdraw.tools.ConvexPolygonTool;

public class ConvexPolygonEditor extends GeometricalObjectEditor{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ConvexPolygon polygon;
	
	private List<JSpinner> xSpinners;
	
	private List<JSpinner> ySpinners;
	
	private JColorArea oColor;
	private JColorArea fColor;
	
	
	
	public ConvexPolygonEditor(ConvexPolygon polygon) {
		super();
		this.polygon = polygon;
		
		xSpinners = new ArrayList<>();
		ySpinners = new ArrayList<>();
		
		setPreferredSize(new Dimension(150, 200));
		this.setLayout(new GridLayout(polygon.getPoints().size()+2, 2));

		initGUI();
	}

	
	private void initGUI() {
		for(int i =0,n=polygon.getPoints().size();i<n;i++) {
			this.add(new JLabel((i+1)+".x"));
			xSpinners.add(getSpinner(polygon.getPoints().get(i).x));
			this.add(xSpinners.get(i));
			
			this.add(new JLabel((i+1)+".y"));
			ySpinners.add(getSpinner(polygon.getPoints().get(i).y));
			this.add(ySpinners.get(i));

		}
		
		this.add(new JLabel("Outline color"));
		oColor = new JColorArea(polygon.getOutlineColor());
		this.add(oColor);
		
		this.add(new JLabel("Fill color"));
		fColor = new JColorArea(polygon.getFillColor());
		this.add(fColor);
	}


	@Override
	public void checkEditing() {
		if(!ConvexPolygonTool.checkConvex(getPoints())){
			throw new GeometricalEditingException("Polygon is not convex!");
		}
		
		List<Point> points = getPoints();
		
		for(int i=0,n=points.size();i<n;i++) {
			if(ConvexPolygonTool.getSquareDistance(points.get(i), points.get((i+1)%n))<=ConvexPolygonTool.MIN_SQUARE_DIST) {
				throw new GeometricalEditingException("Two neighbour vertices must not be closer than "+ConvexPolygonTool.MIN_SQUARE_DIST);
			}
		}
		
	}

	@Override
	public void acceptEditing() {
		polygon.setPoints(getPoints());
		
		polygon.setOutlineColor(oColor.getCurrentColor());
		polygon.setFillColor(fColor.getCurrentColor());
	}


	private List<Point> getPoints() {
		List<Point> points = new ArrayList<>();
		
		for(int i =0,n=polygon.getPoints().size();i<n;i++) {
			points.add(new Point((int)xSpinners.get(i).getValue(), (int)ySpinners.get(i).getValue()));
		}
		
		return points;
	}

}
