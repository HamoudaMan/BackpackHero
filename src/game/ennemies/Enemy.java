package game.ennemies;

import java.util.Objects;

import game.hero.Hero;
/**
 * a class that creates a new enemy 
 * all enemy stats must be passed as parameters 
 */
public class Enemy {
	private final String name;
	private final int maxHealth;
	private int health;
	private final int damage;
	private final int block;
	/**
	 * 
	 * @param name 
	 * @param maxHealth
	 * @param health
	 * @param damage
	 * @param block
	 */
	public Enemy( String name, int maxHealth, int health, int damage, int block) {
		Objects.requireNonNull(name);
		Objects.requireNonNull(damage);
		Objects.requireNonNull(block);
		Objects.requireNonNull(maxHealth);
		Objects.requireNonNull(health);
		
		if(damage <= 0 || health<=0 || maxHealth<= 0|| block <=0) {
			throw new IllegalArgumentException("the enemy stats must be greater than 0 ");
		}
		if(maxHealth != health) {
			throw new IllegalArgumentException("the maxHealth must be equal to health when creating the enemy ");
		}
		this.name = name;
		this.damage = damage;
		this.block = block;
		this.maxHealth = maxHealth;
		this.health = health;
	}

	public boolean isDead() {
		if( health <= 0) {
			//IO.println("FIN Du combat, le rat loup vient de clamser" );
			return true;
		}
		return false;	
	}

	public Action nextAction() {
		return nextAction;
		
	}
	
	

	public void attack(Hero hero) {
		hero.takeDamage(10);
	}

	
	
}
