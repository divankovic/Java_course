package hr.fer.zemris.java.raytracer;

import java.util.List;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * The program demonstrates
 * <a href="https://en.wikipedia.org/wiki/Phong_reflection_model"> Phong's
 * model</a> and <a href = "https://en.wikipedia.org/wiki/Ray_casting">
 * Ray-casting algorithm</a> by rendering a specific scene containing multiple
 * {@link GraphicalObject}'s.
 * 
 * @author Dorian Ivankovic
 *
 */
public class RayCaster {

	/**
	 * This method is called once the program is run.
	 * 
	 * @param args
	 *            - command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), 
				new Point3D(10, 0, 0), 
				new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 
				20, 20);
	}

	/**
	 * Produces a new {@link IRayTracerProducer} that renders the scene using Ray
	 * casting algorithm and Phong's model.
	 * 
	 * @return new <code>IRayTracerProducer</code>
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {

			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D zAxis = view.sub(eye).normalize();
				Point3D xAxis = viewUp.vectorProduct(zAxis.negate()).normalize();
				Point3D yAxis = zAxis.negate().vectorProduct(xAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2)).add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x / (width - 1.0) * horizontal))
								.sub(yAxis.scalarMultiply(y / (height - 1.0) * vertical));
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}

				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}

		};
	}

	/**
	 * This method is used by a concrete {@link IRayTracerProducer} to find
	 * intersections with objects in the scene and determine their colour on the
	 * screen.
	 * 
	 * @param scene
	 *            - scene object containing object in the scene and
	 *            {@link LightSource}'s
	 * @param ray
	 *            - ray from the observer to the projection frame
	 * @param rgb
	 *            - red, green, and blue component of the color
	 */
	public static void tracer(Scene scene, Ray ray, short[] rgb) {
		for (int i = 0; i < rgb.length; i++)
			rgb[i] = 0;

		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null)
			return;

		determineColor(scene, closest, rgb, ray.direction.negate());
	}

	/**
	 * The method determines the color of the point of the screen using
	 * <a href="https://en.wikipedia.org/wiki/Phong_reflection_model"> Phong's
	 * model</a>
	 * 
	 * @param scene
	 *            - scene object containing object in the scene and
	 *            {@link LightSource}'s
	 * @param intersection
	 *            - intersection on the object
	 * @param rgb
	 *            - red, green and blue component of the color
	 * @param v
	 *            - vector from the observer to intersection
	 */
	public static void determineColor(Scene scene, RayIntersection intersection, short[] rgb, Point3D v) {
		List<LightSource> lights = scene.getLights();
		rgb[0] = rgb[1] = rgb[2] = 15; // ambient

		for (LightSource light : lights) {
			Ray ray = Ray.fromPoints(light.getPoint(), intersection.getPoint());
			RayIntersection intersection2 = findClosestIntersection(scene, ray);
			if ((intersection.getPoint().sub(light.getPoint()).norm() - intersection2.getDistance()) > 1e-3)
				continue;

			Point3D l = ray.direction.negate();
			Point3D n = intersection.getNormal();
			Point3D r = getReflected(l, n);
			
			double cosAlfa = r.scalarProduct(v);
			if (cosAlfa < 0) cosAlfa = 0;
			double cosTheta = l.scalarProduct(n);
			if (cosTheta < 0) cosTheta = 0;

			double reflectiveFactor = Math.pow(cosAlfa, intersection.getKrn());

			rgb[0] += (intersection.getKdr() * cosTheta + intersection.getKrr() * reflectiveFactor) * light.getR();
			rgb[1] += (intersection.getKdg() * cosTheta + intersection.getKrg() * reflectiveFactor) * light.getG();
			rgb[2] += (intersection.getKdb() * cosTheta + intersection.getKrb() * reflectiveFactor) * light.getB();

		}
	}

	/**
	 * Returns the vector - {@link Point3D} that is vector l reflected over vector
	 * n.
	 * 
	 * @param l
	 *            - vector to reflect
	 * @param n
	 *            - vector around which to reflect
	 * @return reflected vector
	 */
	public static Point3D getReflected(Point3D l, Point3D n) {
		Point3D k = n.scalarMultiply(n.scalarProduct(l));
		Point3D reflected = k.scalarMultiply(2).sub(l);
		return reflected;
	}

	/**
	 * Finds the closest intersection of the objects in the scene with ray.
	 * 
	 * @param scene
	 *            - scene object containing object in the scene and
	 *            {@link LightSource}'s
	 * @param ray
	 *            - ray from the obsever into the scene
	 * @return the closest intersection to the observer
	 */
	public static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		List<GraphicalObject> objects = scene.getObjects();

		double distance = -1;
		RayIntersection closest = null;

		for (GraphicalObject object : objects) {
			RayIntersection intersection = object.findClosestRayIntersection(ray);
			if (intersection != null) {
				if (distance == -1) {
					distance = intersection.getDistance();
					closest = intersection;
				} else if (intersection.getDistance() < distance) {
					distance = intersection.getDistance();
					closest = intersection;
				}
			}
		}

		return closest;
	}
}
