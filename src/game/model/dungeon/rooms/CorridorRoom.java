package game.model.dungeon.rooms;


import java.util.List;

import game.model.dungeon.Room;
import game.model.dungeon.RoomType;
import game.model.enemy.Enemy;


public class CorridorRoom implements Room{

	@Override
	public RoomType type() {
		return RoomType.CORRIDOR;
	}

	@Override
	public String description() {
		return "a quiet empty room, this is just a corridor";
	}

	
	public List<Enemy> enemiesList() {
		return List.of();
	}



}
