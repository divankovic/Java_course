package hr.fer.zemris.java.webapp.gallery.servlets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet is used for displaying {@link Picture}'s.
 * The thumbnail directory is first created if it doesn't already exist, as
 * well as shrunk thumbnail picture - of size <code>THUMBNAIL_SIZE</code> X <code>THUMBNAIL_SIZE</code>.
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servlets/picture")
public class PictureServlet extends HttpServlet{

	/**
	 * Serial version uid.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Size of the shrunk picture used as a thumbnail. 
	 */
	public static final int THUMBNAIL_SIZE = 150;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String name = req.getParameter("name");
		String thumbnail = req.getParameter("thumb");
		
		String rootPath = req.getServletContext().getRealPath("/WEB-INF");
		BufferedImage image = null;
		
		if(thumbnail.toLowerCase().equals("ok")) {
			
			String thumbPath = rootPath+"/thumbnails";
			
			if(!Files.exists(Paths.get(thumbPath))) {
				Files.createDirectory(Paths.get(thumbPath));
			}
			
			String thumbImagePath = rootPath+"/thumbnails/t_"+name;
			
			if(!Files.exists(Paths.get(thumbImagePath))){
				BufferedImage original = ImageIO.read(Paths.get(rootPath+"/slike/"+name).toFile());
				image = resizeImage(original);
				ImageIO.write(image, "jpg", Paths.get(thumbImagePath).toFile());
			}else {
				image = ImageIO.read(Paths.get(thumbImagePath).toFile());
			}
			
		}else {
			image = ImageIO.read(Paths.get(rootPath+"/slike/"+name).toFile());
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		ImageIO.write(image, "png", bos);
		resp.setContentType("image/png");
		resp.setContentLength(bos.size());
		resp.getOutputStream().write(bos.toByteArray());
		resp.getOutputStream().close();
		
		bos.close();
	}

	/**
	 * The method resizes the input <code>original</code> image to thumbnail size as described in {@link PictureServlet}.
	 * @param original - picture to shrink
	 * @return the shrunk image
	 */
	private BufferedImage resizeImage(BufferedImage original) {
		Image tmp = original.getScaledInstance(THUMBNAIL_SIZE, THUMBNAIL_SIZE, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(THUMBNAIL_SIZE, THUMBNAIL_SIZE, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		
		return resized;
	}
	
	

}
