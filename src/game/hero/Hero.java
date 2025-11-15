package game.hero;

import game.ennemies.Enemy;
import game.items.Item;
import game.items.Weapon;
import java.util.ArrayList;

public class Hero {
	
	private String name;
	private int health;
	private int maxHealth;
	private int mana;
	private int energy;
	private int protection;
	private int gold;
	private Weapon weaponEquipped;
	private ArrayList<Item> inventory; //à la phase 1 : 15 cases 3*5
	
	public Hero(String name) {
		this.name = name;
		this.health = 40;
		this.maxHealth = 40;
		this.mana = 10;//valeur pour test (a changer par la suite )
		this.energy = 3;
		this.protection = 0;
		this.gold = 0;
		this.inventory = new ArrayList<>(15);
	}
	public String name() {
		return name;
	}
	public int health() {
		return health;
	}
	public int mana() {
		return mana;
	}
	public int gold() {
		return gold;
	}
	public int energy() {
		return energy;
	}
	public int protection() {
		return protection;
	}
	
	
	public Boolean canAttack(Enemy enemy) {
		return enemy.health() > 0;
	}
	public void attack(Enemy enemy) {
		var damage = 5;
		enemy.takeDamage(damage);
		//a implementer 
	}
	public void defend() {
		//a implementer 
	}
	public void takeDamage(int damage) {//hero prend des degats 
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
}
