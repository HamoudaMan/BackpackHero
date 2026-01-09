package game.model.dungeon;

import java.util.ArrayList;
import java.util.List;

public class Dungeon {
	private final List<Floor>dungeonFloors;
	private int currentFloor ;//rdc
	
	
	
	public Dungeon(int floorCount) {
		this.dungeonFloors = new ArrayList<>();
		this.currentFloor = 0;
		
		RandomFloorGenerator generator = new RandomFloorGenerator();
		for(var level = 1; level <= floorCount; level++) {
			dungeonFloors.add(generator.generate(level));
		}
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