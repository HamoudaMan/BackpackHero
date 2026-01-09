package game.controller;

import game.model.enemy.Enemy;
import game.model.hero.Hero;
import game.model.item.Weapon;

public class ItemUseController {
	public static void useWeapon(Hero hero, Enemy enemy, Weapon weapon) {
		hero.energy().consumeEnergy(weapon.energyCost());
		enemy.takeDamage(weapon.damage());
	}
	//pareil pour les autre type d'item 
}
