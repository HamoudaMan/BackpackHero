package game.dungeon;

import java.util.List;
import java.util.Scanner;

import game.ennemies.EnemyI;
import game.hero.Hero;

public interface Room {
	void enter(Hero hero);
	void interact(Hero hero, Scanner input);
	RoomType type();
	String description();
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<EnemyI> enemiesList();
	
}
