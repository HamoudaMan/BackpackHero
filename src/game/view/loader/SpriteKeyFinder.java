package game.view.loader;

import game.model.item.Consumables;
import game.model.item.Curse;
import game.model.item.Gold;
import game.model.item.Item;
import game.model.item.Magic;
import game.model.item.Shield;
import game.model.item.Weapon;

public  class SpriteKeyFinder {
	
	public static String spriteKey(Item item) {
		return switch(item) {
		case Weapon w -> switch(w.name()) {
																				case "Wooden Sword" ->"woodenSword";
																				case "Magic Wand" -> "magicwand";
																				
																				
																				case "Paladin Sword" ->"paladinSword";
																				
																				default ->"unknown";
																			};
		case Shield s ->  switch(s.name()) {
																					case "Rough Buckler" -> "roughbuckler";
																					case "Boo Shield" -> "booShield";

																					default -> "ukonwn";
																				} ;
		case Consumables c -> "thunaBox";
		case Gold g ->"gold"; 
		case Curse c ->"curse";
		case Magic m -> "manaStone";
		default ->"uknown";
		};
	}
}
