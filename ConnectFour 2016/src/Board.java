
//DAVID JONES TEST CASE FOR CONNECT FOUR 10:00 PM 2/22/16
import java.util.*;

public class Board {

	private int row;
	private int column;
	private Token[][] grid;
	private Player one;
	private Player two;
	private String location;
	private boolean turn;
	private int turnCount;
	private ArrayList<String> information;
	private String element;
	private boolean isSent;
	
	public Board(Token g[][], int r, int c, String name, String name2) {
		turnCount = 0;
		turn = true;
		location = "";
		row = r;
		column = c;
		grid = g;
		one = new Player(name, 'X', 23);
		two = new Player(name2, 'O', 23);
		information = new ArrayList<String>();
		isSent = true;
		element = "START";
	}

	public Token[][] getGrid() {
		// returns the grid
		return grid;
	}

	public void printGrid() {
		// prints the grid
		for (int i = 0; i < row; i++) {
			System.out.print(i + 1);
			for (int j = 0; j < column; j++) {
				if (grid[i][j] == null)
					System.out.print("( )");
				else if (grid[i][j].getType() == "X")
					System.out.print("(X)");
				else if (grid[i][j].getType() == "O")
					System.out.print("(O)");
			}
			System.out.println();
		}
	}
	
	public String getElement(){
		return element;
	}
	
	public boolean getIsSent(){
		return isSent;
	}
	
	public void setIsSent(boolean b){
		isSent = b;
	}
	
	public void setElement(String e){
		element = e;
	}

	public String placeToken(Token t, int column) {
		// places token in column at the bottom, or above the nearest token
		int c = column - 1;

		for (int i = row; i < getRow(); i++) {
			if (grid[i][c] != null) {
				return "Sorry, this space is occupied already!";
			}
		}
		if (c >= getColumn())
			return "Column is off the charts! Sorry!";
		else if (c < getColumn()) {
			if (t.getType().equals("X"))
				for (int i = getRow() - 1; i >= 0; i--) {
					if (grid[0][c] != null) {
						return "Sorry, this column is full already!";
					}
					if (grid[i][c] == null) {
						grid[i][c] = t;
						setLocation("R: " + (i+1) + " C: " + (c+1));
						setTurnCount(getTurnCount()+1);
						setTurn(!getTurn());
						return "Token X entered successfully at row " + (i+1) + " column " + (c+1);
					}
				}
			else if (t.getType().equals("O"))
				for (int i = getRow() - 1; i >= 0; i--) {
					if (grid[0][c] != null) {
						return "Sorry, this column is full already!";
					}
					if (grid[i][c] == null) {
						grid[i][c] = t;
						setLocation("R: " + (i+1) + " C: " + (c+1));
						setTurnCount(getTurnCount()+1);
						setTurn(!getTurn());
						return "Token O entered successfully at row " + (i+1) +" column " + (c+1);
					}
		}
		}
		return "Token entered unsuccessfully";
	}

	public boolean playerOneHasWon() {
		int count = 0;
		boolean hasWon = false;

		// find diagonal matching
		// find diagonal matching
				for (int x = 0; x < getRow() - 3; x++)
					for (int y = 0; y < getColumn() - 3; y++) {
						if (grid[x][y] != null && grid[x][y].getType().equals("X") && grid[x][y] == grid[x + 1][y + 1] && grid[x][y] == grid[x + 2][y + 2]
								&& grid[x][y] == grid[x + 3][y + 3]){
							return true;
						}
					}

				// Find negative diagonal
				for (int x = 0; x < getRow() - 3; x++)
					for (int y = getColumn() - 1; y > 2; y--) {
						if (grid[x][y] != null && grid[x][y].getType().equals("X") && grid[x][y] == grid[x + 1][y - 1] && grid[x][y] == grid[x + 2][y - 2]
								&& grid[x][y] == grid[x + 3][y - 3]){
							return true;
						}
					}

		// Find vertical line matching

		for (int y = 0; y < getColumn(); y++) {
			for (int x = 0; x < getRow(); x++) {
				if (grid[x][y] != null && grid[x][y].getType().equals("X")) {
					count++;
				} else if (grid[x][y] == null || grid[x][y].getType().equals("O")) {
					count = 0;
				}
				if (count == 4) {
					hasWon = true;
				}
			}
			count = 0;

		}
		count = 0;
		// Find horizontal line matching
		for (int x = 0; x < getRow(); x++) {
			for (int y = 0; y < getColumn(); y++) {
				if (grid[x][y] != null && grid[x][y].getType().equals("X")) {
					count++;
				}
				else if (grid[x][y] == null || grid[x][y].getType().equals("O")) {
					count = 0;
				}
				if (count == 4) {
					hasWon = true;
				}
			}
			count = 0;
		}
		count = 0;

		return hasWon;

	}

