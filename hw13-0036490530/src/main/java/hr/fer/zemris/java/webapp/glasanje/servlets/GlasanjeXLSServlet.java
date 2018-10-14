package hr.fer.zemris.java.webapp.glasanje.servlets;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.webapp.glasanje.MusicalBand;

/**
 * The servlet creates an XLS document with the result of the voting on the form
 * displayed by {@link GlasanjeServlet}. The file is automatically saved to the
 * user's default downloads directory.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/glasanje-xls")
public class GlasanjeXLSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String votingResults = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
		String bandsDefinition = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");

		List<MusicalBand> results = GlasanjeUtils.loadVotingData(Paths.get(bandsDefinition), Paths.get(votingResults));

		HSSFWorkbook hwb = new HSSFWorkbook();
		HSSFSheet sheet = hwb.createSheet(String.valueOf(1));

		HSSFRow row = sheet.createRow(0);
		row.createCell(0).setCellValue("Band");
		row.createCell(1).setCellValue("Total votes");

		int rowCount = 1;
		for (MusicalBand band : results) {
			row = sheet.createRow(rowCount++);
			row.createCell(0).setCellValue(band.name);
			row.createCell(1).setCellValue(band.voteCount);
		}

		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition", "attachment; filename=\"results.xls\"");
		hwb.write(resp.getOutputStream());
		resp.getOutputStream().close();
		hwb.close();

		req.getRequestDispatcher(req.getServletContext().getContextPath()).forward(req, resp);

	}
}
