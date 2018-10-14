package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Program that processes queries and returns the appropriate {@link StudentRecord}'s
 * from the {@link StudentDatabase}.
 * The data for the database is loaded from the current directory from a file "database.txt".
 *<br> Each query consists of multiple expressions bound together by operator <b>AND</b> (case insensitive).
 *<br> Each expression is in format: <b>field operator literal</b>.
 *<br> <b>field</b> : all are case sensitive
 *					  <ul> 
 *						 <li><b>jmbag</b> - jmbag of the student</li>
 *						 <li><b>lastName</b> - last name of the student</li>
 *						 <li><b>firstName</b> - first name of the student</li>
 *					  </ul>
 *<br> <b>operator</b> : <ul>
 *							<li> <b> < </b> - less than
 *							<li> <b> <= </b> - less or equal than
 *							<li> <b> > </b> - greater than
 *							<li> <b> < </b> - greater or equal than
 *							<li> <b> = </b> - equal 		
 *							<li> <b> != </b> - not equal
 *							<li> <b> LIKE </b> (case sensitive) - compares two Strings where literal can have
 *							at most one wildcard <b>*</b> which represents a sequence of any character in String
 *							<br> example AAAA LIKE AA*AA -> true,  aaabb LIKE *abb -> true,  aabb LIKE *ab -> false </li>  
 *						</ul>
 *<br> <b>literal</b> : a String, must be enclosed by "" - example "AB"
 *
 *<br><br>
 *
 * The program displays the table showing <code>StudentRecord</code> obtained by the query
 * and number of obtained queries.
 * 
 * <br> Example : > query firstName LIKE "A*"
 * <br><br> Result :
 * <br> +============+===========+===========+===+
 * <br> | 0000000002 | Bakamović | Petra     | 3 |
 * <br> | 0000000003 | Bosnić    | Andrea    | 4 |
 * <br> | 0000000004 | Božić     | Marin     | 5 |
 * <br> | 0000000005 | Brezović  | Jusufadis | 2 |
 * <br> +============+===========+===========+===+
 * <br>Records selected: 4
 *
 *
 *
 * 
 * @author Dorian Ivankovic
 *
 */
public class StudentDB {
	
    /**
     * This method is called once the program is run.
     * @param args - command line arguments
     */
	public static void main(String[] args) {
		
		List<String> rows;
		
		try {
			rows = Files.readAllLines(Paths.get("./database.txt"),StandardCharsets.UTF_8);
		}catch(IOException ex) {
			System.out.println("Couldn't load file database.txt");
			return;
		}
		
		StudentDatabase db = new StudentDatabase(rows);
		
		try(Scanner scanner = new Scanner(System.in)){
			
			while(true) {
				System.out.print("> ");
				
				if(scanner.hasNextLine()) {
					String line = scanner.nextLine();
					
					if(line.isEmpty()) continue;
					
					line = line.trim();
					
					if(line.equals("exit")) {
						System.out.println("Goodbye!");
						break;
					}
					
					if(!line.startsWith("query")) {
						System.out.println("You must use keyword \"query\"");
						continue;
					}
					
					line = line.substring(5); //remove "query"
					
					try {
						QueryParser parser = new QueryParser(line);
						if(parser.isDirectQuery()) {
							StudentRecord record = db.forJMBAG(parser.getQueriedJMBAG());
							tableOutput(Arrays.asList(record));
						}else{
							tableOutput(db.filter(new QueryFilter(parser.getQuery())));
						}
					}catch(QueryParserException ex) {
						System.out.println(ex.getMessage());
						System.out.println();
						continue;
					}
				}
			}
		}
		
		
		
	}

	
	/**
	 * Formats the table output of <code>StudentRecords</code>.
	 * @param records - obtained records from the query
	 */
	private static void tableOutput(List<StudentRecord> records) {
		
		if(records.isEmpty() || records.get(0)==null) {
			System.out.println("Records selected: 0");
			System.out.println();
			return;
		}
		
		if(records.size()==1) System.out.println("Using index for record retrieval.");
		
		int maxNameLen = records.get(0).getFirstName().length();
		int maxLastNameLen = records.get(0).getLastName().length();
		
		for(StudentRecord record : records) {
			int nameLen = record.getFirstName().length();
			if(nameLen>maxNameLen) maxNameLen = nameLen;
			
			int lastNameLen = record.getLastName().length();
			if(lastNameLen>maxLastNameLen) maxLastNameLen = lastNameLen;
		}
		
		String border = new StringBuilder()
								.append("+")
								.append(getDelimiters(12))
								.append("+")
								.append(getDelimiters(maxLastNameLen+2))
								.append("+")
								.append(getDelimiters(maxNameLen+2))
								.append("+")
								.append(getDelimiters(3))
								.append("+")
								.toString();
		
		System.out.println(border);
		
		for(StudentRecord record : records) {
			System.out.printf("| %s | %"+(-maxLastNameLen)+"s | %"+(-maxNameLen)+"s | %d |%n",
					record.getJmbag(),record.getLastName(),record.getFirstName(),record.getFinalGrade());
		}
		
		System.out.println(border);
		System.out.println("Records selected: "+records.size());
		System.out.println();
	}
	
	/**
	 * Returns a String consisting of <code>count<code> characters =.
	 * Used for drawing borders of the result table.
	 * @param count - number of = delimiters needed
	 * @return String consisting of <code>count<code> characters =
	 */
	private static String getDelimiters(int count) {
		return new String(new char[count]).replace("\0", "=");
	}
}
