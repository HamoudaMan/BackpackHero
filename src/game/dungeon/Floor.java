package game.dungeon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import game.dungeon.rooms.*;
import game.dungeon.state.DungeonState;
import game.ennemies.*;
import game.hero.Hero;



public class Floor {
	private static final int ROWS = 5;
	private static final int COLS = 11;
	private int level = 1;//starting level of the floor is 1 
	private Room[][] floorRooms;
	private Coord positionHero;
	private boolean completed = false;//to follow if the floor was cleared , need to be in a floor state maybe ?

	
	public Floor(int level, Room floorRooms[][], Coord heroPosition) {
		Objects.requireNonNull(floorRooms);
		Objects.requireNonNull(heroPosition);
		if(level <= 0) {
			throw new IllegalArgumentException("Floor must be 1 or greater");
		}
		this.level = level;
		this.positionHero = heroPosition;
		//defensive copy 
		this.floorRooms = new Room[ROWS][COLS];
		for(var i = 0; i<ROWS;i++) {
			for(var j = 0; j<COLS; j++) {
				this.floorRooms[i][j] = floorRooms[i][j];
			}
		}
		if(!validPosition(heroPosition) || isWall(heroPosition)) {
			throw new IllegalArgumentException("Hero Position is not valid");
		}
		
		
		//adda a verification of hero position?
	}
	/**
	 * THis method helps to identify if the room
	 * of coordinates c is wether a valid room or a wall ( a Wall is always null)
	 * @param c coordinates of a room 
	 * @return a boolean
	 */
	public boolean isWall(Coord c) {
		return floorRooms[c.row()][c.col()] == null;
	}
	/**
	 * a method that return true or false if the Coord c is inside the floorRooms[ROWS][COLS]
	 * @param c Coord c
	 * @return a boolean true or false 
	 */
	public boolean validPosition(Coord c) {
		return c.row() >= 0 && c.row() < ROWS && c.col() >= 0 && c.col() < COLS;
	}
	/**
	 * a method to get the room a certain coordiantes 
	 * @param row
	 * @param col
	 * @return Room/or null is the room is a wall
	 */
	public Room getRoomInfo(int row, int col) {
		if(validPosition(new Coord(row, col))) {
			return floorRooms[row][col];
		}else {
			throw new IllegalArgumentException("invalid coordinates");
		}
	}
	/**
	 * a method taht return a list of the 4 adjacent neighboors of a cell
	 * filterd to be valid and not a wall
	 * @param coord
	 * @return List<Coord>
	 */
	private List<Coord> neighboors(Coord coord){
		List<Coord> neighboors = new ArrayList<Coord>();
		neighboors.add(new Coord(coord.row(), coord.col() - 1));
		neighboors.add(new Coord(coord.row(), coord.col() + 1));
		neighboors.add(new Coord(coord.row()-1, coord.col()));
		neighboors.add(new Coord(coord.row()+1, coord.col()));
		
		return neighboors.stream().filter(this::validPosition).filter(c-> !isWall(c)).toList();
	}
	/**
	 * a method that tells us if the room is safe to access or if it is a wall
	 * @param room
	 * @return boolean
	 */
	public boolean safeToAccess(Room room, Coord coord, DungeonState dungeonState) {
		if(room ==null) {
			return false;
		}
		return switch(room.type()) {
		case CORRIDOR, MERCHANT,TREASURE,HEALER,EXIT -> true;
		case  ENEMY ->  dungeonState.enemyState(coord).isCleared();
		//default -> false;
		};
	}
	/**
	 * 
	 * @param start
	 * @param dest
	 * @return boolean
	 */
	public boolean canReach(Coord start, Coord dest, DungeonState dungeonState) {
		
		if(start.equals(dest)) {
			return true;
		}
		
		boolean visited[][] = new boolean[ROWS][COLS];// init le plateau a false partout 
		Queue<Coord> queue = new ArrayDeque<>();
		queue.add(start);
		visited[start.row()][start.col()] = true;//si une case est visité on met true 
		
		while(!queue.isEmpty()) {
			Coord current = queue.remove();
			if(current.equals(dest)) {
				return true; //arrive a destination
			}
			for(Coord next: neighboors(current)) {
				if(!validPosition(next)) {
					continue;
				}
				if(visited[next.row()][next.col()]) {
					continue;
				}
				Room room = floorRooms[next.row()][next.col()];
				//////////////////////////
				if(next.equals(dest)) {
					if(room == null) {
						continue;
					}
					return true;
				}
//////////////////////////
				if(!safeToAccess(room, next, dungeonState)) {//comme ca on ne passe pas par la case ennemie(pour l'instant toute les case sont acessible 
					continue;
				}
				visited[next.row()][next.col()] = true;//on met la case en true pour direqu'on l'a visité
				queue.add(next);
			}
			
		}
		return false;
		
	}
	public List<Coord> findPath(Coord start, Coord dest, DungeonState dungeonState){
		if(start.equals(dest)) {
			return List.of(start);
		}
		boolean visited[][] = new boolean[ROWS][COLS];// to track all visited cells (false->not visited)
		Coord[][] parent = new Coord[ROWS][COLS];
		Queue<Coord> queue = new ArrayDeque<>();
		
		queue.add(start);
		visited[start.row()][start.col()] = true;//si une case est visité on met true 
		
		while(!queue.isEmpty()) {
			Coord current = queue.remove();
			if(current.equals(dest)) {
				break; //already at dest
			}
			for(Coord next: neighboors(current)) {
				if(visited[next.row()][next.col()]) {
					continue;
				}
				Room room = floorRooms[next.row()][next.col()];
				
				if(room == null ) {//wall 
					 System.out.println("BLOCK canEnter: " + room.type() + " at " + next);
					continue;
				}
				if(next.equals(dest)) {
					visited[next.row()][next.col()] = true;
					parent[next.row()][next.col()] = current;
					queue.add(next);
					continue;
					
				}
				if(room.type() == RoomType.ENEMY) {
					if(!dungeonState.enemyState(next).isCleared()) {
						continue;
					}
				}
				/*
				if(!next.equals(dest) && !canTraverse(room, next, dungeonState)) {
					continue;
				}
				*/
				visited[next.row()][next.col()] = true;//set true the cell that we visited 
				parent[next.row()][next.col()] = current;
				queue.add(next);
			}
			
		}
		List<Coord> path = new ArrayList<>();
		Coord step = dest;
		while(step !=null && !step.equals(start)) {
			path.add(step);
			step = parent[step.row()][step.col()];
		}
		if(step == null) {
			return List.of();//return empty, no path
		}
		path.add(start);
		Collections.reverse(path);
		return path;
	}
	
	
	
