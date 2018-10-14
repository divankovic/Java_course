package hr.fer.zemris.java.votingapp.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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

import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.PollOption;
import hr.fer.zemris.java.votingapp.model.PollOption.PollOptionOrder;

/**
 * The servlet displays the results of the voting on the form displayed by
 * {@link PollOptionsServlet} in a form of a
 * <a href="https://en.wikipedia.org/wiki/Pie_chart">Pie chart</a>, where also
 * the percentages of votes for each {@link PollOption} are displayed.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servleti/poll-graphics")
public class PollGraphicsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			long pollID = Long.parseLong(req.getParameter("pollID"));
			List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID, PollOptionOrder.VOTES_DESC);
			DefaultPieDataset dataset = new DefaultPieDataset();

			for (PollOption option : results) {
				if (option.getVotesCount() != 0) {
					dataset.setValue(option.getOptionTitle(), option.getVotesCount());
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

		} catch (NumberFormatException ex) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal id parameter");
			ex.printStackTrace();
		}catch(DAOException ex) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error ocurred while loading data from the database.");
			ex.printStackTrace();
		}
	}
}
