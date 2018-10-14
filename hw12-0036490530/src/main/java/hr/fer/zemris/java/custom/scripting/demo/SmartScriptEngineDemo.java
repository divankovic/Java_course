package hr.fer.zemris.java.custom.scripting.demo;

import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Demonstrates the use of {@link SmartScriptEngine}, takes one argument (file path)
 * as command line argument, parses it using {@link SmartScriptParser} and then executes
 * it using {@link SmartScriptEngine} and writes the result to standard output.
 * @author Dorian Ivankovic
 *
 */
public class SmartScriptEngineDemo {
	
	/**
	 * This method is called once the program is run.
	 * @param args - command line argument: path to smart script file to execute
	 */
	public static void main(String[] args) {
		if(args.length!=1) {
			System.out.println("Program must have one input - path to document to parse.");
			return;
		}
		
		String docBody;
		try {
			docBody = new String(
					 Files.readAllBytes(Paths.get(args[0])),
					 StandardCharsets.UTF_8
					);
		} catch (IOException e) {
			System.out.println("File not found");
			return;
		}
		
		try {
			Map<String,String> parameters = new HashMap<String, String>();
			Map<String,String> persistentParameters = new HashMap<String, String>();
			List<RCCookie> cookies = new ArrayList<>();

			// put some parameter into parameters map
			parameters.put("a", "4");
			parameters.put("b", "2");
			persistentParameters.put("brojPoziva", "3");
			
			// create engine and execute it
			RequestContext rc = new RequestContext(System.out, parameters, persistentParameters,
					cookies);
			new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(),rc).execute();
			
			//System.out.println("Vrijednosti u mapi: "+rc.getPersistentParameter("brojPoziva"));
		} catch(SmartScriptParserException e) {
			System.out.println("Unable to parse document!");
			System.exit(-1);
		}
	}
}
