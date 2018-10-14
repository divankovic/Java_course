package hr.fer.zemris.java.votingapp.listeners;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.votingapp.dao.DAO;
import hr.fer.zemris.java.votingapp.dao.DAOProvider;
import hr.fer.zemris.java.votingapp.model.Poll;
import hr.fer.zemris.java.votingapp.model.PollOption;

/**
 * The listener initializes the application once it's started by loading the database configuration files,and
 * putting the default values into the database if not already there.
 * @author Dorian Ivankovic
 *
 */
@WebListener
public class StartupListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {

		try {
			Properties dbSettings = new Properties();
			dbSettings.load(
					Files.newBufferedReader(Paths.get(sce.getServletContext().getRealPath("WEB-INF/dbsettings.properties"))));

			String host = Objects.requireNonNull(dbSettings.getProperty("host"));
			String port = Objects.requireNonNull(dbSettings.getProperty("port"));
			String dbName = Objects.requireNonNull(dbSettings.getProperty("name"));

			String user = Objects.requireNonNull(dbSettings.getProperty("user"));
			String password = Objects.requireNonNull(dbSettings.getProperty("password"));

			String connectionURL = "jdbc:derby://" + host + ":" + port + "/" + dbName + ";user=" + user + ";password="
					+ password;

			ComboPooledDataSource cpds = new ComboPooledDataSource();

			cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");

			cpds.setJdbcUrl(connectionURL);

			sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);

			try (Connection con = cpds.getConnection()) {
				verifyPollTables(con);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * The listener checks if database tables requested for the application exist, and
	 * creates them using {@link DAO} if they don't.
	 * @param con - database connection
	 * @throws SQLException - if an error occurs while inserting rows into the databsase
	 */
	private void verifyPollTables(Connection con) throws SQLException {
		DatabaseMetaData meta = con.getMetaData();
		
		try(ResultSet res = meta.getTables(null, null, null, null);){
		
			boolean exists = checkTables(res);
		
			if (!exists) {
				
				DAOProvider.getDao().createPollsTable(con);
				DAOProvider.getDao().createPollOptionsTable(con);
					
					
				long[] id = insertPolls(con);
					
				long id1 = id[0];
				long id2 = id[1];
					
				insertPollOptions(con,id1, id2);
			}
		}
	}


	/**
	 * Inserts the initial pollOptions for polls given by id's id1 and id2.
	 * @param conn - database connection
	 * @param id1 - id of the first {@link Poll}
	 * @param id2 - id of the second {@link Poll}
	 * @throws SQLException - if an error occurred while inserting rows into the table.
	 */
	private void insertPollOptions(Connection conn, long id1, long id2) throws SQLException {
		DAO dao = DAOProvider.getDao();
		
		dao.inserPollOption(conn, new PollOption("The Beatles", "https://www.youtube.com/watch?v=z9ypq6_5bsg", id1, 30));
		dao.inserPollOption(conn, new PollOption("The Platters", "https://www.youtube.com/watch?v=H2di83WAOhU", id1, 10));
		dao.inserPollOption(conn, new PollOption("The Beach Boys", "https://www.youtube.com/watch?v=2s4slliAtQU", id1, 18));
		dao.inserPollOption(conn, new PollOption("The Four Seasons", "https://www.youtube.com/watch?v=y8yvnqHmFds", id1, 22));
		dao.inserPollOption(conn, new PollOption("The Marcels", "https://www.youtube.com/watch?v=qoi3TH59ZEs", id1, 27));
		dao.inserPollOption(conn, new PollOption("The Everly Brothers", "https://www.youtube.com/watch?v=z9ypq6_5bsg", id1, 24));
		dao.inserPollOption(conn, new PollOption("The Mamas And The Papas", "https://www.youtube.com/watch?v=N-aK6JnyFmk", id1, 14));
		
		dao.inserPollOption(conn, new PollOption("Lebron James", "https://www.youtube.com/watch?v=7Vre_nWbPxk", id2, 50));
		dao.inserPollOption(conn, new PollOption("Kawhi Leonard", "https://www.youtube.com/watch?v=k9KM-aPj-d0", id2, 45));
		dao.inserPollOption(conn, new PollOption("Kyrie Irving", "https://www.youtube.com/watch?v=XBa8RZKY3QA", id2, 48));
		dao.inserPollOption(conn, new PollOption("Anthony Davis", "https://www.youtube.com/watch?v=DXN_0UpH0sw", id2, 50));
		dao.inserPollOption(conn, new PollOption("Giannis Antetokounmpo", "https://www.youtube.com/watch?v=a5hmTrhtVlQ", id2, 35));
		dao.inserPollOption(conn, new PollOption("DeMarcus Cousins", "https://www.youtube.com/watch?v=N-aK6JnyFmk", id2, 14));
		dao.inserPollOption(conn, new PollOption("Steph Curry", "https://www.youtube.com/watch?v=2hIrbnVx4k4", id2, 48));
		
	}

	
	/**
	 * The method checks if the desired pool and PoolOption tables already exist..
	 * @param res - result set containg a collection of all tables
	 * @return true if the tables contain the specified one, false otherwise
	 * @throws SQLException if an error occurred while reading from result set
	 */
	private boolean checkTables(ResultSet res) throws SQLException {
		while(res.next()) {
			String tableName = res.getString("TABLE_NAME");

			if(tableName.toLowerCase().equals("polls") || tableName.toLowerCase().equals("polloptions")) {
				return true;
			}
		}
		
		return false;
	}
	
	
	/**
	 * This method adds the initial  {@link Poll}'s to the database.
	 * @param conn - database connectkion,
	 * @return array of generated id (one for each inputted {@link Poll}
	 * @throws SQLException - if an error occurred while inserting rows into the table.
	 */
	private long[] insertPolls(Connection conn) throws SQLException {
		DAO dao = DAOProvider.getDao();
		
		long id1 = dao.insertPoll(conn, new Poll("Favourite band voting", "Which band is your favorite? Click on the link to vote!"));
		long id2 = dao.insertPoll(conn, new Poll("Favorite NBA player voting", "Which NBA player is your favorite? Click on the link to vote!"));
		
		return new long[]{id1, id2};
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext()
				.getAttribute("hr.fer.zemris.dbpool");
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}