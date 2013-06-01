package us.kaydell.exercises.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This class implements a console interface using an object of the class
 * EchoClient.
 * 
 * @author kaydell
 *
 */
public class EchoClientConsole {

	public static void main(String[] args) {

		EchoClient client = null;
		try {

			// create a client object
			client = new EchoClient();

			// use the standard input to create a BufferedReader object
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String line;
			do
			{
				// read a line from the Socket
				line = in.readLine();

				// if the server sent any data, then write it to the console
				if ( line != null )
					System.out.println(line);

				// read a line from the user
				line = in.readLine();

				// write the line entered by the user to the server
				client.sendMessage(line);
			}
			// if the user entered "bye" then break out of this loop
			while (!line.trim().equals("bye"));

		} catch (IOException e) {
			// if there were any exceptions then print the message here
			System.err.println("Error while communicating with the server.");
			e.printStackTrace();
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					System.err.println("Error while trying to close client object.");
					e.printStackTrace();
				}
			}
		}
	}

}
