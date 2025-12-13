package game.dungeon.rooms;

import java.util.List;
import java.util.Map;
import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.Enemy;
import game.items.Item;
import game.items.armor.RoughBuckler;
import game.items.magic.MagicWand;
import game.items.weapons.WoodenSword;

public class MerchantRoom implements Room {
	private final Map<Item, Integer> stock = Map.of(new WoodenSword(), 7,
																										new RoughBuckler(), 8,
																										new MagicWand(), 12);
	
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

	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList() {
		return List.of();
	}


}
