package game.model.enemy;

import java.util.ArrayList;
import java.util.List;
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
		System.out.println("ENEMY ATTACK dmg = " + stats.damage());
		hero.takeDamage(stats.damage());
	}
	
	
	public void playTurn(Hero hero, List<Enemy> enemies) {
		Action action = type.nextAction(turn++);
		switch(action) {
			case ATTACK -> attack(hero);
			case BLOCK -> protection += stats.block();
			//afiner le heal pour une valuer precise 
			case HEAL ->  currentHealth = Math.min(stats.maxHealth(), currentHealth+stats.heal());
			case SUMMON -> summon(enemies);
			default -> {}
		}

	}
	//2 possible cases so we keep another playTurn for comptibility
	public void playTurn(Hero hero ) {
		playTurn(hero, new ArrayList<>());
	}

	/**
	 * a methode were the beeQeen summon lilbee, 
	 * if enough place in the screen the summons 
	 * the new summoned ennemy is added to the enemies list 
	 * @param enemies
	 */
	private void summon(List<Enemy> enemies) {
		if(enemies.size()>=3) {
			IO.println("not enough space to summon all ");
			return;//not enough space 
		}
		if(type == EnemyType.BEE_QUEEN) {
			Enemy summonedBee = EnemyFactory.create(EnemyType.LILBEE);
			enemies.add(summonedBee);
			
			IO.println("Queen bee summon lil bee");
		}
	}

	public void takeDamage(int damage) {
		if(damage < 0) {
			throw new IllegalArgumentException("damage must be positive");
		}
		var effectiveDamage = Math.max(0, damage - protection);
		if( protection - damage >=0) {
			protection = protection - damage;
			effectiveDamage = 0;
		}else {
			effectiveDamage = damage - protection;
			protection = 0;
		}
		currentHealth = Math.max(0, currentHealth-effectiveDamage);
		
	}
	
	public Action nextAction() {
		return type.nextAction(turn);
	}
	public int nextActionValue() {
		return switch(nextAction()) {
			case ATTACK -> stats.damage();
			case BLOCK -> stats.block();
			case HEAL -> stats().heal();
			case SUMMON ->0;
			case CURSE -> 0;
			case PASS -> 0;
		
		
		};
	}

	public void resetProtection() {
		protection = 0;
	}
	public int protection() {
		return protection;
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
