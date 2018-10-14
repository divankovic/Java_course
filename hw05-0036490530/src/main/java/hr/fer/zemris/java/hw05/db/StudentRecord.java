package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * A record stored in {@link StudentDatabase} containing basic student 
 * information - jmbag, first name, last name and final grade.
 * @author Dorian Ivankovic
 *
 */
public class StudentRecord {
	
	/**
	 * Unique student identifier - jmbag.
	 */
	private String jmbag;
	
	/**
	 * Student's last name.
	 */
	private String lastName;
	
	/**
	 * Student's first name.
	 */
	private String firstName;
	
	/**
	 * Student's final grade.
	 */
	private int finalGrade;
	
	
	/**
	 * Constructors a new Student using jmbag, lastName, firstName and finalGrade.
	 * @param jmbag - student's jmbag
	 * @param lastName - last name of the student
	 * @param firstName - first name of the student
	 * @param finalGrade - final grade of the student
	 * @throws NullPointerException if jmbag, firstName or lastName is null
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		Objects.requireNonNull(jmbag,"jmbag must not be null.");
		Objects.requireNonNull(lastName,"Last name must not be null.");
		Objects.requireNonNull(firstName,"First name must not be null.");
			
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
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
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return jmbag+" "+lastName+", "+firstName+" : "+finalGrade;
	}

	/**
	 * Returns student's jmbag.
	 * @return student's jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns student's last name.
	 * @return student's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns student's first name.
	 * @return student's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns student's final grade.
	 * @return student's final grade
	 */
	public int getFinalGrade() {
		return finalGrade;
	}
	
	
}
