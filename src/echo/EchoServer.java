package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class contains the programming the implements the model of an
 * "Echo Server".  The echo server receives messages and just sends
 * the same message right back to the client that sent the message
 * This is an exercise in sockets and client/server software development.
 * 
 * This server class has no user interface of its own.  It is a daemon.
 * 
 * @author kaydell
 *
 */
public class EchoServer
{
	/**
	 * The socket that the server listens for requests on.
	 */
	private ServerSocket serverSocket;

	/**
	 * This constructor creates an EchoServer object
	 * 
	 * @param portnum The port that the server will listen on.
	 * @throws IOException 
	 */
	public EchoServer(int portnum) throws IOException {
		// create a ServerSocket (to make connections to clients)
		// TODO: throw an IllegalArgumentException for commonly used ports such as 80??
		serverSocket = new ServerSocket(portnum);
	}

	/**
	 * This method starts the server listening for connection
	 * requests from clients.
	 */
	public void start() {
		Socket socket = null;
		try {
			// do loop as long as the server is running
			while (true) {
				// accept connections from clients. This should really be on its own Thread so we can have more than one client
				socket = serverSocket.accept();
				
				// create a BufferedReader from the Socket's InputStream
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
				// create a PrintWriter from the Socket's OutputStream
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println("Connection made to EchoServer");
				String line;
				do {
					// read a line from the client
					line = in.readLine();
					
					// if line is null, then the connection was lost
					if (line == null) {
						System.err.println("Echo Server: The connections seems to have been lost");
						break;
					} else {
						out.println(line);
					}
				} while (!line.trim().equalsIgnoreCase(EchoUtils.BYE));
			}
		} catch (IOException e) {
			System.err.println("IOException in echo server while processing requests");
			e.printStackTrace();
		} finally {
			// close the server socket
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					System.err.println("IOException while trying to close server socket");
					e.printStackTrace();
				}
			}
			// close the client socket
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					System.err.println("IOException while trying to close client socket");
					e.printStackTrace();
				}
			}
		}
	}

}