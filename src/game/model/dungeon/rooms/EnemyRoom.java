package game.model.dungeon.rooms;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import game.model.dungeon.Room;
import game.model.dungeon.RoomType;
import game.model.enemy.*;


//import game.items.Item;

public class EnemyRoom implements Room{
	private final List<Enemy> enemiesList;
	private static final EnemyGenerator enemyGen = new EnemyGenerator();
	
	public EnemyRoom(int floorLevel){
		if(floorLevel <1) {
			throw new IllegalArgumentException("floor must be greater than 1 : "+ floorLevel);
		}
		//Objects.requireNonNull(enemies);
		//this.enemiesList = new ArrayList<>(enemies);
		this.enemiesList = enemyGen.generateEnemies(floorLevel);
	}
	
	
	public List<Enemy> enemiesList() {
		return enemiesList;
	}


	@Override
	public RoomType type() {
		return RoomType.ENEMY;
	}

	@Override
	public String description() {
		return "Be ready to fight fierce enemies.\n";
	}





}
