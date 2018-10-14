package hr.fer.zemris.java.webserver;

/**
 * This interface specifies a method that dispatches a request to a different url,
 * and the class implementing this interface must implement this functionality.
 * @author Dorian Ivankovic
 *
 */
public interface IDispatcher {
	
	/**
	 * The method dispatches a request for a new url.
	 * @param urlPath - requested url
	 * @throws Exception - if the output data could't be sent
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
