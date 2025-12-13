package game.dungeon.rooms;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.EnemyI;
import game.hero.Hero;

public class CorridorRoom implements Room{

	@Override
	public void enter(Hero hero) {
		Objects.requireNonNull(hero);
		IO.println(description());
		
	}

	@Override
	public void interact(Hero hero, Scanner input) {
		IO.println("don't stop here, there's nothing to see");
		
	}

	@Override
	public RoomType type() {
		return RoomType.CORRIDOR;
	}

	@Override
	public String description() {
		return "a quiet empty room, this is just a corridor";
	}

	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<EnemyI> enemiesList() {
		return List.of();
	}

}
