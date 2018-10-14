package hr.fer.zemris.java.blog.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * An {@link EntityManagerFactory} provider that returns its encapsulated {@link EntityManagerFactory}.
 * @author Dorian Ivankovic
 *
 */
public class JPAEMFProvider {

	/**
	 * Private instance of the actual {@link EntityManagerFactory}.
	 */
	private static EntityManagerFactory emf;
	
	/**
	 * Returns the stored {@link EntityManagerFactory}.
	 * @return stored {@link EntityManagerFactory}
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
	
	/**
	 * Sets the {@link EntityManagerFactory}.
	 * @param emf - new {@link EntityManagerFactory}
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}