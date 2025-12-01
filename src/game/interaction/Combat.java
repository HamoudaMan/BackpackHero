package game.interaction;

import java.util.ArrayList;
import java.util.List;

import game.ennemies.Enemy;
import game.hero.Hero;

public class Combat {
	
	public static CombatResult startCombat(Hero hero, List<Enemy> enemiesList) {
		List<Enemy> enemies = new ArrayList<>(enemiesList);
		while(!enemiesList.isEmpty()) {//tant qu'il ya des ennemies 
			var enemy = enemies.get(0);
			
			hero.attack(enemy);//hero attack en premier 
			if(enemy.isDead()) {
				enemiesList.remove(0);
				continue;
			}
			enemy.attack(hero);//tour de l'ennemie
			if(hero.heroDead()) {
				return CombatResult.LOSE;
			}
		}
		return CombatResult.WIN;
	}
}
