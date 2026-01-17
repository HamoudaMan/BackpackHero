package game.model.dungeon.state;

import java.util.List;
import java.util.Objects;

import game.model.item.Item;

/**
 * A class to help track the state of an enemyroom 
 * and the after combat items selection
 */
public class EnemyState {
	private boolean cleared ;
	private boolean itemsGenerated;
	private List<Item> items;
	
	public EnemyState() {
		this.cleared = false;
		this.itemsGenerated = false;
		this.items = List.of();//empty lst at first
	}
	
	/**
	 * 
	 * @return true if the room is fully completed
	 */
	public boolean isCleared() {
		return cleared;
	}
	
	/**
	 * 
	 * @return true if items are availble to drang and drop
	 */
	public boolean hasItems() {
		return itemsGenerated && !cleared;
	}
	
	public List<Item> items(){
		return items;
	}
	public void clear() {
		this.cleared = true;
	}
	
	/**
	 * generate items (only once)
	 * @param items
	 */
	public void generateItems(List<Item> items) {
		Objects.requireNonNull(items);
		if(itemsGenerated) {
			throw new IllegalStateException("items already geneerated");
		}
		this.items = List.copyOf(items);
		this.itemsGenerated = true;
	}
	
	public void finishItemsSelection() {
		if(!itemsGenerated) {
			throw new IllegalStateException();
		}
		this.cleared = true ;
	}
}