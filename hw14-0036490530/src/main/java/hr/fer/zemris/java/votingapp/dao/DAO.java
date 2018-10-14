package hr.fer.zemris.java.votingapp.dao;

import java.sql.Connection;
import java.util.List;

import hr.fer.zemris.java.votingapp.model.Poll;
import hr.fer.zemris.java.votingapp.model.PollOption;
import hr.fer.zemris.java.votingapp.model.PollOption.PollOptionOrder;

/**
 * An interface towards the data persistence API
 * Contains methods that are used to retrive and mofify the data in the database.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface DAO {

	/**
	 * Creates the default polls table.
	 * @param conn - opened connection
	 * @throws DAOException - if an error occurs during computation.
	 */
	public void createPollsTable(Connection conn) throws DAOException;
	
	/**
	 * Creates the default poll Options table.
	 * @param conn - opened connection
	 * @throws DAOException - if an error occurs during computation.
	 */
	public void createPollOptionsTable(Connection conn) throws DAOException;
	
	/**
	 * Inserts the specified {@link Poll} into the table in the database.
	 * @param conn - opened connection
	 * @param poll - {@link Poll} to insert into the database table
	 * @return id of the inserted row
	 * @throws DAOException - if an error occurs during insertion
	 */
	public long insertPoll(Connection conn, Poll poll) throws DAOException;
	
	/**
	 * Inserts the specified {@link PollOption} into the table in the database.
	 * @param conn - opened connection
	 * @param option - {@link PollOption} to insert into the database table
	 * @throws DAOException - if an error occurs during insertion
	 */	
	public void inserPollOption(Connection conn, PollOption option) throws DAOException;
	
	
	/**
	 * Returns all available {@link Poll}'s in the database.
	 * @return available {@link Poll}'s.
	 * @throws DAOException - if the data cannot be loaded from the database.
	 */
	public List<Poll> getAvailablePolls() throws DAOException;
	
	/**
	 * Returns the {@link Poll} under the specified id key.
	 * @param id - key of the {@link Poll} in the database.
	 * @return {@link Poll} with the specified id
	 * @throws DAOException - if the data couldn't be loaded from the database.
	 */
	public Poll getPoll(long id) throws DAOException;
	
	/**
	 * Returns all {@link PollOption}'s for a single {@link Poll}.
	 * @param pollId - id of the {@link Poll}
	 * @param order - {@link PollOptionOrder} - by id, descdending or ascending by total votes
	 * @return list of available {@link PollOption}'s for the {@link Poll}.
	 * @throws DAOException if the options couldn't be loaded from the database.
	 */
	public List<PollOption> getPollOptions(long pollId, PollOptionOrder order) throws DAOException;
	
	/**
	 * Returns the {@link PollOption} specified by it's id.
	 * @param id - id of the poll option
	 * @return specified {@link PollOption}, if it exists
	 * @throws DAOException - if the option couldn+t be loaded from the database.
	 */
	public PollOption getPollOption(long id) throws DAOException;
	
	/**
	 * Updates the current number of votes for {@link PollOption}.
	 * @param id - id of the {@link PollOption}
	 * @param votesCount - new number of votes to set in the database
	 */
	public void updatePollOptionVotes(long id, long votesCount);
}