package hr.fer.zemris.java.webserver;

/**
 * Defines an interface to any object that can process current request,
 * it gets {@link RequestContext} and is expected to create content for a client.
 * @author Dorian Ivankovic
 *
 */
public interface IWebWorker {
	
	/**
	 * Processes the request and creates content for the client,
	 * either directly or using {@link IDispatcher}.
	 * @param context - used for generating output content
	 * @throws Exception - if it's not possible to generate output data
	 */
	void processRequest(RequestContext context) throws Exception;
}
