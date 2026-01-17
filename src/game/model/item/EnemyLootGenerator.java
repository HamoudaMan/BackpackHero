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
		if(r< 25) {
			return ItemCatalog.random(ItemCategory.MAGIC);
		}
		if(r< 50) {
			return ItemCatalog.random(ItemCategory.WEAPON);
		}
		if(r< 70) {
			return ItemCatalog.random(ItemCategory.SHIELD);
		}
		if(r< 90) {
			return ItemCatalog.random(ItemCategory.CONSUMABLES);
		}
		return ItemCatalog.random(ItemCategory.CONSUMABLES); //add curse
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
