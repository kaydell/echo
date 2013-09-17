package echo;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class implements a console interface using an object of the class
 * EchoClient.
 * 
 * @author kaydell
 *
 */
public class EchoClientConsole {

	/**
	 * This method creates an object of the class EchoClient and connects to an echo server.
	 * 
	 * @param args Not used.
	 */
	public static void main(String[] args) {

		// debugging output
		System.out.println("C: EchoClientConsole: entering main()");
		
		// set the client object to null so that in the finally part of the try-catch-finally, that
		// the code will know whether to call client.close() or not, depending upon whether client == null
		// or not.
		System.out.println("C: setting up local variables");
		Scanner console = new Scanner(System.in);
		EchoClient client = null;
		boolean bye = false;
		try {

			// create a client object
			System.out.println("C: about to create EchoClient object");
			client = new EchoClient();

			// repeatedly read and write to the server
			// note that this is a new connection and we first read from the server to
			// get the welcome message from the server.
			do
			{
				// read a line from the server using the client object
				System.out.println("C: about to call receiveMessage()");
				String messageIn = client.receiveMessage();
				System.out.println("C: just returned from receiveMessage() messageIn: " + messageIn);

				// exit if the message from the server is BYE this should only be when the
				// server is echoing a message that the client sent that is BYE
				bye = messageIn.trim().equalsIgnoreCase(EchoUtils.BYE);
				if (bye) {
					System.out.println("C: BYE was received from the server");
					break;
				}

				// read a line from the console
				System.out.println("C: Enter a line of text to be echoed by the Echo Server. enter 'Bye' to quit: ");
				System.out.println("C: >>>>>>>");
				String messageOut = console.nextLine();
				System.out.println("C: The user typed in: " + messageOut);

				// write the line entered by the user to the server
				System.out.println("C: sending the message to the server");
				client.sendMessage(messageOut);
				System.out.println("C: The message was sent");

				// if the server sent any data, then write it to the console
				if (messageOut == null) {
					System.out.flush();
					System.err.println("C: Error: the connection with the server seems to have been lost.");
					break;
				}
				
				System.out.println("C: at the bottom of the loop in main()");
			} while (true);

		// catch any IOExceptions (and subclasses of IOException)
		} catch (IOException e) {
			// print the exception's message here
			System.out.flush();
			System.err.println("C: Error while communicating with the server: " + e);
			e.printStackTrace();
			
		// close the client stuff (such as its socket and whatever else)
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					System.out.flush();
					System.err.println("C: Error while trying to close client object: " + e);
					e.printStackTrace();
				}
			}
		}
		
		// let the user know that the program is exiting
		System.out.println("C: exiting main()");
	}

}
