import java.awt.Color;
import java.awt.Container;

import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.Border;
import javax.swing.JScrollPane;

import java.util.TimerTask;
import java.util.Timer;
import java.util.ArrayList;
import java.util.Random;

public class GUI extends JFrame {
	
	private Random r;
	private int gameNumber;
	private Board b;
	private Database d;
	private JTextArea information;	
	private JFrame frame;
	private JTextArea time;
	private Timer timer;
	private JButton textbox, forfeit;
	private JButton restart;
	private JPanel panel1;
	private JScrollPane sp;
	private JPanel panel2;
	private Clock clock;
	private UpdateClock update;
	private ArrayList<String> informationList;

	private String element;

	private boolean victory;

	private int row, col, timeCounter, indexCounter;

	private Player one, two;
	private Token x, o;

	private JToggleButton[][] button = new JToggleButton[7][8];
	private GridLayout myGrid = new GridLayout(7, 8);

	private final ImageIcon c0 = new ImageIcon("source/empty.jpg");
	private final ImageIcon c1 = new ImageIcon("source/token1.jpg");
	private final ImageIcon c2 = new ImageIcon("source/token2.jpg");

	public GUI(Board board, Clock clock2) {
		d = new Database();
		d.start();
		informationList = new ArrayList<String>();
		r = new Random();
		element = "";
		gameNumber = r.nextInt(20000 - 10000 + 1);
		timeCounter = 0;
		indexCounter = 0;
		timer = new Timer();
		time = new JTextArea("");
		clock = clock2;
		clock.resetClock();
		update = new UpdateClock();
		timer.schedule(update, 0, 1000);
		victory = false;
		update.run();
		one = new Player("one", 'X', 23);
		x = new Token("X", one);
		two = new Player("two", 'O', 23);
		o = new Token("O", two);
		b = board;
		information = new JTextArea("Information" + '\t' + '\t' + '\t');
		sp = new JScrollPane(information);
		sp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		sp.setPreferredSize(new Dimension(300, 550));
		textbox = new JButton();
		forfeit = new JButton("Forfeit");
		restart = new JButton("Restart");
		restart.addActionListener(new Restart());
		forfeit.addActionListener(new Forfeit());
		// input = new JToggleButton("Enter");
		setLayout(new BorderLayout());
		textbox.setBorder(BorderFactory.createLineBorder(Color.black));
		information.setBorder(BorderFactory.createLineBorder(Color.black));
		time.setBackground(Color.WHITE);
		time.setBorder(BorderFactory.createLineBorder(Color.black));
		restart.setBackground(Color.WHITE);
		forfeit.setBackground(Color.WHITE);
		time.setForeground(Color.BLACK);
		Container c = getContentPane();
		c.add(textbox, BorderLayout.NORTH);
		c.add(time, BorderLayout.SOUTH);
		c.add(forfeit, BorderLayout.EAST);
		c.add(restart, BorderLayout.WEST);
		// c.add(input, BorderLayout.CENTER);
		// input.addActionListener(new ListenerButton());
		Border emptyBorder = BorderFactory.createEmptyBorder();
		frame = new JFrame("Connect Four David Jones Assignment 2");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel2 = new JPanel();
		panel2.add(sp, BorderLayout.CENTER);
		panel1 = new JPanel();
		panel1.setLayout(myGrid);
		for (row = 0; row < 7; row++) {
			for (col = 0; col < 8; col++) {
				button[row][col] = new JToggleButton(c0);
				button[row][col].setPreferredSize(new Dimension(95, 95));
				button[row][col].addActionListener(new Listener(b.getTurn()));
				panel1.add(button[row][col]);
				button[row][col].setBorder(emptyBorder);
			}
		}
		frame.add(c, BorderLayout.SOUTH);
		frame.add(panel2, BorderLayout.EAST);
		frame.add(panel1, BorderLayout.CENTER);
		// frame.add(panel2);
		frame.setPreferredSize(new Dimension(1000, 700));
		frame.pack();
		frame.setVisible(true);
	}
	public void startGame(){
		if (b.getTurn())
			setTextbox("Player " + b.getPlayerOne().getName() + " turn, place token");
		else
			setTextbox("Player " + b.getPlayerTwo().getName() + " turn, place token");
		
	}

	public Board getBoard() {
		return b;
	}
	
	public String getElement() {
		return element;
	}
	
	public int getIndexCounter() {
		return indexCounter;
	}
	
	public int getGameNumber() {
		return gameNumber;
	}

	public boolean getVictory() {
		return victory;
	}
	
	public ArrayList<String> getInformationList() {
		return informationList;
	}

	public void setVictory(boolean b) {
		victory = b;
	}

	public void setBoard(Board board) {
		b = board;
	}