	public boolean moveHero(Coord dest , DungeonState dungeonState) {
		if( positionHero.equals(dest) ) {
			return true;
		}
		if(!canReach(positionHero ,dest, dungeonState)){
			return false;
		}
		Room currentRoom = floorRooms[dest.row()][dest.col()];
		switch(currentRoom.type()) {
			case ENEMY : { };//pour l'instant a changer dans la suite 
			case MERCHANT, TREASURE, HEALER, EXIT : {};
			default :{ };
		}
		positionHero = dest; 
		//currentRoom.enter(hero);//si la room n'est pas ennemi on y bouge notre hero
		return true;
	}
	public boolean allEnemiesCleared(DungeonState dungeonState) {
		for(var r = 0; r< rows(); r++) {
			for(var c = 0; c <cols(); c++) {
				Room room = floorRooms[r][c];
	
				if(room !=null && room.type() == RoomType.ENEMY ) {
					if(!dungeonState.enemyState(new Coord(r, c)).isCleared())
						return false;
				}
			}
		}
		return true;
	}
	/**
	 * 
	 * @param room
	 * @param coord
	 * @param dungeonState
	 * @return
	 */
	public boolean canTraverse(Room room, Coord coord, DungeonState dungeonState) {
		if(room ==null) {
			return false;
		}
		return switch(room.type()) {
		case CORRIDOR -> true;
		case ENEMY -> dungeonState.enemyState(coord).isCleared();
		default ->false;
		};
	}
	
	public boolean canEnter(Room room) {
		return room !=null;
	}

	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted() {
		this.completed = true;//utilisation de this juste par securité et evité ambiguité
	}
	public int rows() {
		return ROWS;
	}
	public int cols() {
		return COLS;
	}
	public int level() {
		return level;
	}
	public Coord postionHero() {
		return positionHero;
	}
	public Room[][] floorRooms(){
		return floorRooms;
	}

	/*
	public Floor(int level) {
		if(level <= 0) {
			throw new IllegalArgumentException("Floor must be 1 or greater");
		}
		
		this.floorRooms = new Room[ROWS][COLS];
		this.blocked = new boolean[ROWS][COLS];
		this.visited = new boolean[ROWS][COLS];
		
		for(var i = 0; i<ROWS; i++) {
			for(var j = 0; j<COLS; j++) {
				blocked[i][j] = true;//every room is blocked at first
				visited[i][j] = false;//no room is visited at first
				//floorRooms[i][j] = new CorridorRoom();//every room in the floor is a corridor at first
			}
		}
		//hero starting position
		positionHero = new Coord(2, 0);
		//generate the path from the startin postion of the hero
		generatePaths(positionHero);
		//then we place the Rooms
		placeRooms();
		
	}
*/
	


	/**
	 * 
	 * @param coord
	 * @return
	 */
	private List<Coord> getAllneighboors(Coord coord){
		List<Coord> neighboors = new ArrayList<Coord>();
		neighboors.add(new Coord(coord.row(), coord.col() - 1));
		neighboors.add(new Coord(coord.row(), coord.col() + 1));
		neighboors.add(new Coord(coord.row()-1, coord.col()));
		neighboors.add(new Coord(coord.row()+1, coord.col()));
		
		return neighboors.stream().filter(this::validPosition).toList();
	}
	/**
	 * 
	 * @param coord
	 * @return
	 */
	private List<Coord> shuffleDirections(Coord coord){
		Objects.requireNonNull(coord);
		
		List<Coord> directions = new ArrayList<Coord>(getAllneighboors(coord));
		Collections.shuffle(directions);
		return directions;
	}
	

	

	

}
