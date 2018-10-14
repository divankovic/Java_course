package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;

import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Multithread producer of <a href="https://www.google.hr/search?q=newton+fractals&source=lnms&tbm=isch&sa=X&ved=0ahUKEwiJvOfQgfnaAhVBa1AKHWm_Ci8Q_AUICigB&biw=662&bih=613#imgrc=xJMwF7DJSakM5M:">
 * Newton's fractals</a>.
 * 
 * @author Dorian Ivankovic
 *
 */
public class NewtonFractalProducer implements IFractalProducer {

	/**
	 * Maximum number of iterations used in Newton-Raphon's {@link #Newton.calculate()}
	 */
	public static final int MAX_ITER = 16*16*16;
	
	/**
	 * Convergence threshold used in Newton-Raphon's {@link #Newton.calculate()}
	 */
	public static final double CONV_THRESHOLD = 1E-3;
	
	/**
	 * Threshold for determining minimum root distance used in Newton-Raphon's {@link #Newton.calculate()}
	 */
	public static final double ROOT_THRESHOLD = 1E-3;
	
	
	/**
	 * Polynom used in Newton-Raphon's {@link #Newton.calculate()}.
	 */
	private ComplexRootedPolynomial polynom;
	
	/**
	 * Thread pool used for multithreading.
	 */
	private ExecutorService pool;
	
	/**
	 * Constructs a new <code>NewtonFractalProducer</code> using it's polynom.
	 * @param polynom - polynom of the Newton fractal
	 */
	public NewtonFractalProducer(ComplexRootedPolynomial polynom) {
		this.polynom = polynom;
		pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(),
											new ThreadFactory() {
												@Override
												public Thread newThread(Runnable runnable) {
													Thread thread = new Thread(runnable);
													thread.setDaemon(true);
													return thread;
												}
											});
	}
	
	@Override
	public void produce(double reMin, double reMax, double imMin, double imMax, 
			int width, int height, long requestNo, IFractalResultObserver observer) {

		short[] data = new short[width*height];
		int jobCount = 8*Runtime.getRuntime().availableProcessors();
		int yPerLane = height/jobCount;
		
		List<Future<Void>> results = new ArrayList<>();
		
		for(int i = 0; i<jobCount; i++) {
			int yMin = i*yPerLane;
			int yMax = i==jobCount-1?height-1:(i+1)*yPerLane-1;
			
			NewtonCalculator job = new NewtonCalculator(reMin, reMax, imMin, imMax, width, height, yMin, yMax, data, polynom);
			results.add(pool.submit(job));
		}
		
		for(Future<Void> result : results) {
			try {
				result.get();
			}catch(InterruptedException | ExecutionException ignorable) {
			}
		}
		
		observer.acceptResult(data, (short)(polynom.order()+1), requestNo);
		
	}
	
	/**
	 * Represents a job calculating values using Newton-Raphson's iteration for a part of the picture.
	 * @author Dorian Ivankovic
	 *
	 */
	public static class NewtonCalculator implements Callable<Void>{

		/**
		 * Real component minimum.
		 */
		private double reMin;
		
		/**
		 * Real component maximum.
		 */
		private double reMax;
		
		/**
		 * Imaginary component minimum.
		 */
		private double imMin;
		
		/**
		 * Imaginary component maximum.
		 */
		private double imMax;
		
		/**
		 * Frame width.
		 */
		private int width;
		
		/**
		 * Frame height.
		 */
		private int height;
		
		/**
		 * Starting y coordinate.
		 */
		private int yMin;
		
		/**
		 * End y coordinate.
		 */
		private int yMax;
		
		/**
		 * Data used to draw fractals on the screen.
		 */
		private short[] data;
		
		/**
		 * Used in Newton-Raphson's iteration.
		 */
		private ComplexRootedPolynomial polynom;
		
		
		/**
		 * Constructs a new <code>NewtonCalculator</code> using all relevant arguments.
		 * @param reMin - real component minimum
		 * @param reMax - real component maximum
		 * @param imMin - imaginary component minimum
		 * @param imMax - imaginary component maximum
		 * @param width - width of the frame
		 * @param height - height of the frame
		 * @param yMin - starting y coordinate
		 * @param yMax - end y coordinate
		 * @param data - data used to draw fractals on the screen
		 * @param polynom - Used in Newton-Raphson's iteration
		 */
		public NewtonCalculator(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, short[] data, ComplexRootedPolynomial polynom) {
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.data = data;
			this.polynom = polynom;
		}
		
		@Override
		public Void call() {
			Newton.calculate(reMin, reMax, imMin, imMax, width, height, yMin, yMax, polynom, data);
			return null;
		}
		
	}

}
