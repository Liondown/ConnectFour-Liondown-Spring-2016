
public class Token {
	private String type;
	private Player player;
	
	public Token(String t, Player p){
		type = t;
		player = p;
	}
	
	public void setType(String t){
		type = t;
	}
	
	public String getType(){
		return type;
	}
	
	public void setPlayer(Player p){
		player = p;
	}
	
	public Player getPlayer(){
		return player;
	}

}
