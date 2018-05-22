
import java.io.*;
import java.net.*;
public class ClientMain
{
  public static void main(String[] args) throws Exception
  {
	  Token[][] array = new Token[7][8];
	   Board b = new Board(array, 7, 8, "one", "two");
	   Clock c = new Clock();
	   GUIMulti2 gui = new GUIMulti2(b, c, false);
     Socket sock = new Socket("172.19.152.54", 9114);
     OutputStream out = sock.getOutputStream(); 
     PrintWriter in = new PrintWriter(out, true);
     InputStream stream = sock.getInputStream();
     BufferedReader receive = new BufferedReader(new InputStreamReader(stream));
     String message, message2 = "", send; 
     AskForPlayer one = new AskForPlayer("one");
 	AskForPlayer two = new AskForPlayer("two");
     c.startClock();
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
        		int firstDigit = Integer.parseInt("" + firstLetterChar);
        		gui.enterInput(firstDigit);
        	}
        }         
      }               
    }                    
}   