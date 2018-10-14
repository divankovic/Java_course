package hr.fer.zemris.java.hw06.demo4;

/**
 * Represents a simple record containing basic information about a student.
 * @author Dorian Ivankovic
 *
 */
public class StudentRecord {

	/**
	 * Unique identifier of the student.
	 */
	private String jmbag;
	
	/**
	 * Last name of the student.
	 */
	private String lastName;
	
	/**
	 * First name of the student.
	 */
	private String firstName;
	
	/**
	 * Achieved points on the midterm exam.
	 */
	private double midtermExamPoints;
	
	/**
	 * Achieved points on the final exam.
	 */
	private double finalExamPoints;
	
	/**
	 * Achieved points on laboratory exercises.
	 */
	private double labPoints;
	
	/**
	 * Final grade of the student.
	 */
	private int finalGrade;
	
	
	/**
	 * Constructs a new Student record using all relevant information about the student.
	 * @param jmbag - unique identifier of the student
	 * @param lastName - last name of the student
	 * @param firstName - first name of the student
	 * @param midtermExamPoints - Achieved points on the midterm exam
	 * @param finalExamPoints - Achieved points on the final exam
	 * @param labPoints - Achieved points on laboratory exercises
	 * @param finalGrade - final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, 
			double midtermExamPoints,double finalExamPoints, double labPoints, int finalGrade) {
		
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.midtermExamPoints = midtermExamPoints;
		this.finalExamPoints = finalExamPoints;
		this.labPoints = labPoints;
		this.finalGrade = finalGrade;
	
	}
	
	/**
	 * Returns the unique identifier of the student - jmbag.
	 * @return jmbag
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Returns the last name of the student.
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the first name of the student.
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Returns the points achieved on midterm exam.
	 * @return points achieved on midterm exam
	 */
	public double getMidtermExamPoints() {
		return midtermExamPoints;
	}

	/**
	 * Returns the points achieved on final exam.
	 * @return points achieved on final exam
	 */
	public double getFinalExamPoints() {
		return finalExamPoints;
	}

	/**
	 * Returns the points achieved on laboratory exercises.
	 * @return points achieved on laboratory exercises
	 */
	public double getLabPoints() {
		return labPoints;
	}

	/**
	 * Returns the final grade of the student.
	 * @return final grade of the student
	 */
	public int getFinalGrade() {
		return finalGrade;
	}

	/**
	 * Returns the sum of points from midterm exam, final exam and laboratory
	 * exercises - total points
	 * @return total points achieved
	 */
	public double getTotalPoints() {
		return midtermExamPoints + finalExamPoints + labPoints;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StudentRecord)) return false;
		StudentRecord other = (StudentRecord) obj;
		return jmbag.equals(other.jmbag);
	}
	
	@Override
	public String toString() {
		return jmbag+" "+lastName+" "+firstName+" "+midtermExamPoints+" "+finalExamPoints+" "+labPoints+" "+finalGrade;
	}
	
	
	
}
