package connect4;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel intro, playerInfo;
	private boolean running;
	ArrayList<Spot> Spots;
	private Player [] players;
	private Timer t;
	JLabel gameInfo, clock;
	ArrayList<Spot> availableSpots;
	boolean gameOver;
	boolean moved;
	CPUPlayer AI;
	
	public Main() {
		setup();
	}

	public static void main(String[] args) {
		Main m = new Main();
		m.setVisible(true);
	}
	
	public boolean getGameState() {
		return running;
	}
	
	private void setup(){
		this.setSize(500, 400);
		this.setTitle("Connect 4");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.running = true;
		
		intro = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,10,10,10);
		
		JLabel p1 = new JLabel("Player One Name: ");
		c.gridx = 0;
		c.gridy = 0;
		intro.add(p1, c);
		
		JLabel p2 = new JLabel("Player Two Name: ");
		c.gridx = 0;
		c.gridy = 1;
		intro.add(p2, c);
		
		JTextField name1 = new JTextField(15);
		c.gridx = 1;
		c.gridy = 0;
		intro.add(name1, c);
		
		JTextField name2 = new JTextField(15);
		c.gridx = 1;
		c.gridy = 1;
		intro.add(name2, c);
		
		JButton start = new JButton("Start");
		c.gridx = 1;
		c.gridy = 2;
		intro.add(start, c);
		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buildGame(name1.getText(), name2.getText());
				t.start();
				runGame(name1.getText(), name2.getText());
			}
		});
		gameInfo = new JLabel();
		clock = new JLabel();
		
		AI = new CPUPlayer(this);
		
		this.add(intro);
	}
	
	private void buildGame(String p1, String p2){
		Player player1 = new Player(p1, true, Color.BLUE);
		Player player2 = new Player(p2, false, Color.RED);
		
		players = new Player [2];
		players[0] = player1;
		players[1] = player2;
		
		t = new Timer(player1, player2, this);
		
		Spots = new ArrayList<Spot>();
		availableSpots = new ArrayList<Spot>();
		
		for(int x = 100; x < 1400; x+=163) {
			for(int y = 50; y < 850; y+=100) {
				int [] coordinates = {x, y};
				Spots.add(new Spot(coordinates, Spots.size()));
			}
		}
		
		for(int x = 7; x < 64; x+=8) {
			availableSpots.add(Spots.get(x));
		}
	}
	
	private void runGame(String n1, String n2) {
		this.setVisible(false);
		this.setSize(1500, 900);
		this.remove(intro);
		//this.setContentPane(new JLabel(new ImageIcon("C:\\Users\\cjthe\\Dropbox\\For Mom\\public\\background.jpg")));
		
		JPanel newContent = new JPanel();
		GameBoard gb = new GameBoard();
		playerInfo = new JPanel();
		moved = false;
		
		newContent.setLayout(new BoxLayout(newContent, BoxLayout.X_AXIS));
		
		playerInfo.setLayout(new BoxLayout(playerInfo, BoxLayout.Y_AXIS));
		playerInfo.setSize(100, 100);
		playerInfo.add(new JLabel(n1));
		playerInfo.add(new JLabel(n2));
 
		JButton pause = new JButton("Pause");
		
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(t.pause){
					t.pause = false;
				}else {
					t.interrupt();
				}
			}});
		
		playerInfo.add(clock);
		playerInfo.add(pause);
		playerInfo.add(gameInfo);
		newContent.add(gb);
		newContent.add(playerInfo);
		newContent.setBackground(Color.black);
		
		this.add(newContent);
		
		this.setVisible(true);
	}
	
	public void updateBoard(int x, int y) {

		int [] point = {x,y};
		for(int i = 0; i < availableSpots.size(); i++){
			Spot spot = availableSpots.get(i);
			if(spot.contains(point)) {
				spot.setOwner(t.current);
				t.current.mySpots.add(spot);
				availableSpots.remove(i);
				try{
					if((spot.index - 1)%8 != 0){
						availableSpots.add(Spots.get(spot.index - 1));
					}
				}catch(ArrayIndexOutOfBoundsException e){}//Do nothing
				
				nextTurn();
				break;
			}
				
		}
	}
	
	public void nextTurn(){
		if(players[0].winner()) {
			gameOver = true;
			running = false;
			playerInfo.setBackground(players[0].c);
			cleanup(playerInfo);
		}else if(players[1].winner()) {
			gameOver = true;
			running = false;
			playerInfo.setBackground(players[1].c);
			cleanup(playerInfo);
		}
		t.clicked = true;
	}
	
	private void cleanup(JPanel panel) {
		
		for(int i = 0; i < panel.getComponentCount()+2; i++) {
			panel.remove(0);
		}
		panel.repaint();
		
	}
	public class GameBoard extends JPanel{
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public GameBoard() {
			setSize(1000, 900);
			this.addMouseListener(new MouseAdapter() {
				@Override
			    public void mouseClicked(MouseEvent e) {
					if(running) {
						moved = true;
						updateBoard(e.getX(), e.getY());
						repaint();
					}
					
			    }
				
			});
		}
		
		public void paint(Graphics g) {
			for(Spot s: Spots) {
				int [] coordinates = s.getCoords();
				g.setColor(s.getColor());
				g.fillOval(coordinates[0],coordinates[1],80,80);
			}
		}
		
	}

}
