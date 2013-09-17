package echo;

import java.io.IOException;

/**
 * This class will test the classes EchoClient and EchoServer
 * while running on the same computer in the same IDE so that
 * both can be debugged without having two computers running
 * two IDEs, and without having two separate processes running
 * under Eclipse which makes it harder to interpret the debugging
 * output and harder to run the debugger on both client and server.
 *
 * @author kaydell
 *
 */
public class EchoClientServerDebug {

	public static void main(String[] args) throws IOException {
		Log log = new Log(EchoUtils.LOG_PATH);
		System.out.println("D: Entering EchoClientServerDebug");
		System.out.println("D: about to call EchoServerDaemon.main()");
		EchoServer server = EchoServerDaemon.runServer(log);
		System.out.println("D: about to call EchoClientConsole.main()");
		EchoClientConsole.main(args);
		System.out.println("D: just returned from EchoClientConsole.main()");
		server.halt(); // have the server exit its threads too, so the app can exit
		System.out.println("D: Exiting EchoClientServerDebug");
	}

}
