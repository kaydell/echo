/* TheHackerLounge (6-9-2013)
 * This class will add threads to the echo project started by Kaydell.
 */
//@ author James

package echo;

import java.net.*;
import java.io.*;

public class EchoThreads implements Runnable{
  
  private Socket socket = null;
  
  public EchoThreads(Socket socket){
    
    this.socket = socket;
  }
  
  public void run(){
    try{
      String text;
      BufferedReader input = null;
      PrintWriter output = null;
      
      input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      output = new PrintWriter(socket.getOutputStream(), true);
      output.println("Connection established!");
      
      while(true){
        text = input.readLine();
        output.println(text);
      }
    }
    
    catch (IOException e) {
      System.err.println("IOException in echo server while processing requests");
      e.printStackTrace();
    }
    
  }
}