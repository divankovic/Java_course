package hr.fer.zemris.java.votingapp.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.PollOption;

/**
 * This servlet handles the storing the vote of the user that voted on the page
 * displayed by {@link PollOptionsServlet}. The total votes count is updated in
 * the vote record file, which is created if it didn't already exist.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servleti/poll-voting")
public class PollOptionVoteServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			long id = Long.parseLong(req.getParameter("id"));

			PollOption option = DAOProvider.getDao().getPollOption(id);
			DAOProvider.getDao().updatePollOptionVotes(id, option.getVotesCount() + 1);

			resp.sendRedirect(req.getContextPath() + "/servleti/poll-results?id=" + option.getPollId());

		} catch (NumberFormatException ex) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal id parameter");
			ex.printStackTrace();
		}catch(DAOException ex) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error ocurred while loading data from the database.");
			ex.printStackTrace();
		}
	}
}
