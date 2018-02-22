package connect4;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;

public class Player implements Comparable<Player>{

	final String name;
	private int score;
	boolean myTurn;
	Color c;
	ArrayList<Spot> mySpots;
	private compareSpot sorting;
	
	public Player(String name, boolean myTurn, Color c) {
		this.name = name;
		this.score = 0;
		this.myTurn = myTurn;
		this.c = c;
		mySpots = new ArrayList<Spot>();
		sorting = new compareSpot();
	}
	
	public void increaseScore() {
		score += 10;
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean winner() {
		boolean winner = false;
		int [] coords = {0,0};
		if(mySpots.size() > 3) {
			mySpots.sort(sorting);
			for(Spot s: mySpots){
				score = 0;
				try{
					if(mySpots.contains(new Spot(coords, (s.index + 7)))) score += 1;
					if(mySpots.contains(new Spot(coords, (s.index + 14)))) score += 1;
					if(mySpots.contains(new Spot(coords, (s.index + 21)))) score += 1;
					
					if(score == 3) winner = true; break;
					
				}catch(IndexOutOfBoundsException e){}//do nothing
				try {
					if(!winner){
						score = 0;
						if(mySpots.contains(new Spot(coords, (s.index + 8)))) score+=1;
						if(mySpots.contains(new Spot(coords, (s.index + 16)))) score+=1;
						if(mySpots.contains(new Spot(coords, (s.index + 24)))) score+=1;
						
						if(score == 3) winner = true; break;
					}
				}catch(IndexOutOfBoundsException e) {}//do nothing
				try {
					if(!winner){ // Error somewhere here, preventing victory by stacking.
						score = 0;
						if(mySpots.contains(new Spot(coords, (s.index - 1)))) score+=1;
						if(mySpots.contains(new Spot(coords, (s.index - 2)))) score+=1;
						if(mySpots.contains(new Spot(coords, (s.index - 3)))) score+=1;
						
						if(score == 3) winner = true; break;
					}
				}catch(IndexOutOfBoundsException e) {}//do nothing
				
				try {
					if(!winner){
						score = 0;
						if(mySpots.contains(new Spot(coords, (s.index + 9)))) score+=1;
						if(mySpots.contains(new Spot(coords, (s.index + 18)))) score+=1;
						if(mySpots.contains(new Spot(coords, (s.index + 27)))) score+=1;
						
						if(score == 3) winner = true; break;
					}
				}catch(IndexOutOfBoundsException e) {}//do nothing
			}
		}
		
		return winner;
	}

	@Override
	public int compareTo(Player arg0) {
		if (arg0.name.equals(this.name)) {
			return 0;
		}else {
			return 1;
		}
	}
	
	public class compareSpot implements Comparator<Spot>{
		@Override
		public int compare(Spot arg0, Spot arg1) {
			return arg0.compareTo(arg1);
		}
	}
}
