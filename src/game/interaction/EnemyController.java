package game.interaction;

import java.util.Objects;

import game.ennemies.Enemy;
import game.hero.Hero;

public class EnemyController {
	public void exe(Enemy enemy, Hero hero) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(enemy);
		enemy.doAction(hero);
	}
}
