package hr.fer.zemris.java.votingapp.model;

/**
 * The class represents different options that can be voted on in a
 * {@link Poll}.
 * 
 * @author Dorian Ivankovic
 *
 */
public class PollOption {

	/**
	 * Defines the order of the retrieved {@link PollOption}'s from the database.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	public static enum PollOptionOrder {
		ID("id"), VOTES_ASC("votesCount asc"), VOTES_DESC("votesCount desc");

		/**
		 * Text content of the constants.
		 */
		private final String text;

		/**
		 * Default text constructor.
		 * 
		 * @param text
		 *            - text of the constant
		 */
		private PollOptionOrder(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	/**
	 * id of the poll option.
	 */
	private long id;

	/**
	 * Title of the option.
	 */
	private String optionTitle;

	/**
	 * Link to the video of the option.
	 */
	private String optionLink;

	/**
	 * Id of the {@link Poll} to which this option belongs.
	 */
	private long pollId;

	/**
	 * Total number of votes for the option.
	 */
	private long votesCount;

	/**
	 * Default empty constructor.
	 */
	public PollOption() {

	}

	/**
	 * Construct a new {@link PollOption} using all relevant information.
	 * 
	 * @param optionTitle
	 *            - title of the option
	 * @param optionLink
	 *            - link to the video of the option
	 * @param pollId
	 *            - id of the poll to which this option belongs
	 * @param votesCount
	 *            - total number of votes for the option on the {@link Poll}.
	 */
	public PollOption(String optionTitle, String optionLink, long pollId, long votesCount) {
		this.optionTitle = optionTitle;
		this.optionLink = optionLink;
		this.pollId = pollId;
		this.votesCount = votesCount;
	}

	/**
	 * Returns the id of the option.
	 * 
	 * @return id of the option.
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id of the option.
	 * 
	 * @param id
	 *            - id of the option
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Returns the option title.
	 * 
	 * @return the title of the option.
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Sets the title of the option
	 * 
	 * @param optionTitle
	 *            - title of the option
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Returns the link to the video of the option.
	 * 
	 * @return link to video with of the option
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Sets the link to video of the option.
	 * 
	 * @param optionLink
	 *            - link of the option
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
	}

	/**
	 * Returns the id of the {@link Poll}.
	 * 
	 * @return poll id
	 */
	public long getPollId() {
		return pollId;
	}

	/**
	 * Sets the id of the {@link Poll}
	 * 
	 * @param pollId
	 *            - id of teh poll
	 */
	public void setPollId(long pollId) {
		this.pollId = pollId;
	}

	/**
	 * Returns the total number of votes for the option.
	 * 
	 * @return total number of votes for the option
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Sets the total votes for the option.
	 * 
	 * @param votesCount - total number of votes
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}
}
