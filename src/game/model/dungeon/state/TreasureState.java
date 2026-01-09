package game.model.dungeon.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import game.model.item.Item;

/**
 * to follow the state of the treasure room 
 
 */
public class TreasureState {
	private boolean opened;
	private List<Item> loot;
	
	public TreasureState() {
		this.opened = false;
		this.loot = List.of();//empty chest untill while it not opened yet 
		
	}
	
	public boolean isOpened() {
		return opened;
	}
	
	public void open(List<Item> treasure) {
		Objects.requireNonNull(treasure);
		if(opened) {
			return;
		}
		opened = true;
		this.loot = List.copyOf(treasure);//copy
	}
	
	/**
	 * removes the item that the user chose from loot 
	 * the immuatble initial list of loot is transformed into a mutable list 
	 * we remove the item from the new list 
	 * then we set this.loot to the new list without the item removed 
	 * @param item
	 */
	public void take(Item item) {
		Objects.requireNonNull(item);
		if(!loot.contains(item)) {
			return;
		}
		var newLoot = new ArrayList<Item>(loot);
		newLoot.remove(item);
		loot = List.copyOf(newLoot);//immutable list 
		
	}
	public List<Item> loot(){
		return loot;
	}
	
}