package connect4;

import java.awt.Color;

public class Spot implements Comparable<Spot>{

	int [] coordinates;
	int index;
	private Player owner;
	private Color c;
	
	public Spot(int [] coordinates, int index) {
		this.coordinates = coordinates;
		this.owner = null;
		this.c = Color.WHITE;
		this.index = index;
	}
	
	public Player getOwner() {
		return owner;
	}
	
	public void setOwner(Player p){
		this.owner = p;
	}
	
	public int [] getCoords() {
		return coordinates;
	}
	
	public Color getColor() {
		if(this.owner == null) {
			return this.c;
		}else {
			return owner.c;
		}
	}
	
	@Override
	public String toString() {
		return "x: " + coordinates[0] + " y: " + coordinates[1];
	}
	
	public boolean contains(int [] point) {
		
		if((Math.abs(point[0]-coordinates[0]) < 60) && (Math.abs(point[1] - coordinates[1]) < 60)) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public int compareTo(Spot arg0) {
		if(arg0.index == this.index) {
			return 0;
		}else if(arg0.index > this.index){
			return -1;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Spot other = (Spot) obj;
		if (index != other.index)
			return false;
		return true;
	}
	

}
