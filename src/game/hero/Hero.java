package game.hero;

import java.util.Objects;

import game.ennemies.Enemy;
import game.items.Item;
import game.items.MagicBackPack;
import game.items.weapons.Weapon;



public class Hero {
	
	private final String name;
	private int health;
	private final int maxHealth;
	private int mana;
	private int energy;//une action est possible que si energy>0
	private int protection;
	private int gold;
	private Weapon weaponEquiped;
	private int exp;
	private MagicBackPack stuff; //à la phase 1 : 15 cases 3*5
	
	public Hero(String name) {
		this.name = name;
		this.health = 40;
		this.maxHealth = 40;
		this.mana = 10;//valeur pour test (a changer par la suite )
		this.energy = 3;
		this.protection = 0;
		this.gold = 0;
		this.exp = 0;
		this.stuff = new MagicBackPack();
	}
	public String name() {
		return name;
	}
	public int health() {
		return health;
	}
	public int maxHealth() {
		return maxHealth;
	}
	public int mana() {
		return mana;
	}
	public int gold() {
		return gold;
	}
	public int exp() {
		return exp;
	}
	public int energy() {
		return energy;
	}
	public int protection() {
		return protection;
	}
	
	
	//----Methode pour les duels----
	
	public Boolean canAttack() {
		return energy > 0 && weaponEquiped != null;
	}
	
	public void attack(Enemy enemy) {
		Objects.requireNonNull(enemy);
		if(!canAttack()) {
			var damage = weaponEquiped.damage();
			enemy.takeDamage(damage);
			energy--;//chaque attaque coute de l'energie
		}
		var damage = 5;
		enemy.takeDamage(damage);
		
	}
	
	public void block() {
		protection += 5;
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
	
	public Boolean heroDead() {
		if( health <= 0) {
			IO.println("FIN DE PARTIE : "+name + " vient de clamser" );
			return true;
		}
		return false;
	}
}
