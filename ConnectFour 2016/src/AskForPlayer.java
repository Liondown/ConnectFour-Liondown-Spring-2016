import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class AskForPlayer {

        final JFrame parent = new JFrame();
        private String name;
        private boolean isSet = false;
        
        public AskForPlayer(String player){
        	setName(JOptionPane.showInputDialog(parent,
                    "Please enter Player " + player +"'s name", null));
            }
        
        public String getName(){
        	return name;
        }
        
        public void setName(String n){
        	name = n;
        	setIsSet(true);
        }
        
        public boolean getIsSet(){
        	return isSet;
        }
        
        public void setIsSet(boolean b){
        	isSet = b;
        }
}