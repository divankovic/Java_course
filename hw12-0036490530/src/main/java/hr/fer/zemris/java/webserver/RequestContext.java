package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The class represent a server response context, that is used for generating
 * response header, cookies and data(html, text, ...). The context uses
 * parameters which are either given to the server through a http request,
 * cookies or set from a script(temporary parameter).
 * 
 * @author Dorian Ivankovic
 *
 */
public class RequestContext {

	/**
	 * Default charset used for header to bytes encoding.
	 */
	public static final Charset HEADER_CHARSET = StandardCharsets.ISO_8859_1;

	/**
	 * Output stream of context to which all data is written.
	 */
	private OutputStream outputStream;

	/**
	 * Charset parameter used in http header.
	 */
	private Charset charset;

	/**
	 * Encoding parameter from which charset parameter is generated.
	 */
	private String encoding = "UTF-8";

	/**
	 * Response status code.
	 */
	private int statusCode = 200;

	/**
	 * Response status text.
	 */
	private String statusText = "OK";

	/**
	 * Response mime type.
	 */
	private String mimeType = "text/html";

	/**
	 * Lenght of the response, only possible to show this property when reading a
	 * static file.
	 */
	private Long contentLength = null;

	/**
	 * Read only parameters obtained from http request.
	 */
	private Map<String, String> parameters;

	/**
	 * Temporary parameters.
	 */
	private Map<String, String> temporaryParameters;

	/**
	 * Persistent parameters - used when operating with cookies.
	 */
	private Map<String, String> persistentParameters;

	/**
	 * Output response cookies.
	 */
	private List<RCCookie> outputCookies;

	/**
	 * Used to determine whether or not the header has been generated so it can be
	 * generated when one of write methods, for example {@link #write(byte[])} is
	 * called for the first time.
	 */
	private boolean headerGenerated = false;

	/**
	 * Used to dispatch requests to other url's.
	 */
	private IDispatcher dispatcher;

	/**
	 * Cookie used to save session's parameters.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	public static class RCCookie {

		/**
		 * Name of the cookie.
		 */
		private String name;

		/**
		 * Value of the cookie.
		 */
		private String value;

		/**
		 * Cookie server domain.
		 */
		private String domain;

		/**
		 * Cookie path on the server.
		 */
		private String path;

		/**
		 * Maximal age of the cookie.
		 */
		private Integer maxAge;

		/**
		 * Used to determine if the cookie is http only or not - exposed only to http
		 * and https channels.
		 */
		private boolean httpOnly;

		/**
		 * Constructs a new {@link RCCookie} using all it's relevant information.
		 * 
		 * @param name
		 *            - name of the cookie
		 * @param value
		 *            - value of the cookie
		 * @param domain
		 *            - server domain of the cookie
		 * @param path
		 *            - path on the server
		 * @param maxAge
		 *            - max age of the cookie
		 * @throws NullPointerException
		 *             if name or value is null
		 */
		public RCCookie(String name, String value, String domain, String path, Integer maxAge) {
			Objects.requireNonNull(name, "Name must not be null.");
			Objects.requireNonNull(value, "Value must not be null.");
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

		/**
		 * Returns the name of the cookie.
		 * 
		 * @return name of the cookie
		 */
		public String getName() {
			return name;
		}

		/**
		 * Returns the value of the cookie.
		 * 
		 * @return value of the cookie
		 */
		public String getValue() {
			return value;
		}

		/**
		 * Returns the domain of the cookie.
		 * 
		 * @return domain of the cookie
		 */
		public String getDomain() {
			return domain;
		}

		/**
		 * Returns the path of the cookie.
		 * 
		 * @return path of the cookie.
		 */
		public String getPath() {
			return path;
		}

		/**
		 * Returns the max age of the cookie.
		 * 
		 * @return max age of the cookie.
		 */
		public Integer getMaxAge() {
			return maxAge;
		}

		/**
		 * Sets the httpOnly property of the cookie.
		 * 
		 * @param httpOnly
		 *            - is cookie accessible only from http and https channels or not
		 */
		public void setHttpOnly(boolean httpOnly) {
			this.httpOnly = httpOnly;
		}
	}

