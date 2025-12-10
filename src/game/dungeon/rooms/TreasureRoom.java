package game.dungeon.rooms;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import game.ASCII.PrintBackPack;
import game.dungeon.Room;
import game.dungeon.RoomType;
import game.ennemies.Enemy;
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

	@Override
	public void enter(Hero hero) {
		Objects.requireNonNull(hero);
		IO.println(description());
		IO.println("Here's the items availble in the treasure :");
		for(var i = 0; i<treasure.size(); i++) {//affichage des items 
			IO.println(i +1 + " - "+treasure.get(i).name());
		}
		//IO.println("Press O to quit without taking any item");
		
	}
	public void interact(Hero hero, Scanner input) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(input);
		
		var choice = -1;
//var choice = scanner.nextInt();
		while (true) {//on boucle tant que le user ne rentre pas de input valide 
			IO.println("Choose items wisely ");
			IO.println("Choose between ? (1, 2, 3, or press 0 to leave)");
			//Scanner scanner = new Scanner(System.in);
			if (!input.hasNextInt()) {//cas ou input n'ets pas un entier 
				IO.println("Invalid entry, please enter a number ");
				input.nextLine();
				continue;
			}
			choice = input.nextInt();
			input.nextLine();
			
			if (choice == 0) {
				IO.println("you're leaving everything behind aight");
				return;
			}
			
			if (choice < 1 || choice > treasure.size()) {
				IO.println("Invalid choice ");
				continue;
				
			} 
			break;// e-input valide donc on sort de la vboucle
		}
		
		Item chosen = treasure.get(choice - 1);
		/*pour debug
		System.out.println("Before add:");
		PrintBackPack.PrintMagicBackPack(hero.backPack().stuff());
		boolean added = hero.backPack().add(chosen);
		*/
		
		
	
		if(!hero.backPack().add(chosen)) {
			IO.println("BackPack full, impossible to add "+ chosen.name());
		}else {
			IO.println(chosen.name() + "have been added to your Back Pack");
			/*pur debug
			System.out.println("After add:");
			PrintBackPack.PrintMagicBackPack(hero.backPack().stuff());
*/
		}
		IO.println(hero.backPack());
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
