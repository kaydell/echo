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
public class EchoServer extends Thread
{	
	/**
	 * This field references a server socket object which the server will
	 * use to listen for connection requests from clicents.
	 */
	private ServerSocket serverSocket;

	/**
	 * This constructor creates an EchoServer object
	 * @param portnum The port that the server will listen on.
	 * @throws IOException 
	 */
	public EchoServer(int portnum) throws IOException {
		System.out.println("S: Entering the constructor for EchoServer");
		serverSocket = new ServerSocket(portnum);
		System.out.println("S: Exiting the constructor for EchoServer");
	}

	private class ClientServicer extends Thread {

		private BufferedReader in = null;
		private PrintWriter out = null;

		public ClientServicer(Socket clientSocket) throws IOException {
			System.out.println("S: Entering the constructor for ClientServicer.");
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream());
			System.out.println("S: Exiting the constructor for ClientServicer.");
		}

		@Override
		public void run() {

			System.out.println("S: Entering the method serve()");
			try {
				out.println("Connection established!");
				while (true) {
					System.out.println("S: about to read message from the client");
					String message = in.readLine();
					System.out.println("S: just read message from the client" + message);
					if (message == null) {
						System.out.println("S: read a null string, breaking of of loop in the serve() method" + message);
						break;
					} else {
						System.out.println("S: just read message from the client" + message);
						out.println(message);
						System.out.println("S: just read message from the client" + message);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			System.out.println("S: Entering the method serve()");
		}

	}

	/**
	 * This method listens for connection requests and when a connection
	 * is made, the serve() method is called to serve the client.
	 */
	public void run() {
		System.out.println("S: Entering Server run()");
		while (true) {
			try {
				System.out.println("S: about to call serverSocket.accept()");
				Socket clientSocket = serverSocket.accept();
				System.out.println("S: just returned from serverSocket.accept()");
				System.out.println("S: about to call serve()");
				new ClientServicer(clientSocket).start();
				System.out.println("S: just returned from serve()");
			} catch (IOException e) {
				System.err.println("S: Exception: " + e);
				e.printStackTrace();
				break;
			}
		}
		System.out.println("S: Exiting Server run()");

	}

}