package hr.fer.zemris.java.blog.dao.jpa;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import hr.fer.zemris.java.blog.dao.DAO;
import hr.fer.zemris.java.blog.dao.DAOException;
import hr.fer.zemris.java.blog.model.BlogComment;
import hr.fer.zemris.java.blog.model.BlogEntry;
import hr.fer.zemris.java.blog.model.BlogUser;

public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public BlogUser getBlogUser(Long id) throws DAOException {
		BlogUser user = JPAEMProvider.getEntityManager().find(BlogUser.class, id);
		return user;
	}
	
	@Override 
	public BlogUser getUserByNick(String nick) throws DAOException{
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser user = null;
		try {
			user =  em.createNamedQuery("BlogUser.byNick", BlogUser.class)
					  .setParameter("n", nick)
				      .getSingleResult();
	
		}catch(NoResultException ex) {
		}
		
		return user;
	}

	@Override
	public BlogUser getUserByEmail(String email) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		BlogUser user = null;
		
		try {
			user = em.createNamedQuery("BlogUser.byEmail", BlogUser.class)
					 .setParameter("e", email)
					 .getSingleResult();
	
		}catch(NoResultException ex){
		}
		
		return user;
	}
	
	@Override
	public List<BlogUser> getBlogUsers() throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<String> userNicks = em.createNamedQuery("BlogUser.allUsers", String.class).getResultList();
		List<BlogUser> users = userNicks.stream().map(nick->new BlogUser(nick)).collect(Collectors.toList());
		return users;
	}

	@Override
	public List<BlogEntry> getUserBlogs(Long userId) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		List<BlogEntry> blogs = em.createNamedQuery("BlogUser.blogsForUser", BlogEntry.class)
								  .setParameter("id", userId)
								  .getResultList();
		
		return blogs;
	}

	@Override
	public List<BlogComment> getBlogComments(Long blogId) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		
		List<BlogComment> comments = em.createNamedQuery("BlogEntry.commentsForBlog", BlogComment.class)
								  .setParameter("be", blogId)
								  .getResultList();
		
		return comments;
	}

	@Override
	public void addBlogUser(BlogUser user) throws DAOException {
		JPAEMProvider.getEntityManager().persist(user);
	}

	@Override
	public void addBlogEntry(BlogEntry entry) throws DAOException {
		JPAEMProvider.getEntityManager().persist(entry);		
	}

	@Override
	public void addBlogComment(BlogComment comment) throws DAOException {
		JPAEMProvider.getEntityManager().persist(comment);		
	}

}