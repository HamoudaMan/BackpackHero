package game.dungeon.rooms;

import java.util.List;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.Enemy;
import game.items.*;
import game.items.armor.RoughBuckler;
import game.items.magic.MagicWand;
import game.items.weapons.WoodenSword;


public class TreasureRoom implements Room{
	
	private final List<Item> treasure;
	
	
	public TreasureRoom() {
		this.treasure = List.of(new RoughBuckler(), new MagicWand(), new WoodenSword());
	}

	
	public List<Item> treasure(){
		return treasure;
	}
	@Override
	public RoomType type() {
		// TODO Auto-generated method stub
		return RoomType.TREASURE;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "Welcome to the treasure room, don't be shy stuff yourself !";
	}
	
	//methode juste pour le ZenCOntroller ne pas y faire attention 
	public List<Enemy> enemiesList() {
		return List.of();
	}

}
