package game.model.dungeon.rooms;

import java.util.List;
import java.util.Map;
import game.model.dungeon.Room;
import game.model.dungeon.RoomType;
import game.model.enemy.*;
import game.model.item.Item;
import game.model.item.Weapon;


public class MerchantRoom implements Room {
	private final Map<Item, Integer> stock = Map.of(new Weapon("WoodenSword", 1, 0, 7,new boolean[][]{{true, true}} ) , 7,
																									new Weapon("RoughBuckler", 1, 0, 7,new boolean[][]{{true, true}} ), 8
																									);
	
	@Override
	public RoomType type() {
		// TODO Auto-generated method stub
		return RoomType.MERCHANT;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Welcome to the merchant Room ";
	}

	public Map<Item, Integer> stock(){
		return stock;
	}
	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList() {
		return List.of();
	}



}