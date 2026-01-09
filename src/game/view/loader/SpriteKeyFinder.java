package game.view.loader;

import game.model.item.Curse;
import game.model.item.Item;
import game.model.item.Weapon;

public  class SpriteKeyFinder {
	
	public static String spriteKey(Item item) {
		return switch(item) {
		case Weapon w -> switch(w.name()) {
																				case "Wooden Sword" ->"woodensword";
																				case "Magic Wand" -> "magicwand";
																				default ->"unknown";
																			};
		case Curse c ->"curse";
		default ->"uknown";
		};
	}
}
