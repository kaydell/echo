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

	public static void main(String[] args) {

		// debugging output
		System.out.println("EchoClientConsole: entering main()");
		
		// set the client object to null so that in the finally part of the try-catch-finally, that
		// the code will know whether to call client.close() or not, depending upon whether clien == null
		// or not.
		Scanner console = new Scanner(System.in);
		EchoClient client = null;
		boolean bye = false;
		try {

			// create a client object
			client = new EchoClient();

			// repeatedly read and write to the server
			// note that this is a new connection and we first read from the server to
			// get the welcome message from the server.
			do
			{
				// read a line from the server using the client object
				String messageIn = client.receiveMessage();

				// print the message received from the server
				System.out.println(messageIn);

				// exit if the message from the server is BYE this should only be when the
				// server is echoing a message that the client sent that is BYE
				bye = messageIn.trim().equalsIgnoreCase(EchoUtils.BYE);
				if (bye) {
					break;
				}

				// read a line from the console
				System.out.println("Enter a line of text to be echoed by the Echo Server. enter 'Bye' to quit: ");
				String messageOut = console.nextLine();

				// write the line entered by the user to the server
				client.sendMessage(messageOut);

				// if the server sent any data, then write it to the console
				if (messageOut == null) {
					System.err.println("Error: the connection with the server seems to have been lost.");
					break;
				}
				
			} while (true);

		// catch any IOExceptions (and subclasses of IOException)
		} catch (IOException e) {
			// print the exception's message here
			System.err.println("Error while communicating with the server: " + e);
			e.printStackTrace();
			
		// close the client stuff (such as its socket and whatever else)
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					System.err.println("Error while trying to close client object: " + e);
					e.printStackTrace();
				}
			}
		}
		
		// let the user know that the program is exiting
		System.out.println("EchoClientConsole: exiting main()");
	}

}
