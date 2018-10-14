package hr.fer.zemris.java.raytracer.model;

import static java.lang.Math.pow;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;

/**
 * Sphere graphical object given by {@link Point3D} center, radius and
 * colour(rgb) components used for rendering :
 * <a href="https://en.wikipedia.org/wiki/Phong_reflection_model"> Phong's
 * model</a>.
 * 
 * @author Dorian Ivankovic
 *
 */
public class Sphere extends GraphicalObject {

	/** Center of the sphere */
	private Point3D center;

	/** Radius of the sphere */
	private double radius;

	/** Diffuse red component */
	private double kdr;

	/** Diffuse green component */
	private double kdg;

	/** Diffuse blue component */
	private double kdb;

	/** Reflection red component */
	private double krr;

	/** Reflection green component */
	private double krg;

	/** Reflection blue component */
	private double krb;

	/** Reflective component coefficient */
	private double krn;

	/**
	 * Constructs a new Sphere using all relevant information.
	 * 
	 * @param center
	 *            - center of the sphere
	 * @param radius
	 *            - radius of the sphere
	 * @param kdr
	 *            - diffuse red component
	 * @param kdg
	 *            - diffuse green component
	 * @param kdb
	 *            - diffuse blue component
	 * @param krr
	 *            - reflection red component
	 * @param krg
	 *            - reflection green component
	 * @param krb
	 *            - reflection blue component
	 * @param krn
	 *            - reflection component coefficient
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Point3D start = ray.start;
		Point3D direction = ray.direction;

		double a = direction.x * direction.x + direction.y * direction.y + direction.z * direction.z;
		double b = 2 * direction.x * (start.x - center.x) + 2 * direction.y * (start.y - center.y)
				+ 2 * direction.z * (start.z - center.z);
		double c = pow(start.x - center.x, 2) + pow(start.y - center.y, 2) + pow(start.z - center.z, 2)
				- pow(radius, 2);
		double det = b*b - 4*a*c;

		if (det < 0)
			return null;

		det = Math.sqrt(det);

		double lambda1 = (-b - det) / (2 * a);
		double lambda2 = (-b + det) / (2 * a);

		double distance = Math.abs(lambda1) < Math.abs(lambda2) ? lambda1 : lambda2;
		Point3D intersection = start.add(direction.scalarMultiply(distance));
		return new SphereIntersection(intersection, distance, true);

	}

	/**
	 * An intersection of a sphere and a {@link Ray}.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	private class SphereIntersection extends RayIntersection {

		/**
		 * Construcs a new <code>SphereIntersection</code> using intersection point,
		 * distance to intersection and a boolean flag indicated if the intersection is
		 * outer or inner.
		 * 
		 * @param point
		 *            - intersection point
		 * @param distance
		 *            - distance from starting point of ray to intersection
		 * @param outer
		 *            - flag indicating if the intersection is outer or not
		 */
		public SphereIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}

		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
		}

		@Override
		public double getKdr() {
			return kdr;
		}

		@Override
		public double getKdg() {
			return kdg;
		}

		@Override
		public double getKdb() {
			return kdb;
		}

		@Override
		public double getKrr() {
			return krr;
		}

		@Override
		public double getKrg() {
			return krg;
		}

		@Override
		public double getKrb() {
			return krb;
		}

		@Override
		public double getKrn() {
			return krn;
		}

	}
}
