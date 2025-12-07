package game.dungeon;

import java.util.ArrayDeque;

import java.util.List;
import java.util.Queue;

import game.dungeon.rooms.*;
import game.ennemies.*;
import game.hero.Hero;
import game.interaction.Combat;
import game.interaction.CombatResult;


public class Floor {
	private static final int ROWS = 5;
	private static final int COLS = 11;
	private int level = 1;
	private boolean completed = false;
	private Room[][] floorRooms;
	private Coord positionHero;
	
	
	public Floor(int level) {
		this.floorRooms = new Room[ROWS][COLS];
		//au debut je rempli la map qu'avec des coduloir 
		for(var i = 0; i < ROWS; i++) {
			for(var j = 0; j < COLS; j++) {
				floorRooms[i][j] = new CorridorRoom();
			}
		}
		
		switch(level) {
			case 1 -> initFloor1();
			case 2 -> initFloor2();
			case 3 -> initFloor3();
			default -> throw new IllegalArgumentException("floor exceded");
		}
		this.level = level;
		
	}
	
	public Room[][] floor() {
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
		floorRooms[2][2] = new EnemyRoom(List.of(new SmallRatWolf()));
		floorRooms[2][8] = new EnemyRoom(List.of(new SmallRatWolf()));
    floorRooms[3][5] = new EnemyRoom(List.of(new SmallRatWolf()));
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
		floorRooms[2][2] = new EnemyRoom(List.of(new SmallRatWolf()));
		floorRooms[2][7] = new EnemyRoom(List.of(new SmallRatWolf()));
		floorRooms[3][5] = new EnemyRoom(List.of(new SmallRatWolf()));
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
		floorRooms[2][1] = new EnemyRoom(List.of(new SmallRatWolf()));
		floorRooms[3][7] = new EnemyRoom(List.of(new SmallRatWolf(), new SmallRatWolf()));
		floorRooms[4][5] = new EnemyRoom(List.of(new SmallRatWolf()));
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
	//plus tard elles seront implementé dans une classe seul 
	public boolean validPosition(Coord c) {
		if(c.row() < 0 || c.row() >= 5 || c.col() < 0 || c.col() >= 11) {
			return false;
		}
		return true;
	}
	public List<Coord> getVoisins(Coord c){
		Coord gauche = new Coord(c.row(), c.col() - 1);
		Coord droite = new Coord(c.row(), c.col() + 1);
		Coord haut = new Coord(c.row() - 1, c.col());
		Coord bas = new Coord(c.row() + 1, c.col());
		
		List <Coord> voisins = List.of(gauche, droite, haut, bas);
		
		return voisins.stream().filter(coord -> validPosition(coord)).toList();
	}
	
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
				if(!safeToAccess(room)) {//comme ca on ne passe pas par la case ennemie
					continue;
				}
				visited[next.row()][next.col()] = true;
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
		
		/*
		if(currentRoom instanceof EnemyRoom enemyRoom ) {//si la room contient un ennemi
			//logique de combat 
			if( Combat.startCombat(hero,enemyRoom.enemiesList()) ==   CombatResult.LOSE) {
				return false;
			}
		}*/
	
		
		switch(currentRoom.type()) {//bizarre ca marchait pas avec -> peut etre bug de mon eclipse 
			case ENEMY : { };
			case MERCHANT, TREASURE, HEALER, EXIT : currentRoom.enter(hero);
			
			default :{ };
		}
		positionHero = dest; 
		//currentRoom.enter(hero);//si la room n'est ennemi on y bouge notre hero
		return true;
	}
}
