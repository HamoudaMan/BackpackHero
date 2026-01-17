package game.model.item;

import java.util.List;
import java.util.Random;

public class EnemyLootGenerator {
	private final Random random = new Random();
	
	/**
	 * generate loot 
	 * @return
	 */
	private Item randomItem() {
		var r = random.nextInt(100);
		if(r< 50) {
			return ItemCatalog.random(ItemCategory.WEAPON);
		}
		if(r< 70) {
			return ItemCatalog.random(ItemCategory.CONSUMABLES);
		}
		if(r< 90) {
			return ItemCatalog.random(ItemCategory.ACCESSORY);
		}
		return ItemCatalog.random(ItemCategory.MAGIC); //add curse
	}
	/**
	 * generate loot after enemyroom is cleared 
	 * @param floorLevel
	 * @return
	 */
	public List<Item> generate(int floorLevel){
		return List.of(randomItem(), randomItem(),randomItem());
	}
	
 
}
