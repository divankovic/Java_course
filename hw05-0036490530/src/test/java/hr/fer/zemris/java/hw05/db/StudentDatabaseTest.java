package hr.fer.zemris.java.hw05.db;

import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StudentDatabaseTest {
	
	@Test
	public void basicFunctionalityTest() throws IOException {
		List<String> rows = Files.readAllLines(Paths.get("./database.txt"),StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(rows);
		
		assertTrue(db.forJMBAG("0000000001")!=null);
		assertTrue(db.forJMBAG("0000000002")!=null);
		
		
		List<StudentRecord> records = db.filter(y->true);
		assertEquals(rows.size(),records.size());
		
		records = db.filter(y-> false);
		assertEquals(0,records.size());
	}
}
