package game.model.dungeon.rooms;


import java.util.List;

import game.model.dungeon.Room;
import game.model.dungeon.RoomType;
import game.model.enemy.*;

/**
 * describe the room
 * does not store the items 
 * items will be generated in the state 
 */
public class TreasureRoom implements Room{
	


	
	@Override
	public RoomType type() {
		// TODO Auto-generated method stub
		return RoomType.TREASURE;
	}

	@Override
	public String description() {
		return "Welcome to the treasure room, don't be shy stuff yourself !";
	}
	

	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList() {
		return List.of();
	}



}
