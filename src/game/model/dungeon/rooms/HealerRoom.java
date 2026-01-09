package game.model.dungeon.rooms;


import java.util.List;


import game.model.dungeon.Room;
import game.model.dungeon.RoomType;
import game.model.enemy.*;
import game.model.hero.Hero;

public class HealerRoom implements Room{

	private static final int cost = 5;
	private static final int healAmount = 20;
	
	
	@Override
	public RoomType type() {
		return RoomType.HEALER;
	}
	
	
	private boolean enoughGold(Hero hero) {
		return hero.backPack().getGold() >= cost;
	}
	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Welcome to the healer room , Mr.Heal will take care of you (if you can pay, no charity here)";
	}

	public int cost() {
		return cost;
	}
	public int healAmount() {
		return healAmount;
	}
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList() {
		return List.of();
	}

}