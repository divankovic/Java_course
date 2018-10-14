package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class QueryFilterTest {

	
	@Test
	public void testFilter() throws IOException {
		List<String> rows = Files.readAllLines(Paths.get("./database.txt"),StandardCharsets.UTF_8);
		StudentDatabase db = new StudentDatabase(rows);
		QueryParser parser = new QueryParser("firstName LIKE \"A*\"");
		
		assertEquals(6,db.filter(new QueryFilter(parser.getQuery())).size());
	}
	
}
