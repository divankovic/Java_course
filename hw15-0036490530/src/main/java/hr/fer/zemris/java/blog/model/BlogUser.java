package hr.fer.zemris.java.blog.model;

import java.util.HashSet;


import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@NamedQueries({
	@NamedQuery(name="BlogUser.blogsForUser", query = "select blog from BlogEntry as blog where blog.creator.id=:id"),
	@NamedQuery(name="BlogUser.allUsers", query="select user.nick from BlogUser as user"),
	@NamedQuery(name="BlogUser.byNick", query="select user from BlogUser as user where user.nick=:n"),
	@NamedQuery(name="BlogUser.byEmail", query="select user from BlogUser as user where user.email=:e")

})

/**
 * The class models a user in a simple web blog application.
 * @author Dorian Ivankovic
 *
 */
@Entity
@Table(name="blog_users")
@Cacheable(true)
public class BlogUser {
	
	/**
	 * Max length of the first name in the database.
	 */
	public static final int FIRST_NAME_MAX_LENGHT=30;
	

	/**
	 * Max length of the last name in the database.
	 */
	public static final int LAST_NAME_MAX_LENGHT=50;
	

	/**
	 * Max length of the nick in the database.
	 */
	public static final int NICK_MAX_LENGHT = 20;
	

	/**
	 * Max length of the email in the database.
	 */
	public static final int EMAIL_MAX_LENGTH = 50;
	

	/**
	 * Min length of the password in the database.
	 */
	public static final int MIN_PASSWORD_LENGTH = 10;
	
	/**
	 * {@link BlogUser}'s id.
	 */
	private Long id;
	
	/**
	 * First name.
	 */
	private String firstName;
	
	/**
	 * Last name.
	 */
	private String lastName;
	
	/**
	 * Nick name.
	 */
	private String nick;
	
	/**
	 * email of the user.
	 */
	private String email;
	
	/**
	 * password hash value.
	 */
	private String passwordHash;
	
	/**
	 * User's blog.
	 */
	private Set<BlogEntry> blogs = new HashSet<>();
	
	
	/**
	 * Default constructor
	 */
	public BlogUser() {
		
	}
	
	/**
	 * Creates a {@link BlogUser} with the specified nick.
	 * @param nick - nick of the blogUser.
	 */
	public BlogUser(String nick) {
		this.nick = nick;
	}
	
	
	/**
	 * Returns the id of the {@link BlogUser}.
	 * @return if of the {@link BlogUser}
	 */
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	/**
	 * Sets the id of the {@link BlogUser}
	 * @param id new id of the {@link BlogUser}
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	
	/**
	 * Returns the first name of the {@link BlogUser}.
	 * @return first name of the blog user
	 */
	@Column(length = FIRST_NAME_MAX_LENGHT, nullable=false)
	public String getFirstName() {
		return firstName;
	}
	
	
	/**
	 * Sets the first name of the {@link BlogUser}.
	 * @param firstName - first name of the blog user
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	/**
	 * Returns the last name of the {@link BlogUser}.
	 * @return last name of the user
	 */
	@Column(length = LAST_NAME_MAX_LENGHT, nullable=false)
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Sets the last name of the {@link BlogUser}.
	 * @param lastName - new lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * Returns the nick of the BlogUser
	 * @return user's nick
	 */
	@Column(nullable = false, length = NICK_MAX_LENGHT, unique = true)
	public String getNick() {
		return nick;
	}
	
	
	/**
	 * Sets the user's nick.
	 * @param nick - new nick.
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	
	/**
	 * Returns the users e-mail.
	 * @return user's email.
	 */
	@Column(length=EMAIL_MAX_LENGTH, nullable=false, unique = true)
	public String getEmail() {
		return email;
	}
	
	
	/**
	 * Sets the user's email.
	 * @param email - new user's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	/**
	 * Returns the hash of the user's password.
	 * @return hash of the user's password
	 */
	@Column(nullable=false)
	public String getPasswordHash() {
		return passwordHash;
	}
	
	
	/**
	 * Sets the passwordHash of the user
	 * @param passwordHash - new password hash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	
	/**
	 * Gets the user's blogs.
	 * @return returns the blogs associated with the user.
	 */
	@OneToMany(mappedBy="creator",fetch=FetchType.LAZY, cascade=CascadeType.PERSIST, orphanRemoval=true)
	public Set<BlogEntry> getBlogs() {
		return blogs;
	}

	
	/**
	 * Sets the user's blog
	 * @param blogs - blogs
	 */
	public void setBlogs(Set<BlogEntry> blogs) {
		this.blogs = blogs;
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
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	

	
	
	
	
}
