package game.ASCII;

import java.util.List;

import game.hero.Hero;
import game.items.Item;

public class PrintTreasure {
	public static void printT(Hero hero, List<Item> treasure) {
		IO.println("========= Treasure Room =========");
		IO.println("You found some items !");
		IO.println("---------------------------------");
		var i = 1;
		for(Item it : treasure) {
			IO.println(i + " - " + it.name());
			i++;
		}
		IO.println("---------------------------------");
		IO.println("Choose an item (1 - " + treasure.size() + "), or press 0 to leave\n");
	}
}
