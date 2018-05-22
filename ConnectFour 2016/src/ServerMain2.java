
import java.io.*;
import java.net.*;
import java.util.Timer;;
public class ServerMain2
{
  public static void main(String[] args) throws Exception
  {
	  ServerSocket ssock = new ServerSocket(9114);
      Socket sock = ssock.accept( );  
	  OutputStream out = sock.getOutputStream(); 
      PrintWriter in = new PrintWriter(out, true);    
      Token[][] array = new Token[7][8];
	   Board b = new Board(array, 7, 8, "one", "two");
	   Clock c = new Clock();
	   GUIMulti gui = new GUIMulti(b, c, true);
      InputStream stream = sock.getInputStream();
      BufferedReader receive = new BufferedReader(new InputStreamReader(stream));
      String message, send, message2 = "";     
      c.startClock();
  	AskForPlayer one = new AskForPlayer("one");
  	AskForPlayer two = new AskForPlayer("two");
      while(one.getIsSet() && two.getIsSet()){
			b.getPlayerOne().setName(one.getName());
			b.getPlayerTwo().setName(two.getName());
			if(one.getIsSet() && two.getIsSet())
				gui.startGame();
				break;
		}
      while(true)
      {
    	  send = "" + gui.getToSend(); 
          in.println(send);             
          in.flush();
        if((message = receive.readLine()) != null)  
        {
        	if(!message2.equals(message)){
        		message2 = message;
        		char firstLetterChar = message2.charAt(0);
        		int firstDigit = Integer.parseInt(""+firstLetterChar);
        		gui.enterInput(firstDigit);
        	}
        }  
      }               
    }                    
}         