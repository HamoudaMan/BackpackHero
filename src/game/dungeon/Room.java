package game.dungeon;

import java.util.List;


import game.ennemies.Enemy;



public interface Room {

	RoomType type();
	String description();

	
	
	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList();
	
}
