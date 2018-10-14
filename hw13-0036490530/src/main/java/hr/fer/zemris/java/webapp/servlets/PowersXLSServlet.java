package hr.fer.zemris.java.webapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This servlet accepts 3 parameters - a in range [-100, 100], b in range
 * [-100,100] and n in range [1,5] and creates an XLS document with n pages,
 * with powers of numbers(the power is the current number of the page)from a to
 * b on each page. The XLS document is automatically saved to user's default
 * download directory.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/powers")
public class PowersXLSServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer a = parseParameter(req.getParameter("a"), -100, 100);
		Integer b = parseParameter(req.getParameter("b"), -100, 100);
		Integer n = parseParameter(req.getParameter("n"), 1, 5);

		if (a == null || b == null || n == null) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid parameters, arguments a,b and n expected: \n   "
					+ " a must be from [-100,100], b must be from [-100,100] and n must be from [1,5].");
			return;
		}

		if (a > b) {
			int temp = a;
			a = b;
			b = temp;
		}
		HSSFWorkbook hwb = new HSSFWorkbook();

		for (int power = 1; power <= n; power++) {
			HSSFSheet sheet = hwb.createSheet(String.valueOf(power));

			HSSFRow row = sheet.createRow(0);
			row.createCell(0).setCellValue("number");
			row.createCell(1).setCellValue("power(" + power + ")");

			for (int i = a; i <= b; i++) {
				row = sheet.createRow(i - a + 1);
				row.createCell(0).setCellValue(i);
				row.createCell(1).setCellValue(Math.pow(i, power));
			}
		}
		resp.setContentType("application/xls");
		resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
		hwb.write(resp.getOutputStream());
		resp.getOutputStream().close();
		hwb.close();

		req.getRequestDispatcher(req.getServletContext().getContextPath()).forward(req, resp);
	}

	/**
	 * The method parses the given parameter into {@link Integer} and returns that
	 * number if parsing can be performed and the number is in specifed bounds
	 * [lower, upper] and returns null otherwise.
	 * 
	 * @param parameter
	 *            - parameter to parse
	 * @param lower
	 *            - lower bound
	 * @param upper
	 *            - upper bound
	 * @return {@link Integer} value if the parameter can be parsed and is in
	 *         specified bounds, null otherwise
	 */
	private Integer parseParameter(String parameter, int lower, int upper) {
		try {
			Integer value = Integer.valueOf(parameter);
			if (value < lower || value > upper)
				return null;
			return value;
		} catch (NumberFormatException ex) {
			return null;
		}
	}

}
