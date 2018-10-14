package hr.fer.zemris.java.blog.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import hr.fer.zemris.java.blog.dao.DAOException;

/**
 * An {@link EntityManager} provider that returns its encapsulated {@link EntityManager}.
 * @author Dorian Ivankovic
 *
 */
public class JPAEMProvider {

	/**
	 * Map holding {@link EntityManager}'s for each thread.
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Returns the stored {@link EntityManager} for the current thread.
	 * @return stored {@link EntityManagerFactory}
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if(em==null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Closes the {@link EntityManager} for the current thread.
	 * @throws DAOException if an error occurs during interaction with the database
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if(em==null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch(Exception ex) {
			dex = new DAOException("Unable to commit transaction.", ex);
		}
		try {
			em.close();
		} catch(Exception ex) {
			if(dex!=null) {
				dex = new DAOException("Unable to close entity manager.", ex);
			}
		}
		locals.remove();
		if(dex!=null) throw dex;
	}
	
}