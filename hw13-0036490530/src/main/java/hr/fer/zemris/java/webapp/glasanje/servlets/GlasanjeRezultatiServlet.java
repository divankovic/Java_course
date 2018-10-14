package hr.fer.zemris.java.webapp.glasanje.servlets;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapp.glasanje.MusicalBand;

/**
 * The servlet displays the results of the voting form displayed by
 * {@link GlasanjeServlet}. A simple table showing the results of the voting is
 * displayed as well as a Pie chart showing the percentages and links to the
 * songs of the winning bands. The user can also download an XLS file with the
 * results of the voting.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String votingResults = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String bandsDefinition = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<MusicalBand> results = null;
		try {
			results = GlasanjeUtils.loadVotingData(Paths.get(bandsDefinition), Paths.get(votingResults));

		} catch (ArrayIndexOutOfBoundsException | IOException | NumberFormatException ex) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Couldn't open bands definition file.");
			return;
		}

		req.setAttribute("results", results);

		long maxVote = results.get(0).voteCount;
		List<MusicalBand> topBands = results.stream().filter(band -> band.voteCount == maxVote)
				.collect(Collectors.toList());
		req.setAttribute("topBands", topBands);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);

	}
}
