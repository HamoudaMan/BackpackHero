package game.items.weapons;

import game.items.Item;

public interface Weapon extends Item {
	int damage();
	int turnUsable();//tour utilisable restant
	Boolean usable();
	Weapon use();
	
}
