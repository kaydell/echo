/**
 * It's always a good idea to use a package other than the default package.
 */
package echo;

/**
 * The unit implements an "echo client" with no user interface.  This is purely
 * network programming.  This is the "model" of the "model-view-controller (MVC)" 
 * design pattern.
 */
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

/**
 * This class contains the programming the implements the model of an
 * "Echo Client".  The echo client sends messages to the echo server and
 * the echo server just sends them right back.
 * 
 * This is an exercise in sockets and client/server software development.
 * 
 * @author kaydell
 *
 */
public class EchoClient implements Closeable {

	/**
	 * This field is a reference to a socket object from the Java API class called "Socket"
	 */
	private Socket socket = null;
	
	/**
	 * This field allows us to read data from the server using the method calld "readLine()"
	 * it also gives us the benefit of buffering the input so that the program can run faster.
	 * The speed doesn't really matter in this program, but buffering can help for programs
	 * that receive large amounts of data.
	 */
	private BufferedReader in = null;
	
	/**
	 * This field is an object of the type "PrintWriter" which allows the client to easily
	 * send data to the server
	 */
	private PrintWriter out = null;

	private Log log = null;
			
	/**
	 * This constructor accepts an ipAddress and the port number of the server 
	 * to specify which server to connect to.
	 * 
	 * @param ipAddress The ip address of the server or null, meaning localhost
	 * @param portNum The port number of the server to try to connect to.
	 * @throws IOException Thrown for any kind of IO error.
	 * 
	 */
	public EchoClient(String ipAddress, int portNum, Log log) throws IOException {
		
		this.log = log;

		log.println("C: Entering constructor");
		log.println("C: IP Address: " + ipAddress + " portNum: " + portNum);
		
		try {
			// create a Socket and connect to the server which is running on the same computer on port number 9999
			socket = new Socket(ipAddress, portNum);

			// use the input stream of the Socket and use it to create a BufferedReader
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// use the OutputStream of the Socket and use it to create a PrintWriter
			out = new PrintWriter(socket.getOutputStream(), EchoUtils.AUTO_FLUSH);

		} catch (ConnectException e) {
			System.err.println("C: ConnectionException: could not connect to the server: " + e);
			throw e;
		}

		log.println("C: Exiting constructor");
	}

	/**
	 * This constructor uses the default values for ip address and port number
	 * to try to connect with a server.
	 * 
	 * @throws IOException
	 */
	public EchoClient(Log log) throws IOException {
		this(EchoUtils.LOCAL_HOST, EchoUtils.DEFAULT_PORT_NUM, log);
	}
	
	/**
	 * This method returns whether the client is still connected to the
	 * server or not.
	 * 
	 * @return Returns whether this client is still connected to the server or not.
	 */
	public boolean isConnected() {
		log.println("C: Entering isConnected()");
		boolean isConnected = (socket != null && socket.isConnected());
		log.println("C: Exiting isConnected() isConnected: " + isConnected);
		return isConnected;
	}

	/**
	 *  This method reads a line from the echo server
	 * @return Returns the String read from the echo server
	 * @throws IOException Thrown when there is an error reading from the echo server
	 */
	public String receiveMessage() throws IOException {
		log.println("C: Entering receiveMessage()");
		String message = in.readLine();
		log.println("C: Exiting receiveMessage() message: " + message);
		return message;
	}

	/**
	 * This method writes a message to the echo server.
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		log.println("C: Entering sendMessage(), message: " + message);
		out.println(message);
		log.println("C: Exiting receiveMessage()");
	}

	/**
	 * This method is called to close all IO objects that this echo client object
	 * has open. TODO: close "in" and "out".
	 */
	@Override
	public void close() throws IOException {
		log.println("C: Entering close()");
		if (socket != null && !socket.isClosed()) {
			socket.close();
		}
		log.println("C: Exiting close()");
	}

}