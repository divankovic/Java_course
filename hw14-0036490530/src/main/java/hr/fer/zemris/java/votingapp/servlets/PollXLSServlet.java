package hr.fer.zemris.java.votingapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.PollOption;
import hr.fer.zemris.java.votingapp.model.PollOption.PollOptionOrder;

/**
 * The servlet creates an XLS document with the result of the voting on the form
 * displayed by {@link PollOptionsServlet}.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servleti/poll-xls")
public class PollXLSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			long pollID = Long.parseLong(req.getParameter("pollID"));
			List<PollOption> results = DAOProvider.getDao().getPollOptions(pollID, PollOptionOrder.VOTES_DESC);

			HSSFWorkbook hwb = new HSSFWorkbook();
			HSSFSheet sheet = hwb.createSheet(String.valueOf(1));

			HSSFRow row = sheet.createRow(0);
			row.createCell(0).setCellValue("Option");
			row.createCell(1).setCellValue("Total votes");

			int rowCount = 1;
			for (PollOption option : results) {
				row = sheet.createRow(rowCount++);
				row.createCell(0).setCellValue(option.getOptionTitle());
				row.createCell(1).setCellValue(option.getVotesCount());
			}

			resp.setContentType("application/xls");
			resp.setHeader("Content-Disposition", "attachment; filename=\"results.xls\"");
			hwb.write(resp.getOutputStream());
			resp.getOutputStream().close();
			hwb.close();

			req.getRequestDispatcher(req.getServletContext().getContextPath()).forward(req, resp);
		
		} catch (NumberFormatException ex) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal id parameter");
			ex.printStackTrace();
		} catch (DAOException ex) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error ocurred while loading data from the database.");
			ex.printStackTrace();
		}
	}
}
