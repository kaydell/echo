
import java.io.*;
import java.net.*;

public class EchoClient
{
	public static void main(String[] args)
	{
		try
		{
			// create a Socket and connect to the server which is running on the same computer on port number 9999
			Socket s = new Socket("127.0.0.1", 9999);
			
			// use the input stream of the Socket and use it to create a BufferedReader
			BufferedReader r = new BufferedReader(new InputStreamReader(s.getInputStream()));
			
			// use the OutputStream of the Socket and use it to create a PrintWriter
			PrintWriter w = new PrintWriter(s.getOutputStream(), true);
			
			// use the standard input to create a BufferedReader object
			BufferedReader con = new BufferedReader(new InputStreamReader(System.in));
			String line;
			do
			{
				// read a line from the Socket
				line = r.readLine();
				
				// if the server sent any data, then write it to the console
				if ( line != null )
					System.out.println(line);
				
				// read a line from the user
				line = con.readLine();
				
				// write the line entered by the user to the server
				w.println(line);
			}
			// if the user entered "bye" then break out of this loop
			while ( !line.trim().equals("bye") );
		}
		catch (Exception err)
		{
			// if there were any exceptions then print the message here
			System.err.println(err);
		}
	}
}