package hr.fer.zemris.java.webserver.workers;

import java.io.IOException;
import java.util.Set;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The worker ouputs back to the user the parameters it obtained formatted as an
 * HTML table.
 * @author Dorian Ivankovic
 *
 */
public class EchoParams implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {

		context.setMimeType("text/html");
		
		
		try {
			context.write("<html>");
			context.write("<head><title>Parameters table</title></head>");
			context.write("<body><table border=\"1\"><thead><tr><th>Name</th><th>Value</th></tr></thead>");
			context.write("<tbody>");

			Set<String> paramNames = context.getParameterNames();
			for(String paramName : paramNames) {
				context.write("<tr><td>"+paramName+"</td><td>"+ context.getParameter(paramName)+"</td></tr>");
			}
			
			context.write("</tbody></table></body></html>");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
