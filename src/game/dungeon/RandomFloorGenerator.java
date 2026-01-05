package game.dungeon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

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
			if(validPosition(right) && rooms[right.row()][right.col()] == null && !forbiddenDiagonal(right, rooms) ) {
				directions.add(right);
			}
			
			var up = new Coord(row-1, col);
			if(validPosition(up) && rooms[up.row()][up.col()] == null && !forbiddenDiagonal(up, rooms)) {
				directions.add(up);
			}
			
			var down = new Coord(row+1, col);
			if(validPosition(down) && rooms[down.row()][down.col()] == null && !forbiddenDiagonal(down, rooms)) {
				directions.add(down);
			}
			
			Coord next;
			if(directions.isEmpty()) {
				return null;
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
	 * Checks if a coord is adjacent to another coord
	 * this methode is used to help us verify that no cell is generated in diagonal 
	 * @param coord
	 * @param existingRooms
	 * @return boolean
	 */
	private boolean isAdjacent(Coord coord,  Set<Coord> exsistingRooms) {
		/*
		var dr = Math.abs(a.row() - b.row());
		var dc = Math.abs(a.col() - b.col());
		return dr +dc ==1;*/
		var directions = List.of(new Coord(-1, 0), new Coord(1, 0), new Coord(0, -1), new Coord(0, 1));//a list of directions (up, down, left, right)
		
		for(var dir:directions) {
			var neighboor =coord.sum(dir);
			if(exsistingRooms.contains(neighboor)) {
				return true;
			}
			
		}
		return false;
	}
	private boolean forbiddenDiagonal(Coord coord, Room[][] rooms) {
		var diagonals = List.of(new Coord(-1, -1), new Coord(-1, 1), new Coord(1, -1), new Coord(1, 1));//a list of diagonals
		for(var d: diagonals) {
			var diag = coord.sum(d);
			if(!validPosition(diag)) {//if the postion is not valid continue to the next diagonal
				continue;
			}
			if(rooms[diag.row()][diag.col()] != null) {
				var a = new Coord(coord.row(), diag.col());
				var b = new Coord(diag.row(), coord.col());
				
				if(!(validPosition(a) && rooms[a.row()][a.col()] != null) && !(validPosition(b) && rooms[b.row()][b.col()] != null)) {
					return true;
				}
			}
		}
		return false;
	}
	private Coord randomFreeNeighboor(Coord coord, Room[][] rooms, Set<Coord> exsistingRooms) {
		var directions = List.of(new Coord(-1, 0), new Coord(1, 0), new Coord(0, -1), new Coord(0, 1));//a list of directions (up, down, left, right)
		var candidates = new ArrayList<Coord>();
		for(var d: directions) {
			var n = coord.sum(d);
			if(validPosition(n) && rooms[n.row()][n.col()] == null && isAdjacent(n, exsistingRooms) && !forbiddenDiagonal(n, rooms)) {
				candidates.add(n);
			}
		}
		if(candidates.isEmpty()) {
			return null;
		}
		return candidates.get(random.nextInt(candidates.size()));
	}
	/**
	 * generates deadsends from the main path 
	 * each cell is adjacacent to an existing cell
	 * 
	 * @param rooms
	 * @param heroStart
	 * @param exit
	 */
	//todo filter le hero start et exit room 
	private List<Coord> generateDeadEnds(List<Coord> mainPath,Room[][] rooms) {
		Objects.requireNonNull(mainPath);
		Objects.requireNonNull(rooms);
		
		var deadEnds = new ArrayList<Coord>();
		var exsistingRooms = new HashSet<>(mainPath);
		var deadEndsCount = random.nextInt(3)+3;//between 3 and 5 deadends
	
		for(var i = 0; i<deadEndsCount; i++) {
			//choose a random starting cell from the mainPath 
			var start = mainPath.get(random.nextInt(mainPath.size()));//the start of the deadend 
			//var directions = List.of(new Coord(-1, 0), new Coord(1, 0), new Coord(0, -1), new Coord(0, 1));//a list of directions (up, down, left, right)
			//var direction = directions.get(random.nextInt(directions.size()));//get a random direction
			//Collections.shuffle(new ArrayList<>(directions));
	
			var deadEndLength = 2+random.nextInt(6);//lenght of the deadend path between 2 and 7
			var current = start;
			
			for(var j = 0; j<deadEndLength; j++) {
				//var next = current.sum(direction);
				var next = randomFreeNeighboor(current,rooms, exsistingRooms);
				if(next ==null) {
					break;
				}
				rooms[next.row()][next.col()] = new CorridorRoom(); 
				deadEnds.add(next);
				exsistingRooms.add(next);
				current = next;
				/*
				//check valid postion or if room already ocuppied
				if(!validPosition(next) || rooms[next.row()][next.col()] != null) {
					break;
				}
				if(!isAdjacent(current,next)) {//verify adjacent or not 
					IO.println("Case non prise en compte car non adjacaentes : "+next);
					break;
				}*/

			}
		}
		
		return deadEnds;
	}
	/*
	private void fixDiagonals(Room[][] rooms) {
    for (int r = 0;r < ROWS - 1; r++) {
        for (int c = 0;c <COLS - 1; c++) {
            if (rooms[r][c] != null && rooms[r+1][c+1] != null
                && rooms[r][c+1] == null
                && rooms[r+1][c] == null) {
                rooms[r][c+1] = new CorridorRoom();
            }
            if (rooms[r+1][c] != null && rooms[r][c+1] != null
                && rooms[r][c] == null
                && rooms[r+1][c+1] == null) {
                rooms[r][c] = new CorridorRoom();
            }
        }
    }
}
*/
	
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
		for(var coord: mainPath) {
			if(!coord.equals(heroStart)&& !coord.equals(exit)&& rooms[coord.row()][coord.col()].type() == RoomType.CORRIDOR) {
				candidates.add(coord);
			}
		}
		//candidates.addAll(deadEnds);
		Collections.shuffle(candidates, random);
		
		//at least one room is places 
		List<RoomType> toPlace = new ArrayList<>(List.of(RoomType.TREASURE, RoomType.HEALER, RoomType.MERCHANT) );
		if(random.nextBoolean()) {
			toPlace.add(RoomType.TREASURE);
		}
		/*
		if(random.nextBoolean()) {
			toPlace.add(RoomType.TREASURE);
		}*/
		Collections.shuffle(toPlace, random);
		
		var count = Math.min(toPlace.size(), candidates.size());//to avoid indexoutofbounds
		
		for(var i = 0;i<count; i++) {
			var c = candidates.get(i);
			var type = toPlace.get(i);
			switch(type) {
				case TREASURE ->rooms[c.row()][c.col()] = new TreasureRoom();
				case HEALER -> rooms[c.row()][c.col()] = new HealerRoom();
				case MERCHANT -> rooms[c.row()][c.col()] = new MerchantRoom();
				default ->{}
			}
			
			
		}
		
	}
	public Floor generate(int level) {
		while (true) {
			Room[][] rooms = initFloor();
			var heroStart = chooseHeroStart();
			var mainPath = generateMainPath(rooms, heroStart);
			if(mainPath == null) {
				continue;//restart till the mainPath is not null
			}
			var exit = placeExit(mainPath, rooms);
			placeEnemyRooms(mainPath, rooms, heroStart, exit);
			var deadEnds = generateDeadEnds(mainPath, rooms);
			placeOtherRooms(mainPath, deadEnds, rooms, heroStart, exit);
			//fixDiagonals(rooms);
			assertNoDiagonal(rooms);
			return new Floor(level, rooms, heroStart);
		}
		
	}
	private static void assertNoDiagonal(Room[][] rooms) {
    int rows = rooms.length;
    int cols = rooms[0].length;

    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (rooms[r][c] == null) continue;

            // 4 diagonales
            int[][] diags = {
                {-1, -1}, {-1, 1},
                {1, -1}, {1, 1}
            };

            for (var d : diags) {
                int rr = r + d[0];
                int cc = c + d[1];

                if (rr < 0 || rr >= rows || cc < 0 || cc >= cols) continue;

                if (rooms[rr][cc] != null) {
                    // si les deux orthogonaux sont vides → vraie diagonale
                    boolean ortho1 = rooms[r][cc] != null;
                    boolean ortho2 = rooms[rr][c] != null;

                    if (!ortho1 && !ortho2) {
                        throw new IllegalStateException(
                            "DIAGONALE LOGIQUE détectée entre (" +
                            r + "," + c + ") et (" + rr + "," + cc + ")"
                        );
                    }
                }
            }
        }
    }
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
