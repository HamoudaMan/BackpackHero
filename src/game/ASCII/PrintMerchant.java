package game.ASCII;

import java.util.Map;

import game.hero.Hero;
import game.items.Item;

public class PrintMerchant {
	public static void printM(Hero hero, Map<Item, Integer> stock) {
		IO.println("========= Merchant Room =========");
		IO.println("Your Gold : " + hero.gold());
		int i = 1;
		IO.println("---------------------------------");
		for(var obj : stock.entrySet()) {
			var item = obj.getKey();
			var price  = obj.getValue();
			IO.println(i + " - " + item.name() + " (price : " + price + ")");
			i++;
		}
		
		IO.println("0 - leave the shop\n");
		
	}
}
