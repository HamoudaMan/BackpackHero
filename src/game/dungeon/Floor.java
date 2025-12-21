package game.dungeon;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

import game.dungeon.rooms.*;
import game.ennemies.*;
import game.hero.Hero;



public class Floor {
	private static final int ROWS = 5;
	private static final int COLS = 11;
	private int level = 1;
	private boolean completed = false;
	private Room[][] floorRooms;
	private Coord positionHero;
	
	private boolean blocked[][];//for cells that are blocked (walls)
	private boolean visited[][];//to store cells already visited (used in the generation of the floor )
	
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

	
	//plus tard elles seront implementé dans une classe seul 
	public boolean validPosition(Coord c) {
		if(c.row() < 0 || c.row() >= 5 || c.col() < 0 || c.col() >= 11) {
			return false;
		}
		return true;
	}

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
	/**
	 * 
	 * @param coord
	 */
	private void depthFirstSearch(Coord coord) {
		System.out.println("DFS at " + coord);

		Objects.requireNonNull(coord);
		visited[coord.row()][coord.col()] = true;
		blocked[coord.row()][coord.col()] = false;
		var directions = shuffleDirections(coord);
		
		for(Coord next : directions) {
			if(visited[next.row()][next.col()]) {
				continue;
				
				//depthFirstSearch(next);
			}
			if (Math.random() < 0.5) {
			  depthFirstSearch(next);
			}

			
		}
		
	}
	
	public void generatePaths(Coord startPosition) {
		depthFirstSearch(startPosition);//recherche en profondeur 
	}
	/**
	 * finds a path between the start and the end of the floor 
	 * @param start starting position of the hero
	 * @param end exit room postion
	 * @return a list of Coord that represents the path form start to end of the floor 
	 */
	private List<Coord> findPath(Coord start, Coord end){
		boolean[][] visited = new boolean[ROWS][COLS];
		//sotres the cell before , to reconstruct the path 
		Coord[][] parent = new Coord[ROWS][COLS];
		Queue<Coord> queue = new ArrayDeque<>();
		//put the start cood in the queue
		queue.add(start);
		visited[start.row()][start.col()] = true;
		while(!queue.isEmpty()) {
			Coord current = queue.poll();
			if(current.equals(end)) {
				break; //we found the path
			}
			//explore all the accessible neighboors
			for(Coord next : getVoisins(current)) {
				if(visited[next.row()][next.col()]) {
					continue;
				}
				visited[next.row()][next.col()] = true;
				parent[next.row()][next.col()] = current;
				queue.add(next);
			}
			
		}
		//reconstruct the path from end to start
		List<Coord> path = new ArrayList<>();
		Coord step = end;
		while(step != null && !step.equals(start)) {
			path.add(step);
			step = parent[step.row()][step.col()];
		}
		//add the start to the path
		path.add(start);
		Collections.reverse(path);
		return path;
	}
	
	private void placeRooms() {
		Coord exit = new Coord(0,COLS-1);//exit will be random after 
		floorRooms[exit.row()][exit.col()] = new ExitRoom();
		// a list of coord of the cells from posHero to the exit 
		List<Coord> mainPath = findPath(positionHero, exit);
		//place enemies at least 2 per floor 
		var enemiesRoomCount = 2;
		for(var i = 2; i<mainPath.size() && enemiesRoomCount>0;i++) {
			Coord c = mainPath.get(i);
			floorRooms[c.row()][c.col()]= new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
			enemiesRoomCount--;
		}
		for(var i = 0; i < ROWS; i++) {
			for(var j = 0; j < COLS; j++) {
				if(blocked[i][j] || floorRooms[i][j] !=null) {
					continue;
					
				}
				double r = Math.random();
				if(r<0.1) {
					floorRooms[i][j]=new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
				}else if(r< 0.5) {
					floorRooms[i][j]= new TreasureRoom();
				}else if(r<0.7) {
					floorRooms[i][j]=new HealerRoom();
				}else {
					floorRooms[i][j]=new CorridorRoom();
				}
				
			}
		}
			
			
			
	}
	

	/**
	 * a method to return all valid neigboors of a cell (used for the hero mouvment )
	 * @param coord
	 * @return anImmutable list of the neighboors of c
	 */
	public List<Coord> getVoisins(Coord c){
		Coord gauche = new Coord(c.row(), c.col() - 1);
		Coord droite = new Coord(c.row(), c.col() + 1);
		Coord haut = new Coord(c.row() - 1, c.col());
		Coord bas = new Coord(c.row() + 1, c.col());
		
		List <Coord> voisins = List.of(gauche, droite, haut, bas);
		
		return voisins.stream().filter(coord -> validPosition(coord) && !blocked[coord.row()][coord.col()]).toList();
	}
	
	
	public int rows() {
		return ROWS;
	}
	public int cols() {
		return COLS;
	}
	
