package hr.fer.zemris.java.webserver;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngineException;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * A simple server capable of loading css - like scripts using
 * {@link SmartScriptEngine}, supports both configuration and
 * convention-over-configuration approach and it also provides a simple cookie
 * support. The server is setup from configuration file where server's address,
 * domain name, port, worker threads, document roots,... are specified.
 * 
 * @author Dorian Ivankovic
 *
 */
public class SmartHttpServer {

	/**
	 * Address of the server.
	 */
	private String address;

	/**
	 * Server domain name.
	 */
	private String domainName;

	/**
	 * Port on which the server listens for requests.
	 */
	private int port;

	/**
	 * Number of working threads in the server.
	 */
	private int workerThreads;

	/**
	 * Server session timeout(in seconds)
	 */
	private int sessionTimeout;

	/**
	 * Supported mime types;
	 */
	private Map<String, String> mimeTypes = new HashMap<String, String>();

	/**
	 * Main server thread.
	 */
	private ServerThread serverThread;

	/**
	 * Used to stop the server.
	 */
	private volatile boolean stopServer = false;

	/**
	 * Thread pool used for serving client't requests.
	 */
	private ExecutorService threadPool;

	/**
	 * Document root of the server.
	 */
	private Path documentRoot;

	/**
	 * Workers loaded from workers configuration file.
	 */
	private Map<String, IWebWorker> workersMap;

	/**
	 * Current client's sessions.
	 */
	private Map<String, SessionMapEntry> sessions = new HashMap<String, SessionMapEntry>();

	/**
	 * Used for generating radnom session id's.
	 */
	private Random sessionRandom = new Random();

	/**
	 * Size of the session id.
	 */
	public static final int SID_SIZE = 20;

	/**
	 * Entry holding a map of session parameters.
	 * 
	 * @author Dorian Ivankovic
	 *
	 */
	private static class SessionMapEntry {
		/**
		 * host
		 */
		String host;

		/**
		 * Time until the entry is valid.
		 */
		long validUntil;

		/**
		 * Stored parameters
		 */
		Map<String, String> map;

