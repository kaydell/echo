/**
 * It's always a good idea to use a package other than the default package.
 */
package echo;

/**
 * I like to specify each class imported rather than using
 * a wildcard. Then, you're more aware of what you're actually
 * using from other java packages
 * (kaydell)
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
	 * private instance variables
	 */
	private Socket socket = null;
	private BufferedReader in = null;
	private PrintWriter out = null;

	/**
	 * This constructor accepts an ipAddress and the port number of the server 
	 * to try to connect o.
	 * 
	 * @param ipAddress The ip address of the server or null, meaning localhost
	 * @param portNum The port number of the server to try to connect to.
	 * @throws IOException Thrown for any kind of IO error.
	 * 
	 */
	public EchoClient(String ipAddress, int portNum) throws IOException {

		try {
			// create a Socket and connect to the server which is running on the same computer on port number 9999
			socket = new Socket(ipAddress, portNum);

			// use the input stream of the Socket and use it to create a BufferedReader
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// use the OutputStream of the Socket and use it to create a PrintWriter
			final boolean AUTO_FLUSH = true;
			out = new PrintWriter(socket.getOutputStream(), AUTO_FLUSH);

		} catch (ConnectException e) {
			System.err.println("ConnectionException: could not connect to the server: " + e);
		}

	}

	/**
	 * This constructor uses the default values for ip address and port number
	 * to try to connect with a server.
	 * 
	 * @throws IOException
	 */
	public EchoClient() throws IOException {
		this(EchoUtils.LOCAL_HOST, EchoUtils.DEFAULT_PORT_NUM);
	}
	
	public boolean isConnected() {
		return socket != null && socket.isConnected();
	}

	/**
	 *  This method reads a line from the echo server
	 *  
	 * @return Returns the String read from the echo server
	 * 
	 * @throws IOException Thrown when there is an error reading from the echo server
	 */
	public String receiveMessage() throws IOException {
		return in.readLine();
	}

	/**
	 * This method writes a message to the echo server.
	 * 
	 * @param message
	 */
	public void sendMessage(String message) {
		out.println(message);
	}

	/**
	 * This method is called to close all IO objects that this echo client object
	 * has open. TODO: close "in" and "out".
	 */
	@Override
	public void close() throws IOException {
		if (socket != null && !socket.isClosed()) {
			socket.close();
		}
	}

}