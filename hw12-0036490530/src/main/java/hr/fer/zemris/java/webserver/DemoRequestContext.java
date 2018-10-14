package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A program that demonstrates the use of {@link RequestContext}.
 * @author Dorian Ivankovic
 *
 */
public class DemoRequestContext {
	
	/**
	 * This method is called once the program is called.
	 * @param args - command line arguments, not used
	 * @throws IOException - if the specified demo file couldn't be opened.
	 */
	public static void main(String[] args) throws IOException {
		demo1("primjer1.txt", "ISO-8859-2");
		demo1("primjer2.txt", "UTF-8");
		demo2("primjer3.txt", "UTF-8");
	}

	/**
	 * Writes sample text to <code>filePath</code> using encoding <code>encoding</code>.
	 * @param filePath - path to the file to write output to
	 * @param encoding - encoding used
	 * @throws IOException - if the output file couldn't be opened
	 */
	private static void demo1(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}

	
	/**
	 * Writes sample text to <code>filePath</code> using encoding <code>encoding</code>.
	 * and also add some sample {@link RCCookie} cookies to the header.
	 * @param filePath - path to the file to write output to
	 * @param encoding - encoding used
	 * @throws IOException - if the output file couldn't be opened
	 */
	private static void demo2(String filePath, String encoding) throws IOException {
		OutputStream os = Files.newOutputStream(Paths.get(filePath));
		RequestContext rc = new RequestContext(os, new HashMap<String, String>(), new HashMap<String, String>(),
				new ArrayList<RequestContext.RCCookie>());
		rc.setEncoding(encoding);
		rc.setMimeType("text/plain");
		rc.setStatusCode(205);
		rc.setStatusText("Idemo dalje");
		rc.addRCCookie(new RCCookie("korisnik", "perica", "127.0.0.1", "/", 3600));
		rc.addRCCookie(new RCCookie("zgrada", "B4", null, "/", null));
		// Only at this point will header be created and written...
		rc.write("Čevapčići i Šiščevapčići.");
		os.close();
	}
}
