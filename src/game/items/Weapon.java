package game.items;

public interface Weapon extends Stuff {
	int damage();
	int turnUsable();//tour utilisable restant
	Boolean usable();
	
}
