package game.interaction;

import java.util.List;

import game.ennemies.Enemy;
import game.hero.Hero;

public class Combat {
	
	public CombatResult startCombat(Hero hero, List<Enemy> enemiesList) {
		while(!enemiesList.isEmpty()) {//tant qu'il ya des ennemies 
			hero.attack(enemiesList.getFirst());
			if(enemiesList.getFirst().isDead()) {
				enemiesList.remove(0);
			}else if(!hero.heroDead()) {
				enemiesList.getFirst().attack(hero);
			}else if(hero.heroDead()) {
				return CombatResult.LOSE;
			}
		}
		return CombatResult.WIN;
	}
}
