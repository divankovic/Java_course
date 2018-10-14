package hr.fer.zemris.java.raytracer;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * {@link RayCaster} achieved using multiple threads which speeds up the process of rendering the scene.
 * @author Dorian Ivankovic
 *
 */
public class RayCasterParallel {
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(new ParallelRayTracerProducer(),
				new Point3D(10,0,0),
				new Point3D(0,0,0),
				new Point3D(0,0,10),
				20, 20);
	}
	
	/**
	 * {@link IRayTracerProducer} that renders the scene using Ray
	 * casting algorithm and Phong's model by separating the rendering job into multiple smaller jobs.
	 * @author Dorian Ivankovic
	 *
	 */
	public static class ParallelRayTracerProducer implements IRayTracerProducer{
		
		/**
		 * Pool of threads.
		 */
		private ForkJoinPool pool;
		
		/**
		 * Constructs a new <code>ParallelRayTracerProducer</code>
		 */
		public ParallelRayTracerProducer() {
			pool = new ForkJoinPool();
		}
		
		@Override
		public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
				int height, long requestNo, IRayTracerResultObserver observer) {
			
			System.out.println("Započinjem izračune...");
			short[] red = new short[width*height];
			short[] green = new short[width*height];
			short[] blue = new short[width*height];
			
			Point3D zAxis = view.sub(eye).normalize();
			Point3D xAxis = viewUp.vectorProduct(zAxis.negate()).normalize();
			Point3D yAxis = zAxis.negate().vectorProduct(xAxis).normalize();
			
			Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal/2)).add(yAxis.scalarMultiply(vertical/2));
			
			Scene scene = RayTracerViewer.createPredefinedScene();
			
			pool.invoke(new CastingJob(0,height-1,eye, horizontal, vertical, width, height, red, green, blue, 
					xAxis, yAxis, zAxis, screenCorner, scene));
		
			System.out.println("Izračuni gotovi...");
			observer.acceptResult(red, green, blue, requestNo);
			System.out.println("Dojava gotova...");
		}
		
	}
	
	/**
	 * Represents a rendering scene job in rendering scene using Ray
	 * casting algorithm and Phong's model.
	 * @author Dorian Ivankovic
	 *
	 */
	public static class CastingJob extends RecursiveAction{

		/**
		 * 
		 */
		private static final long serialVersionUID = -8315258048938622685L;
		
		/**
		 * Size of scene columns for which job division is no longer done; the smallest job unit.
		 */
		public static final int threshold = 16;
		
		/**Start y coordinate*/
		private int yMin;
		
		/**End y coordinate*/
		private int yMax;
		
		/**Observers position*/
		private Point3D eye;
		
		/**Horizontal width of observed space*/
		private double horizontal;
		 
		/**Vertical height of observed space*/
		private double vertical;
		
		/**Number of pixel per screen column*/
		private int height;
		
		/**Number of pixels per screen row*/
		private int width;
		
		/**Red components of the frame. */
		private short[] red;
		
		/**Green components of the frame. */
		private short[] green;
		
		/**Blue components of the frame. */
		private short[] blue;
		
		/**x-axis vector in the view-frame*/
		private Point3D xAxis;
		
		/**y-axis vector in the view-frame*/
		private Point3D yAxis;

		/**z-axis vector in the view-frame*/
		private Point3D zAxis;

		/**Corner of the screen.*/
		private Point3D screenCorner;
		
		/**Scene - contains objects and light sources in the scene*/
		private Scene scene;
		
		
		/**
		 * 
		 * @param yMin - Start y coordinate
		 * @param yMax - End y coordinate
		 * @param eye - Observers position
		 * @param horizontal - Horizontal width of observed space
		 * @param vertical - Vertical height of observed space
		 * @param height - Number of pixel per screen column
		 * @param width - Number of pixels per screen row
		 * @param red - Red components of the frame. 
		 * @param green - Green components of the frame.
		 * @param blue - Blue components of the frame. 
		 * @param xAxis - x-axis vector in the view-frame
		 * @param yAxis - y-axis vector in the view-frame
		 * @param zAxis - z-axis vector in the view-frame
		 * @param screenCorner - Corner of the screen. 
		 * @param scene - contains objects and light sources in the scene
		 */
		public CastingJob(int yMin, int yMax, Point3D eye, double horizontal, double vertical, int width, int height, short[] red,
				short[] green, short[] blue, Point3D xAxis, Point3D yAxis, Point3D zAxis, Point3D screenCorner,
				Scene scene) {

			this.yMin = yMin;
			this.yMax = yMax;
			this.eye = eye;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.height = height;
			this.width = width;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.zAxis = zAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			
		}

		@Override
		protected void compute() {
			if(yMax-yMin+1 <=threshold) {
				computeDirect();
				return;
			}
			invokeAll(
					new CastingJob(yMin, yMin+(yMax-yMin)/2, eye, horizontal, vertical, width, height, red, green, blue, xAxis, yAxis, zAxis, screenCorner, scene),
					new CastingJob(yMin+(yMax-yMin)/2+1,yMax, eye, horizontal, vertical, width, height, red, green, blue, xAxis, yAxis, zAxis, screenCorner, scene)
			);
		}
		
		/**
		 * Direction calculation of frame red, green and blue pixel values for yMax - yMin range of <code>threshold</code>.
		 */
		protected void computeDirect() {
			short[] rgb = new short[3];
			int offset = yMin*width;
			for(int y = yMin;y<=yMax;y++) {
				for(int x = 0;x<width;x++) {
					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x/(width-1.0)*horizontal))
													  .sub(yAxis.scalarMultiply(y/(height-1.0)*vertical));
					Ray ray = Ray.fromPoints(eye, screenPoint);

					RayCaster.tracer(scene, ray, rgb);
					
					red[offset] = rgb[0] > 255?255:rgb[0];
					green[offset] = rgb[1] > 255?255:rgb[1];
					blue[offset] = rgb[2] > 255?255:rgb[2];
					
					
					offset++;
				}
			}
		}
		
	}
}
