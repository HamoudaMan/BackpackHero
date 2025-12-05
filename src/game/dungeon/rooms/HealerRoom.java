package game.dungeon.rooms;

import java.util.Objects;
import java.util.Scanner;

import game.dungeon.Room;
import game.dungeon.RoomType;
import game.hero.Hero;

public class HealerRoom implements Room{

	private static final int cost = 5;
	private static final int healAmount = 20;
	
	@Override
	public void enter(Hero hero) {
		// TODO Auto-generated method stub
		Objects.requireNonNull(hero);
		IO.println(description());
		IO.println("You can heal : " +healAmount + " hp for " + cost + " gold");
		IO.println("type y for yes and n for no (y/n)");
	}

	@Override
	public void interact(Hero hero, Scanner input) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(input);
		
		String choice;
		while(true) {//on boucle tant que choice n'est ni y ou n
			choice = input.nextLine().trim().toLowerCase();
			if(choice.equals("y") || choice.equals("n")) {
				break;
			}
			IO.println("invalid choice , please press only y or n");
		}
		if(choice.equals("n")) {
			//choix == non donc pas de heal et on quitte la salle 
			IO.println("You don't want to be heald leave the room");
			return;
		}if (!enoughGold(hero)) {
			//choice == yes donc on heal
			IO.println("Not enough gold , you only have " + hero.gold() + " gold");
			return;
		}
		var healthBefore = hero.health(); // juste pour l'affichage 
		hero.spendGold(cost);
		hero.heal(healAmount);
		
		IO.println("Healer doing his job....");
		IO.println("HP : "+healthBefore +" -> HP : " +hero.health());

	}

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
	

}