	public boolean isBlocked(int row, int col) {
		return blocked[row][col];
	}
	public Room[][] rooms(){
		return floorRooms;
	}
	

	public int level() {
		return level;
	}
	public Coord postionHero() {
		return positionHero;
	}

	public Room getRoomInfo(int row, int col) {
		if(row < 0 || row >= ROWS || col < 0 ||col >= COLS ) {
			throw new IllegalArgumentException();
		}
		return floorRooms[row][col];
	}
	
	//----floor1---
	public void initFloor1() {
		//ajout de salle ennemies
		level = 1;
		positionHero = new Coord(0, 0);
		floorRooms[2][2] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		floorRooms[2][8] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
    floorRooms[3][5] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		//ajout salle du marchand 
		floorRooms[1][7] = new MerchantRoom();
		//ajout salle healer
		floorRooms[3][6] = new HealerRoom();
		//ajout salle aux tresors 
		floorRooms[2][10] = new TreasureRoom();
		floorRooms[0][1] = new TreasureRoom();
		//ajout salle exit
		floorRooms[0][10] = new ExitRoom();
	}
	
	//----floor2---
	public void initFloor2() {
		//ajout de salle ennemies
		level = 2;
		positionHero = new Coord(1, 0);
		floorRooms[2][2] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		floorRooms[2][7] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		floorRooms[3][5] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		//ajout salle du marchand 
		floorRooms[1][8] = new MerchantRoom();
		//ajout salle healer
		floorRooms[2][8] = new HealerRoom();
		//ajout salle aux tresors 
		floorRooms[2][0] = new TreasureRoom();
		floorRooms[3][10] = new TreasureRoom();
		//ajout salle exit
		floorRooms[0][10] = new ExitRoom();
	}
	//----floor3---
	public void initFloor3() {
		//ajout de salle ennemies
		level = 3;
		positionHero = new Coord(2, 0);
		floorRooms[2][1] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		floorRooms[3][7] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		floorRooms[4][5] = new EnemyRoom(List.of( EnemyFactory.create(EnemyType.SMALLRATWOLF)));
		//ajout salle du marchand 
		floorRooms[1][7] = new MerchantRoom();
		//ajout salle healer
		floorRooms[1][9] = new HealerRoom();
		//ajout salle aux tresors 
		floorRooms[2][3] = new TreasureRoom();
		floorRooms[3][10] = new TreasureRoom();
		//ajout salle exit
		floorRooms[0][10] = new ExitRoom();
	}
	
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted() {
		this.completed = true;//utilisation de this juste par securité et evité ambiguité
	}
	
	//---methode de deplacement
	

	
	//salle accessible hors salle d'ennemis 
	public boolean safeToAccess(Room room) {
		return switch(room.type()) {
		case CORRIDOR -> true;
		case ENEMY ->true;
		case MERCHANT -> true;
		case TREASURE -> true;
		case HEALER -> true;
		case EXIT -> true;
		default -> false;
		};
	}
	
	public boolean canReach(Coord start, Coord dest) {
		
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
			for(Coord next: getVoisins(current)) {
				if(!validPosition(next)) {
					continue;
				}
				if(visited[next.row()][next.col()]) {
					continue;
				}
				Room room = floorRooms[next.row()][next.col()];
				if(!safeToAccess(room)) {//comme ca on ne passe pas par la case ennemie(pour l'instant toute les case sont acessible 
					continue;
				}
				visited[next.row()][next.col()] = true;//on met la case en true pour direqu'on l'a visité
				queue.add(next);
			}
			
		}
		return false;
		
	}
	
	public boolean moveHero(Coord dest, Hero hero ) {
		if( positionHero.equals(dest) ) {
			return true;
		}
		if(!canReach(positionHero ,dest)){
			return false;
		}
		Room currentRoom = floorRooms[dest.row()][dest.col()];

		
		switch(currentRoom.type()) {//bizarre ca marchait pas avec -> peut etre bug de mon eclipse 
			case ENEMY : { };//pour l'instant a changer dans la suite 
			case MERCHANT, TREASURE, HEALER, EXIT : {};
			
			default :{ };
		}
		positionHero = dest; 
		//currentRoom.enter(hero);//si la room n'est pas ennemi on y bouge notre hero
		return true;
	}
}
