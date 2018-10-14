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
import hr.fer.zemris.java.votingapp.model.PollOption;
import hr.fer.zemris.java.votingapp.model.PollOption.PollOptionOrder;

/**
 * The servlet displays a page with the vote form where the user can vote for
 * his/her favorite option by clicking on the link of the option.
 * 
 * @author Dorian Ivankovic
 *
 */

@WebServlet("/servleti/poll-options")
public class PollOptionsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			long pollID = Long.parseLong(req.getParameter("pollID"));

			Poll poll = DAOProvider.getDao().getPoll(pollID);
			List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID, PollOptionOrder.ID);

			req.setAttribute("poll", poll);
			req.setAttribute("pollOptions", pollOptions);

			req.getRequestDispatcher("/WEB-INF/pages/pollOptionsIndex.jsp").forward(req, resp);

		} catch (NumberFormatException ex) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal id parameter");
			ex.printStackTrace();
		}catch(DAOException ex) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error ocurred while loading data from the database.");
			ex.printStackTrace();
		}

	}

}
