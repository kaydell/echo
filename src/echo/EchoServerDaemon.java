package echo;

import java.io.IOException;
import java.net.BindException;

public class EchoServerDaemon {

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
	public static void main(String[] args) throws IOException
	{
		// use the command-line argument for a portNum if there is one\
		int portNum;
		if (args.length >= 1) {
			portNum = Integer.parseInt(args[0]);
			// otherwise, use the DEFAULT_PORT_NUM
		} else {
			portNum = EchoUtils.DEFAULT_PORT_NUM;
		}

		// create a server object
		EchoServer server;
		try {
			server = new EchoServer(portNum);
		} catch (BindException e) {
			System.err.println("S: BindException: The server could not be started because the port is already in use.  The server may alreay be running or you may need to restart your computer.");
			return;
		}
		
		// start the server object
		System.out.println("S: about to call server.start().");
		server.start();
		System.out.println("S: just returned from server.start().");

	}

}
