package hr.fer.zemris.java.webapp.glasanje.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.util.Rotation;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.webapp.glasanje.MusicalBand;

/**
 * The servlet displays the results of the voting on the form displayed by
 * {@link GlasanjeServlet} in a form of a
 * <a href="https://en.wikipedia.org/wiki/Pie_chart">Pie chart</a>, where also
 * the percentages of votes for each band are displayed.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGraphicsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String votingResults = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String bandsDefinition = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<MusicalBand> results = GlasanjeUtils.loadVotingData(Paths.get(bandsDefinition), Paths.get(votingResults));

		DefaultPieDataset dataset = new DefaultPieDataset();

		for (MusicalBand band : results) {
			if (band.voteCount != 0) {
				dataset.setValue(band.name, band.voteCount);
			}
		}

		JFreeChart chart = ChartFactory.createPieChart3D("", dataset, true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(90);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setSimpleLabels(true);

		PieSectionLabelGenerator gen = new StandardPieSectionLabelGenerator("{2}", new DecimalFormat("0"),
				new DecimalFormat("0%"));
		plot.setLabelGenerator(gen);

		BufferedImage image = chart.createBufferedImage(600, 400);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			ImageIO.write(image, "png", bos);
			resp.setContentType("image/png");
			resp.setContentLength(bos.size());
			resp.getOutputStream().write(bos.toByteArray());
			resp.getOutputStream().close();

		} catch (IOException e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Couldn't render chart.");
			return;
		}
	}
}
