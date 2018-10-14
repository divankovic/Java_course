package hr.fer.zemris.java.hw05.db;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class QueryParserTest {
	
	QueryParser parser;
	
	@Test(expected = QueryParserException.class)
	public void testEmptyQuery() {
		parser = new QueryParser("");
	}
	
	@Test
	public void testDirectQuery() {
		parser = new QueryParser(" jmbag =\"0123456789\" ");
		assertTrue(parser.isDirectQuery());
		assertEquals("0123456789",parser.getQueriedJMBAG());
		assertEquals(1,parser.getQuery().size());
	}
	
	@Test(expected = IllegalStateException.class)
	public void testQueriedJMBAG() {
		parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(parser.isDirectQuery());
		parser.getQueriedJMBAG();
		
	}
	
	@Test
	public void testMultipleExpressionQuery() {
		parser = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertEquals(2,parser.getQuery().size());
	}
	
	@Test(expected = QueryParserException.class)
	public void testMissingANDQuery() {
		parser = new QueryParser("jmbag=\"0123456789\"  lastName>\"J\"");
	}
	
	@Test(expected = QueryParserException.class)
	public void testIllegalOperator() {
		parser = new QueryParser("jmbag^\"0123456789\"  and lastName>\"J\"");
	}
	
	@Test(expected = QueryParserException.class)
	public void testIllegalField() {
		parser = new QueryParser("weight=\"0123456789\"  and lastName>\"J\"");
		
	}
	
	@Test(expected = QueryParserException.class)
	public void testIllegalLiteral() {
		parser = new QueryParser("jmbag=\"0123456789  and lastName>\"J\"");
	}
	
	@Test(expected = QueryParserException.class)
	public void testMultipleWildcards() {
		parser = new QueryParser("jmbag=\"0123456789\"  and lastName LIKE \"J**\"");
	}
	
	
}
