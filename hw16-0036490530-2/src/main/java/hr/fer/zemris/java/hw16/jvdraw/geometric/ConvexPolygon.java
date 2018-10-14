package hr.fer.zemris.java.hw16.jvdraw.geometric;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.ConvexPolygonEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectVisitor;

public class ConvexPolygon extends GeometricalObject{
	private List<Point> points;
	private Color outlineColor;
	private Color fillColor;
	
	
	
	public ConvexPolygon(List<Point> points, Color outlineColor, Color fillColor) {
		super();
		this.points = points;
		this.outlineColor = outlineColor;
		this.fillColor = fillColor;
	}


	public List<Point> getPoints() {
		return points;
	}


	public void setPoints(List<Point> points) {
		this.points = points;
	}

	
	public Color getOutlineColor() {
		return outlineColor;
	}


	public void setOutlineColor(Color outlineColor) {
		this.outlineColor = outlineColor;
	}


	public Color getFillColor() {
		return fillColor;
	}


	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public static int[] getXCoordinates(List<Point> points) {
		int[] xPoints = new int[points.size()];
		for(int i=0,n=points.size();i<n;i++) {
			xPoints[i] = points.get(i).x;
		}
		return xPoints;
	}

	public static int[] getYCoordinates(List<Point> points) {
		int[] yPoints = new int[points.size()]; 
		for(int i=0,n=points.size();i<n;i++) {
			yPoints[i] = points.get(i).y;
		}
		return yPoints;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((points == null) ? 0 : points.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvexPolygon other = (ConvexPolygon) obj;
		if (points == null) {
			if (other.points != null)
				return false;
		} else if (!points.equals(other.points))
			return false;
		return true;
	}


	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}


	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new ConvexPolygonEditor(this);
	}

	@Override
	public String toString() {
		return toJvd();
	}

	@Override
	public String toJvd() {
		StringBuilder jvdBuilder = new StringBuilder();
		
		jvdBuilder.append("FPOLY ").append(points.size()).append(" ");
		
		for(Point point:points) {
			jvdBuilder.append(point.x).append(" ").append(point.y).append(" ");
		}
		
		jvdBuilder.append(String.format("%d %d %d ", outlineColor.getRed(), outlineColor.getGreen(), outlineColor.getBlue()));
		jvdBuilder.append(String.format("%d %d %d ", fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue()));
		
		return jvdBuilder.toString();
	}
	
	
}
