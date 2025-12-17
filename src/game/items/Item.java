package game.items;

import game.ennemies.Enemy;
import game.hero.Hero;

//interface de tout ce qui sera dans le backpack
public interface Item {
	String spriteKey();
	String name();
	void use(Hero hero, Enemy enemy);
	boolean canUse(Hero hero);
	int energyCost();
	
	int width();
	int height();
	void rotate();
	String description();
	int turnUsable();
}
