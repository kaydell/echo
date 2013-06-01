package debug;

import java.io.IOException;

import us.kaydell.exercises.echo.EchoClient;
import us.kaydell.exercises.echo.EchoServer;

/**
 * This class will test the classes EchoClient and EchoServer
 * while running on the same computer in the same IDE so that
 * both can be debugged without having two computers running
 * two IDEs.
 * 
 * @author kaydell
 *
 */
public class EchoClientServerDebug {
	
	public static void main(String[] args) throws IOException {

		// setup connection parameters
		String localhost = null;
		int portNum = 9000;
		
		// start the server
		EchoServer server = new EchoServer(portNum);
		server.start();
		
		// create the client
		EchoClient client = new EchoClient(localhost,portNum);
		
		// have the client send the message "BYE" this should cause
		// the server to echo the message and quit.
		String message = "Bye";
		client.sendMessage("Bye");
		
		String reply = client.receiveMessage();
		if (!message.equals(reply)) {
			System.err.println("Error: sent the message: '" + message + "' and received the reply: '" + reply + "'");
		}

	}

}
