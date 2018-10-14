package hr.fer.zemris.java.blog.dao;

import java.util.List;

import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;

/**
 * An interface towards the data persistence API
 * Contains methods that are used to retrive and mofify the data in the database.
 * 
 * @author Dorian Ivankovic
 *
 */
public interface DAO {

	/**
	 * Gets the {@link BlogEntry} with specified <code>id</code> from the database.
	 * @param id - id of the {@link BlogEntry}
	 * @return <code>BlogEntry</code> with the specified <code>id</code>
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	
	/**
	 * Gets the {@link BlogUser} with specified <code>id</code> from the database.
	 * @param id - id of the {@link BlogUser}
	 * @return <code>BlogUser</code> with the specified <code>id</code>
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public BlogUser getBlogUser(Long id) throws DAOException;
	
	
	/**
	 * Gets the {@link BlogUser} with specified <code>nick</code> from the database.
	 * @param nick - nick of the {@link BlogUser}
	 * @return <code>BlogUser</code> with the specified <code>nick</code>
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public BlogUser getUserByNick(String nick) throws DAOException;
	
	
	/**
	 * Gets the {@link BlogUser} with specified <code>email</code> from the database.
	 * @param email - email of the {@link BlogUser}
	 * @return <code>BlogUser</code> with the specified <code>email</code>
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public BlogUser getUserByEmail(String email) throws DAOException;
	
	/**
	 * Gets all {@link BlogUser}'s from the database, gets only their <code>nicks</code>.
	 * @return list of all {@link BlogUser}'s in the database
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public List<BlogUser> getBlogUsers() throws DAOException;
	
	
	/**
	 * Gets all {@link BlogEntry}es created by the {@link BlogUser} with the specified <code>userId</code>.
	 * @param userId - id of the {@link BlogUser} who created the {@link BlogEntry}es
	 * @return list of {@link BlogEntry}es
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public List<BlogEntry> getUserBlogs(Long userId) throws DAOException;
	
	
	/**
	 * Gets all {@link BlogComment}'s associated with the {@link BlogEntry} specified by <code>blogId</code>
	 * @param blogId - id of the {@link BlogEntry}
	 * @return list of {@link BlogComment}'s associated with {@link BlogEntry}
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public List<BlogComment> getBlogComments(Long blogId) throws DAOException;
	
	
	/**
	 * Adds a new {@link BlogUser} into the database.
	 * @param user - user to add into the database
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public void addBlogUser(BlogUser user) throws DAOException;
	
	
	/**
	 * Adds a new {@link BlogEntry} into the database.
	 * @param entry - entry to add into the database
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public void addBlogEntry(BlogEntry entry) throws DAOException;
	
	
	/**
	 * Adds a new {@link BlogComment} into the database.
	 * @param comment - comment to add into the database
	 * @throws DAOException - if an error occurs during interaction with the database
	 */
	public void addBlogComment(BlogComment comment) throws DAOException;
}