		/**
		 * Constructs a new {@link SessionMapEntry} using host, time until the entry is
		 * valid, and a map of parameters.
		 * 
		 * @param host
		 *            - host
		 * @param validUntil
		 *            - time until the entry is valid
		 * @param map
		 *            - parameters map
		 */
		public SessionMapEntry(String host, long validUntil, Map<String, String> map) {
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	/**
	 * Creates a new {@link SmartHttpServer} and loads it's parameters from the
	 * given <code>configFileName</code>
	 * 
	 * @param configFileName
	 *            - file holding server's configuration parameters
	 */
	public SmartHttpServer(String configFileName) {
		workersMap = new HashMap<>();
		loadProperties(configFileName);
		Thread expiredSessionsCollector = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(300_000);
						synchronized (sessions) {
							Set<String> expiredSessionKeys = new HashSet<>();
							for (Entry<String, SessionMapEntry> sessionEntry : sessions.entrySet()) {
								SessionMapEntry session = sessionEntry.getValue();
								if (session.validUntil < System.currentTimeMillis()) {
									expiredSessionKeys.add(sessionEntry.getKey());
								}
							}

							for (String expiredSession : expiredSessionKeys) {
								sessions.remove(expiredSession);
							}
						}
					} catch (InterruptedException e) {
					}
				}
			}

		});

		expiredSessionsCollector.setDaemon(true);
		expiredSessionsCollector.start();
	}

	/**
	 * Loads server properties - server address, domain name, port, worker threads,
	 * document root, timeout, mime types and workers.
	 * 
	 * @param configFileName
	 *            - file holding server's configuration properties
	 */
	private void loadProperties(String configFileName) {
		Properties props = new Properties();
		Properties mimeProps = new Properties();
		Properties workerProps = new Properties();

		try {
			props.load(Files.newBufferedReader(Paths.get("config").toAbsolutePath().resolve(configFileName)));
			mimeProps.load(Files.newBufferedReader(Paths.get(props.getProperty("server.mimeConfig"))));
			workerProps.load(Files.newBufferedReader(Paths.get(props.getProperty("server.workers"))));

			for (Entry<Object, Object> entry : mimeProps.entrySet()) {
				mimeTypes.put((String) entry.getKey(), (String) entry.getValue());
			}

			for (Entry<Object, Object> entry : workerProps.entrySet()) {
				String path = (String) entry.getKey();
				String fqcn = (String) entry.getValue();

				IWebWorker worker = loadWorker(fqcn);
				workersMap.put(path, worker);
			}

		} catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			System.out.println("Couldn't load server configuration files.");
			return;
		}

		address = props.getProperty("server.address");
		domainName = props.getProperty("server.domainName");
		port = Integer.parseInt(props.getProperty("server.port"));

		workerThreads = Integer.parseInt(props.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(props.getProperty("session.timeout"));

		documentRoot = Paths.get(props.getProperty("server.documentRoot"));
	}

	/**
	 * Loads the worker from using it's fully qualified class name.
	 * @param fqcn - fully qualified class name
	 * @return worker
	 * @throws InstantiationException - if the worker couldn't be loaded
	 * @throws IllegalAccessException - if the worker couldn't be loaded
	 * @throws ClassNotFoundException - if the worker couldn't be loaded
	 */
	private IWebWorker loadWorker(String fqcn)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		@SuppressWarnings("deprecation")
		IWebWorker worker = (IWebWorker) this.getClass().getClassLoader().loadClass(fqcn).newInstance();
		return worker;
	}

	/**
	 * Starts the server by starting the server thread and adding
	 * and creating a thread pool used for serving clients.
	 */
	protected synchronized void start() {

		if (serverThread == null) {
			threadPool = Executors.newFixedThreadPool(workerThreads);
			serverThread = new ServerThread();
			serverThread.setDaemon(true);
			serverThread.start();

			while (true) {
				try {
					serverThread.join();
					break;
				} catch (InterruptedException e) {
				}
			}

		}

	}

	/**
	 * Stops the server.
	 */
	protected synchronized void stop() {
		stopServer = true;
		threadPool.shutdown();
	}

	/**
	 * Main server thread, opens a socket on the specified 
	 * ip address and port, listens for clients, and then serves them
	 * by using multiple threads in thread pool - {@link ClientWorker}.
	 * @author Dorian Ivankovic
	 *
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {

			try (ServerSocket serverSocket = new ServerSocket()) {

				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));

				while (true) {
					if (stopServer)
						break;
					Socket client = serverSocket.accept();
					ClientWorker clientWorker = new ClientWorker(client);
					threadPool.execute(clientWorker);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * A worker implementing all requested work that is required to serve
	 * a server client. All processing and data loading is done here.
	 * The request is read and parsed, the appropriate error message and status
	 * (400,404,...) is returned to the client if something is wrong with the request.
	 * The client can request a regular file in the server root directory, a script in the
	 * scrips directory or a specific worker.
	 * Each client is given a session id, from which he can obtain his cookies
	 * from the server.
	 * @author Dorian Ivankovic
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {
		
		/**
		 * Client-server socket.
		 */
		private Socket csocket;
		
		/**
		 * Input stream.
		 */
		private PushbackInputStream istream;
		
		/**
		 * Output stream.
		 */
		private OutputStream ostream;

		/**
		 * Protocol version.
		 */
		private String version;
		
		/**
		 * Methos - GET, POST,...
		 */
		private String method;
		
		/**
		 * Server's domain.
		 */
		private String host;

		/**
		 * Parameters used in the request, obtained from the request.
		 */
		private Map<String, String> params = new HashMap<String, String>();
		
		/**
		 * Temporary parameters used in the request.
		 */
		private Map<String, String> tempParams = new HashMap<String, String>();
		
		/**
		 * Persistent parameters used, key for obtaining cookie support.
		 */
		private Map<String, String> permParams = new HashMap<String, String>();
	
		/**
		 * Output response cookies.
		 */
		private List<RCCookie> outputCookies = new ArrayList<RequestContext.RCCookie>();
		
		/**
		 * Session id of the client used for getting cookies from the server's
		 * session map.
		 */
		private String SID;

		/**
		 * Context used for writing output data.
		 */
		private RequestContext context;

		/**
		 * Creates a new {@link ClientWorker} using the 
		 * socket connection to the server.
		 * @param csocket - connection to the server
		 */
		public ClientWorker(Socket csocket) {
			this.csocket = csocket;
		}

		@Override
		public void run() {
			try {
				istream = new PushbackInputStream(csocket.getInputStream());
				ostream = csocket.getOutputStream();

				List<String> request = readRequest();
				if (request.isEmpty()) {
					sendError(400, "Bad request");
					return;
				}

				String[] firstLine = request.get(0).split("\\s+");
				if (firstLine.length != 3) {
					sendError(400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				version = firstLine[2].toUpperCase();

				if (!method.equals("GET") || (!version.equals("HTTP/1.0") && !version.equals("HTTP/1.1"))) {
					sendError(400, "Bad request");
					return;
				}

				for (String header : request) {
					if (header.toUpperCase().startsWith("HOST:")) {
						host = header.substring(5).trim();
						if (host.contains(":"))
							host = host.substring(0, host.lastIndexOf(":"));
						break;
					}
				}

				if (host == null)
					host = domainName;

				checkSession(request);

				String path = null;
				if (firstLine[1].contains("?")) {
					String[] pathData = firstLine[1].split("\\?");
					if (pathData.length != 2) {
						sendError(400, "Bad request");
						return;
					}

					path = pathData[0];
					String paramString = pathData[1];
					parseParameters(paramString);
				} else {
					path = firstLine[1];
				}

				if (path.startsWith("/"))
					path = path.substring(1);

				internalDispatchRequest(path, true);

			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}

		/**
		 * The method reads the header of the request and
		 * returns a list of read lines.
		 * @return lines of the request header
		 * @throws IOException - if input data couldn't be read
		 */
		private List<String> readRequest() throws IOException {
			byte[] headerContent = getRequestHeader();

			List<String> headers = new ArrayList<>();
			if (headerContent == null)
				return headers;

			String header = new String(headerContent, RequestContext.HEADER_CHARSET);
			String currentLine = null;

			for (String line : header.split("\n")) {
				if (line.isEmpty())
					break;

				char c = line.charAt(0);

				if (c == ' ' || c == '\t') {
					currentLine += line;
				} else {
					if (currentLine != null) {
						headers.add(currentLine);
					}

					currentLine = line;
				}
			}

			if (!currentLine.isEmpty()) {
				headers.add(currentLine);
			}

			return headers;
		}

		/**
		 * The method reads the request header by reading all the bytes
		 * until it reaches \r\n\r\n or \n\n, which is performed by a 
		 * finite state automaton.
		 * @return header bytes
		 * @throws IOException - if input data from the request couldn't be read
		 */
		private byte[] getRequestHeader() throws IOException {

			int state = 0;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();

			loop: while (true) {
				int b = istream.read();
				if (b == -1)
					return null;
				if (b != '\r')
					bos.write(b);

				switch (state) {
				case 0:
					if (b == '\r')
						state = 1;
					else if (b == '\n')
						state = 4;
					break;
				case 1:
					if (b == '\n')
						state = 2;
					else
						state = 0;
					break;
				case 2:
					if (b == '\r')
						state = 3;
					else
						state = 0;
					break;
				case 3:
					if (b == '\n')
						break loop;
					else
						state = 0;
					break;
				case 4:
					if (b == '\n')
						break loop;
					else
						state = 0;
					break;
				}
			}

			return bos.toByteArray();
		}

		/**
		 * The method extract session id cookies from the header, if it is there,
		 * and constructs a new client session to make cookie services available to
		 * the client.
		 * All parameters that are put in permParams are stored as parameters in servers session map
		 * so that the client can retrieve them as cookies if his session hasn't expired in
		 * which case a new session is created.
		 * Session and parameter construction and retrieval is made to be a multi thread 
		 * safe operation.
		 * @param headers - request headers
		 */
		private void checkSession(List<String> headers) {
			String sidCandidate = null;

			for (String header : headers) {

				if (header.toUpperCase().startsWith("COOKIE: ")) {
					String[] cookies = header.substring(8).split(";");

					for (String cookie : cookies) {
						String[] cookieData = cookie.split("=");

						String cookieName = cookieData[0].trim();
						String cookieValue = cookieData[1].trim();
						if (cookieValue.startsWith("\"") && cookieValue.endsWith("\"")) {
							cookieValue = cookieValue.substring(1, cookieValue.length() - 1);
						}

						if (cookieName.equals("sid")) {
							sidCandidate = cookieValue;
						}
					}
				}

			}

			synchronized (sessions) {
				if (sidCandidate == null) {
					constructSession();
				} else {
					SessionMapEntry sessionEntry = sessions.get(sidCandidate);
					if (sessionEntry == null || !sessionEntry.host.equals(host)) {
						constructSession();
					} else if (sessionEntry.validUntil < System.currentTimeMillis()) {
						sessions.remove(sidCandidate);
						constructSession();

					} else {
						SID = sidCandidate;
						sessionEntry.validUntil = System.currentTimeMillis() + sessionTimeout * 1000;
					}
				}

				SessionMapEntry sessionEntry = sessions.get(SID);
				permParams = sessionEntry.map;
			}
		}

		/**
		 * The method generates a new session id, and constructs a new
		 * session for the client, saving his persistent parameters for later
		 * use (cookies).
		 */
		private void constructSession() {

			SID = generateSID();
			SessionMapEntry sessionEntry = new SessionMapEntry(host, System.currentTimeMillis() + sessionTimeout * 1000,
					new ConcurrentHashMap<>());
			sessions.put(SID, sessionEntry);

			RCCookie cookie = new RCCookie("sid", SID, host, "/", null);
			cookie.setHttpOnly(true);
			outputCookies.add(cookie);
		}

		/**
		 * The method generates a session identifier of size <code>SID_SIZE</code>
		 * that containts only A-Z (uppercase letters)
		 * @return new session id
		 */
		private String generateSID() {
			StringBuilder sidBuilder = new StringBuilder();
			for (int i = 0; i < SID_SIZE; i++) {
				sidBuilder.append((char) (Math.abs(sessionRandom.nextInt()) % ('Z' - 'A' + 1) + 'A'));
			}
			return sidBuilder.toString();
		}

		/**
		 * The method parses the parameters from the request and stores
		 * them into the client's parameters map.
		 * @param paramString - parameters String to parse
		 */
		private void parseParameters(String paramString) {
			String[] par = paramString.split("&");
			for (String param : par) {
				String[] paramMap = param.split("=");
				if (paramMap.length == 2) {
					params.put(paramMap[0], paramMap[1]);
				}
			}
		}

		/**
		 * The method sens an error response with specified error code and message.
		 * @param code - error code of the response
		 * @param message - error message of the response
		 */
		private void sendError(int code, String message) {
			try {
				ostream.write(
						("HTTP/1.1 " + code + " " + message + "\r\n" + "Content-Type: text/plain;charset=UTF-8\r\n"
								+ "Content-Length: 0\r\n" + "Connection: close\r\n" + "\r\n")
										.getBytes(StandardCharsets.US_ASCII));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		/**
		 * The method handles the dispatch request,
		 * if the requested path is not valid, error with status 403 is returned.
		 * Based on the urlPath it determines whether to call a specific worker(ext/className,..),
		 * get a file from root or execute a smscr script.
		 * Private directory calls must not be direct, only dispatched from {@link IWebWorker}'s.
		 * @param urlPath - path of the requested file
		 * @param directCall - whether a call is direct or dispatched via {@link IWebWorker}
		 * @throws Exception - can happen if a {@link IWebWorker} is requested in his {@link #processRequest}
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			if (urlPath.startsWith("private") && directCall) {
				sendError(404, "File not found!");
			}

			Path requestedPath = documentRoot.resolve(urlPath).toAbsolutePath();

			if (urlPath.startsWith("ext/")) {
				try {
					IWebWorker worker = loadWorker("hr.fer.zemris.java.webserver.workers." + urlPath.substring(4));
					if (context == null) {
						context = new RequestContext(ostream, params, permParams, outputCookies);
					}
					worker.processRequest(context);
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException
						| IndexOutOfBoundsException ex) {
					sendError(400, "Bad request");
				}
				return;
			}

			if (!requestedPath.startsWith(documentRoot)) {
				sendError(403, "Forbidden");
				return;
			}

			String mimeType = null;
			boolean smartScript = false;
			String fileName = requestedPath.getFileName().toString();

			if (!Files.isRegularFile(requestedPath)) {

				if (workersMap.get("/" + fileName) != null) {
					if (context == null) {
						context = new RequestContext(ostream, params, permParams, outputCookies, tempParams, this);
					}
					workersMap.get("/" + fileName).processRequest(context);
					return;
				}

				sendError(404, "File not found!");
				return;
			} else {

				String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
				if (extension.equals("smscr")) {
					smartScript = true;
				} else {

					mimeType = mimeTypes.get(extension);

					if (mimeType == null)
						mimeType = "application/octet-stream";
				}
			}

			if (smartScript)
				smartScriptResponse(requestedPath);
			else
				basicResponse(requestedPath, mimeType);
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Generates a basic request of a file in server's root directory.
		 * @param requestedPath - path of the file
		 * @param mimeType - mime type of the file
		 * @throws IOException - if the input stream to the file couldn't be opened
		 */
		private void basicResponse(Path requestedPath, String mimeType) throws IOException {

			if (context == null) {
				context = new RequestContext(ostream, params, permParams, outputCookies);
			}

			context.setMimeType(mimeType);
			context.setStatusCode(200);
			context.setContentLength(requestedPath.toFile().length());

			byte[] buff = new byte[1024];
			try (InputStream fis = Files.newInputStream(requestedPath)) {
				while (true) {
					int r = fis.read(buff);
					if (r < 1)
						break;
					context.write(buff, 0, r);
				}
			}
		}

		/**
		 * The method generates a smartScript response by parsing and executing
		 * the requested file using {@link SmartScriptParser} and {@link SmartScriptEngine}.
		 * @param filePath - path to the requested file
		 * @throws IOException - if the data to the output stream couldn't be written
		 */
		private void smartScriptResponse(Path filePath) throws IOException {
			String docBody;
			docBody = new String(Files.readAllBytes(filePath), StandardCharsets.UTF_8);

			ByteArrayOutputStream outBuffer = new ByteArrayOutputStream();

			if (context == null) {
				context = new RequestContext(outBuffer, params, permParams, outputCookies, tempParams, this);
			}

			try {
				new SmartScriptEngine(new SmartScriptParser(docBody).getDocumentNode(), context).execute();
				ostream.write(outBuffer.toByteArray());
			} catch (SmartScriptEngineException ex) {
				sendError(400, "Bad request");
			}
		}
	}

	/**
	 * This method is called once the program is run.
	 * The method starts the server and sends it it's configuration file name.
	 * @param args - command line arguments, not used
	 */
	public static void main(String[] args) {
		SmartHttpServer server = new SmartHttpServer("server.properties");
		server.start();
	}
}
