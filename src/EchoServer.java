import java.io.*;
import java.net.*;

public class EchoServer
{
	public EchoServer(int portnum)
	{
		try
		{
			// create a ServerSocket (to make connections to clients)
			server = new ServerSocket(portnum);
		}
		catch (Exception err)
		{
			System.out.println(err);
		}
	}

	public void serve()
	{
		try
		{
			// do loop as long as the server is running
			while (true)
			{
				// accept connections from clients. This should really be on its own Thread so we can have more than one client
				Socket client = server.accept();
				
				// create a BufferedReader from the Socket's InputStream
				BufferedReader r = new BufferedReader(new InputStreamReader(client.getInputStream()));
				
				// create a PrintWriter from the Socket's OutputStream
				PrintWriter w = new PrintWriter(client.getOutputStream(), true);
				w.println("Welcome to the Java EchoServer. Type 'bye' to close.");
				String line;
				do
				{
					// read a line from the client
					line = r.readLine();
					
					// if something was read then echo it back to the client
					if ( line != null )
						w.println("Got: "+ line);
				}
				while ( !line.trim().equals("bye") );
				
				// close the Socket. (This should really be done in a try-finally
				client.close();
			}
		}
		catch (Exception err)
		{
			System.err.println(err);
		}
	}

	public static void main(String[] args)
	{
		EchoServer s = new EchoServer(9999);
		s.serve();
	}

	private ServerSocket server;
}