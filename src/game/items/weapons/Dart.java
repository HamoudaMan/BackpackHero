package game.items.weapons;

import java.util.Objects;

import game.ennemies.Enemy;
import game.hero.Hero;
import game.items.Item;

public class Dart implements Item, Weapon{
	
	private final String name = "Dart";
	private final int damage = 7;
	private int turnUsable = 100;//une grande valeur pour dire illimié
	//private boolean usable = true;
	private int width = 1;
	private int height = 2;
	private final int energyCost = 1;	
	
	@Override
	public boolean canUse(Hero hero) {
		Objects.requireNonNull(hero);
		return hero.energy()>0;
	}
	
	@Override
	public void use(Hero hero, Enemy enemy) {
		Objects.requireNonNull(hero);
		Objects.requireNonNull(enemy);
		if(!canUse(hero)) {
			IO.println("Not enough enerfy to use "+ name());
			return;
		}
		hero.consumeEnergy(energyCost);
		enemy.takeDamage(damage);
		
	}
	@Override
	public int damage() {
		return damage;
	}
	public int energyCost() {
		return energyCost;
	}

	@Override
	public int turnUsable() {
		return turnUsable;
	}



	@Override
	public String name() {
		return name;
	}

	@Override
	public int width() {
		return width;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return height;
	}

	@Override
	public void rotate() {
		var tmp = height;
		height = width;
		width = tmp;
		
	}

	@Override
	public String spriteKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return" testets  test ";
	}
}
