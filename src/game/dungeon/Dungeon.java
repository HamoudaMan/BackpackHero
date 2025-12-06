package game.dungeon;

import java.util.List;

public class Dungeon {
	private final List<Floor>dungeonFloors;
	private int currentFloor ;//rdc
	
	
	public Dungeon() {
		this.dungeonFloors = List.of(new Floor(1), new Floor(2), new Floor(3));// on a les 3 etages
		//this.currentFloor = 0;
	}
	
	public List<Floor> dungeonFloors(){
		return dungeonFloors;
	}
	public int currentFLoorIndex() {
		return currentFloor;//index de l'etage en cours 
	}
	
	public Floor getCurrentFloor() {
		return dungeonFloors.get(currentFloor);// renvoi la salle en cours 
	}
	public boolean onLastFloor() {//pour verifer si on est au dernier etage 
		return currentFloor ==dungeonFloors.size()-1;
	}
	public void gotNextFloor() {
		if(!onLastFloor()) {
			currentFloor ++;
		}
	}


	public boolean isCompleted() {
		return onLastFloor() && getCurrentFloor().isCompleted();
	}
	
	
}

