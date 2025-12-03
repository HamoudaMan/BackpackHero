package game.dungeon.rooms;

import java.util.List;
import java.util.Scanner;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.hero.Hero;
import game.items.*;
import game.items.armor.RoughBuckler;
import game.items.magic.MagicWand;
import game.items.weapons.WoodenSword;


public class TreasureRoom implements Room{
	
	private final List<Item> treasure;
	
	
	public TreasureRoom() {
		this.treasure = List.of(new RoughBuckler(), new MagicWand(), new WoodenSword());
	}

	public void interactWithRoom(Hero hero) {
		IO.println("Choose items wisely from this treasure ");
		IO.println("Choose between ? (1, 2, 3, or press 0 to leave)");
		Scanner scanner = new Scanner(System.in);
		var choice = scanner.nextInt();
		if( choice < 1 || choice > treasure.size()) {
			throw new IllegalArgumentException("nop, choose only a valid item");
		}
		if(choice == 0) {
			IO.println("you're leaving everything behind aight");
		}
		Item chosen = treasure.get(choice - 1);
	
		if(!hero.backPack().add(chosen)) {
			IO.println("BackPack full");
		}else {
			IO.println("You chose " +chosen.name());
		}
	}

	
	@Override
	public void enter(Hero hero) {
		IO.println(description());
		for(Item obj :treasure){
			IO.println(obj.name());
		}
		
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

}
