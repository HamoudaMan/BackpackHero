package game.dungeon;

import game.dungeon.rooms.*;


public class Floor {
	private static final int ROWS = 5;
	private static final int COLS = 11;
	
	private Room[][] floorRooms;
	
	public Floor(int level) {
		floorRooms = new Room[ROWS][COLS];
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
	}
	
	public Room[][] floor() {
		return floorRooms;
	}
	
	public Room getRoomInfo(int row, int col) {
		if(row < 0 || col >= COLS || row >= ROWS || col < 0) {
			throw new IllegalArgumentException();
		}
		return floorRooms[row][col];
	}
	
	//----floor1---
	public void initFloor1() {
		//ajout de salle ennemies
		floorRooms[2][2] = new EnemyRoom();
		floorRooms[2][7] = new EnemyRoom();
    floorRooms[3][5] = new EnemyRoom();
		//ajout salle du marchand 
		floorRooms[1][8] = new MerchantRoom();
		//ajout salle healer
		floorRooms[2][8] = new HealerRoom();
		//ajout salle aux tresors 
		floorRooms[4][1] = new TreasureRoom();
		floorRooms[3][10] = new TreasureRoom();
		//ajout salle exit
		floorRooms[0][10] = new ExitRoom();
	}
	
	//----floor2---
	public void initFloor2() {
		//ajout de salle ennemies
		floorRooms[2][2] = new EnemyRoom();
		floorRooms[2][7] = new EnemyRoom();
		floorRooms[3][5] = new EnemyRoom();
		//ajout salle du marchand 
		floorRooms[1][8] = new MerchantRoom();
		//ajout salle healer
		floorRooms[2][8] = new HealerRoom();
		//ajout salle aux tresors 
		floorRooms[4][1] = new TreasureRoom();
		floorRooms[3][10] = new TreasureRoom();
		//ajout salle exit
		floorRooms[0][10] = new ExitRoom();
	}
	//----floor3---
	public void initFloor3() {
		//ajout de salle ennemies
		floorRooms[2][2] = new EnemyRoom();
		floorRooms[2][7] = new EnemyRoom();
		floorRooms[3][5] = new EnemyRoom();
		//ajout salle du marchand 
		floorRooms[1][8] = new MerchantRoom();
		//ajout salle healer
		floorRooms[2][8] = new HealerRoom();
		//ajout salle aux tresors 
		floorRooms[4][1] = new TreasureRoom();
		floorRooms[3][10] = new TreasureRoom();
		//ajout salle exit
		floorRooms[0][10] = new ExitRoom();
	}
	
}
