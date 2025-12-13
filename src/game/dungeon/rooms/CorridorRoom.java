package game.dungeon.rooms;

import java.util.List;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.Enemy;


public class CorridorRoom implements Room{

	@Override
	public RoomType type() {
		return RoomType.CORRIDOR;
	}

	@Override
	public String description() {
		return "a quiet empty room, this is just a corridor";
	}

	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList() {
		return List.of();
	}

}
