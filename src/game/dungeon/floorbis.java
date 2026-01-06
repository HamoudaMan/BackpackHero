package game.dungeon;

import java.util.List;

import game.dungeon.rooms.EnemyRoom;
import game.dungeon.rooms.ExitRoom;
import game.dungeon.rooms.HealerRoom;
import game.dungeon.rooms.MerchantRoom;
import game.dungeon.rooms.TreasureRoom;
import game.ennemies.EnemyFactory;
import game.ennemies.EnemyType;

public class floorbis {
	/**
	 * 
	 * @param coord
	 *//*
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
	*/
	/**
	 * finds a path between the start and the end of the floor 
	 * @param start starting position of the hero
	 * @param end exit room postion
	 * @return a list of Coord that represents the path form start to end of the floor 
	 */
	/*
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
	/*
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
	*/

	/**
	 * a method to return all valid neigboors of a cell (used for the hero mouvment )
	 * @param coord
	 * @return anImmutable list of the neighboors of c
	 */
	/*
	public List<Coord> getVoisins(Coord c){
		Coord gauche = new Coord(c.row(), c.col() - 1);
		Coord droite = new Coord(c.row(), c.col() + 1);
		Coord haut = new Coord(c.row() - 1, c.col());
		Coord bas = new Coord(c.row() + 1, c.col());
		
		List <Coord> voisins = List.of(gauche, droite, haut, bas);
		
		return voisins.stream().filter(coord -> validPosition(coord) && !blocked[coord.row()][coord.col()]).toList();
	}
	
	

/*
	public boolean isBlocked(int row, int col) {
		return blocked[row][col];
	}

	*/



	
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
	

	
	//---methode de deplacement
	
}
