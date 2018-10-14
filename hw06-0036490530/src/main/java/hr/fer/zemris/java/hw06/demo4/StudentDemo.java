package hr.fer.zemris.java.hw06.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The program demonstrates the use of <a href = https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html>stream API</a>
 * using {@link StudentRecord}'s as elements of the collections.
 * @author Dorian Ivankovic
 *
 */
public class StudentDemo {

	/**
	 * This method is called once the program is run.
	 * @param args - command line arguments
	 * @throws IOException - if the file "studenti.txt" cannot be opened
	 */
	public static void main(String[] args) throws IOException {

		List<String> lines = Files.readAllLines(Paths.get("./res/studenti.txt"), StandardCharsets.UTF_8);
		List<StudentRecord> records = parseRecords(lines);

		long number = moreThan25Points(records);
		System.out.println(number + " students have more than 25 total points.");
		System.out.println();

		long numberOfExcellent = getNumOfExcellentStudents(records);
		System.out.println(numberOfExcellent + " students have final grade 5.");
		System.out.println();

		List<StudentRecord> excellentStudents = getExcellentStudents(records);
		System.out.println("Excellent students:");
		excellentStudents.forEach(System.out::println);
		System.out.println();

		List<StudentRecord> excellentStudentsSorted = getExcellentStudentsSorted(records);
		System.out.println("Excellent students sorted by total points:");
		excellentStudentsSorted.forEach(s->System.out.printf(s+" total points: %.2f\n", s.getTotalPoints()));
		System.out.println();

		List<String> jmbagsOfFailed = getFailedStudents(records);
		System.out.println("JMBAG's of students who failed:");
		jmbagsOfFailed.forEach(System.out::println);
		System.out.println();

		@SuppressWarnings("unused")
		Map<Integer, List<StudentRecord>> gradesMap = mapByGrades(records);

		Map<Integer, Integer> gradesMap2 = numberOfStudentsByGrades(records);
		System.out.println("Number of students by grades: ");
		gradesMap2.forEach((grade, num) -> System.out.println(grade + " : " + num));
		System.out.println();

		Map<Boolean, List<StudentRecord>> passOrFail = classifyPassOrFail(records);
		System.out.println("Passed: " + passOrFail.get(true).size());
		System.out.println("Failed: " + passOrFail.get(false).size());

	}

	/**
	 * Parses the data from "studenti.txt" into a list of {@link StudentRecord}'s.
	 * @param lines - lines of "studenti.txt"
	 * @return a list of <code>StudentRecord</code>'s
	 */
	private static List<StudentRecord> parseRecords(List<String> lines) {

		List<StudentRecord> records = new ArrayList<>();

		for (String line : lines) {
			String[] data = line.split("\\s+");

			String jmbag = data[0];
			String lastName = data[1];
			String firstName = data[2];
			double midtermExamPoints = Double.parseDouble(data[3]);
			double finalExamPoints = Double.parseDouble(data[4]);
			double labPoints = Double.parseDouble(data[5]);
			int finalGrade = Integer.parseInt(data[6]);

			records.add(new StudentRecord(jmbag, lastName, firstName, midtermExamPoints, finalExamPoints, labPoints,
					finalGrade));
		}

		return records;
	}

	/**
	 * Returns the number of students that have achieved a total of more than 25 points.
	 * @param records - student records
	 * @return number of students that have achieved a total of more than 25 points
	 */
	private static long moreThan25Points(List<StudentRecord> records) {
		long number = records.stream().filter(student -> student.getTotalPoints() > 25).count();
		return number;
	}

	/**
	 * Returns the number of students with final grade 5 (excellent).
	 * @param records - student records
	 * @return number of students with final grade 5 (excellent)
	 */ 
	private static long getNumOfExcellentStudents(List<StudentRecord> records) {
		long numberOfExcellent = records.stream().filter(student -> student.getFinalGrade() == 5).count();
		return numberOfExcellent;
	}

	/**
	 * Returns a list of excellent students.
	 * @param records - student records
	 * @return list of excellent students
	 */
	private static List<StudentRecord> getExcellentStudents(List<StudentRecord> records) {
		return records.stream().filter(student -> student.getFinalGrade() == 5).collect(Collectors.toList());
	}

	/**
	 * Returns a list of excellent students sorted by total points achieved, descending.
	 * @param records - student records
	 * @return list of excellent students sorted by total points achieved
	 */
	private static List<StudentRecord> getExcellentStudentsSorted(List<StudentRecord> records) {
		return records.stream().filter(student -> student.getFinalGrade() == 5)
				.sorted((s1, s2) -> Double.compare(s2.getTotalPoints(), s1.getTotalPoints())).collect(Collectors.toList());
	}

	/**
	 * Returns a list of students that have failed (have final grade = 1),
	 * sorted by jmbags - ascending.
	 * @param records - student records
	 * @return list of students that have failed
	 */
	private static List<String> getFailedStudents(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 1).map(student -> student.getJmbag()).sorted()
				.collect(Collectors.toList());
	}

	/**
	 * Returns a map that maps each grade to a list of students that have that final grade.
	 * @param records - student records
	 * @return map that maps each grade to a list of students that have that final grade
	 */
	private static Map<Integer, List<StudentRecord>> mapByGrades(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(s -> s.getFinalGrade()));
	}

	/**
	 * Returns a map that maps each grade to the number of students that have that final grade.
	 * @param records - student records
	 * @return map that maps each grade to the number of students that have that final grade
	 */
	private static Map<Integer, Integer> numberOfStudentsByGrades(List<StudentRecord> records) {
		return records.stream().collect(Collectors.toMap(s -> s.getFinalGrade(), s -> 1, (s1, s2) -> s1 + s2));
	}

	/**
	 * Returns a map that maps a boolean (passed or failed) to all students that passed or failed.
	 * @param records - student records
	 * @return map that maps a boolean (passed or failed) to all students that passed or failed
	 */
	private static Map<Boolean, List<StudentRecord>> classifyPassOrFail(List<StudentRecord> records) {
		return records.stream().collect(Collectors.partitioningBy(s -> s.getFinalGrade() > 1));
	}
}
