package game.ennemies;

import java.util.Random;
import game.hero.Hero;



public class RatWolf implements Enemy {
	private final  String name;
	private int health;
	private final int maxHealth;
	private final int protection;
	
	public RatWolf() {
		this.name = "Rat-loup";
		this.health = 30;
		this.maxHealth = 30;
		this.protection = 0;
	}
	@Override
	public String name() {
		return name;
	}

	@Override
	public int health() {
		return health;
	}

	@Override
	public int maxHealth() {
		return maxHealth;
	}

	@Override
	public int protection() {
		return protection;
	}
	
	@Override
	public boolean isAlive() {
		return health > 0;	
	}
	
	@Override
	public void attack(Hero hero) {
		hero.takeDamage(10);
	}
	@Override
	public void takeDamage(int damage) {
		var effectiveDamage = damage - protection;
		
		if(effectiveDamage < 0) {//effective damage  doit etre postive ou 0 pas negatif 
			effectiveDamage = 0;
		}	
		health -= effectiveDamage;
		
		if(health < 0) {
			health = 0;// les pv ne peuvent pas etre negatif
		}
		IO.println(name + " subit "+ effectiveDamage + " : pv restant: "+ health + " pv");
	}
	
	@Override 
	public void buffProtection() {
		
	}
	
	@Override 
	public void announceAction() {
		
	}
	
	public void chooseAction() {
		
	}


	
	
}
