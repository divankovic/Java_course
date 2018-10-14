package hr.fer.zemris.java.hw16.jvdraw.tools;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.hw16.jvdraw.colors.ColorChangeListener;
import hr.fer.zemris.java.hw16.jvdraw.colors.IColorProvider;
import hr.fer.zemris.java.hw16.jvdraw.geometric.ConvexPolygon;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Vector3;
import hr.fer.zemris.java.hw16.jvdraw.model.DrawingModel;

public class ConvexPolygonTool extends AbstractTool{

	public static final double MIN_SQUARE_DIST = 9;
	private JFrame frame;
	private List<Point> points;
	private Color bgColor;
	
	public ConvexPolygonTool(JFrame frame, DrawingModel drawingModel, IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
		super(drawingModel, fgColorProvider);
		
		points = new ArrayList<>();
		bgColorProvider.addColorChangeListener(new ColorChangeListener() {
			
			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				bgColor = newColor;
			}
		});
	}

	@Override
	public void mouseClicked(MouseEvent e){
		if(e.getButton()==MouseEvent.BUTTON1) {
			
			Point p = e.getPoint();
			
			if(points.size()>=4 && getSquareDistance(points.get(points.size()-2), p)<=MIN_SQUARE_DIST) {
				addGeometricalObject();
				reset();
				return;
			}
			
			if(points.size()>=4 && !ConvexPolygonTool.checkConvex(points)) {
				JOptionPane.showMessageDialog(frame, "Can't select this point because the polygon would no longer be convex.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			if(!points.isEmpty() && getSquareDistance(points.get(points.size()-2), p)<=MIN_SQUARE_DIST) {
				return;
			}
			
			points.add(e.getPoint());
		}else if(e.getButton()==MouseEvent.BUTTON3) {
			reset();
		}
	}
	

	public static double getSquareDistance(Point first, Point second) {
		return Math.pow(first.x-second.x,2) + Math.pow(first.y-second.y, 2);
	}
	
	public static boolean checkConvex(List<Point> points) {
		int sign = 0;
		
		for(int i=0,n=points.size();i<n;i++) {
			int s = ConvexPolygonTool.getVector(points,i,(i+1)%n).cross(ConvexPolygonTool.getVector(points,i, (i+2)%n)).getZ()>0 ? 1 : -1;
			if(s==0) continue;
			
			if(sign==0) {
				sign = s;
			}
			
			if(sign!=s) return false;
		}
		
		return true;
	}
	
	public static Vector3 getVector(List<Point> points, int i, int j) {
		Point first = points.get(i);
		Point second = points.get(j);
		
		return new Vector3(second.x - first.x, second.y - first.y, 0);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(points.size()==0) return;
		
		if(points.size()==1) {
			points.add(e.getPoint());
		}else {
			points.remove(points.size()-1);
			points.add(e.getPoint());
		}
	}

	@Override
	public void reset() {
		points.clear();
	}

	@Override
	public void paint(Graphics2D g2d) {
		g2d.setColor(bgColor);
		g2d.fillPolygon(ConvexPolygon.getXCoordinates(points), ConvexPolygon.getYCoordinates(points), points.size());
		
		g2d.setColor(fgColor);
		g2d.drawPolygon(ConvexPolygon.getXCoordinates(points), ConvexPolygon.getYCoordinates(points), points.size());
	}
	
	
	@Override
	public void addGeometricalObject() {
		points.remove(points.size()-1);
		ConvexPolygon polygon = new ConvexPolygon(new ArrayList<>(points),fgColor, bgColor);
		drawingModel.add(polygon);
	}

}