	/**
	 * Constructs a new {@link RequestContext} using all relevant parameters.
	 * 
	 * @param outputStream
	 *            - output stream used for writing request data
	 * @param parameters
	 *            - parameters of the request
	 * @param persistentParameters
	 *            - persistent parameters (obtained from cookies) of the request
	 * @param outputCookies
	 *            - ouput cookies of the request
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {
		this(outputStream, parameters, persistentParameters, outputCookies, null, null);
	}

	/**
	 * Constructs a new {@link RequestContext} using all relevant parameters.
	 * 
	 * @param outputStream
	 *            - output stream used for writing request data
	 * @param parameters
	 *            - parameters of the request
	 * @param persistentParameters
	 *            - persistent parameters (obtained from cookies) of the request
	 * @param outputCookies
	 *            - ouput cookies of the request
	 * @param temporaryParameters
	 *            - temporary parameters of the context
	 * @param dispatcher
	 *            - used to dispatch requests to other url's
	 * @throws NullPointerException
	 *             if outputStream is null
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies,
			Map<String, String> temporaryParameters, IDispatcher dispatcher) {

		Objects.requireNonNull(outputStream, "Output stream must not be null!");
		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.temporaryParameters = temporaryParameters == null ? new HashMap<>() : temporaryParameters;
		this.outputCookies = outputCookies == null ? new ArrayList<>() : new ArrayList<>(outputCookies);
		this.dispatcher = dispatcher;
	}

	/**
	 * Sets the encoding parameter used in header parameters.
	 * 
	 * @param encoding
	 *            - encoding header parameter
	 * @throws UnsupportedOperationException
	 *             if the header is already generated
	 */
	public void setEncoding(String encoding) {
		if (headerGenerated) {
			throw new UnsupportedOperationException(
					"Changing encoding when the header is already generated is not allowed.");
		}
		this.encoding = encoding;
	}

	/**
	 * Sets the status code of the response header.
	 * 
	 * @param statusCode
	 *            - status code of the header
	 * @throws UnsupportedOperationException
	 *             if the header is already generated
	 */
	public void setStatusCode(int statusCode) {
		if (headerGenerated) {
			throw new UnsupportedOperationException(
					"Changing status code when the header is already generated is not allowed.");
		}
		this.statusCode = statusCode;
	}

	/**
	 * Sets the status text of the response header.
	 * 
	 * @param statusText
	 *            - status text of the header
	 * @throws UnsupportedOperationException
	 *             if the header is already generated
	 */
	public void setStatusText(String statusText) {
		if (headerGenerated) {
			throw new UnsupportedOperationException(
					"Changing status text when the header is already generated is not allowed.");
		}
		this.statusText = statusText;
	}

	/**
	 * Sets the mime type of the response header.
	 * 
	 * @param mimeType
	 *            - mime type property of the header
	 * @throws UnsupportedOperationException
	 *             if the header is already generated
	 */
	public void setMimeType(String mimeType) {
		if (headerGenerated) {
			throw new UnsupportedOperationException(
					"Changing mime type when the header is already generated is not allowed.");
		}
		this.mimeType = mimeType;
	}

	/**
	 * Sets the content length property in the header.
	 * 
	 * @param length
	 *            - length of the request data that will be sent
	 * @throws UnsupportedOperationException
	 *             if the header is already generated
	 */
	public void setContentLength(Long length) {
		if (headerGenerated) {
			throw new UnsupportedOperationException(
					"Changing content length when the header is already generated is not allowed.");
		}
		this.contentLength = length;
	}

	/**
	 * Adds a new cookie to request context cookies.
	 * 
	 * @param rcCookie
	 *            - new response cookie
	 * @throws NullPointerException
	 *             if rcCookie is null
	 * @throws UnsupportedOperationException
	 *             if the header is already generated
	 */
	public void addRCCookie(RCCookie rcCookie) {
		if (headerGenerated) {
			throw new UnsupportedOperationException(
					"Changing cookies when the header is already generated is not allowed.");
		}
		Objects.requireNonNull(rcCookie, "Cookie must not be null!");

		outputCookies.add(rcCookie);

	}

	/**
	 * Returns the value of the parameter under name <code>name</code>.
	 * 
	 * @param name
	 *            - name of the parameter
	 * @return value of the parameter
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}

	/**
	 * Returns an unmodifiable(read only) set of parameter names.
	 * 
	 * @return parameter names
	 */
	public Set<String> getParameterNames() {
		return Collections.unmodifiableSet(parameters.keySet());
	}

	/**
	 * Returns the value of the persistent parameter under name <code>name</code>.
	 * 
	 * @param name
	 *            - name of the persistent parameter
	 * @return value of the persistent parameter
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.get(name);
	}

	/**
	 * Returns an unmodifiable(read only) set of persistent parameter names.
	 * 
	 * @return persistnent parameter names
	 */
	public Set<String> getPersistentParameterNames() {
		return Collections.unmodifiableSet(persistentParameters.keySet());
	}

