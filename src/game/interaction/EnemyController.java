package game.interaction;

import java.util.Objects;

import game.ennemies.EnemyI;
import game.hero.Hero;

public class EnemyController {
	public void exe(EnemyI enemy, Hero hero) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(enemy);
		enemy.doAction(hero);
	}
}
