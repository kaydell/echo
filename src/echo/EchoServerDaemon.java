package echo;

import java.io.IOException;
import java.net.BindException;

public class EchoServerDaemon {

	static EchoServer runServer(Log log) throws IOException {

		int portNum = EchoUtils.DEFAULT_PORT_NUM;

		// create a server object
		EchoServer server;
		try {
			server = new EchoServer(portNum, log);
		} catch (BindException e) {
			System.err.println("S: BindException: The server could not be started because the port is already in use.  The server may alreay be running or you may need to restart your computer.");
			throw e;
		}

		// start the server object
		log.println("S: about to call server.start().");
		server.start();
		log.println("S: just returned from server.start().");

		return server;
	}

	/**
	 * This main method starts the echo server with no user-interface
	 * (i.e. as a daemon).
	 * 
	 * @param args Command-line arguments.  Either none for the default port number
	 *             or one argument containing the port number for the server to listen on
	 *             for connection requests.
	 * 
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		Log log = new Log(EchoUtils.LOG_PATH);
		runServer(log);
	}

}
