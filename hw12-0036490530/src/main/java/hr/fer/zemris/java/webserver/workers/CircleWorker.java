package hr.fer.zemris.java.webserver.workers;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Produces a png image with dimensions 200x200 and with a single filled
 * circle.
 * @author Dorian Ivankovic
 *
 */
public class CircleWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {

		BufferedImage bim = new BufferedImage(200, 200, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g2d = bim.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillOval(0, 0, 200, 200);
		g2d.dispose();

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(bim, "png", bos);
			context.setMimeType("image/png");
			context.setContentLength(Long.parseLong(String.valueOf(bos.size())));
			context.write(bos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
