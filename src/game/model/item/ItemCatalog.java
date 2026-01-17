package game.model.item;

import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * catalog of all availble items 
 */
public class ItemCatalog {
	private static final Random r = new Random();
	
	private static final List<Item> weapons = List.of(
			new Weapon("Wooden Sword", 1, 0, 7, new boolean[][] {{true, true}}),
			new Weapon("Paladin Sword", 2, 0, 9, new boolean[][] {{true, true}}),
			new Weapon("Magic Wand", 1, 0, 8, new boolean[][] {{true, true}})
			);
	
	private static final List<Item> consumables = List.of(
			new Gold(10),
			new Consumables("Thuna box", 0, 0, 7, new boolean[][] {{true}})
			);
	
	private static final List<Item> shield = List.of(

			new Shield("Rough Buckler", 0, 0, 7, new boolean[][] {{true}}),
			new Shield("Boo Shield", 0, 0, 5, new boolean[][] {{true, true}})
			);
	private static final List<Item> magic = List.of(
			new Magic("Mana Stone", new boolean[][] {{true}})
			);
			
	/**
	 * pick a random item from a list of items 
	 * @param items
	 * @return
	 */
	private static Item randomOf(List<Item> items) {
		Objects.requireNonNull(items);
		return items.get(r.nextInt(items.size()));
	}
	/**
	 * return a random item from the category picked 
	 * @param category
	 * @return
	 */
	public static Item random(ItemCategory category) {
		Objects.requireNonNull(category);
		return switch(category) {
			case WEAPON ->randomOf(weapons);
			case CONSUMABLES -> randomOf(consumables);
			case SHIELD -> randomOf(shield);
			case MAGIC -> randomOf(magic);
			default -> throw new IllegalArgumentException("error category  " + category);
		};
	}
}
