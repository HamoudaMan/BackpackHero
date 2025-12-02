package game.items.magic;

import game.ennemies.Enemy;
import game.hero.Hero;
import game.items.Item;

public class MagicWand implements Item{
	
	private final String name = "Magic Wand";
	private final int manaCost = 2;
	private final int damage = 7;
	
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
		return 0;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		
	}
	public void use(Hero hero, Enemy enemy) {
		if(hero.mana() < manaCost()) {
			IO.println("Not enough mana !");
			return;
		}
		hero.consumeMana(manaCost());
		enemy.takeDamage(damage);
		IO.println(name + " caused : "+ damage+" damage to "+ enemy.name());
	}
	
}
