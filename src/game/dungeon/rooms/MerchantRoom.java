package game.dungeon.rooms;

import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.hero.Hero;
import game.items.Item;
import game.items.armor.RoughBuckler;
import game.items.magic.MagicWand;
import game.items.weapons.WoodenSword;

public class MerchantRoom implements Room {
	private final Map<Item, Integer> stock = Map.of(new WoodenSword(), 7,
																										new RoughBuckler(), 8,
																										new MagicWand(), 12);
	@Override
	public void enter(Hero hero) {
		Objects.requireNonNull(hero);
		IO.println(description());
		IO.println("Welcome Hero, take a look at my shop");
		
	}
	@Override
	public void interact(Hero hero, Scanner input) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(input);
		
		while(true) {
			IO.println("your gold -> "+hero.gold());
			
			var i = 1;
			for(var obj : stock.entrySet()) {//afficher les item en stock
				IO.println(i +" - " + obj.getKey().name() + "(price: " +obj.getValue() +")");
				i++;
			}
			IO.println("0 - leave merchant ");
			
			if(!input.hasNextInt()) {
				IO.println("Invalid input, enter a number.");
				input.nextLine();
				continue;
			}
			int choice = input.nextInt();
			input.nextLine();
			
			if(choice == 0) {
				IO.println("Bye bye");
				return ;
			}
			
			if(choice < 1 || choice > stock.size()) {
				IO.println("Invalid choice");
				continue;
			}
			//recuperer l'item et son prix 
			//Item item = stock.keySet().toArray(new Item[0])[choice -1];// je convertit les key de la map en liste pour y acceder avec choice -1 
			var entryItem = stock.entrySet().stream().skip(choice -1).findFirst().orElse(null);
			if(entryItem == null) {
				IO.println("erreur acessing the item ");
			}
			Item item = entryItem.getKey();
			int price= entryItem.getValue();
			
			if(hero.gold() < price) {
				IO.println("not enough gold!");
				continue;
			}
			if(!hero.backPack().add(item)) {
				IO.println("Back PAck is full, cannot add " + item.name());
				continue;
			}
			hero.spendGold(price);
			IO.println("Thank you for your purchase : " +item.name());
			
		}
	
	}

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



}
