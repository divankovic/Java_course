package hr.fer.zemris.java.blog.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedQueries({
	@NamedQuery(name="BlogEntry.commentsForBlog",query="select b from BlogComment as b where b.blogEntry=:be")
})

/**
 * The class represents a single entry(blog) owned by a  BlogUser.
 * @author Dorian Ivankovic
 *
 */
@Entity
@Table(name="blog_entries")
@Cacheable(true)
public class BlogEntry {

	/**
	 * Id of the entry.
	 */
	private Long id;
	
	/**
	 * Comments of the entry.
	 */
	private List<BlogComment> comments = new ArrayList<>();
	
	/**
	 * Date of creation;
	 */
	private Date createdAt;
	
	/**
	 * Date of lst modification.
	 */
	private Date lastModifiedAt;
	
	/**
	 * Title of the {@link BlogEntry};
	 */
	private String title;
	
	/**
	 * Content(text) of the blog Entry
	 */
	private String text;
	
	/**
	 * Creator of the {@link BlogEntry}
	 */
	private BlogUser creator;
	
	
	/**
	 * Default constructor.
	 */
	public BlogEntry() {
		
	}
	
	/**
	 * Constructs a new {@link BlogEntry} using it's title.
	 * @param title - title of the blog
	 */
	public BlogEntry(String title) {
		this.title = title;
	}
	
	
	/**
	 * Returns the id of the blog.
	 * @return id of the blog.
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id of the blog.
	 * @param id - id of the blog.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Returns the comments of the blog.
	 * @return comments of the blog.
	 */
	@OneToMany(mappedBy="blogEntry",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	@OrderBy("postedOn")
	public List<BlogComment> getComments() {
		return comments;
	}
	
	/**
	 * Sets the comments of the {@link BlogEntry}.
	 * @param comments - new blog comments
	 */
	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	/**
	 * Returns the date of the blog creation.
	 * @return date of blog creation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the blog's date of creation
	 * @param createdAt - date of creation
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Returns the date of last modification of the blog.
	 * @return last date of bloh mofification.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=true)
	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	
	/**
	 * Sets the date of the blog's last date of modification
	 * @param lastModifiedAt - last modified date
	 */
	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	
	/**
	 * Returns the title of the blog.
	 * @return title of the blog
	 */
	@Column(length=200,nullable=false)
	public String getTitle() {
		return title;
	}

	
	/**
	 * Sets the title of the {@link BlogEntry}
	 * @param title - blog title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	
	/**
	 * Returns the text of the blog.
	 * @return text of the blog
	 */
	@Column(length=4096,nullable=false)
	public String getText() {
		return text;
	}

	/**
	 * Sets the blog's text.
	 * @param text - text of thr blog
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	
	/**
	 * Returns the creator of the blog.
	 * @return creator of the blog.
	 */
	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogUser getCreator() {
		return creator;
	}

	
	/**
	 * Sets the creator of the blog.
	 * @param creator - blog - dreatore of the blog
	 */
	public void setCreator(BlogUser creator) {
		this.creator = creator;
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
		BlogEntry other = (BlogEntry) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}