package game.interaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import game.ennemies.Enemy;
import game.hero.Hero;
import game.items.Item;
import game.items.consumables.Consumables;

public class ItemManager {
	private final UICombat ui;
	private final Scanner input;
	
	public ItemManager(UICombat ui, Scanner input) {
		this.ui = ui;
		this.input = input;
		
	}
	private void useItem(Hero hero, Enemy enemy) {
		Objects.requireNonNull(hero);// on verifie quand meme quand c'est en private ? a verifier 
		Objects.requireNonNull(enemy);
		
		List<Item> items = extractItems(hero.backPack().stuff());//on prend pas les doublons 
		if(items.isEmpty()) {
			IO.println("back PAck empty");
			return;
		}
		
		for(var i = 0; i<items.size(); i++) {
			IO.println(i+ 1 +" - "+ items.get(i).name());
		}
		IO.println("0 -cancel");
		var choice = Integer.parseInt(input.nextLine().trim());
		if(choice == 0) {
			return;
		}
		
		Item currentItem = items.get( choice-1);
		if(!currentItem.canUse(hero)) {
			IO.println("Connot use item");
			return;
		}
		
		if(currentItem instanceof Consumables cons) {
			cons.consume();
			if(cons.isConsumed()) {
				hero.removeFromBackPack(currentItem);
			}
	
		}
	}
	
	private List<Item> extractItems(Item[][] items){//comme ca y apas de doublons dans les items qu'on affichere 
		List<Item> list = new ArrayList<>();
		for(Item[] r :items) {
			for(Item it : r) {
				if( it !=null && !list.contains(it)) {
					list.add(it);
				}

			}
		}
		return list;
	}
	public void heroTurn(Hero hero, Enemy enemy) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(enemy);
		
		boolean continueTurn = true;
		while(continueTurn) {
			var choice = ui.askAction();//entier
			switch(choice) {
			case 1 -> useItem(hero, enemy);
			case 2 ->continueTurn = false;
			default ->IO.println("Invalid choice");
			}
			
		}
	}
}
