package hr.fer.zemris.java.votingapp.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.Poll;

/**
 * The servlet loads and displays available polls from the database.
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			List<Poll> polls = DAOProvider.getDao().getAvailablePolls();

			req.setAttribute("polls", polls);

			req.getRequestDispatcher("/WEB-INF/pages/pollIndex.jsp").forward(req, resp);
		} catch (DAOException ex) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					"An error ocurred while loading data from the database.");
			ex.printStackTrace();
		}
	}

}
