package hr.fer.zemris.java.webapp.glasanje;

import hr.fer.zemris.java.webapp.glasanje.servlets.GlasanjeServlet;

/**
 * Represent a simple musical band with id of the band, name, example song url
 * and total votes count. Used in {@link GlasanjeServlet} for band voting
 * application.
 * 
 * @author Dorian Ivankovic
 *
 */
public class MusicalBand {

	/**
	 * Id of the band.
	 */
	public int id;

	/**
	 * Name of the band.
	 */
	public String name;

	/**
	 * Url of the example song of the band.
	 */
	public String songUrl;

	/**
	 * Total vote count.
	 */
	public long voteCount;

	/**
	 * Default no argument constructor.
	 */
	public MusicalBand() {

	}

	/**
	 * Constructs a band using it's id, name and example song url.
	 * 
	 * @param id
	 *            - id of the band
	 * @param name
	 *            - name of the band
	 * @param songUrl
	 *            - example song url
	 */
	public MusicalBand(int id, String name, String songUrl) {
		this.id = id;
		this.name = name;
		this.songUrl = songUrl;
	}

	/**
	 * Returns the id of the band.
	 * 
	 * @return if of the band
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id of the band.
	 * 
	 * @param id
	 *            - id of the band
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Returns the name of the band.
	 * 
	 * @return name of the band
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the band.
	 * 
	 * @param name
	 *            - new name of the band
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the band's example song url.
	 * 
	 * @return example song url
	 */
	public String getSongUrl() {
		return songUrl;
	}

	/**
	 * Sets the example song url.
	 * 
	 * @param songUrl
	 *            - new url of the example song
	 */
	public void setSongUrl(String songUrl) {
		this.songUrl = songUrl;
	}

	/**
	 * Returns the total vote count for the band.
	 * 
	 * @return total vote count
	 */
	public long getVoteCount() {
		return voteCount;
	}

	/**
	 * Sets the total vote count for the band.
	 * 
	 * @param voteCount
	 *            - total votes for the band
	 */
	public void setVoteCount(long voteCount) {
		this.voteCount = voteCount;
	}

}