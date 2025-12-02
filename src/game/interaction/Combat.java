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
			enemy.announceAction();
			
			hero.attack(enemy);//hero attack en premier 
			if(enemy.isDead()) {
				enemiesList.remove(0);//si l'ennemie est mort on le suprr de la liste 
				continue;
			}
			enemy.doAction(hero);//tour de l'ennemie, il execute l'acotion qu'il a annoncé 
			
			if(hero.heroDead()) {
				return CombatResult.LOSE;
			}
		}
		return CombatResult.WIN;
	}
}
