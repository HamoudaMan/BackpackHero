package game.dungeon.rooms;

import java.util.List;


import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.Enemy;
import game.hero.Hero;

public class HealerRoom implements Room{

	private static final int cost = 5;
	private static final int healAmount = 20;
	
	

	private boolean enoughGold(Hero hero) {
		return hero.gold() >= cost;
	}
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Welcome to the healer room , Mr.Heal will take care of you (if you can pay, no charity here)";
	}
	@Override
	public RoomType type() {
		// TODO Auto-generated method stub
		return RoomType.HEALER;
	}
	

	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList() {
		return List.of();
	}
}