	public boolean playerTwoHasWon() {
		int count = 0;
		boolean hasWon = false;

		// find diagonal matching
		for (int x = 0; x < getRow() - 3; x++)
			for (int y = 0; y < getColumn() - 3; y++) {
				if (grid[x][y] != null && grid[x][y].getType().equals("O") && grid[x][y] == grid[x + 1][y + 1] && grid[x][y] == grid[x + 2][y + 2]
						&& grid[x][y] == grid[x + 3][y + 3]){
					return true;
				}
			}

		// Find negative diagonal
		for (int x = 0; x < getRow() - 3; x++)
			for (int y = getColumn() - 1; y > 2; y--) {
				if (grid[x][y] != null && grid[x][y].getType().equals("O") && grid[x][y] == grid[x + 1][y - 1] && grid[x][y] == grid[x + 2][y - 2]
						&& grid[x][y] == grid[x + 3][y - 3]){
					return true;
				}
			}

		// find vertical line matching
		for (int y = 0; y < getColumn(); y++) {
			for (int x = 0; x < getRow(); x++) {
				if (grid[x][y] != null && grid[x][y].getType().equals("O"))
					count++;
				else if (grid[x][y] == null || grid[x][y].getType().equals("X")) {
					count = 0;
				}
				if (count == 4) {
					hasWon = true;
				}
			}
			count = 0;
		}
		count = 0;
		// find horizontal line matching.
		for (int x = 0; x < getRow(); x++) {
			for (int y = 0; y < getColumn(); y++) {
				if (grid[x][y] != null && grid[x][y].getType().equals("O"))
					count++;
				if (grid[x][y] == null || grid[x][y].getType().equals("X")) {
					count = 0;
				}
				if (count == 4){
					hasWon = true;
				}
			}
			count = 0;
		}
		count = 0;

		return hasWon;

	}

	public int getRow() {
		// returns row
		return row;
	}
	
	public void setRow(int r) {
		row = r;
	}
	
	public int getColumn() {
		// gets column
		return column;
	}
	
	public void setColumn(int c) {
		column = c;
	}


	public void forceSetToken(int r, int c, char t) {
		// if you need to place a token for testing purposes without using the
		// placeToken method use this method
		Player one = new Player("one", 'X', 23);
		Player two = new Player("two", 'O', 23);
		Token x = new Token("X", one);
		Token o = new Token("O", two);
		if(t == ('X'))
			grid[r][c] = x;
		else if(t == ('O'))
			grid[r][c] = o;
	}
	public Token getTokenAt(int r, int c){
		return grid[r][c];
	}

//	public boolean hasDiagonal() {
//		// Find positive diagonal
//		for (int x = 0; x < getRow() - 3; x++)
//			for (int y = 0; y < getColumn() - 3; y++) {
//				if (grid[x][y] != null && grid[x][y] == grid[x + 1][y + 1] && grid[x][y] == grid[x + 2][y + 2]
//						&& grid[x][y] == grid[x + 3][y + 3]){
//					return true;
//				}
//			}
//
//		// Find negative diagonal
//		for (int x = 0; x < getRow() - 3; x++)
//			for (int y = getColumn() - 1; y > 2; y--) {
//				if (grid[x][y] != null && grid[x][y] == grid[x + 1][y - 1] && grid[x][y] == grid[x + 2][y - 2]
//						&& grid[x][y] == grid[x + 3][y - 3]){
//					return true;
//				}
//			}
//		return false;
//	}

	public void resetGame() {
		// Resets the game
		setTurnCount(0);
		for (int row = 0; row < getRow(); row++) {
			for (int col = 0; col < getColumn(); col++) {
				grid[row][col] = null;
			}
		}
	}
	public String getLocation(){
		return location;
	}
	public void setLocation(String s){
		location = s;
	}

	public boolean isTie() {
		boolean isEmpty = false;
		for (int i = 0; i < getRow(); i++) {
			for (int j = 0; j < getColumn(); j++) {
				if (grid[i][j] == null)
					isEmpty = true;
			}
		}
		// Checks to see if the game is in a tie or not

		if (isEmpty == false && playerTwoHasWon() == false && playerOneHasWon() == false)
			return true;
		if (isEmpty == true && playerTwoHasWon() == false && playerOneHasWon() == false)
			return false;
		return false;
	}

	public void setPlayerOne(Player o) {
		one = o;
	}

	public Player getPlayerOne() {
		return one;
	}

	public void setPlayerTwo(Player t) {
		two = t;
	}

	public Player getPlayerTwo() {
		return two;
	}
	
	public boolean isPlayerOnesTurn() {
		// Picks whether or not Player one goes first.
		Random r = new Random();
		setTurn(r.nextBoolean());
		return getTurn();

	}
	
	public boolean getTurn(){
		return turn;
	}
	
	public void setTurn(boolean t){
		turn  = t;
	}
	
	public int getTurnCount(){
		return turnCount;
	}
	
	public void setTurnCount(int t){
		turnCount  = t;
	}
	
	public void addToArray(String element){
		setIsSent(true);
		information.add(element);
		setElement(element);
	}
	
}