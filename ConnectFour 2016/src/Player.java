
public class Player {
	private String name;
	private char token;
	private int tokens;
	
	public Player(String n, char t, int t2){
		name = n;
		token = t;
		tokens = t2;
	}
	
	public void setName(String n){
		name = n;
	}
	
	public String getName(){
		return name;
	}
	
	public void setToken(char t){
		token = t;
	}
	
	public char getToken(){
		return token;
	}
	
	public int getTokens(){
		return tokens;
	}
	public void setTokens(int t){
		tokens = t;	
	}

}
