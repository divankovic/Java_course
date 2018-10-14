package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.collections.SimpleHashTable;
import hr.fer.zemris.java.hw05.collections.SimpleHashTable.TableEntry;

/**
 * A simple database consisting of {@link StudentRecord}'s.
 * @author Dorian Ivankovic
 *
 */
public class StudentDatabase {
	
	/**
	 * Internal storing of <code>StudentRecords</code>
	 */
	private SimpleHashTable<String,StudentRecord> studentRecords;
	
	
	/**
	 * Constructs a new database from rows -
	 * textual form of {@link StudentRecord}'s.
	 * @param rows - rows containing data about <code>StudentRecords</code>
	 * in format: <br>jmbag lastName firstName finalGrade
	 */
	public StudentDatabase(List<String> rows) {
		
		studentRecords = new SimpleHashTable<>();
		
		for(String row : rows) {
			String[] data = row.split("\\t");
			
			String jmbag = data[0];
			String lastName = data[1];
			String firstName = data[2];
			int finalGrade = Integer.parseInt(data[3]);
		
			StudentRecord record = new StudentRecord(jmbag, lastName, firstName, finalGrade);
			studentRecords.put(jmbag, record);
		}
	}
	
	/**
	 * Returns the {@link StudentRecord} associated with <code>jmbag</code>.
	 * <br> The complexity of this method is O(1);
	 * @param jmbag - jmbag of the student
	 * @return
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return studentRecords.get(jmbag);
	}
	
	
	/**
	 * Filters the database using <code>filter</code> and returns
	 * the remaining <code>StudentRecords</code> that satisfy the <code>filter</code>'s condition.
	 * @param filter - used to filter the database
	 * @return records that satisfy the <code>filter's</code> condition
	 */
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> filteredRecords = new ArrayList<>();
		
		for(TableEntry<String,StudentRecord> recordEntry : studentRecords) {
			StudentRecord record = recordEntry.getValue();
			if(filter.accepts(record)) {
				filteredRecords.add(record);
			}
		}
		
		return filteredRecords;
		
	}
}
