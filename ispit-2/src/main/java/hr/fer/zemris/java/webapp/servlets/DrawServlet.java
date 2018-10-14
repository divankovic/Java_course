package hr.fer.zemris.java.webapp.servlets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw16.jvdraw.geometric.Circle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.ConvexPolygon;
import hr.fer.zemris.java.hw16.jvdraw.geometric.FilledCircle;
import hr.fer.zemris.java.hw16.jvdraw.geometric.GeometricalObject;
import hr.fer.zemris.java.hw16.jvdraw.geometric.Line;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw16.jvdraw.geometric.visitors.GeometricalObjectPainter;

@WebServlet("/crtaj")
public class DrawServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String data = req.getParameter("data");
		
		try {
			List<GeometricalObject> objects = loadJvdDocument(data);
			GeometricalObjectBBCalculator bbCalc = new GeometricalObjectBBCalculator();

			for (int i = 0, n = objects.size(); i < n; i++) {
				objects.get(i).accept(bbCalc);
			}

			Rectangle box = bbCalc.getBoundingBox();
			BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

			Graphics2D g = image.createGraphics();
			g.translate(-box.x, -box.y);

			g.setColor(Color.WHITE);
			g.fill(box);
			GeometricalObjectPainter painter = new GeometricalObjectPainter(g);

			for (int i = 0, n = objects.size(); i < n; i++) {
				objects.get(i).accept(painter);
			}

			g.dispose();

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			ImageIO.write(image, "png", bos);
			resp.setContentType("image/png");
			resp.setContentLength(bos.size());
			resp.getOutputStream().write(bos.toByteArray());
			resp.getOutputStream().close();
			
			bos.close();

		}catch(Exception ex) {
			req.getRequestDispatcher("pogreska.html").forward(req, resp);
		}
	}
	
	private List<GeometricalObject> loadJvdDocument(String data){
		List<String> lines = new ArrayList<>();
		String[] elems = data.split("\n");
		
		for(String element:elems) {
			lines.add(element);
		}
		
		List<GeometricalObject> objects = new ArrayList<>();

		int startX, startY, radius, red, green, blue;
		for (String line : lines) {
			if (line.isEmpty())
				continue;
			String[] elements = line.split(" ");
			String object = elements[0].toUpperCase();
			switch (object) {
			case "LINE":
				startX = Integer.parseInt(elements[1]);
				startY = Integer.parseInt(elements[2]);
				int endX = Integer.parseInt(elements[3]);
				int endY = Integer.parseInt(elements[4]);
				red = Integer.parseInt(elements[5]);
				green = Integer.parseInt(elements[6]);
				blue = Integer.parseInt(elements[7]);

				if (startX < 0 || startY < 0 || endX < 0 || endY < 0) {
					throw new IllegalArgumentException();
				}

				checkColor(red, green, blue);
				Line l = new Line(new Point(startX, startY), new Point(endX, endY), new Color(red, green, blue));
				if (!objects.contains(l)) {
					objects.add(l);
				}

				break;
			case "CIRCLE":

				startX = Integer.parseInt(elements[1]);
				startY = Integer.parseInt(elements[2]);
				radius = Integer.parseInt(elements[3]);
				red = Integer.parseInt(elements[4]);
				green = Integer.parseInt(elements[5]);
				blue = Integer.parseInt(elements[6]);

				if (startX < 0 || startY < 0 || radius < 0) {
					throw new IllegalArgumentException();
				}

				checkColor(red, green, blue);
				Circle circle = new Circle(new Point(startX, startY), radius, new Color(red, green, blue));
				if (!objects.contains(circle)) {
					objects.add(circle);
				}

				break;
			case "FCIRCLE":
				startX = Integer.parseInt(elements[1]);
				startY = Integer.parseInt(elements[2]);
				radius = Integer.parseInt(elements[3]);
				red = Integer.parseInt(elements[4]);
				green = Integer.parseInt(elements[5]);
				blue = Integer.parseInt(elements[6]);
				int rf = Integer.parseInt(elements[7]);
				int gf = Integer.parseInt(elements[8]);
				int bf = Integer.parseInt(elements[9]);

				if (startX < 0 || startY < 0 || radius < 0) {
					throw new IllegalArgumentException();
				}

				checkColor(red, green, blue);
				checkColor(rf, gf, bf);
				FilledCircle fcircle = new FilledCircle(new Point(startX, startY), radius, new Color(red, green, blue),
						new Color(rf, gf, bf));
				if (!objects.contains(fcircle)) {
					objects.add(fcircle);
				}

				break;
			case "FPOLY":
				int n = Integer.parseInt(elements[1]);

				List<Point> points = new ArrayList<>();
				
				for(int i = 0;i<n;i++) {
					points.add(new Point(Integer.parseInt(elements[2*i+2]), Integer.parseInt(elements[2*i+3])));
				}
				
				red = Integer.parseInt(elements[2*n+2]);
				green = Integer.parseInt(elements[2*n+3]);
				blue = Integer.parseInt(elements[2*n+4]);
				rf = Integer.parseInt(elements[2*n+5]);
				gf = Integer.parseInt(elements[2*n+6]);
				bf = Integer.parseInt(elements[2*n+7]);

				checkColor(red, green, blue);
				checkColor(rf, gf, bf);
				
				ConvexPolygon polygon = new ConvexPolygon(points, new Color(red, green, blue), new Color(rf, gf, bf));
				if (!objects.contains(polygon)) {
					objects.add(polygon);
				}

				break;
			default:
				throw new IllegalArgumentException();
			}
		}

		return objects;
	}
	
	private void checkColor(int red, int green, int blue) {
		if (red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
			throw new IllegalArgumentException();
		}
	}
}
