package echo;

import java.io.IOException;
import java.util.Scanner;

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

		System.out.println("D: Entering EchoClientServerDebug");

		Scanner console = new Scanner(System.in);

		// setup connection parameters
		int portNum = EchoUtils.DEFAULT_PORT_NUM;

		// start the server
		System.out.println("D: About to create an EchoServer on portNum: " + portNum);
		EchoServer server = new EchoServer(portNum);
		System.out.println("D: About to call server.start()");
		server.start();
		System.out.println("D: Just returned from server.start()");

		// create the client
		System.out.println("D: About to create EchoClient object");
		EchoClient client = new EchoClient(EchoUtils.LOCAL_HOST, portNum);
		System.out.println("D: Just returned from creating EchoClient object");

		do {
			// have the client send the message BYE this should cause
			// the server to echo the message and quit.
			System.out.println(">>> Please enter a message >>>. ");
			String message = console.nextLine();
			System.out.println("D: The user entered: " + message);
			client.sendMessage(message);
			System.out.println("D: sent the message to the server");

			// test the reply to make sure that it was the same message that was sent.
			System.out.println("D: About to call receiveMessage()");
			String reply = client.receiveMessage();
			System.out.println("D: Received the message '" + reply + " from the server.");

			if (reply.equals(EchoUtils.BYE)) {
				System.out.println("Success: received the same message that was sent.");
				break;
			} else {
				System.err.println("Error: sent the message: '" + message + "' and received the reply: '" + reply + "'");
			}
		} while(true);

		System.out.println("D: Exiting EchoClientServerDebug");

	}

}