import java.util.ArrayList;

public class Game {
	private Token[][] array = new Token[7][8];
	private Board b = new Board(array, 7, 8, "one", "two");
	private Clock c = new Clock();
	private GUI gui = new GUI(b, c);
	private AskForPlayer one = new AskForPlayer("one");
	private AskForPlayer two = new AskForPlayer("two");
			
	public static void main(String[] args) {
		Game g = new Game();
		
		g.b.isPlayerOnesTurn();
		g.c.startClock();
		
		
		while(g.one.getIsSet() && g.two.getIsSet()){
			g.b.getPlayerOne().setName(g.one.getName());
			g.b.getPlayerTwo().setName(g.two.getName());
			if(g.one.getIsSet() && g.two.getIsSet())
				g.gui.startGame();
				break;
		}
	}
	
}
	