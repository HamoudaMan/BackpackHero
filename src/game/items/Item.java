package game.items;

import game.ennemies.EnemyI;
import game.hero.Hero;

//interface de tout ce qui sera dans le backpack
public interface Item {
	String name();
	void use(Hero hero, EnemyI enemy);
	boolean canUse(Hero hero);
	int energyCost();
	
	int width();
	int height();
	void rotate();
}
