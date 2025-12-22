package game.dungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import game.dungeon.rooms.CorridorRoom;
import game.dungeon.rooms.ExitRoom;

public final class RandomFloorGenerator {
	private static final int ROWS = 5;
	private static final int COLS = 11;
	private Random random;
	
	/**
	 * a mehtod that initiales a floor of rooms to null
	 * @return rooms
	 */
	private Room[][] initFloor() {
		Room[][] rooms = new Room[ROWS][COLS];
		for(var r = 0; r<ROWS; r++) {
			for(var c=0; c<COLS; c++) {
				rooms[r][c] = null;
			}
		}
		return rooms;
	}
	/**
	 * generates a random hero starting position in a random row , first col 
	 * @return Coord
	 */
	private Coord chooseHeroStart() {
		return new Coord(random.nextInt(ROWS), 0);
	}
	
	private boolean validPosition(Coord c) {
		return c.row() >= 0 && c.row() < ROWS && c.col() >= 0 && c.col() < COLS;
	}
	
	/**
	 * this method generates a path from herStart to a Room at the end of the floor ,
	 * the coordinates of the last room in the list will be set as an exit room in the next method
	 * @param level
	 * @return Floor
	 */
	private List<Coord> generateMainPath(Room[][] rooms, Coord heroStart ){
		List<Coord> mainPath = new ArrayList<>();//array that will store the main path
		var current = heroStart;
		var row = current.row();
		var col = current.col();
		mainPath.add(current);
		rooms[current.row()][current.col()] = new CorridorRoom();//hero start at a corridor 

		while(current.col() <COLS-1) {
			
			List<Coord> directions = new ArrayList<>();
			var right = new Coord(row,col+1);
			if(validPosition(right) && rooms[right.row()][right.col()] == null) {
				directions.add(right);
			}
			
			var up = new Coord(row-1, col);
			if(validPosition(up) && rooms[up.row()][up.col()] == null) {
				directions.add(up);
			}
			
			var down = new Coord(row+1, col);
			if(validPosition(down) && rooms[down.row()][down.col()] == null) {
				directions.add(down);
			}
			
			Coord next;
			if(directions.isEmpty()) {
				next = right;
			}else if(directions.contains(right)&& random.nextInt(100)<70){
					next = right;
			}else {
					next = directions.get(random.nextInt(directions.size()));
			}
			
			rooms[next.row()][next.col()] = new CorridorRoom();
		  mainPath.add(next);
		  current=next;

		}	
		return mainPath;
	}
	
	private void placeExit(List<Coord> mainPath,Room[][] rooms ) {
		if(mainPath.isEmpty()) {
			throw new IllegalArgumentException("path can't be empty");
		}
		var exitRow = mainPath.getLast().row() ;
		var exitCol = mainPath.getLast().col() ;
		if(rooms[exitRow][exitCol] == null) {
			throw new IllegalArgumentException("exitroom must be on an accessible cell ");
		}
		rooms[exitRow][exitCol] = new ExitRoom();
	}
	
	private void placeRooms(Room[][] rooms, Coord heroStart, Coord exit) {
	
	}
	public Floor generate(int level) {
		return;
	}
}
