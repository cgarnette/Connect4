package connect4;

import java.util.ArrayList;

import java.util.Random;

public class CPUPlayer {

	//An automatic move that happens when the player times out
	//Also how the computer plays
	//Will be improved to incorporate machine learning for better play
	
	private Main driver;
	private ArrayList<Spot> Spots;
	private Random rand;
	
	public CPUPlayer(Main driver) {
		this.driver = driver;
		rand = new Random();
		update();
	}
	
	public void Move(){
		update();
		int index = rand.nextInt(7);
		driver.updateBoard(Spots.get(index).coordinates[0], Spots.get(index).coordinates[1]);
	}
	
	private void update() {
		this.Spots = driver.availableSpots;
	}
	
	
}