	public void matchGrid() {
		Board b = getBoard();
		Token[][] grid = b.getGrid();
		JToggleButton[][] g = getGrid();
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 8; j++) {
				if (grid[i][j] == null)
					g[i][j].setIcon(c0);
				else if (grid[i][j].getType() == "X")
					g[i][j].setIcon(c1);
				else if (grid[i][j].getType() == "O")
					g[i][j].setIcon(c2);
			}
		}
	}

	public void setInformation(String s) {
		information.setText(information.getText().concat("\n" + s));
		informationList.add(s);
		
	}

	public String getInformation() {
		return information.getText();
	}

	public void resetGrid() {
		b.resetGame();
		for (row = 0; row < 7; row++) {
			for (col = 0; col < 8; col++) {
				button[row][col].setIcon(c0);
			}
		}
		setVictory(false);
	}

	public void enterTurn(int column) {
		boolean stopLoop = false;
		if (column != 0) {
			while (!stopLoop) {
				if (b.getTurn()) {
					String message = b.placeToken(x, column);
					if (message.equals("Sorry, this column is full already!")
							|| message.equals("Column is off the charts! Sorry!")) {
						stopLoop = true;
						setTextbox(message);
						break;
					}
					setInformation(b.getLocation().concat(" . Player " + b.getPlayerOne().getName() + " turn, Token X"
							+ " , Turn " + b.getTurnCount() + " . "));
					b.setTurn(false);
					stopLoop = true;
				} else {
					String message = b.placeToken(o, column);
					if (message.equals("Sorry, this column is full already!")
							|| message.equals("Column is off the charts! Sorry!")) {
						stopLoop = true;
						setTextbox(message);
						break;
					}
					setInformation(b.getLocation().concat(" . Player " + b.getPlayerTwo().getName() + " turn, Token O"
							+ " , Turn " + b.getTurnCount()  + " ."));
					b.setTurn(true);
					stopLoop = true;
				}
			}
		}
	}

	public void setGrid(JToggleButton[][] g) {
		button = g;
	}

	public JToggleButton[][] getGrid() {
		return button;
	}

	public void setTextbox(String s) {
		textbox.setText(s);
	}

	public String getTextbox() {
		return textbox.getText();
	}

	public String getTime() {
		return time.getText();
	}

	public void setTime(String s) {
		time.setText(s);
	}
	public void setPlayerName(Player p, String n){
		if(p.equals(b.getPlayerOne()))
			b.getPlayerOne().setName(n);
		else
			b.getPlayerTwo().setName(n);
	}

	public void addInformationToField(String information) {
		setInformation(information);
		informationList.add(information);
		b.addToArray(information);
		
	}
	
	public void setClock(Clock c){
		clock = c;
	}
	
	public Clock getClock(){
		return clock;
	}
	
	public void setUpdate(UpdateClock u){
		update = u;
	}
	
	public UpdateClock getUpdate(){
		return update;
	}
	
	public class Listener implements ActionListener {
		private boolean turn;

		public Listener(boolean t) {
			turn = t;
		}

		public void setTurn(boolean t) {
			turn = t;
		}

		public boolean getTurn() {
			return turn;
		}

		public void actionPerformed(ActionEvent event) {
			outerloop:
			for (int r = 6; r >= 0; r--) {
				for (int c = 7; c >= 0; c--) {
					if (event.getSource() == button[r][c]) {
						if(!victory){
						if (!b.getTurn())
							setTextbox("Player " + b.getPlayerOne().getName() + " turn, place token.");
						else
							setTextbox("Player " + b.getPlayerTwo().getName() + " turn, place token.");
						enterTurn(c + 1);
						setTurn(!b.getTurn());
						matchGrid();
						}
					}
						if (b.playerOneHasWon()) {
							addInformationToField(
									"Player " + b.getPlayerOne().getName() + " has won! Press Restart to play again");
							setTextbox("Player " + b.getPlayerOne().getName() + " has won! Press Restart to play again");
							setVictory(true);
							break outerloop;
						}
						if (b.playerTwoHasWon()) {
							addInformationToField(
									"Player " + b.getPlayerTwo().getName() + " has won! Press Restart to play again");
							setTextbox("Player " + b.getPlayerTwo().getName() + " has won! Press Restart to play again");
							setVictory(true);
							break outerloop;
						}
						if (b.isTie()) {
							addInformationToField("The game is a tie, game over. Press Restart to play again");
							setTextbox("The game is a tie, press restart to play again");
							setVictory(true);
							break outerloop;
						}
					}

				}
		}
	}

	public class Restart implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			addInformationToField("Game has been resetted. Press enter to continue.");
			b.isPlayerOnesTurn();
			gameNumber = r.nextInt(20000 - 10000 + 1);
			getClock().resetClock();
			setVictory(false);
			resetGrid();
		}
	}

	public class Forfeit implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			if (b.getTurn() == false) {
				addInformationToField("Player " + b.getPlayerOne().getName() + " has won, Player " + b.getPlayerTwo().getName() + " has forfeited. Press restart to play again");
			} else if (b.getTurn() == true) {
				addInformationToField("Player " + b.getPlayerTwo().getName() +  " has won, Player "+ b.getPlayerOne().getName() + " has forfeited. Press restart to play again");
			}
			addInformationToField("Game lasted " + getClock().getTime());
			getClock().resetClock();
			gameNumber = r.nextInt(20000 - 10000 + 1);
			b.isPlayerOnesTurn();
			setVictory(false);
			resetGrid();
		}
	}
	
	public class UpdateClock extends TimerTask {
		public void run(){
			setTime(clock.getTime());
			timeCounter++;
			if(timeCounter == 10){
				for(int i= 0; i < informationList.size(); i++){
					d.start();
					if(!informationList.get(indexCounter).equals(element) && !element.equals("")){
						d.writeResultSet(element, gameNumber, clock.getTime());
					}
					element = informationList.get(indexCounter);
					if(indexCounter  < informationList.size()-1)
						indexCounter++;
				}
				timeCounter = 0;
			}
		}
	}
}
