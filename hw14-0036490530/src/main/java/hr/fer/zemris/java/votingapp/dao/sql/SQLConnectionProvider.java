package hr.fer.zemris.java.votingapp.dao.sql;

import java.sql.Connection;

/**
 * A storage of connections to the database in a {@link ThreadLocal} object which is 
 * actually a map whose keys are threads that perform the operation on it.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SQLConnectionProvider {

	/**
	 * A map of connections.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();
	
	/**
	 * Postavi vezu za trenutnu dretvu (ili obriši zapis iz mape ako je argument <code>null</code>).
	 * Set the connection for the current thread, or delete it if the argument is null.
	 * @param con database connection 
	 */
	public static void setConnection(Connection con) {
		if(con==null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}
	
	/**
	 * Dohvati vezu koju trenutna dretva (pozivatelj) smije koristiti.
	 * Gets the connection to the database for the current thread
	 * @return connection to database
	 */
	public static Connection getConnection() {
		return connections.get();
	}
	
}