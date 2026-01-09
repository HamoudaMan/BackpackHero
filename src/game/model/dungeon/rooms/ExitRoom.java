package game.model.dungeon.rooms;


import java.util.List;

import game.model.dungeon.Room;
import game.model.dungeon.RoomType;
import game.model.enemy.*;

public class ExitRoom implements Room{



	@Override
	public RoomType type() {
		return RoomType.EXIT;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return " You reached the end of this floor GG";
	}

	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList() {
		return List.of();
	}




}