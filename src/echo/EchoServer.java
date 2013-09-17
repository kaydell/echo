package echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

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
	 * use to listen for connection requests from clients.
	 */
	private ServerSocket serverSocket;

	volatile boolean halted = false;
	private static int nextID = 1;
	private Log log;
	
	private ArrayList<ClientServicer> clientServicers = new ArrayList<ClientServicer>();

	/**
	 * This constructor creates an EchoServer object
	 * @param portnum The port that the server will listen on.
	 * @throws IOException 
	 */
	public EchoServer(int portnum, Log log) throws IOException {
		this.log = log;
		log.println("S: Entering the constructor for EchoServer");
		serverSocket = new ServerSocket(portnum);
		log.println("S: Exiting the constructor for EchoServer");
	}

	/**
	 * This method listens for connection requests and when a connection
	 * is made, the serve() method is called to serve the client.
	 */
	public void run() {
		System.out.println("S: Entering Server run()");
		while (!halted) {
			try {
				log.println("S: about to call serverSocket.accept()");
				Socket clientSocket = serverSocket.accept();
				log.println("S: just returned from serverSocket.accept()");
				log.println("S: about to create a ClientServicer");
				ClientServicer clientServicer = new ClientServicer(clientSocket, nextID++);
				clientServicer.start();
				clientServicers.add(clientServicer);
				log.println("S: just returned from creating a ClientServicer");
			} catch (IOException e) {
				System.err.println("S: Exception: " + e);
				e.printStackTrace();
				break;
			}
		}
		log.println("S: Exiting Server run()");
	}

	public void halt() {
		halted = true;
	}

	private class ClientServicer extends Thread {
		
		private int id;
		private BufferedReader in = null;
		private PrintWriter out = null;

		public ClientServicer(Socket clientSocket, int id) throws IOException {
			this.id = id;
			log.println("S: Entering the constructor for ClientServicer.");
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new PrintWriter(clientSocket.getOutputStream(), EchoUtils.AUTO_FLUSH);
			log.println("S: Exiting the constructor for ClientServicer.");
		}
		
		private void sendToClient(String message) {
			log.println("Sending the message: " + message + " to the client");
			out.println(message);
			log.println("Sent the message: " + message + " to the client");
		}

		@Override
		public void run() {
			setName("ClientService-" + id);
			log.println("S: Entering the method serve()");
			try {
				log.println("S: About to send welcome message to the client");
				sendToClient("Connection established!");
				log.println("S: Just sent welcome message to the client");
				while (!halted) {
					log.println("S: about to read message from the client");
					String message = in.readLine();
					log.println("S: just read message from the client: " + message);
					if (message == null) {
						log.println("S: read a null string, breaking of of loop in the serve() method" + message);
						break;
					} else {
						log.println("S: About to send message back to the client: " + message);
						sendToClient(message);
						log.println("S: Just returned from sending message back to the client:" + message);
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
			log.println("S: Exiting the run() method for ClientServicer");
		}
		
	}
	
}