package hr.fer.zemris.java.votingapp.dao;

import hr.fer.zemris.java.votingapp.dao.sql.SQLDAO;

/**
 * Singleton class that knows who the actual DAOProvider is and is used by 
 * components in the appllication by components to get the database's functionalities.
 * @author Dorian Ivanković
 *
 */
public class DAOProvider {

	/**
	 * Private instance of the actual 
	 */
	private static DAO dao = new SQLDAO();
	
	/**
	 * The method returns a concrete implementation of DAO.
	 * 
	 * @return an object that encapdulates the dao layer..
	 */
	public static DAO getDao() {
		return dao;
	}
	
}