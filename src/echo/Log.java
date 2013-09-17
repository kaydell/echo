package echo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Log {
	
	public final static boolean APPEND = true;
	private PrintWriter out;
	
	public Log(String pathName) throws IOException {
		out = new PrintWriter(new FileWriter(new File(pathName), APPEND), EchoUtils.AUTO_FLUSH);
	}
	
	public void println(String message) {
		out.println(message);
	}

}
