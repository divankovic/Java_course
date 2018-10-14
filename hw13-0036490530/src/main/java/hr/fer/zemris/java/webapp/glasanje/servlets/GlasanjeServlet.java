package hr.fer.zemris.java.webapp.glasanje.servlets;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.webapp.glasanje.MusicalBand;

/**
 * The servlet displays a page with the vote form where the user can vote for
 * his/her favorite band by clicking on the link of the band.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
		List<MusicalBand> bands = null;

		try {
			HashMap<Integer, MusicalBand> bandMap = GlasanjeUtils.loadBands(Paths.get(fileName));

			bands = bandMap.entrySet().stream().map(entry -> entry.getValue())
					.sorted((band1, band2) -> Integer.compare(band1.id, band2.id)).collect(Collectors.toList());

		} catch (ArrayIndexOutOfBoundsException | IOException | NumberFormatException ex) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Couldn't open bands definition file.");
			return;
		}

		req.setAttribute("bands", bands);

		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}

}
