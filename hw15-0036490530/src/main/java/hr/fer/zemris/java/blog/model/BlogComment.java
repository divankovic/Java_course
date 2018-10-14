package hr.fer.zemris.java.blog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The class models a single {@link BlogEntry}'s comment.
 * @author Dorian Ivankovic
 *
 */
@Entity
@Table(name="blog_comments")
public class BlogComment {

	/**
	 * Comment id.
	 */
	private Long id;
	
	/**
	 * {@link BlogEntry} which is being commented.
	 */
	private BlogEntry blogEntry;
	
	/**
	 * Nickname of the user that posted the comment.
	 */
	private String userNick;
	
	/**
	 * Message of the comment.
	 */
	private String message;
	
	/**
	 * Date when the comment has been posted.
	 */
	private Date postedOn;
	
	
	/**
	 * Returns the id of the comment.
	 * @return comment id
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	
	/**
	 * Sets the id of the comment.
	 * @param id - comment id.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the blog which is being commented.
	 * @return blog that is being commented
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	/**
	 * Sets the blog entry.
	 * @param blogEntry - new blog entry
	 */
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	
	/**
	 * Returns the nickname of the user that posted the comment.
	 * @return the nickname of the user that posted the comment
	 */
	@Column(length=20,nullable=false)
	public String getUserNick() {
		return userNick;
	}

	/**
	 * Sets the user's nickname
	 * @param userNick - nickname of the domain
	 */
	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	/**
	 * Returns the message of the comment.
	 * @return message of the comment
	 */
	@Column(length=4096,nullable=false)
	public String getMessage() {
		return message;
	}

	
	/**
	 * Sets the message of the comment
	 * @param message - message of the comment
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	/**
	 * Returns the date when the comment has been posted.
	 * @return date when the comment gas been posted
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	/**
	 * Sets the date when the comment has been posted.
	 * @param postedOn - date when the comment was posted
	 */
	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}