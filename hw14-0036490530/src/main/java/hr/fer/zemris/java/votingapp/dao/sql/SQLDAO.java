package hr.fer.zemris.java.votingapp.dao.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.votingapp.dao.DAO;
import hr.fer.zemris.java.votingapp.dao.DAOException;
import hr.fer.zemris.java.votingapp.model.Poll;
import hr.fer.zemris.java.votingapp.model.PollOption;
import hr.fer.zemris.java.votingapp.model.PollOption.PollOptionOrder;

/**
 * A subsystem {@link DAO} implementation using SQL.
 * @author Dorian Ivankovic
 */
public class SQLDAO implements DAO {

	@Override
	public List<Poll> getAvailablePolls() throws DAOException {
		List<Poll> availablePolls = new ArrayList<>();

		Connection conn = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = conn.prepareStatement("select id, title from Polls order by id");
				ResultSet rSet = statement.executeQuery();) {

			while (rSet != null && rSet.next()) {
				Poll poll = new Poll();
				poll.setId(rSet.getLong(1));
				poll.setTitle(rSet.getString(2));
				availablePolls.add(poll);
			}

		} catch (Exception ex) {
			throw new DAOException("An error has occurred while loading available polls.", ex);
		}
		return availablePolls;
	}

	@Override
	public Poll getPoll(long id) throws DAOException {

		Poll poll = null;

		Connection conn = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = conn.prepareStatement("select id, title, message from Polls where id=?");) {

			statement.setLong(1, id);

			try (ResultSet rSet = statement.executeQuery();) {
				if (rSet != null && rSet.next()) {
					poll = new Poll();
					poll.setId(rSet.getLong(1));
					poll.setTitle(rSet.getString(2));
					poll.setMessage(rSet.getString(3));
				}
			}

		} catch (Exception ex) {
			throw new DAOException("An error has occurred while loading the poll with specified id.", ex);
		}

		return poll;
	}

	@Override
	public List<PollOption> getPollOptions(long pollId, PollOptionOrder order) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();

		Connection conn = SQLConnectionProvider.getConnection();

		try (PreparedStatement statement = conn.prepareStatement(
				"select id, optionTitle, optionLink, votesCount from PollOptions where pollID=? order by "
						+ order.toString());) {

			statement.setLong(1, pollId);

			try (ResultSet rSet = statement.executeQuery();) {
				while (rSet != null && rSet.next()) {
					PollOption pollOption = new PollOption();
					pollOption.setId(rSet.getLong(1));
					pollOption.setOptionTitle(rSet.getString(2));
					pollOption.setOptionLink(rSet.getString(3));
					pollOption.setVotesCount(rSet.getLong(4));

					pollOptions.add(pollOption);
				}
			}

		} catch (Exception ex) {
			throw new DAOException("An error has occurred while loading available poll options.", ex);
		}
		return pollOptions;
	}

	@Override
	public PollOption getPollOption(long id) throws DAOException {
		PollOption pollOption = null;

		Connection conn = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = conn.prepareStatement(
				"select id, optionTitle, optionLink, pollID, votesCount from PollOptions where id=?");) {

			statement.setLong(1, id);

			try (ResultSet rSet = statement.executeQuery();) {
				if (rSet != null && rSet.next()) {
					pollOption = new PollOption();
					pollOption.setId(rSet.getLong(1));
					pollOption.setOptionTitle(rSet.getString(2));
					pollOption.setOptionLink(rSet.getString(3));
					pollOption.setPollId(rSet.getLong(4));
					pollOption.setVotesCount(rSet.getLong(5));
				}
			}

		} catch (Exception ex) {
			throw new DAOException("An error has occurred while loading the poll option with specified id.", ex);
		}

		return pollOption;
	}

	@Override
	public void updatePollOptionVotes(long id, long votesCount) {
		Connection conn = SQLConnectionProvider.getConnection();
		try (PreparedStatement statement = conn.prepareStatement("update PollOptions set votesCount=? where id=?");) {

			statement.setLong(1, votesCount);
			statement.setLong(2, id);

			statement.executeUpdate();
		} catch (Exception ex) {
			throw new DAOException("An error has occurred while updating the option's vote count", ex);
		}
	}

	@Override
	public void createPollsTable(Connection conn) throws DAOException {
		try (Statement statement = conn.createStatement()) {
			statement.executeUpdate(
					"CREATE TABLE Polls\r\n" 
							+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
							+ " title VARCHAR(150) NOT NULL,\r\n" 
							+ " message CLOB(2048) NOT NULL\r\n" 
							+ ")");
		} catch (SQLException e) {
			throw new DAOException(e);
		}
	}

	@Override
	public void createPollOptionsTable(Connection conn) throws DAOException {
		try (Statement statement = conn.createStatement()) {
			statement.executeUpdate(
					"CREATE TABLE PollOptions\r\n" 
							+ " (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\r\n"
							+ " optionTitle VARCHAR(100) NOT NULL,\r\n" 
							+ " optionLink VARCHAR(150) NOT NULL,\r\n"
							+ " pollID BIGINT,\r\n" 
							+ " votesCount BIGINT,\r\n"
							+ " FOREIGN KEY (pollID) REFERENCES Polls(id)\r\n" 
							+ ")");
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

	@Override
	public long insertPoll(Connection conn, Poll poll) throws DAOException {
		
		try(PreparedStatement insertStatement = conn.prepareStatement("insert into Polls (title, message) values (?,?)", Statement.RETURN_GENERATED_KEYS);){
			insertStatement.setString(1, poll.getTitle());
			insertStatement.setString(2, poll.getMessage());
			insertStatement.executeUpdate();
			
			try(ResultSet rset = insertStatement.getGeneratedKeys()){
				if(rset!=null && rset.next()) {
					return rset.getLong(1);
				}
			}
		} catch (SQLException e) {
			throw new DAOException(e);
		}
		
		return 0;
	}

	@Override
	public void inserPollOption(Connection conn, PollOption option) throws DAOException {
		try(PreparedStatement insertStatement = conn.prepareStatement("insert into PollOptions (optionTitle, optionLink, pollID, votesCount) values (?,?,?,?)");){
			insertStatement.setString(1, option.getOptionTitle());
			insertStatement.setString(2, option.getOptionLink());
			insertStatement.setLong(3, option.getPollId());
			insertStatement.setLong(4, option.getVotesCount());
			insertStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DAOException(e);
		}

	}

}