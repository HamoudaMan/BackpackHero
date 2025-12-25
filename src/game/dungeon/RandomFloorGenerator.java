package game.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import game.dungeon.rooms.CorridorRoom;
import game.dungeon.rooms.EnemyRoom;
import game.dungeon.rooms.ExitRoom;
import game.dungeon.rooms.HealerRoom;
import game.dungeon.rooms.MerchantRoom;
import game.dungeon.rooms.TreasureRoom;
import game.ennemies.EnemyFactory;
import game.ennemies.EnemyType;

public final class RandomFloorGenerator {
	private static final int ROWS = 5;
	private static final int COLS = 11;
	private Random random = new Random();
	

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

		mainPath.add(current);
		rooms[current.row()][current.col()] = new CorridorRoom();//hero start at a corridor 

		while(current.col() <COLS-1) {
			var row = current.row();
			var col = current.col();
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
	/**
	 * this methoded places the exitROom in the map 
	 * @param mainPath
	 * @param rooms
	 */
	private Coord placeExit(List<Coord> mainPath,Room[][] rooms ) {
		if(mainPath.isEmpty()) {
			throw new IllegalArgumentException("path can't be empty");
		}
		var exitRow = mainPath.getLast().row() ;
		var exitCol = mainPath.getLast().col() ;
		if(rooms[exitRow][exitCol] == null) {
			throw new IllegalArgumentException("exitroom must be on an accessible cell ");
		}
		rooms[exitRow][exitCol] = new ExitRoom();
		return new Coord(exitRow, exitCol);
	}
	/**
	 * method to place the obliged ennemy rooms between 1 and 3 per floor 
	 * @param mainPath
	 * @param rooms
	 * @param heroStart
	 * @param exit
	 */
	private void placeEnemyRooms(List<Coord> mainPath, Room[][] rooms, Coord heroStart, Coord exit) {
		Objects.requireNonNull(mainPath);
		Objects.requireNonNull(rooms);
		Objects.requireNonNull(heroStart);
		Objects.requireNonNull(exit);
		if(!validPosition(heroStart) || !validPosition(exit)) {
			throw new IllegalArgumentException("postions are not valid ");
		}
		var candidates = new ArrayList<Coord>(mainPath);
		//we don't touch the herostart and the exit 
		candidates.remove(heroStart);
		candidates.remove(exit);
		Collections.shuffle(candidates);
		var enemyRoomCount = random.nextInt(1, 3);//a min to ensure no idexoutof bound ? 
		for(var i = 0; i< enemyRoomCount; i++) {
			var current = candidates.get(i);
			rooms[current.row()][current.col()] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		}	
	}
	/**
	 * transform rooms filled with null and Corridor to a functional rooms with enemy, merchancht, healer and treasure 
	 * @param rooms
	 * @param heroStart
	 * @param exit
	 */
	//todo filter le hero start et exit room 
	private List<Coord> generateDeadEnds(List<Coord> mainPath,Room[][] rooms) {
		Objects.requireNonNull(mainPath);
		Objects.requireNonNull(rooms);
		var deadEnds = new ArrayList<Coord>();
		var deadEndsCount = random.nextInt(1,3);
	
		for(var i = 0; i<deadEndsCount; i++) {
			var depart = mainPath.get(random.nextInt(mainPath.size()));//the start of the deadend 
			var directions = List.of(new Coord(-1, 0), new Coord(1, 0), new Coord(0, -1));//a list of directions 
			var direction = directions.get(random.nextInt(directions.size()));

			var deadEndLength = random.nextInt(1,3);
			var current = depart;
			
			for(var j = 0; j<deadEndLength; j++) {
				var next = current.sum(direction);
				if(!validPosition(next) || rooms[next.row()][next.col()] != null) {
					break;
				}
				rooms[next.row()][next.col()] = new CorridorRoom(); 
				deadEnds.add(next);
				current = next;
			}
		}
		
		return deadEnds;
	}
	private void placeOtherRooms(List<Coord> mainPath,List<Coord> deadEnds, Room[][] rooms, Coord heroStart, Coord exit) {
		Objects.requireNonNull(mainPath);
		Objects.requireNonNull(deadEnds);
		Objects.requireNonNull(rooms);
		Objects.requireNonNull(heroStart);
		Objects.requireNonNull(exit);
		if(!validPosition(heroStart) || !validPosition(exit)) {
			throw new IllegalArgumentException("postions are not valid ");
		}
		 
		 if(deadEnds.isEmpty()) {
			 return;
		 }
		
		var candidates = new ArrayList<>(deadEnds);
		//just in case 
		//candidates.removeLast();//remove the exit room
		//candidates.removeFirst();//remove the hero start 
		Collections.shuffle(candidates, random);
		List<RoomType> toPlace = new ArrayList<>( List.of(RoomType.TREASURE, RoomType.HEALER, RoomType.MERCHANT) );
		
		var count = Math.min(toPlace.size(), candidates.size());//to avoid indexoutofbounds
		
		for(var i = 0;i<count; i++) {
			var c = deadEnds.get(i);
			var type = toPlace.get(i);
			switch(type) {
				case TREASURE ->rooms[c.row()][c.col()] = new TreasureRoom();
				case HEALER -> rooms[c.row()][c.col()] = new HealerRoom();
				case MERCHANT -> rooms[c.row()][c.col()] = new MerchantRoom();
			}
			
			
		}
		
	}
	public Floor generate(int level) {
		Room[][] rooms = initFloor();
		var heroStart = chooseHeroStart();
		var mainPath = generateMainPath(rooms, heroStart);
		var exit = placeExit(mainPath, rooms);
		placeEnemyRooms(mainPath, rooms, heroStart, exit);
		var deadEnds = generateDeadEnds(mainPath, rooms);
		placeOtherRooms(mainPath, deadEnds, rooms, heroStart, exit);
		return new Floor(level, rooms, heroStart);
		
	}
	/*
	public boolean checkInvariants(Floor floor, List<Coord>mainPath, List<Coord> deadEnds ) {
		Objects.requireNonNull(floor);
		Objects.requireNonNull(mainPath);
		Objects.requireNonNull(deadEnds);
		if(mainPath.size() < deadEnds.size()) {
			throw new IllegalArgumentException("the main path must be greater than deadEnds Path");
		}
		
		
	}
	*/
}
