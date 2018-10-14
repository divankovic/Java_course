package hr.fer.zemris.java.webapp.glasanje.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet handles the storing the vote of the user that voted on the page
 * displayed by {@link GlasanjeServlet}. The total votes count is updated in the
 * vote record file, which is created if it didn't already exist.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		synchronized (this) {
			String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");

			Path path = Paths.get(fileName);

			String id = req.getParameter("id");

			List<String> lines = null;
			if (Files.exists(path)) {
				lines = Files.readAllLines(path, StandardCharsets.UTF_8);

				int idx = -1;
				for (int i = 0, n = lines.size(); i < n; i++) {
					if (lines.get(i).split("\t")[0].equals(id)) {
						idx = i;
						break;
					}
				}

				if (idx != -1) {
					String line = lines.get(idx);
					lines.remove(idx);

					long voteCount = Long.parseLong(line.split("\t")[1]) + 1;

					lines.add(idx, id + "\t" + voteCount);
				} else {
					lines.add(id + "\t1");
				}

			} else {
				Files.createFile(path);
				lines = new ArrayList<>();
				lines.add(id + "\t1");
			}

			lines.sort((l1, l2) -> {
				int id1 = Integer.parseInt(l1.split("\t")[0]);
				int id2 = Integer.parseInt(l2.split("\t")[0]);
				return Integer.compare(id1, id2);
			});

			Files.write(path, getString(lines).getBytes(StandardCharsets.UTF_8));

			resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
		}
	}

	/**
	 * Converts a list of lines into a text where lines are separated by "\n".
	 * @param lines - list of lines to convert into text
	 * @return text made of list of lines
	 */
	private String getString(List<String> lines) {
		StringBuilder builder = new StringBuilder();
		for (String line : lines) {
			builder.append(line).append("\n");
		}

		String text = builder.toString();
		text = text.substring(0, text.length() - 1);

		return text;
	}
}