	/**
	 * Sets the value of persistent parameter under name <code>name</code>.
	 * 
	 * @param name
	 *            - name of the persistent parameter
	 * @param value
	 *            - value of the persistent parameter
	 * @throws NullPointerException
	 *             if name is null
	 */
	public void setPersistentParameter(String name, String value) {
		Objects.requireNonNull(name, "Name must not be null!");
		persistentParameters.put(name, value);
	}

	/**
	 * Removes the persistent parameter under name <code>name</code>.
	 * 
	 * @param name
	 *            - name of the persistent parameter to remove
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Returns the value of the temporary parameter under name <code>name</code>.
	 * 
	 * @param name
	 *            - name of the temporary parameter
	 * @return value of the temporary parameter
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.get(name);
	}

	/**
	 * Returns an unmodifiable(read only) set of temporary parameter names.
	 * 
	 * @return temporary parameter names
	 */
	public Set<String> getTemporaryParameterNames() {
		return Collections.unmodifiableSet(temporaryParameters.keySet());
	}

	/**
	 * Sets the value of temporary parameter under name <code>name</code>.
	 * 
	 * @param name
	 *            - name of the temporary parameter
	 * @param value
	 *            - value of the temporary parameter
	 * @throws NullPointerException
	 *             if name is null
	 */
	public void setTemporaryParameter(String name, String value) {
		Objects.requireNonNull(name, "Name must not be null!");
		temporaryParameters.put(name, value);
	}

	/**
	 * Removes the temporary parameter under name <code>name</code>.
	 * 
	 * @param name
	 *            - name of the persistent parameter to remove
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Returns the request dispatcher used by this context.
	 * 
	 * @return dispatcher
	 */
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * <p>
	 * Writes <code>data</code> to the <code>outputStream</code> by generating the
	 * header if the header hasn't already been generated.
	 * </p>
	 * The header consists of protocol, status code, status message and header
	 * properties such as content-type, cookies, mime-type,...<br>
	 * Each line must be terminated by \r\n and the header must be separated from
	 * the actual data using \r\n\r\n.
	 * 
	 * @param data
	 *            - data to write
	 * @return context
	 * @throws IOException
	 *             if the data couldn't be written
	 */
	public RequestContext write(byte[] data) throws IOException {

		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(data);
		return this;
	}

	/**
	 * Generates the request's header as specified in {@link #write(byte[])}.
	 * 
	 * @throws IOException
	 *             if the header data couldn't be written
	 */
	private void generateHeader() throws IOException {
		charset = Charset.forName(encoding);

		StringBuilder headerBuilder = new StringBuilder();

		headerBuilder.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append("\r\n");

		if (contentLength != null) {
			headerBuilder.append("Content-Length: ").append(contentLength).append("\r\n");
		}

		headerBuilder.append("Content-Type: ").append(mimeType);
		if (mimeType.startsWith("text/")) {
			headerBuilder.append("; charset=").append(encoding);
		}
		headerBuilder.append("\r\n");

		for (RCCookie cookie : outputCookies) {
			headerBuilder.append("Set-Cookie: ");
			headerBuilder.append(cookie.name).append("=\"").append(cookie.value).append("\"");

			if (cookie.domain != null) {
				headerBuilder.append("; ").append("Domain=").append(cookie.domain);
			}

			if (cookie.path != null) {
				headerBuilder.append("; ").append("Path=").append(cookie.path);
			}

			if (cookie.maxAge != null) {
				headerBuilder.append("; ").append("Max-Age=").append(cookie.maxAge);
			}

			if (cookie.httpOnly) {
				headerBuilder.append("; HttpOnly");
			}

			headerBuilder.append("\r\n");
		}

		headerBuilder.append("\r\n");

		outputStream.write(headerBuilder.toString().getBytes(HEADER_CHARSET));

		headerGenerated = true;

	}

	/**
	 * Writes the text using <code>charset</code> encoding, for more details see
	 * {@link #write(byte[])}.
	 * 
	 * @param text
	 *            - text to write to the output
	 * @return context
	 * @throws IOException
	 *             - if the text could't be written to the output
	 */
	public RequestContext write(String text) throws IOException {

		if (!headerGenerated) {
			generateHeader();
		}

		outputStream.write(text.getBytes(charset));
		return this;
	}

	/**
	 * Writes the data from buffer starting at <code>offset</code> and then taking
	 * <code>len</code> bytes using {@link #write(byte[])} method.
	 * 
	 * @param data
	 *            - data buffer
	 * @param offset
	 *            - offset of the staring byte to write to the output
	 * @param len
	 *            - len of the bytes to write to the output
	 * @return context
	 * @throws IOException
	 *             if the data couldn't be written
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {
		return this.write(Arrays.copyOfRange(data, offset, offset + len));
	}
}
