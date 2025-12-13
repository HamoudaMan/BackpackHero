package game.ennemies;

import java.util.Objects;
import java.util.Random;

import game.hero.Hero;
/**
 * a class that creates a new enemy 
 * all enemy stats must be passed as parameters 
 */
public class Enemy {

	private final EnemyType type;
	private final EnemyStats stats;
	private int currentHealth;
	private int protection;
	private Action nextAction;
	private static final Random RANDOM = new Random();
	
	public Enemy( EnemyType type, EnemyStats stats) {
		Objects.requireNonNull(type);
		Objects.requireNonNull(stats);
		
		this.type = type;
		this.stats = stats;
		this.currentHealth = stats.maxHealth();
		this.protection = 0;
		
		//the next action is decided as soon as the enemy is created 
		decideNextAction();

	}


	public void attack(Hero hero) {
		hero.takeDamage(stats.damage());
	}
	
	public void takeDamage(int damage) {
		if(damage < 0) {
			throw new IllegalArgumentException("damage must be positive");
		}
		var effectiveDamage = Math.max(0, damage - protection);
		currentHealth = Math.max(0, currentHealth-effectiveDamage);
		protection = 0;
	}
	
	public void decideNextAction() {
		if(RANDOM.nextBoolean()) {
			nextAction = Action.ATTACK;
		}else {
			nextAction = Action.BLOCK;
		}
		
	}
	/**
	 * do the action that was anounced 
	 * @param hero
	 */
	public void doNextAction(Hero hero) {
		switch(nextAction) {
		case ATTACK -> attack(hero);
		case BLOCK -> protection+= stats.block();
		
		}
		decideNextAction(); // decide the action for the next turn 
	}
	
	public Action nextAction() {
		return nextAction;
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
