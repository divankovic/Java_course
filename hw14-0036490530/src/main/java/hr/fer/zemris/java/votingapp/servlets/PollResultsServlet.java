package hr.fer.zemris.java.votingapp.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.PollOption;
import hr.fer.zemris.java.votingapp.model.PollOption.PollOptionOrder;

/**
 * The servlet displays the results of the voting form displayed by
 * {@link PollOptionsServlet}. A simple table showing the results of the voting
 * is displayed as well as a Pie chart showing the percentages and links to the
 * videos of the winning options. The user can also download an XLS file with the
 * results of the voting.
 * 
 * @author Dorian Ivankovic
 *
 */
@WebServlet("/servleti/poll-results")
public class PollResultsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			long pollID = Long.parseLong(req.getParameter("id"));
			List<PollOption> pollOptions = DAOProvider.getDao().getPollOptions(pollID, PollOptionOrder.VOTES_DESC);

			req.setAttribute("poll", DAOProvider.getDao().getPoll(pollID));
			req.setAttribute("results", pollOptions);

			long maxVote = pollOptions.get(0).getVotesCount();
			List<PollOption> topOptions = pollOptions.stream().filter(option -> option.getVotesCount() == maxVote)
					.collect(Collectors.toList());

			req.setAttribute("topOptions", topOptions);

			req.getRequestDispatcher("/WEB-INF/pages/pollResults.jsp").forward(req, resp);

		} catch (NumberFormatException ex) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal id parameter");
			ex.printStackTrace();
		}catch(DAOException ex) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error ocurred while loading data from the database.");
			ex.printStackTrace();
		}
	}
}
