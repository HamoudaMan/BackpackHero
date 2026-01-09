package game.model.dungeon;

import java.util.List;


import game.model.enemy.*;



public interface Room {

	RoomType type();
	String description();


	public List<Enemy> enemiesList();
	
}