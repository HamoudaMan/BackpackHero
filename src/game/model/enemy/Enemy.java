package game.model.enemy;

import java.util.Objects;
import game.model.hero.Hero;
/**
 * a class that creates a new enemy 
 * all enemy stats must be passed as parameters 
 */
public class Enemy {

	private final EnemyType type;
	private final EnemyStats stats;
	private int currentHealth;
	private int protection;
	private int turn = 0;
	
	public Enemy( EnemyType type) {
		Objects.requireNonNull(type);	
		this.type = type;
		this.stats = type.stats();
		this.currentHealth = stats.maxHealth();
		this.protection = 0;
		

	}

	public void attack(Hero hero) {
		System.out.println("[ENEMY ATTACK] dmg = " + stats.damage());
		hero.takeDamage(stats.damage());
	}
	
	
	public void playTurn(Hero hero) {
		Action action = type.nextAction(turn++);
		 System.out.println("[ENEMY TURN] action = " + action);
		switch(action) {
			case ATTACK -> attack(hero);
			case BLOCK -> protection += stats.block();
			//afiner le heal pour une valuer precise 
			case HEAL ->  currentHealth = Math.min(stats.maxHealth(), currentHealth+stats.block());
			default -> {}
		}
	}

	public void takeDamage(int damage) {
		if(damage < 0) {
			throw new IllegalArgumentException("damage must be positive");
		}
		var effectiveDamage = Math.max(0, damage - protection);
		currentHealth = Math.max(0, currentHealth-effectiveDamage);
		protection = 0;
	}
	


	public boolean isDead() {
		return currentHealth <= 0;
	}
	
	public int currentHealth() {
		return currentHealth;
	}
	
	public EnemyType type() {
		return type;
	}
	public EnemyStats stats() {
		return stats;
	}
	
}
