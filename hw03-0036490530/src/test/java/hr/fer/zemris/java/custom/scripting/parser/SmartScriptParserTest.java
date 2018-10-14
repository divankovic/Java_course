package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.hw03.prob2.SmartScriptTester;

public class SmartScriptParserTest {

	SmartScriptParser parser;
	
	@Test
	public void test1() {
		String document = loader("test1.txt");
		check(document);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void testIllegalFor1() {
		String document = loader("test2.txt");
		check(document);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void testIllegalFor2() {
		String document = loader("test3.txt");
		check(document);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void testIllegalFor3() {
		String document = loader("test4.txt");
		check(document);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void testMissingEND() {
		String document = loader("test5.txt");
		check(document);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void testExtraEND() {
		String document = loader("test6.txt");
		check(document);
	}
	
	@Test(expected = SmartScriptParserException.class)
	public void testUnexistingTag() {
		String document = loader("test7.txt");
		check(document);
	}
	
	@Test
	public void testLargeInput() {
		String document = loader("test8.txt");
		check(document);
	}
	
	
	private void check(String document) {
		parser = new SmartScriptParser(document);
		DocumentNode documentNode = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptTester.createOriginalDocumentBody(documentNode);

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode documentNode2 = parser2.getDocumentNode();
		
		assertEquals(documentNode,documentNode2);
	}
	
	
	

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}
}
