package game.items.magic;

import java.util.Objects;

import game.ennemies.Enemy;
import game.hero.Hero;
import game.items.Item;

public class MagicWand implements Item{
	
	private final String name = "Magic Wand";
	private final int manaCost = 2;
	private final int damage = 10;
	private int height = 3;
	private int width = 1;
	@Override
	public boolean canUse(Hero hero) {
		// TODO Auto-generated method stub
		Objects.requireNonNull(hero);
		return hero.mana() >= manaCost;
	}
	
	public void use(Hero hero, Enemy enemy) {
		if(hero.mana() < manaCost()) {
			IO.println("Not enough mana !");
			return;
		}
		hero.consumeMana(manaCost());
		enemy.takeDamage(damage);
		IO.println(name + " caused : "+ damage+" damage to "+ enemy.type());
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
	}
	public int manaCost() {
		return manaCost;
	}
	
	public int damage() {
		return damage;
	}

	@Override
	public int width() {
		// TODO Auto-generated method stub
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
	public int energyCost() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String spriteKey() {
		return "magicwand";
	}
	
	
}
