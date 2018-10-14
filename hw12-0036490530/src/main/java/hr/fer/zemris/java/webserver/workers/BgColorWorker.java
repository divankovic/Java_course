package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * The worker updates the current background color and displays
 * a message about the change and a link to the original page.
 * @author Dorian Ivankovic
 *
 */
public class BgColorWorker implements IWebWorker{

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String color = context.getParameter("bgcolor");
		if(!checkColor(color)) {
			context.setTemporaryParameter("message", "Background color is not updated.");
		}else {
			context.setPersistentParameter("bgcolor", color);
			context.setTemporaryParameter("message", "Background color updated");
		}
		context.getDispatcher().dispatchRequest("private/bgLink.smscr");
	}

	private boolean checkColor(String color) {
		if(color.length()!=6) return false;
		
		return color.matches("[0-9A-Fa-f]+");
	}

}
