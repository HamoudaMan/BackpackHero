package game.ennemies;

import java.util.Random;
import game.hero.Hero;



public class RatWolf implements Enemy {
	private final  String name;
	private int health;
	private final int maxHealth;
	private int protection;
	private static Random RANDOM = new Random();
	private Action nextAction;
	
	public RatWolf() {
		this.name = "Rat-loup";
		this.health = 20;
		this.maxHealth = 20;
		this.protection = 0;
		this.nextAction = Action.ATTACK;
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
	public boolean isDead() {
		if( health <= 0) {
			//IO.println("FIN Du combat, le rat loup vient de clamser" );
			return true;
		}
		return false;	
	}
	@Override 
	public Action nextAction() {
		return nextAction;
	}
	
	
	@Override
	public void attack(Hero hero) {
		hero.takeDamage(10);
	}

	
	@Override 
	public void buffProtection() {
		protection += 2 + RANDOM.nextInt(4);
	}
	
	@Override 
	public void announceAction() {
		if( RANDOM.nextBoolean()) {
			nextAction = Action.ATTACK;
		}else {
			nextAction = Action.BLOCK;
		}
		
		IO.println("Next action " + nextAction);
	}
	
	public void doAction(Hero hero) {
		if( nextAction == Action.ATTACK) {
			attack(hero);
		}else if( nextAction == Action.BLOCK ){
			buffProtection();
		}
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
		protection = 0;//on remet la protection a 0 
		IO.println(name + " subit "+ effectiveDamage + " : pv restant: "+ health + " pv");
	}

	@Override
	public void resetProtection() {
		this.protection = 0;
		
	}

	
	
}
